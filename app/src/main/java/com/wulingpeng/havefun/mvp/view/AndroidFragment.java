package com.wulingpeng.havefun.mvp.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;

import com.wulingpeng.havefun.mvp.adapter.AndroidListAdapter;
import com.wulingpeng.havefun.mvp.adapter.ZhihuListAdapter;
import com.wulingpeng.havefun.mvp.model.Android;
import com.wulingpeng.havefun.mvp.model.Zhihu;
import com.wulingpeng.havefun.mvp.presenter.AndroidPresenter;
import com.wulingpeng.havefun.mvp.presenter.ZhihuPresenter;
import com.wulingpeng.havefun.ui.BaseFragment;
import com.wulingpeng.havefun.ui.UrlActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wulinpeng on 16/8/13.
 */
public class AndroidFragment extends RecyclerFragment implements AndroidViewInterface, AndroidListAdapter.OnItemClickListener {

    private List<Android> data;

    private AndroidPresenter presenter;

    private AndroidListAdapter adapter;

    public static AndroidFragment newInstance() {
        return new AndroidFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        initRecycler();
        presenter = new AndroidPresenter(this);
        initData();
    }

    private void initRecycler() {
        data = new ArrayList<Android>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AndroidListAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void initData() {
        setRefreshState(true);
        presenter.getData(data);
        setRefreshState(true);
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setRefreshState(boolean state) {
        refreshLayout.setRefreshing(state);
    }

    @Override
    public void onItemClick(String url) {
        Intent intent = new Intent(getActivity(), UrlActivity.class);
        intent.putExtra("url", url);
        getActivity().startActivity(intent);
    }
}
