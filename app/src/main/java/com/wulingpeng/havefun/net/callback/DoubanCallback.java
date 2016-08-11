package com.wulingpeng.havefun.net.callback;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.wulingpeng.havefun.mvp.model.Douban;
import com.zhy.http.okhttp.callback.Callback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by wulinpeng on 16/8/10.
 */
public class DoubanCallback extends Callback<List<Douban>> {

    protected Fragment fragment;

    protected int t;

    @Override
    public List<Douban> parseNetworkResponse(Response response) throws Exception {
        List<Douban> result = new ArrayList<>();
        String res = response.body().string();
        Document document = Jsoup.parse(res);
        // 获得url
        // div[class=thumbnail]表示div标签，属性class为thumbnail
        // >代表子元素 a img 都是代表标签，最后得到img标签
        // 通过attr得到相应属性
        Elements elements = document.select("div[class=thumbnail] > div[class=img_single] > a > img");
        for (int i = 0; i != elements.size(); i++) {
            String url = elements.get(i).attr("src");
            Douban douban = new Douban();
            douban.setUrl(url);
            try {
                // 解析url并得到实际长宽
                Bitmap bitmap = Glide.with(fragment)
                        .load(url)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                if (bitmap != null) {
                    douban.setWidth(bitmap.getWidth());
                    douban.setHeight(bitmap.getHeight());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            douban.setType(t);
            result.add(douban);
        }
        // 获得id
        elements = document.select("div[class=bottombar] > span[class=fr p5 meta] > span[class=starcount]");
        for (int i = 0; i != elements.size(); i++) {
            result.get(i).setId(elements.get(i).attr("topic-image-id"));
            Log.d("Debug", elements.get(i).attr("topic-image-id"));
        }

        return result;
    }

    @Override
    public void onError(Call call, Exception e) {
    }

    @Override
    public void onResponse(List<Douban> response) {
    }
}
