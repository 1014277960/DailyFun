package com.wulingpeng.havefun.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wulingpeng.havefun.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wulinpeng on 16/8/12.
 */
public class UrlActivity extends AppCompatActivity {

    @BindView(R.id.web_view)
    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        ButterKnife.bind(this);
        initViews();
    }

    protected void initViews() {
        String url = getIntent().getStringExtra("url");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
