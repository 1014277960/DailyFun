package com.wulingpeng.havefun.net.callback;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.wulingpeng.havefun.mvp.model.Gank;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 用于解析从网络返回的Gank json数组
 */
public class GankCallback extends Callback<List<Gank>> {

    protected Fragment fragment;

    @Override
    public List<Gank> parseNetworkResponse(Response response) throws Exception {
        List<Gank> result = new ArrayList<Gank>();
        JSONObject object = new JSONObject(response.body().string());
        JSONArray jsonArray = object.getJSONArray("results");
        for (int i = 0; i != jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            result.add(parseJsonToGank(jsonObject));
        }
        return result;
    }

    @Override
    public void onError(Call call, Exception e) {
    }

    @Override
    public void onResponse(List<Gank> response) {
    }

    private Gank parseJsonToGank(JSONObject jsonObject) {
        Gank gank = new Gank();
        try {
            gank.set_id(jsonObject.getString("_id"));
            gank.setCreatedAt(jsonObject.getString("createdAt"));
            gank.setDesc(jsonObject.getString("desc"));
            gank.setPublishedAt(jsonObject.getString("publishedAt"));
            gank.setUpdatedAt(jsonObject.getString("source"));
            gank.setType(jsonObject.getString("type"));
            gank.setUrl(jsonObject.getString("url"));
            gank.setUsed(jsonObject.getBoolean("used"));
            gank.setWho(jsonObject.getString("who"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 通过Glide得到bitmap，然后测量出长宽，传入Gank，以便后面使用
        try {
            Bitmap bitmap = Glide.with(fragment)
                    .load(gank.getUrl())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap != null) {
                gank.setWidth(bitmap.getWidth());
                gank.setHeight(bitmap.getHeight());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gank;
    }
}
