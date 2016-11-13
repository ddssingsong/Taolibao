package com.jhs.taolibao.code.challenge.model;

import java.util.List;

/**
 * @author jiao on 2016/7/19 17:29
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:
 */
public class ArenaRankingList {
    //排行榜列表
    private List<ArenaUserInfo> ArList;
    //排行榜类型
    private int RankingType;

    public List<ArenaUserInfo> getArList() {
        return ArList;
    }

    public void setArList(List<ArenaUserInfo> arList) {
        ArList = arList;
    }

    public int getRankingType() {
        return RankingType;
    }

    public void setRankingType(int rankingType) {
        RankingType = rankingType;
    }
}
