package com.jhs.taolibao.code.news.model;

/**
 * Created by dds on 2016/6/14.
 *
 * @TODO 轮播图片处理
 */
public interface DynamicModel {
    /**
     * 获取轮播图片
     */
    void loadDynamic(DynamicModelImpl.OnDynamicListener listener);
}
