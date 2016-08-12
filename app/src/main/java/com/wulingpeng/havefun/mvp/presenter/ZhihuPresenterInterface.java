package com.wulingpeng.havefun.mvp.presenter;

import com.wulingpeng.havefun.mvp.model.Zhihu;

import java.util.List;

import io.realm.Realm;

public interface ZhihuPresenterInterface {

    public void getZhihuData(List<Zhihu> header, List<Zhihu> items);
}
