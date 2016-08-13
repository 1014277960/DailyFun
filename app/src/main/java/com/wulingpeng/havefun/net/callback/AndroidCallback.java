package com.wulingpeng.havefun.net.callback;

import com.wulingpeng.havefun.mvp.model.Android;
import com.wulingpeng.havefun.mvp.model.Zhihu;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by wulinpeng on 16/8/13.
 */
public class AndroidCallback extends Callback<List<Android>> {
    @Override
    public List<Android> parseNetworkResponse(Response response) throws Exception {
        List<Android> result = new ArrayList<Android>();
        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i != results.length(); i++) {
            JSONObject j = results.getJSONObject(i);
            Android android = new Android();
            android.setCreatedAt(j.getString("createdAt"));
            android.setUrl(j.getString("url"));
            android.setDesc(j.getString("desc"));
            android.setWho(j.getString("who"));
            result.add(android);
        }
        return result;
    }

    @Override
    public void onError(Call call, Exception e) {

    }

    @Override
    public void onResponse(List<Android> response) {

    }
}
