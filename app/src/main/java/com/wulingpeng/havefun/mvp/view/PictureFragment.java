package com.wulingpeng.havefun.mvp.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;

import com.wulingpeng.havefun.R;
import com.wulingpeng.havefun.mvp.adapter.PictureListAdapter;
import com.wulingpeng.havefun.mvp.model.Douban;
import com.wulingpeng.havefun.mvp.model.Gank;
import com.wulingpeng.havefun.mvp.presenter.PicturePresenter;
import com.wulingpeng.havefun.net.API;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.RealmObject;

/**
 * MVP中代表View
 */
public class PictureFragment extends RecyclerFragment implements PictureViewInterface {

    public static final int TYPE_GANK = 0;

    public static final int TYPE_BREAST = 1;

    public static final int TYPE_BUTT = 2;

    public static final int TYPE_SILK = 3;

    public static final int TYPE_LEG = 4;

    public static final int TYPE_RANK = 5;

    private String currentUrl;

    public static final int GANK_COUNT = 5;

    public static final int BEAUTY_COUNT = 20;

    private int page = 1;

    public int type;

    private int lastCount;

    private PicturePresenter mPresenter;

    private List<? extends RealmObject> mData;

    private PictureListAdapter mAdapter;

    public static PictureFragment newInstance(int type) {
        PictureFragment pictureFragment = new PictureFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        pictureFragment.setArguments(bundle);
        return pictureFragment;
    }

    @Override
    protected void initViews() {
        super.initViews();
        type = getArguments().getInt("type");
        initUrl();

        mPresenter = new PicturePresenter(this);
        mData = new ArrayList<>();

        mAdapter = new PictureListAdapter(getContext(), mData);
        final StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 由于是瀑布流，我们有两列，所以可以得到两列的最大的出现的位置
                    int[] positions = lm.findLastVisibleItemPositions(null);
                    // 得到两者较大值，如果＋2大于全部数量，就说明当前到达最底端，需要刷新
                    int lastPosition = Math.max(positions[0], positions[1]);
                    if (lastPosition + 2 > mAdapter.getItemCount()) {
                        if (type == TYPE_GANK) {
                            // 每次从网络获取都要page++，保证下一次刷新都是新数据
                            mPresenter.getGankFromNet(GANK_COUNT, page++, (List<Gank>) mData);
                        } else {
                            mPresenter.getDoubanFromNet(page++, (List<Douban>)mData, currentUrl, type);
                        }
                    }
                }
            }
        });

        if (type == TYPE_GANK) {
            // 第一次进入，手动刷新
            mPresenter.getGankFromDB((List<Gank>)mData);
            mPresenter.getGankFromNet(GANK_COUNT, page++, (List<Gank>)mData);
        } else {
            mPresenter.getDoubanFromDB((List<Douban>)mData, type);
            mPresenter.getDoubanFromNet(page++, (List<Douban>) mData, currentUrl, type);
        }

    }

    /**
     *  如果是beauty的那就确定url以便使用
     */
    private void initUrl() {
        switch (type) {
            case TYPE_BREAST:
                currentUrl = API.BREAST;
                break;
            case TYPE_BUTT:
                currentUrl = API.BUTT;
                break;
            case TYPE_LEG:
                currentUrl = API.LEG;
                break;
            case TYPE_RANK:
                currentUrl = API.RANK;
                break;
            case TYPE_SILK:
                currentUrl = API.SILK;
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (type == TYPE_GANK) {
            mPresenter.getGankFromNet(GANK_COUNT, page++, (List<Gank>) mData);
        } else {
            mPresenter.getDoubanFromNet(page++, (List<Douban>) mData, currentUrl, type);
        }
    }

    @Override
    public void notifyDataChanged() {
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 这是由Presenter调用的，当开始从网络得到数据的时候设为true并记录当前数据量，加载完了就false
     * 如果下载完了也加载完了数据量还是不变，就一直加载
     * 原因有上一次使用的时候加载了6页数据，重新开始的话，page从1开始，如果网页数据没有更新
     * 那么我们下载6次也就是6页本地数据库都是不变的，一直第七次刷新才会有所改变
     * @param state
     */
    @Override
    public void setRefreshState(boolean state) {
        if (state) {
            lastCount = mAdapter.getItemCount();
            refreshLayout.setRefreshing(true);
        } else {
            if (lastCount == mAdapter.getItemCount()) {
                if (type == TYPE_GANK) {
                    mPresenter.getGankFromNet(GANK_COUNT, page++, (List<Gank>) mData);
                } else {
                    mPresenter.getDoubanFromNet(page++, (List<Douban>) mData, currentUrl, type);
                }
            } else {
                refreshLayout.setRefreshing(false);
            }
        }
        Log.d("Debug", mAdapter.getItemCount() + " " + page);
    }
}
