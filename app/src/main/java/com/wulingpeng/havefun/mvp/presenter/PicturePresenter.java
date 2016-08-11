package com.wulingpeng.havefun.mvp.presenter;

import com.wulingpeng.havefun.mvp.model.Douban;
import com.wulingpeng.havefun.mvp.model.Gank;
import com.wulingpeng.havefun.mvp.view.PictureFragment;
import com.wulingpeng.havefun.net.API;
import com.wulingpeng.havefun.net.callback.DoubanCallback;
import com.wulingpeng.havefun.net.callback.GankCallback;
import com.wulingpeng.havefun.utils.DBUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.List;

import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 与PictureFragment相互作用的Presenter
 */
public class PicturePresenter implements PicPresenterInterface {

    private PictureFragment rootView;

    private Realm realm;

    public PicturePresenter(PictureFragment fragment) {
        rootView = fragment;
        realm = rootView.mRealm;
    }

    /**
     * 通过自定义的Callback把得到的json转换为Gank，将结果导入数据库，最后调用getGankFromDB
     * @param count
     * @param page
     * @param objects
     */
    @Override
    public void getGankFromNet(int count, int page, final List<Gank> objects) {

        rootView.setRefreshState(true);

        OkHttpUtils.get().url(API.GANK + "/" + count + "/" + page).build().execute(new GankCallback() {

            @Override
            public List<Gank> parseNetworkResponse(Response response) throws Exception {
                fragment = rootView;
                return super.parseNetworkResponse(response);
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
            }

            @Override
            public void onResponse(List<Gank> response) {
                super.onResponse(response);
                DBUtil.saveGanks(response, realm);
                getGankFromDB(objects);
            }
        });
    }

    /**
     * 清除所有数据，然后直接从数据库得到所有的数据，有点暴力
     * @param objects
     */
    @Override
    public void getGankFromDB(List<Gank> objects) {
        DBUtil.getGanks(objects, realm);
        rootView.setRefreshState(false);
        rootView.notifyDataChanged();
    }

    @Override
    public void getDoubanFromNet(int page, final List<Douban> objects, String url, final int type) {
        rootView.setRefreshState(true);

        OkHttpUtils.get().url(url + page).build().execute(new DoubanCallback() {

            @Override
            public List<Douban> parseNetworkResponse(Response response) throws Exception {
                fragment = rootView;
                t = type;
                return super.parseNetworkResponse(response);
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
            }

            @Override
            public void onResponse(List<Douban> response) {
                super.onResponse(response);
                DBUtil.saveDoubans(response, realm);
                getDoubanFromDB(objects, type);
            }
        });
    }

    @Override
    public void getDoubanFromDB(List<Douban> objects, int type) {

        DBUtil.getDoubans(objects, realm, type);
        rootView.setRefreshState(false);
        rootView.notifyDataChanged();
    }


}
