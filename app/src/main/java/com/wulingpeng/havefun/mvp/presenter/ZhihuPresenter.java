package com.wulingpeng.havefun.mvp.presenter;

import android.util.Log;

import com.wulingpeng.havefun.mvp.model.Zhihu;
import com.wulingpeng.havefun.mvp.view.ZhihuFragment;
import com.wulingpeng.havefun.net.API;
import com.wulingpeng.havefun.net.callback.ZhihuCallback;
import com.wulingpeng.havefun.utils.DBUtil;
import com.wulingpeng.havefun.utils.DateUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import okhttp3.Call;

public class ZhihuPresenter implements ZhihuPresenterInterface {

    private ZhihuFragment rootView;

    private Realm realm;

    public ZhihuPresenter(ZhihuFragment fragment) {
        rootView = fragment;
        realm = fragment.mRealm;
    }

    @Override
    public void getZhihuData(List<Zhihu> header, List<Zhihu> items) {
        List<Zhihu> result = DBUtil.getZhihus(realm, DateUtil.parseStandardDate(new Date()));
        if (result.size() == 0) {
            // 没有当天数据，删除以前数据，然后从网络获取当天数据
            DBUtil.deleteZhihus(realm);
            getZhihuDataFromNet(header, items);
        } else {
            // 解析返回的数据为header和items
            parseZhihuResult(result, header, items);
        }
    }

    private void getZhihuDataFromNet(final List<Zhihu> header, final List<Zhihu> items) {
        OkHttpUtils.get().url(API.ZHIHU_LATEST).build().execute(new ZhihuCallback() {
            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
            }

            @Override
            public void onResponse(List<Zhihu> response) {
                super.onResponse(response);
                // 得到所有的share_url
                getUrls(response, header, items);
            }
        });
    }

    private void parseZhihuResult(List<Zhihu> result, List<Zhihu> header, List<Zhihu> items) {
        // parse
        header.clear();
        items.clear();
        for (Zhihu z : result) {
            if (z.getType() == Zhihu.TYPE_HEADER) {
                header.add(z);
            } else {
                items.add(z);
            }
        }
        rootView.notifyDataChanged();
        rootView.setRefreshState(false);
    }

    private void getUrls(final List<Zhihu> zhihus, final List<Zhihu> header, final List<Zhihu> items) {
        for (final Zhihu zhihu : zhihus) {
            OkHttpUtils.get().url(API.ZHIHU_DETAIL + "/" + zhihu.getId()).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {

                }

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        zhihu.setUrl(jsonObject.getString("share_url"));
                        // 数据全部得到后，也就是得到share_url后才写入数据库
                        DBUtil.saveZhihus(realm, zhihus);
                        parseZhihuResult(zhihus, header, items);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
