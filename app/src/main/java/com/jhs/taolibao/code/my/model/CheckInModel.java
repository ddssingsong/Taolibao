package com.jhs.taolibao.code.my.model;

/**
 * Created by dds on 2016/7/25.
 *
 * @TODO
 */
public interface CheckInModel {
    /**
     * 签到接口
     * @param userId
     * @param listener
     */
    void CheckIn(String userId, CheckInModelImpl.onCheckInListener listener);
}
