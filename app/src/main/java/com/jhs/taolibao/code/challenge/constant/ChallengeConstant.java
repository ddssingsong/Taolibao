package com.jhs.taolibao.code.challenge.constant;

/**
 * @author jiao on 2016/7/18 15:38
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:"擂台"常量类
 */
public class ChallengeConstant {
    //域名
    public static final String URL = "http://218.245.0.52:8081";
    //擂台基本接口
    public static final String ChallengeUrl = "http://218.245.0.52:8081/ArenaService";

    //擂台信息接口
    public static final String ARENAINFO = "/GetArenaInfo";

    //用户擂台角色接口
    public static final String USERROLE = "/GetArenaUserRole";

    //排行榜接口
    public static final String RANKLIST = "/GetArenaRankList";

    //我的擂台信息
    public static final String MYARENA = "/GetArenaListByState";

    //用户信息接口
    public static final String USERINFO = "/GetUser";

    //用户开户信息保存接口
    public static final String USEROPENACCOUNT = "/OpenAccount";

    //"擂台主页"多个擂台
    public static final String ARENA = "/GetChallengesList";
    //挑战、观战
    public static final String INSERTARENAUSER = "/InsertArenaUser";
    //挑战、观战规则
    public static final String RULE = "/GetArenaRule";
    //擂台首页的广告轮播
    public static final String ADS = "/GetSlogan";
    //获取用户的个人信息
    public static final String GET_USER_INFO = "http://218.245.0.52:8081/Webservices/UserInfo";

    //新增接口 购买官方擂台观看时长
    public static final String SET_OFFICIAL_ARENA = "/SetOfficialArena";
    //返回 { code = code, Errinfo = errInfo }  code= 0 成功 1失败
     //判断账户是否有观看官方擂台权限
    public static final String CHECK_OFFICIAL_ARENA = "/CheckOfficialArena";
    //返回 { code = code, Errinfo = errInfo }  code= 0有权限  1无权限
    /**
     * 用户擂台角色
     */
    public enum ArenaRole{
        NO_ROLE, //无角色
        CHAMPION,//擂主
        CHANGLLER,//挑战者
        WHATCHER,//观看者
    }

    /**
     * 观战、挑战类型
     */
    public enum ArenaType{
        TYPE_NO,
        TYPE_CHALLENGE,//挑战
        TYPE_WHATCH,//观战
    }

    public static final int COST_POINT = 200;
}
