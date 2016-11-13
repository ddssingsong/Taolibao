package com.jhs.taolibao.code.simtrade.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author jiao on 2016/7/8 15:34
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:
 */
public class NoScrollListView extends ListView {
    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mHeightMeasureSpec);
    }
}
