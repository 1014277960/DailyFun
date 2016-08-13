package com.wulingpeng.havefun.mvp.presenter;

import com.wulingpeng.havefun.mvp.adapter.AndroidListAdapter;
import com.wulingpeng.havefun.mvp.model.Android;
import com.wulingpeng.havefun.mvp.view.AndroidFragment;
import com.wulingpeng.havefun.net.API;
import com.wulingpeng.havefun.net.callback.AndroidCallback;
import com.wulingpeng.havefun.utils.DBUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import io.realm.Realm;
import okhttp3.Call;

/**
 * Created by wulinpeng on 16/8/13.
 */
public class AndroidPresenter implements AndroidPresenterInterface {

    private AndroidFragment rootView;

    private Realm realm;

    public AndroidPresenter(AndroidFragment fragment) {
        rootView = fragment;
        realm = rootView.mRealm;
    }

    @Override
    public void getData(final List<Android> data) {
        OkHttpUtils.get().url(API.ANDROID + "/" + 10 + "/" + "1").build().execute(new AndroidCallback() {
            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
            }

            @Override
            public void onResponse(List<Android> response) {
                super.onResponse(response);
                DBUtil.saveAndroids(realm, response);
                // 清空然后更新全部数据
                data.clear();
                data.addAll(DBUtil.getAndroids(realm));
                rootView.notifyDataChanged();
                rootView.setRefreshState(false);
            }
        });
    }
}
