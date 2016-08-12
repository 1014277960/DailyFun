package com.wulingpeng.havefun.net.callback;

import com.wulingpeng.havefun.mvp.model.Zhihu;
import com.wulingpeng.havefun.utils.DateUtil;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ZhihuCallback extends Callback<List<Zhihu>> {

    private String date;

    @Override
    public List<Zhihu> parseNetworkResponse(Response response) throws Exception {
        List<Zhihu> result = new ArrayList<Zhihu>();
        JSONObject jsonObject = new JSONObject(response.body().string());
        date = jsonObject.getString("date");
        JSONArray top = jsonObject.getJSONArray("top_stories");
        JSONArray items = jsonObject.getJSONArray("stories");
        for (int i = 0; i != top.length(); i++) {
            JSONObject object = top.getJSONObject(i);
            result.add(parseJson(object, Zhihu.TYPE_HEADER));
        }

        for (int i = 0; i != items.length(); i++) {
            JSONObject object = items.getJSONObject(i);
            result.add(parseJson(object, Zhihu.TYPE_ITEM));
        }
        return result;
    }

    @Override
    public void onError(Call call, Exception e) {

    }

    @Override
    public void onResponse(List<Zhihu> response) {

    }

    private Zhihu parseJson(JSONObject jsonObject, int type) {
        Zhihu zhihu = new Zhihu();
        zhihu.setType(type);
        try {
            zhihu.setDate(date);
            zhihu.setId(jsonObject.getString("id"));
            zhihu.set_id(zhihu.getId() + type);
            zhihu.setTitle(jsonObject.getString("title"));
            if (type == Zhihu.TYPE_HEADER) {
                zhihu.setImageUrl(jsonObject.getString("image"));
            } else {
                JSONArray strings = jsonObject.getJSONArray("images");
                String url = strings.getString(0);
                zhihu.setImageUrl(url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return zhihu;
    }
}
