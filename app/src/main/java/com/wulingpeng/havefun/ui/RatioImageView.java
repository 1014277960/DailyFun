package com.wulingpeng.havefun.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 根据原图长宽比例改变大小
 */
public class RatioImageView extends ImageView {

    private int originalWidth;

    private int originalHeight;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOriginalSize(int originalWidth, int originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
    }

    /**
     * 由于用于两列的瀑布流，那么父布局给的width肯定是一半，我们就用这个width
     * 并且根据width通过原来的比例等比例算出改变后的height
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height;
        if (originalHeight > 0 && originalWidth > 0) {
            float ratio = (float) originalWidth / (float) originalHeight;
            height = (int) (width / ratio);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
