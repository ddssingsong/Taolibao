package com.jhs.taolibao.code.challenge.utils;

import android.view.View;

import java.util.Calendar;

/**
 * 防止button过快点击造成多次事件
 * Created by xujingbo on 2016/8/2.
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 2000;
    private long lastClickTime = 0;
    public abstract void onNoDoubleClick(View v);
    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }
}
