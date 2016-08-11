package com.wulingpeng.havefun.mvp.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Gank类型图片类
 */
public class Gank extends RealmObject {
    /**
     * {
     "_id": "57aa8c8a421aa90b3aac1ee1",
     "createdAt": "2016-08-10T10:08:10.911Z",
     "desc": "8-10",
     "publishedAt": "2016-08-10T11:37:13.981Z",
     "source": "chrome",
     "type": "\u798f\u5229",
     "url": "http://ww2.sinaimg.cn/large/610dc034jw1f6ofd28kr6j20dw0kudgx.jpg",
     "used": true,
     "who": "\u4ee3\u7801\u5bb6"
     }
     */
    @PrimaryKey
    private String _id;

    private String url;

    private String publishedAt;

    private String createdAt;

    private String updatedAt;

    private String who;

    private String desc;

    private String type;

    private boolean used;

    private int width;

    private int height;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
