package com.wulingpeng.havefun.mvp.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.wulingpeng.havefun.mvp.adapter.ZhihuListAdapter;
import com.wulingpeng.havefun.mvp.model.Zhihu;
import com.wulingpeng.havefun.mvp.presenter.ZhihuPresenter;
import com.wulingpeng.havefun.ui.UrlActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 知乎日报
 */
public class ZhihuFragment extends RecyclerFragment implements ZhihuViewInterface, ZhihuListAdapter.OnItemClickListener{

    private List<Zhihu> items;

    private List<Zhihu> header;

    private ZhihuListAdapter adapter;

    private ZhihuPresenter presenter;

    public static ZhihuFragment newInstance() {
        return new ZhihuFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        initRecycler();
        presenter = new ZhihuPresenter(this);
        initData();
    }

    private void initRecycler() {
        items = new ArrayList<Zhihu>();
        header = new ArrayList<Zhihu>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ZhihuListAdapter(getContext(), items, header);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        setRefreshState(true);
        presenter.getZhihuData(header, items);
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
    public void onRefresh() {
        initData();
    }

    @Override
    public void onItemClick(String url) {
        Intent intent = new Intent(getActivity(), UrlActivity.class);
        intent.putExtra("url", url);
        getActivity().startActivity(intent);
    }
}
