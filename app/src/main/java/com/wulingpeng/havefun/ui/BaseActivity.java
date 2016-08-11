package com.wulingpeng.havefun.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.wulingpeng.havefun.R;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by wulinpeng on 16/8/2.
 * 作为基类，绑定ButterKnife，实例Realm
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected int layoutId;

    protected Toolbar mToolbar;

    public Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutId();
        initViews();
    }

    protected abstract void initLayoutId();

    protected void initViews() {
        setContentView(layoutId);
        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();
        initAppBar();
    }

    public void initAppBar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();
    }
}
