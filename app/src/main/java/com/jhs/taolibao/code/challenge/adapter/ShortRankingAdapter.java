package com.jhs.taolibao.code.challenge.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jhs.taolibao.code.challenge.fragment.ShortRankingFragment;

import java.util.List;

/**
 * @author jiao、xujingbo on 2016/7/15 10:46
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:
 */
public class ShortRankingAdapter extends FragmentPagerAdapter {
    private List<String> mList;

    public ShortRankingAdapter(FragmentManager fm, List<String> mList) {
        super(fm);
        this.mList = mList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment currentFragment = null;
        //1:综合排序  2累计收益率排序
        switch (position) {
            case 0:
                currentFragment = ShortRankingFragment.newInstance(1);
                break;
            case 1:
                currentFragment = ShortRankingFragment.newInstance(2);
                break;
        }
        return currentFragment;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
