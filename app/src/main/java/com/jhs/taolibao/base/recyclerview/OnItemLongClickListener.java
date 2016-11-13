package com.jhs.taolibao.base.recyclerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dds on 2016/5/31.
 */
public interface OnItemLongClickListener<T> {
    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}
