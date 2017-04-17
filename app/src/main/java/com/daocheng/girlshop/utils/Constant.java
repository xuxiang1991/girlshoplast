package com.daocheng.girlshop.utils;

/**
 * 项目名称：Cooke
 * 类描述：固定的状态
 * 创建人：Dove
 * 创建时间：2015/10/8 10:04
 * 修改人：Dove
 * 修改时间：2015/10/8 10:04
 * 修改备注：
 */
public class Constant {

    public static String CITYBROADCAST = "com.ufo.cooker.citybroadcast";
    public static String ORDERBROADCAST = "com.ufo.cooker.orderbroadcast";//订单状态
    public static String HOMEINDEX = "com.ufo.cooker.indexbroadcast";//订单状态

    public static String YUYINPINCE = "com.shidai.yuyin.pingce";//订单状态

    public static String BASE_URL = "http://192.168.2.200:8080";

    //Condition
    public static String price = "Price";//价格区间   section类


    public interface IConstants {
        int UNLIKE = 0;//文章喜欢
        int LIKE = 1;//文章不喜欢

        int UNHEHE = 0;
        int HEHE = 1;

        int TYPE_LIKE=1;
        int TYPE_HEHE=2;

        String action_add="add";
        String action_cancel="cancel";
    }


    //订单状态
    public static String RESERVE = "NEW";


    public static int MEALPLEACE = 10001; //地址返回索引
    public static int COUPON = 10002;//优惠券

    //收藏厨师ac
    public static String ADD = "add";
    public static String DEL = "del";

    //厨师ty
    public static String Large = "L";
    public static String Small = "S";
    public static String Other = "N";

    //厨师与商户type
    public static String Chef = "Chef";
    public static String Waiter = "Waiter";

    // 厨师预付定金界限
    public static int cost_limit = 50000;//厨师


    public static int cost_low = 500;
    public static int cost_high = 1000;

    //茶旦预付金界限
    public static int cha_cost_limit = 10000;//厨师


    public static int cha_cost_low = 300;
    public static int cha_cost_high = 600;

    //role  User    ， Chef
    public static String USER = "User";
    public static String CHEF = "Chef";


    //状态类型
    public static String NEW = "NEW";
    public static String CONFIRM = "CONFIRM";
    public static String DEPOSIT = "DEPOSIT";
    public static String FINISH = "FINISH";
    public static String REFUND = "REFUND";
    public static String CLOSE = "CLOSE";

    //User 订单状态
    public static String ALL = "ALL";
    public static String UNFINISH = "UNFINISH";
//    public static String FINISH="FINISH";
//    public static String CLOSE="CLOSE";

    //厨师 订单状态
//    public static String ALL="ALL"
//    public static String NEW = "NEW";
    public static String ENSURE = "ENSURE";
//    public static String CLOSE="CLOSE";


    //umeng message
    public static final String DESCRIPTOR = "com.umeng.share";

    private static final String TIPS = "请移步官方网站 ";
    private static final String END_TIPS = ", 查看相关说明.";
    public static final String TENCENT_OPEN_URL = TIPS + "http://wiki.connect.qq.com/android_sdk使用说明"
            + END_TIPS;
    public static final String PERMISSION_URL = TIPS + "http://wiki.connect.qq.com/openapi权限申请"
            + END_TIPS;

    public static final String SOCIAL_LINK = "http://www.umeng.com/social";
    public static final String SOCIAL_TITLE = "友盟社会化组件帮助应用快速整合分享功能";
    public static final String SOCIAL_IMAGE = "http://www.umeng.com/images/pic/banner_module_social.png";

    public static final String SOCIAL_CONTENT = "";

    //退款类型
    public static String accType_Weixin = "2";
    public static String accType_Zhifubao = "1";


    //列表数据每页做多10
    public static int pageNum = 10;

    public static int found_pageNum=10;

    //厨师正常还是停用
    public static String NOMAL = "正常";
    public static String PAUSE = "停用";


    //静态网页
    public static String GIT_NAME = "我的礼包";
    public static String GIT = "http://shenchujiayan.com/Introduce/giftDesc.html";//我的礼包
    public static String XIADAN_NAME = "订单须知";
    public static String XIADAN = "http://shenchujiayan.com/Introduce/Order.html";//下单须知
    public static String JOINUS_NAME = "加入我们";
    public static String JOINUS = "http://shenchujiayan.com/Introduce/JoinUs.html";//加入我们
    public static String BAOZHANG = "http://shenchujiayan.com/Introduce/Server.html";
    public static String BAOZHANG_NAME = "服务保障";
    public static String FUWUXIEYI = "http://shenchujiayan.com/Introduce/ServiceAgreement.html";//用户服务协议
    public static String FUWUXIEYI_NAME = "用户服务协议";
    //官网地址
    public static String OFFICIALADRESS = "http://shenchujiayan.com";
    //官方微信
    public static String OFFICIALWEIXIN = "时代国际英语";


    //首页轮回图type
    public static String CHEFDETAIL = "chefdetail";//跳转厨师页
    public static String URL = "url";//跳转链接


    //umeng 通知
    public static final int UPDATE_LOCAL_NOTIFICATION = 1;
    public static final int CREATE_LOCAL_NOTIFICATION = 2;
    public static final int ADD_LOCAL_NOTIFICATION = 3;
    public static final int CLEAR_LOCAL_NOTIFICATION = 4;
    public static final int DELETE_LOCAL_NOTIFICATION = 5;

    // umeng  推送自定义标准    cls 活动class的名字//
    //cls
    public static int ACTION_WEB = 201;//打开网页
    public static int ACTION_COOKER = 202;//打开厨师页

    //message
    public static int MESSAGE_FAIL = 1;
    public static int MESSAGE_SUCCESS = 0;


}
