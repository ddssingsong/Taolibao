package com.jhs.taolibao.base.pullrefresh.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.ListView;

public class MyListView extends ListView{

	public MyListView(Context context) {
		super(context);
	}
	
	@Override
    protected void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (IndexOutOfBoundsException e) {
            // samsung error
        }
    }

}
