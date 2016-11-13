package com.jhs.taolibao.base;


import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BasePagerAdapter extends PagerAdapter {
    private ArrayList<View> viewList = new ArrayList<View>();

    public void addViewToAdapter(View view) {
        addViewToAdapter(view, false);
    }

    public void addViewToAdapter(View view, boolean isReset) {
        if (isReset) {
            viewList.clear();
        }
        viewList.add(view);
        notifyDataSetChanged();
    }

    public void addViewToAdapter(ArrayList<View> views) {
        addViewToAdapter(views, false);
    }

    public void addViewToAdapter(ArrayList<View> views, boolean isReset) {
        if (isReset) {
            viewList.clear();
        }
        viewList.addAll(views);
        notifyDataSetChanged();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = viewList.get(position);
        container.removeView(view);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view1, Object obj) {
        View view2 = (View) obj;
        return view2 == view1;
    }
}
