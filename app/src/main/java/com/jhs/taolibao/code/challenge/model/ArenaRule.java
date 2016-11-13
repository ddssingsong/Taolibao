package com.jhs.taolibao.code.challenge.model;

/**
 * 挑战规则
 * Created by xujingbo on 2016/7/28.
 */
public class ArenaRule {

    /**
     * code : 0
     * Rule : 挑战规则
     夺股擂主，一统江湖
     欢迎加入挑战大军，战胜擂主可以获得超额宝币奖励，宝币可兑换豪礼哦！规则如下：
     1.挑战需要扣除一定数量的宝币，最低1000个宝币，最高20000个宝币；
     2.挑战成功可以获得双倍挑战者扣除宝币的奖励；
     3.以5个交易日的模拟交易的收益率来判定是否挑战成功，超过擂主收益率计为挑战成功；
     4.挑战期间挑战者和擂主能够相关看到彼此持仓；
     5.每期擂台的冠军（成功的挑战者中第一名成为擂主）成为擂主将会成为下期擂主，下期擂主可根据账户等级获得一定比例所有挑战者和观战者消费宝币的奖励，宝币可兑换平台奖品。
     * Errinfo :
     */

    private int code;
    private String Rule;
    private String Errinfo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRule() {
        return Rule;
    }

    public void setRule(String Rule) {
        this.Rule = Rule;
    }

    public String getErrinfo() {
        return Errinfo;
    }

    public void setErrinfo(String Errinfo) {
        this.Errinfo = Errinfo;
    }
}
