package com.wulingpeng.havefun;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wulingpeng.havefun.mvp.view.TabFragment;
import com.wulingpeng.havefun.net.API;
import com.wulingpeng.havefun.ui.AboutActivity;
import com.wulingpeng.havefun.ui.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    public NavigationView mNavView;

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawer;

    private TabFragment tabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDrawer();
        setupNavigationView();
        initFragment();
    }

    private void initFragment() {
        tabFragment = TabFragment.newInstance(TabFragment.TYPE_BEAUTY);
        replaceFragment(tabFragment);
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.activity_main;
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, 0, 0);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupNavigationView() {
        mNavView.setNavigationItemSelectedListener(this);
        // 刚开始设置第一个选中
        mNavView.getMenu().getItem(0).setChecked(true);

        ImageView header = (ImageView) mNavView.getHeaderView(0).findViewById(R.id.head_image);
        Glide.with(this).load(API.DRAWER_HEADER).
                diskCacheStrategy(DiskCacheStrategy.ALL).into(header);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
