package com.wulingpeng.havefun.mvp.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.wulingpeng.havefun.R;
import com.wulingpeng.havefun.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 两个带TabLayout的Fragment，都用这一个
 */
public class TabFragment extends BaseFragment {

    public static final int TYPE_KNOWLEDGE = 0;

    public static final int TYPE_BEAUTY = 1;

    public static final String[] titles = new String[] {"Gank", "Breast", "Butt", "Leg", "Rank", "Silk"};

    @BindView(R.id.tabs)
    public TabLayout tabs;

    @BindView(R.id.pager)
    public ViewPager viewPager;

    private int type;

    private List<RecyclerFragment> fragments;

    public static TabFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.fragment_tab;
    }

    @Override
    protected void initViews() {
        fragments = new ArrayList<RecyclerFragment>();
        type = getArguments().getInt("type");
        if (type == TYPE_BEAUTY) {
            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_GANK));
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_BREAST));
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_BUTT));
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_LEG));
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_RANK));
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_SILK));
        } else {

        }
        viewPager.setAdapter(new TabAdapter(getChildFragmentManager()));
        tabs.setupWithViewPager(viewPager);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            // 重复点击标签回到顶端
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                fragments.get(tab.getPosition()).recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}