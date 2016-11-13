package com.jhs.taolibao.code.simtrade.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author jiao on 2016/7/8 14:05
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:
 */
public class TouchLinearLayout extends LinearLayout {
    public TouchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }

        return true;
    }
}
