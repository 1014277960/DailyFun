package com.wulingpeng.havefun.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wulingpeng.havefun.MainActivity;
import com.wulingpeng.havefun.R;
import com.wulingpeng.havefun.net.API;
import com.wulingpeng.havefun.utils.DateUtil;
import com.wulingpeng.havefun.utils.SPUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by wulinpeng on 16/8/2.
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash)
    public ImageView mSplash;

    private String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        // 全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initSplash();
    }

    private void initSplash() {
        today = DateUtil.parseStandardDate(new Date());
        // 加载splash
        loadSplash();
        // 数据过期，更新date和对应的url
        if (!today.equals(SPUtil.getString("date"))) {
            updateUrlAndDate();
        }
    }

    /**
     * 通过本地的url得到图片，如果本地没有就网络获取
     */
    private void loadSplash() {
        String url = SPUtil.getString("splash");
        if (url.equals("")) {
            Glide.with(SplashActivity.this).load(R.drawable.splash).animate(R.anim.splash_anim).into(mSplash);
        } else {
            Glide.with(SplashActivity.this).load(url).animate(R.anim.splash_anim)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(mSplash);
        }
        mSplash.postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
            }
        }, 2000);
    }

    /**
     * 更新数据
     */
    private void updateUrlAndDate() {
        OkHttpUtils.get().url(API.SPLASH).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String url = object.getString("img");
                    SPUtil.save("splash", url);
                    SPUtil.save("date", today);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startApp() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 让返回键失效
     */
    @Override
    public void onBackPressed() {
    }
}