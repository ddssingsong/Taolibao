package com.jhs.taolibao.code.my.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.code.my.prestenter.CheckInPresenterImpl;
import com.jhs.taolibao.code.my.view.CheckInView;
import com.jhs.taolibao.entity.CheckInInfo;

/**
 * Created by dds on 2016/6/27.
 *
 * @TODO
 */
public class CheckInFragment extends DialogFragment implements View.OnClickListener, CheckInView {
    private TextView tv_checkin_submit;
    private TextView tv_checkin_day;
    private TextView tv_checkin_tip;

    private CheckInPresenterImpl checkInPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkInPresenter = new CheckInPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_checkin, container);
        initView(view);
        initData();
        return view;
    }


    private void initView(View view) {
        tv_checkin_submit = (TextView) view.findViewById(R.id.tv_checkin_submit);
        tv_checkin_day = (TextView) view.findViewById(R.id.tv_checkin_day);
        tv_checkin_tip = (TextView) view.findViewById(R.id.tv_checkin_tip);
        tv_checkin_submit.setOnClickListener(this);
    }

    private void initData() {
        checkInPresenter.CheckIn(UserInfoSingleton.getUserId());
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }

    @Override
    public void CheckinSuccess(CheckInInfo checkInfo) {
        tv_checkin_day.setText("抽奖结果");
        tv_checkin_tip.setText(checkInfo.getInfo());
    }

    @Override
    public void onHavingCheckIn(CheckInInfo checkInfo) {
        tv_checkin_day.setText(checkInfo.getErrinfo());
        tv_checkin_tip.setText(checkInfo.getInfo2());
    }
}
