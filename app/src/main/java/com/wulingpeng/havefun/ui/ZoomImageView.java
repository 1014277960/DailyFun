package com.wulingpeng.havefun.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 可滑动缩放的View，且处理了滑动冲突
 */
public class ZoomImageView extends View {

    private Bitmap sourceBitmap;

    private int width, height;

    private Matrix matrix;

    public static final int STATUS_INIT = 1;

    /**
     * 图片放大状态常量
     */
    public static final int STATUS_ZOOM_OUT = 2;

    /**
     * 图片缩小状态常量
     */
    public static final int STATUS_ZOOM_IN = 3;

    /**
     * 图片拖动状态常量
     */
    public static final int STATUS_MOVE = 4;

    private int currentState;

    /**
     * 总缩放比例
     */
    private float totalRatio;

    private float initRatio;

    /**
     * 由缩放产生的缩放比例，通过这个计算缩放后的位移
     */
    private float scaleRatio;

    private float totalTranslateX;

    private float totalTranslateY;

    private int currentBitmapWidth;

    private int currentBitmapHeight;

    private double lastFingersDistance;

    private float centerX;

    private float centerY;

    private float lastX = -1;

    private float lastY = -1;

    private float moveDisX;

    private float moveDisY;

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        init();
    }

    private void init() {
        matrix = new Matrix();
        currentState = STATUS_INIT;
    }

    public void setImageBitmap(Bitmap bitmap) {
        sourceBitmap = bitmap;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            // 分别获取到ZoomImageView的宽度和高度
            width = getWidth();
            height = getHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        // 锁定事件，不允许父控件拦截
        boolean result = true;
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    lastFingersDistance = getFingersDistance(event);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 1) {
                    // 滑动
                    float x = event.getX();
                    float y = event.getY();
                    if (lastX == -1 && lastY == -1) {
                        lastX = x;
                        lastY = y;
                    }
                    moveDisX = x - lastX;
                    moveDisY = y - lastY;
                    currentState = STATUS_MOVE;
                    invalidate();
                    lastX = x;
                    lastY = y;
                } else {
                    // 缩放状态
                    calculateCenterPoint(event);
                    double fingerDis = getFingersDistance(event);
                    if (fingerDis > lastFingersDistance) {
                        currentState = STATUS_ZOOM_OUT;
                    } else {
                        currentState = STATUS_ZOOM_IN;
                    }
                    // 没有超过临界，还可以缩放
                    if ((currentState == STATUS_ZOOM_OUT && totalRatio < 4.0f * initRatio)
                            || (currentState == STATUS_ZOOM_IN && totalRatio > initRatio)) {
                        scaleRatio = (float) (fingerDis / lastFingersDistance);
                        totalRatio *= scaleRatio;
                    }
                    // 缩放后超出临界取临界值
                    if (totalRatio > 4.0f * initRatio) {
                        totalRatio = 4.0f * initRatio;
                    } else if (totalRatio < initRatio) {
                        totalRatio = initRatio;
                    }
                    invalidate();
                    lastFingersDistance = fingerDis;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                lastX = -1;
                lastY = -1;
                break;
        }
        return result;
    }

    private double getFingersDistance(MotionEvent event) {
        float x = Math.abs(event.getX(0) - event.getX(1));
        float y = Math.abs(event.getY(0) - event.getY(1));
        return Math.sqrt(x * x + y * y);
    }

    private void calculateCenterPoint(MotionEvent event) {
        centerX = (event.getX(0) + event.getX(1)) / 2;
        centerY = (event.getY(0) + event.getY(1)) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (sourceBitmap != null) {
            switch (currentState) {
                case STATUS_INIT:
                    initBitmap(canvas);
                    break;
                case STATUS_MOVE:
                    move(canvas);
                    break;
                case STATUS_ZOOM_IN:
                case STATUS_ZOOM_OUT:
                    zoom(canvas);
                    break;
            }
        }
    }

    private void initBitmap(Canvas canvas) {
        matrix.reset();
        int bitmapWidth = sourceBitmap.getWidth();
        int bitmapHeight = sourceBitmap.getHeight();
        // 默认为宽度对齐的情况
        float ratio = width * 1.0f / bitmapWidth;
        // 若宽度对齐时高度超过，就高度对齐
        if (bitmapHeight * ratio > height) {
            ratio = height * 1.0f / bitmapHeight;
            totalTranslateX = (width - bitmapWidth * ratio) / 2;
            currentBitmapWidth = (int) (bitmapWidth * ratio);
            currentBitmapHeight = (int) (bitmapHeight * ratio);
        } else {
            totalTranslateY = (height - bitmapHeight * ratio) / 2;
            currentBitmapWidth = (int) (bitmapWidth * ratio);
            currentBitmapHeight = (int) (bitmapHeight * ratio);
        }
        totalRatio = initRatio = ratio;
        matrix.postScale(initRatio, initRatio);
        matrix.postTranslate(totalTranslateX, totalTranslateY);
        canvas.drawBitmap(sourceBitmap, matrix, null);
    }

    private void move(Canvas canvas) {
        matrix.reset();
        // 如果到达边界就允许父布局拦截事件，因为我们没法滑动了，就让父布局滑动
        if ((moveDisX > 0 && totalTranslateX >= 0)
                || (moveDisX < 0 && (currentBitmapWidth + totalTranslateX) <= width)
                || (moveDisY > 0 && totalTranslateY >= 0)
                || (moveDisY < 0 && (currentBitmapHeight + totalTranslateY) <= height)) {
            getParent().requestDisallowInterceptTouchEvent(false);
        } else {
            totalTranslateX += moveDisX;
            totalTranslateY += moveDisY;
        }
        matrix.postScale(totalRatio, totalRatio);
        matrix.postTranslate(totalTranslateX, totalTranslateY);
        canvas.drawBitmap(sourceBitmap, matrix, null);
    }

    private void zoom(Canvas canvas) {
        matrix.reset();
        matrix.postScale(totalRatio, totalRatio);
        // 以手指中心缩放
        float translateX = totalTranslateX;
        float translateY = totalTranslateY;
        // 如果不能缩放了，当然就不会有由缩放产生的位移
        if (totalRatio < 4.0f * initRatio && totalRatio > initRatio) {
            // 这个计算有点复杂，以X为例
            // x方向上缩放的长度为centerX - totalTranslateX，缩放后的长度为(centerX - totalTranslateX) * scaleRatio
            // 这样中点减去图片缩放后的长度就是空白的也就是偏移量了
            translateX = centerX - (centerX - totalTranslateX) * scaleRatio;
            translateY = centerY - (centerY - totalTranslateY) * scaleRatio;
        }
        totalTranslateX = translateX;
        totalTranslateY = translateY;
        matrix.postTranslate(totalTranslateX, totalTranslateY);
        currentBitmapWidth = (int) (sourceBitmap.getWidth() * totalRatio);
        currentBitmapHeight = (int) (sourceBitmap.getHeight() * totalRatio);
        canvas.drawBitmap(sourceBitmap, matrix, null);
    }

}
















