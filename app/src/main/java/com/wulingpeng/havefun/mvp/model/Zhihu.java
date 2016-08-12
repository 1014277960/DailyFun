package com.wulingpeng.havefun.mvp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wulinpeng on 16/8/12.
 */
public class Zhihu extends RealmObject {

    public static int TYPE_HEADER = 0;

    public static int TYPE_ITEM = 1;

    @PrimaryKey
    /**
     * 由于得到的top和items可能有重合的，避免保存到数据库的使用通过id合并，就用这个分别
     * _id = id + type;
     */
    private String _id;

    private String id;

    private String date;

    private String title;

    private String imageUrl;

    private int type;

    private String url = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

