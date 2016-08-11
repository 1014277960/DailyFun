package com.wulingpeng.havefun.mvp.presenter;

import com.wulingpeng.havefun.mvp.model.Douban;
import com.wulingpeng.havefun.mvp.model.Gank;

import java.util.List;

import io.realm.RealmObject;

/**
 * 与pic相关的presenter需要实现的方法
 */
public interface PicPresenterInterface {

    /**
     * 从网络的到数据
     * @param count
     * @param page
     * @param objects
     */
    public  void getGankFromNet(int count, int page, List<Gank> objects);

    /**
     * 从数据库得到数据
     * @param objects
     */
    public void getGankFromDB(List<Gank> objects);

    public  void getDoubanFromNet(int page, List<Douban> objects, String url, int type);

    public void getDoubanFromDB(List<Douban> objects, int type);
}
