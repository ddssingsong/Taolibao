package com.jhs.taolibao.code.challenge.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jhs.taolibao.code.challenge.fragment.MyArenaFragment;

import java.util.List;

/**
 * Created by KANGXIANGTAO on 2016/7/15.
 */
public class MyArenaPageAdapter extends FragmentPagerAdapter {

    private List<String> dataList;
    public MyArenaPageAdapter(FragmentManager fm) {
        super(fm);
    }
    public MyArenaPageAdapter(FragmentManager fm, List<String> dataList) {
        super(fm);
        this.dataList = dataList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dataList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        //2比赛中  4观战 3完成比赛
        if (position == 0){
            fragment = MyArenaFragment.newInstance(2);
            return fragment;
        }if (position == 1){
            fragment = MyArenaFragment.newInstance(4);
            return fragment;
        }
        if (position == 2) {
            fragment = MyArenaFragment.newInstance(3);
            return fragment;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }
}
