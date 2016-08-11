package com.wulingpeng.havefun.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.wulingpeng.havefun.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wulinpeng on 16/8/11.
 */
public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    public ViewPager viewPager;

    private List<String> urls;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 允许过渡动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // 全屏
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        urls = getIntent().getStringArrayListExtra("urls");
        index = getIntent().getIntExtra("index", 0);
        initPager();
    }

    private void initPager() {
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(index);
    }

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public Fragment getItem(int position) {
            return DetailFragment.newInstance(urls.get(position));
        }
    }

}















