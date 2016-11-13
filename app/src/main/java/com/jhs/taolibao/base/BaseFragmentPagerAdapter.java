package com.jhs.taolibao.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jhs.taolibao.code.market.widget.IndustryPagerFragment;

import java.util.ArrayList;
import java.util.List;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragmentToAdapter(BaseFragment fragment, boolean isReset) {
        if (isReset) {
            fragmentList.clear();
        }
        fragmentList.add(fragment);
        notifyDataSetChanged();
    }

    public void clear() {
        fragmentList.clear();
    }

    public void addViewToAdapter(List<IndustryPagerFragment> fragments, boolean isReset) {
        if (isReset) {
            fragmentList.clear();
        }
        fragmentList.addAll(fragments);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentList.get(arg0);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
