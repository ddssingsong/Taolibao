package com.jhs.taolibao.code.challenge.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author jiao on 2016/7/13 20:04
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:
 */
public class MyViewPager extends ViewPager {
    float preX;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean res = super.onInterceptTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            preX = event.getX();
        } else {
            if (Math.abs(event.getX() - preX) > 4) {
                return true;
            } else {
                preX = event.getX();
            }
        }
        return res;
    }
}
