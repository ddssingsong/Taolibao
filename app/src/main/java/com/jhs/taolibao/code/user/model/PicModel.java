package com.jhs.taolibao.code.user.model;

/**
 * Created by dds on 2016/6/29.
 *
 * @TODO
 */
public interface PicModel {
    void uploadimage(String photo,PicModelImpl.onPicListener listener);
}
