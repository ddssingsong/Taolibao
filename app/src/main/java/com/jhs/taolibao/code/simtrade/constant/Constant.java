package com.jhs.taolibao.code.simtrade.constant;

/**
 * @author jiao on 2016/7/12 15:00
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:恒生 常量类
 */
public class Constant {
    public static final String APP_KEY = "1535450b-2d91-4c85-91e1-fcd96840ed7c";
    public static final String APP_SECRET = "72115fe4-204c-4419-bc68-796e1a7062b8";

    /**
     * 帐号类别
     */
    public static final String INPUT_CONTENT = "6";//1表示资金帐号，6表示客户号

    /**
     * 目标机构编码 恒生：9100
     */
     public static final String TARGETCOMP_ID = "91000";
   // public static final String TARGETCOMP_ID = "90099";

    /**
     * 发送机构编码
     */
    // public static final String SENDERCOMP_ID = "91000";
    public static final String SENDERCOMP_ID = "90099";
    public static final String SENDERCOMP_ID2 = "90099";

    /**
     * 帐号类别
     */
    public static final String TARGETBUSINSYS_NO = "1000";//业务系统编号必须填1000

    /**
     * 授权类型 客户端凭证模式时，必须为“client_credentials”；
     */
    public static final String GRANT_TYPE = "client_credentials";//业务系统编号必须填1000

    //回调地址
    public static final String REDIRECT_URI = "182.92.172.134";

    //public static final String URL = "https://sandbox.hscloud.cn";
    public static final String URL = "https://vopen.hs.net";

    public static final String OPEN_URL = "https://open.hscloud.cn";
    //public static final String OPEN_URL = "https://vopen.hs.net";
    //账户资金排名查询
    public static final String QUERY = "/sim/v1/sim_fund_rank_query";
    //账户资金查询
    public static final String QUERY_FUND = "/secu/v1/balancefast_qry";

    //代码输入确认
    public static final String STOCK_CODE_ENTER = "secu/v1/stockcode_enter";
    //获取行情报价
    public static final String REAL = "/quote/v1/real";
    //获取行情报价
    public static final String LIST = "/quote/v1/market/list";

    // 证券成交查询
    public static final String BUSINESS_QRY = "/secu/v1/business_qry";
    // 证券委托查询
    public static final String ENTRUST_QRY = "/secu/v1/entrust_qry";
    // 历史证券委托查询
    public static final String ENTRUSTHIS_QRY = "/secu/v1/entrusthis_qry";
    // 历史证券成交查询
    public static final String BUSINESSHIS_QRY = "/secu/v1/businesshis_qry";
    // 市场年度节假日
    public static final String HOLIDAY = "/quote/v1/market/holiday";
    // 按键精灵
    public static final String WIZARD = "/quote/v1/wizard";


    //证券持仓查询
    public static final String KEEP = "/secu/v1/stockposition_qry";
    //大约可买数量
    public static final String ALMOST_BUY_COUNT = "/secu/v1/almostbuy_qry";
    //普通委托
    public static final String ENTRUST_ENTER = "/secu/v1/entrust_enter";


    // 撤单
    public static final String WITHDRAW_ENTER = "/secu/v1/withdraw_enter";

    //收益率排名查询
    public static final String ASSETS = "/sim/v1/sim_rankings_query";

    //用户个人收益率排名查询
    public static final String USERINCOME = "/sim/v1/sim_personal_rank_query";

    //客户资金精确查询
    public static final String USERMONEYINFO = "/secu/v1/balancefast_qry";

    //存储token
    public static final String TOKEN_SAVE = "http://218.245.0.52:8081/ArenaService/SaveToken";
    //获取token
    public static final String TOKEN_GET = "http://218.245.0.52:8081/ArenaService/GetToken";

    //获取恒生账号
    public static final String GET_HS_ACCOUNT = "http://218.245.0.52:8081/Webservices/UserInfo";
}
