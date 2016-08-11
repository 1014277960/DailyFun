package com.wulingpeng.havefun.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by wulinpeng on 16/8/2.
 * 绑定ButterKnife
 */
public abstract class BaseFragment extends Fragment {

    protected int layoutId;

    private View rootView;

    public Realm mRealm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initLayoutId();
        rootView = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, rootView);
        mRealm = Realm.getDefaultInstance();
        initViews();
        return rootView;
    }

    protected abstract void initLayoutId();

    protected abstract void initViews();

}
