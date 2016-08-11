package com.wulingpeng.havefun.mvp.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.wulingpeng.havefun.R;
import com.wulingpeng.havefun.ui.BaseFragment;

import butterknife.BindView;

/**
 * SwipeRefreshLayout包含RecyclerView的布局
 */
public abstract class RecyclerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler)
    public RecyclerView recyclerView;

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.recycler_layout;
    }

    @Override
    protected void initViews() {
        recyclerView.setHasFixedSize(true);
        refreshLayout.setOnRefreshListener(this);
    }
}
