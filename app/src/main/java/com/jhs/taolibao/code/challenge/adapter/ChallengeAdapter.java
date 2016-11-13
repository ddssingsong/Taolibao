package com.jhs.taolibao.code.challenge.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jhs.taolibao.code.challenge.fragment.ChallengeChildFragment;

import java.util.List;

/**
 * @author jiao on 2016/7/11 18:29
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:挑战"横向布局"模式适配器
 */
public class ChallengeAdapter extends FragmentStatePagerAdapter {

    private List<ChallengeChildFragment> datas;

    public ChallengeAdapter(FragmentManager fm, List<ChallengeChildFragment> size) {
        super(fm);
        this.datas = size;
    }

    public void setDatas(List<ChallengeChildFragment> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }
    @Override
    public ChallengeChildFragment getItem(int position) {
        if (null == datas){
            return null;
        }else {
            return datas.get(position);
        }
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

   @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
