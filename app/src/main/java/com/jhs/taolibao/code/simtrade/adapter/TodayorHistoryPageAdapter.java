package com.jhs.taolibao.code.simtrade.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jhs.taolibao.code.simtrade.fragment.HistoryDealFragment;
import com.jhs.taolibao.code.simtrade.fragment.HistoryDelegationFragment;
import com.jhs.taolibao.code.simtrade.fragment.TodayDealFragment;
import com.jhs.taolibao.code.simtrade.fragment.TodayDelegationFragment;

import java.util.List;

/**
 * 模拟交易界面分类适配器
 * Created by xujingbo on 2016/7/6.
 */
public class TodayorHistoryPageAdapter extends FragmentPagerAdapter {
    private List<String> datas;
    private Boolean isToday;
    public TodayorHistoryPageAdapter(FragmentManager fm, List<String> datas, Boolean isToday) {
        super(fm);
        this.datas = datas;
        this.isToday = isToday;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try {
            return datas.get(position);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment currentFragment;
        if (isToday){
            switch (position){
                //今日委托
                case 0:
                    currentFragment = new TodayDelegationFragment();
                    break;
                //今日成交
                case 1:
                    currentFragment = new TodayDealFragment();
                    break;
                default:
                    currentFragment = new TodayDelegationFragment();
            }
        }else{
            switch (position){
                //历史委托
                case 0:
                    currentFragment = new HistoryDelegationFragment();
                    break;
                //历史成交
                case 1:
                    currentFragment = new HistoryDealFragment();
                    break;
                default:
                    currentFragment = new HistoryDelegationFragment();
            }
        }
        return currentFragment;
    }

    @Override
    public int getCount() {
        try {
            return datas.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
