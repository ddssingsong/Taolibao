package com.jhs.taolibao.app;

/**
 * Created by dds on 2016/6/1.
 */
public class WebBiz {

    //public static final String HOSTNAME = "192.168.1.20:8080"; // 测试接口地址
    public static final String HOSTNAME = "218.245.0.52:8081"; // 测试接口地址
    //public static final String HOSTNAME = "www.jvhesheng.com"; // 测试接口地址
    public static final String SERVICE_PREFIX = "http://" + HOSTNAME + "/Webservices/";
    public static final String HANGQING = "http://" + HOSTNAME + "/MarketService/";


    /**
     * 业务接口
     */
    public static final String SERVICE_MOBILECODE = SERVICE_PREFIX + "SendMobileCode"; // 发送短信验证码
    public static final String SERVICE_LOGIN = SERVICE_PREFIX + "Login"; // 登录
    public static final String SERVICE_REGISTER = SERVICE_PREFIX + "Register"; // 注册
    public static final String SERVICE_FINDPWD = SERVICE_PREFIX + "FindPwd"; // 找回密码
    public static final String SERVICE_CHANGEPWD = SERVICE_PREFIX + "ChangePwd"; // 修改密码
    public static final String SERVICE_USERINFO = SERVICE_PREFIX + "Userinfo"; // 获取用户信息
    public static final String SERVICE_UPDATEUSER = SERVICE_PREFIX + "UpdateUser"; // 修改用户信息userJson
    public static final String SERVICE_PHOTOUPLOAD = SERVICE_PREFIX + "UploadIocnIMG"; // 头像上传imgStearm userID
    public static final String SERVICE_DYNAMICPIC = "http://" + HOSTNAME + "/Webservices/GetadPricList?page=1&pagesize=5"; // 动态图
    public static final String UPLOAD_PREFIX = "http://" + HOSTNAME;// 新闻图片地址
    public static final String UPLOAD_SLIDER = "http://" + HOSTNAME + "/upload/";// 轮播图片地址
    public static final String SERVICE_NEWSLIST = SERVICE_PREFIX + "GetInfoListByType"; // 获取新闻列表
    public static final String SERVICE_COMMENTLIST = SERVICE_PREFIX + "GetCommentByInfo"; // 获取评论列表
    public static final String SERVICE_INSERT = SERVICE_PREFIX + "InsertComment"; // 插入评论

    public static final String UserSiginIn = SERVICE_PREFIX + "UserSiginIn";//签到
    public static final String LuckDraw = SERVICE_PREFIX + "LuckDraw";//签到
    public static final String GetConfigByKey = SERVICE_PREFIX + "GetConfigByKey";//通过Key获取value


    /**
     * +
     * 行情接口
     */
    public static final String GetIndexView = HANGQING + "GetHsIndexView";//获取顶部指数
    public static final String GetHotIndustry = HANGQING + "GetHsHotIndustryByPage";//获取热门行业涨幅数据
    public static final String GetRankingList = HANGQING + "GetHsRankingList";//涨跌幅排行榜


    public static final String GetStockByIndustry = HANGQING + "GetHsStockByIndustry";//根据行业获取股票列表
    public static final String GetRankListByType = HANGQING + "GetHsRankListByType";//更多涨幅榜榜和跌幅榜
    public static final String GetStcokInfo = HANGQING + "GetHsStcokInfo";//个股行情
    public static final String GetBinefStock = HANGQING + "GetBinefStock";//搜索功能
    public static final String GetOptionalByUser = SERVICE_PREFIX + "GetOptionalByUser";//获取自选列表
    public static final String InsetOptional = SERVICE_PREFIX + "InsetOptional";//添加自选
    public static final String DelOptional = SERVICE_PREFIX + "DelOptional";//移除自选
    public static final String CheckOptional = SERVICE_PREFIX + "CheckOptional";//检测是否自选
    public static final String GetMinutehqData = HANGQING + "GetHsMinutehqData";//获取分时
    public static final String GetKline = HANGQING + "GetHsKline";//获取k线入参：  Type day 日K onth 月k week  周k
    public static final String GetrecommendStock = SERVICE_PREFIX + "GetrecommendStock";//获取每日金股
    public static final String GetTockCompany = HANGQING + "GetTockCompany";//获取公司信息


    /**
     * 猜涨跌接口
     */
    public static final String GetHistoryStreet = SERVICE_PREFIX + "GetHistoryStreet";//获取大盘指数
    public static final String GetUserGameInfo = SERVICE_PREFIX + "GetUserGameInfo";//获取用户猜的数据,用来判断今天是否可猜
    public static final String GameInfoAddOrUpdate = SERVICE_PREFIX + "GameInfoAddOrUpdate";//增加或者修改
    public static final String GetGameRuleList = SERVICE_PREFIX + "GetGameRuleList";//获取竞猜区间
    public static final String GetInfoByUser = SERVICE_PREFIX + "GetInfoByUser";//获取竞猜历史列表
    public static final String GetNowDate = SERVICE_PREFIX + "GetNowDate";//获取系统时间


    /**
     * 支付所用接口
     */

    public static final String mMode = "00";//"00" - 启动银联正式环境 "01" - 连接银联测试环境
    public static final String Pay = SERVICE_PREFIX + "Pay";//获取订单orderid
    public static final String GetUnionpayTN = SERVICE_PREFIX + "GetUnionpayTN";//获取订单流水
    public static final String PointChange = SERVICE_PREFIX + "PointChange";//兑换
    public static final String UpPayState = SERVICE_PREFIX + "UpPayState";//通知后台增加宝币
    public static final String PayRemStockDate = SERVICE_PREFIX + "PayRemStockDate";//付费为了看每日金股

    /**
     * 更新App接口
     */

    public static final String VERSION = "http://" + HOSTNAME + "/UploadIocn/version.json";
    public static final String DOWNLOADURL = "http://" + HOSTNAME + "/UploadIocn";
    public static String APKNAME = "juhesheng.apk";


    public static final int FAILED = -1;
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

}
