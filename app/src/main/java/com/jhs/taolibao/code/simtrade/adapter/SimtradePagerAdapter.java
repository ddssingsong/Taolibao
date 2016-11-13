package com.jhs.taolibao.code.simtrade.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jhs.taolibao.code.simtrade.fragment.BuyFragment;
import com.jhs.taolibao.code.simtrade.fragment.CancelFragment;
import com.jhs.taolibao.code.simtrade.fragment.KeepFragment;
import com.jhs.taolibao.code.simtrade.fragment.SearchFragment;
import com.jhs.taolibao.code.simtrade.fragment.SellFragment;

import java.util.List;

/**
 * 模拟交易界面分类适配器
 * Created by xujingbo on 2016/7/6.
 */
public class SimtradePagerAdapter extends FragmentPagerAdapter {
    private List<String> datas;
    private List<Integer> dataImgs;
    public SimtradePagerAdapter(FragmentManager fm, List<String> datas, List<Integer> dataImgs) {
        super(fm);
        this.datas = datas;
        this.dataImgs = dataImgs;
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

    public int getResId(int position){
        try {
            return dataImgs.get(position);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment currentFragment;
        switch (position){
            //买入
            case 0:
                currentFragment = new BuyFragment();
                break;
            //卖出
            case 1:
                currentFragment = new SellFragment();
                break;
            //撤单
            case 2:
                currentFragment = new CancelFragment();
                break;
            //持仓
            case 3:
                currentFragment = new KeepFragment();
                break;
            //查询
            case 4:
                currentFragment = new SearchFragment();
                break;
            //默认持仓界面
            default:
                currentFragment = new KeepFragment();

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
