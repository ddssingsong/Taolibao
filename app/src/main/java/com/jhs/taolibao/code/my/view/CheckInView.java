package com.jhs.taolibao.code.my.view;


import com.jhs.taolibao.entity.CheckInInfo;

/**
 * Created by dds on 2016/7/18.
 *
 * @TODO
 */
public interface CheckInView {
    void CheckinSuccess(CheckInInfo checkInfo);
    void onHavingCheckIn(CheckInInfo checkInfo);
}
