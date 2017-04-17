package com.daocheng.girlshop.net;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Utils;
import com.google.gson.Gson;


import org.apache.http.protocol.HTTP;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class ShidaiApi {


    public static final String BASE_URL = "http://121.40.90.171/";//"http://114.55.5.20:8080"

    public static final String Pic_BASE_URL = "http://7vijhu.com1.z0.glb.clouddn.com/";//"http://114.55.5.20:8080"

//userid 默认 720

    /**
     * 登录
     *
     * @param context
     * @param netCallBack
     * @param rspCls      http://121.40.90.171/cstimes/app/login?mobile=13962325335&password=3380066xx&userAgent=android
     */
    public static void getLogin(Context context, String mobile, String password, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

//        HashMap map = new HashMap();
//        map.put("mobile", mobile);
//        map.put("password", password);
//        map.put("userAgent", "android");
//        NetUtils.post(context, BASE_URL + "cstimes/app/login", map, null, netCallBack, rspCls);


        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/login?");
            sb.append("mobile=");
            sb.append(URLEncoder.encode(mobile, HTTP.UTF_8)).append("&");


            sb.append("password=")
                    .append(password)
                    .append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);
    }


    /**
     * 获取7牛的token
     *
     * @param context
     * @param rspCls
     * @param netCallBack
     */
    public static void getQiNiuUptoken(Context context, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {
        String url = BASE_URL + "cstimes/app/getToken";
        NetUtils.post(context, url, null, null, netCallBack, rspCls);
    }

    public static void register(Context context, String mobile, String password, String head, String nickname, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {
        HashMap map = new HashMap();
        map.put("mobile", mobile);
        map.put("nickname", nickname);
        map.put("password", password);
        map.put("userAgent", "Android");
        map.put("head", head);


//        StringBuilder sb = new StringBuilder(BASE_URL);
//        try {
//            sb.append("cstimes/app/register?");
//            sb.append("mobile=");
//            sb.append(URLEncoder.encode(mobile, HTTP.UTF_8)).append("&");
//
//
//            sb.append("password=")
//                    .append(password)
//                    .append("&userAgent=")
//                    .append("android")
//                    .append("&head=")
//                    .append(head)
//                    .append("&nickname=")
//                    .append(URLEncoder.encode(nickname, HTTP.UTF_8));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        NetUtils.post(context, BASE_URL + "cstimes/app/register", map, "注册", netCallBack, rspCls);
    }

    /**
     * 检验手机号是否被注册过
     *
     * @param context
     * @param mobile
     * @param rspCls
     * @param netCallBack
     */
    public static void gethasUserbyMobile(Context context, String mobile, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {
        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/mobileForUser?");
            sb.append("mobile=");
            sb.append(URLEncoder.encode(mobile, HTTP.UTF_8)).append("&")
                    .append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);
    }

    /**
     * 获取本周内的课程
     *
     * @param context
     * @param catid          56必修课程，87选修课程
     * @param currentPageStr
     * @param maxCountStr
     * @param rspCls
     * @param netCallBack    http://121.40.90.171/cstimes/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getKeweekcoursesList(Context context,int userid, int catid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/weekcourses?");
            sb.append("catid=").append(catid).append("&userid=").append(userid);
            sb.append("&currentPageStr=").append(currentPageStr).append("&maxCountStr=").append(maxCountStr)
                    .append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }


    /**
     * 获取课程详情
     *
     * @param context
     * @param userid
     * @param id          课程id 2454
     * @param rspCls
     * @param netCallBack http://121.40.90.171/cstimes/app/course?id=2245&userid=715
     */
    public static void getKeDetail(Context context, int userid, int id, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/course?");
            sb.append("userid=").append(userid);
            sb.append("&id=").append(id)
                    .append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }


    /**
     * 课程订阅
     *
     * @param context
     * @param userid
     * @param id          课程id 2454
     * @param rspCls
     * @param netCallBack http://121.40.90.171/cstimes/app/course?id=2245&userid=715
     */
    public static void JoinInKe(Context context, int userid, int id, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

//        StringBuilder sb = new StringBuilder(BASE_URL);
//        try {
//            sb.append("cstimes/app/orderCourse?");
//            sb.append("userid=").append(userid);
//            sb.append("&id=").append(id)
//                    .append("&userAgent=")
//                    .append("android");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", Integer.toString(id));
        map.put("userAgent", "android");

        NetUtils.post(context, BASE_URL + "cstimes/app/orderCourse", map, null, netCallBack, rspCls);


    }


    /**
     * 获取我的课程
     *
     * @param context
     * @param userid
     * @param rspCls
     * @param netCallBack http://121.40.90.171/cstimes/app/course?id=2245&userid=715
     */
    public static void getmyKeDetail(Context context, int userid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/myCourses?");
            sb.append("userid=").append(userid);
            sb.append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }


    /**
     * 获取我的词汇
     *
     * @param context
     * @param userid
     * @param currentPageStr
     * @param maxCountStr
     * @param rspCls
     * @param netCallBack    http://121.40.90.171/cstimes/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getMycihuiList(Context context, int userid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/words?");
            sb.append("userid=").append(userid);
            sb.append("&currentPageStr=").append(currentPageStr).append("&maxCountStr=").append(maxCountStr)
                    .append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }

    /**
     * 删除词汇
     *
     * @param context
     * @param userid
     * @param id          词汇id
     * @param rspCls
     * @param netCallBack http://121.40.90.171/cstimes/app/course?id=2245&userid=715
     */
    public static void deleteCihuiFromMy(Context context, int userid, int id, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", Integer.toString(id));
        map.put("userAgent", "android");

        NetUtils.post(context, BASE_URL + "cstimes/app/delWord", map, null, netCallBack, rspCls);


    }

    /**
     * 首页内容
     *
     * @param context
     * @param userid
     * @param rspCls
     * @param netCallBack http://121.40.90.171/cstimes/app/course?id=2245&userid=715
     */
    public static void getHomedata(Context context, int userid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/home?");
            sb.append("userid=").append(userid)
                    .append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }


    /**
     * 获取列表信息
     * <p/>
     * 3 热门推荐
     * 4 每日一句
     * 5 分类学习
     * 6 挑战外教
     * 7 视频练习
     * 8 想唱就唱
     * 9 ECA 活动
     * 10 新生必修
     * 11 预习复习
     *
     * @param context
     * @param userid
     * @param currentPageStr
     * @param maxCountStr
     * @param rspCls
     * @param netCallBack    http://121.40.90.171/cstimes/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getDataList(Context context, int type, int userid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/articles?");
            sb.append("userid=").append(userid);
            sb.append("&currentPageStr=").append(currentPageStr).append("&maxCountStr=").append(maxCountStr)
                    .append("&userAgent=")
                    .append("android").append("&type=").append(type);

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }


    /**
     * 更新句子得分
     *
     * @param context
     * @param userid
     * @param id          词汇id
     * @param rspCls
     * @param netCallBack http://121.40.90.171/cstimes/app/course?id=2245&userid=715
     */
    public static void updateScope(Context context, int userid, int id, int updateScore,int type, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", Integer.toString(id));
        map.put("score", Integer.toString(updateScore));
        map.put("userAgent", "android");
        if (type!=0)
        map.put("type",Integer.toString(type));

        NetUtils.post(context, BASE_URL + "cstimes/app/updateScore", map, null, netCallBack, rspCls);


    }


    /**
     * 加入生词表
     *
     * @param context
     * @param userid
     * @param word
     * @param rspCls
     * @param netCallBack http://121.40.90.171/cstimes/app/course?id=2245&userid=715
     */
    public static void addtoWordbooke(Context context, int userid, String word, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("word", word);
        map.put("userAgent", "android");

        NetUtils.post(context, BASE_URL + "cstimes/app/newWord", map, null, netCallBack, rspCls);


    }

    /**
     * 获取评论页
     *
     * @param context
     * @param id
     * @param currentPageStr
     * @param maxCountStr
     * @param rspCls
     * @param netCallBack
     */
    public static void getpingList(Context context, int userid, int id, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/comments?");
            sb.append("id=").append(id).append("&userid=").append(userid);
            sb.append("&currentPageStr=").append(currentPageStr).append("&maxCountStr=").append(maxCountStr)
                    .append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }


    /**
     * 获取子评论页
     *
     * @param context
     * @param id
     * @param currentPageStr
     * @param maxCountStr
     * @param rspCls
     * @param netCallBack
     */
    public static void getzipingList(Context context, int userid, int id, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/subcomments?");
            sb.append("id=").append(id).append("&userid=").append(userid);
            sb.append("&currentPageStr=").append(currentPageStr).append("&maxCountStr=").append(maxCountStr)
                    .append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }


    /**
     * 发布评论或者语音
     *
     * @param context
     * @param userid
     * @param id          词汇id
     * @param rspCls
     * @param netCallBack
     */
    public static void sendPinglun(Context context, int userid, int id, String type, int length, String url, String content, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", Integer.toString(id));
        map.put("userAgent", "android");
        map.put("type", type);
        map.put("length", Integer.toString(length));
        map.put("url", url);
        map.put("content", content);

        NetUtils.post(context, BASE_URL + "cstimes/app/publishcomment", map, null, netCallBack, rspCls);


    }


    /**
     * 发布子评论或者语音
     *
     * @param context
     * @param userid
     * @param id          词汇id
     * @param rspCls
     * @param netCallBack
     */
    public static void sendZiPinglun(Context context, int userid, int id, String type, int length, String url, String content, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", Integer.toString(id));
        map.put("userAgent", "android");
        map.put("type", type);
        map.put("length", Integer.toString(length));
        map.put("url", url);
        map.put("content", content);

        NetUtils.post(context, BASE_URL + "cstimes/app/publishsubcomment", map, null, netCallBack, rspCls);


    }


    /**
     * 点赞
     *
     * @param context
     * @param userid
     * @param id
     * @param rspCls
     * @param netCallBack
     */
    public static void addZan(Context context, int userid, int id, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", Integer.toString(id));
        map.put("userAgent", "android");


        NetUtils.post(context, BASE_URL + "cstimes/app/zancomment", map, null, netCallBack, rspCls);


    }


    /**
     * 修改密码
     *
     * @param context
     * @param mobile
     * @param password
     * @param rspCls
     * @param netCallBack
     */
    public static void editSecret(Context context, String mobile, String password, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {
        HashMap map = new HashMap();
        map.put("mobile", mobile);
        map.put("password", password);
        map.put("userAgent", "Android");


        NetUtils.post(context, BASE_URL + "cstimes/app/resetPassword", map, "注册", netCallBack, rspCls);
    }


    /**
     * 获取splash图片
     *
     * @param context
     * @param rspCls
     * @param netCallBack
     */
    public static void getLogo(Context context, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/start");


        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }

    /**
     * 获取我的学习
     *
     * @param context
     * @param rspCls
     * @param netCallBack
     */
    public static void getmyStudy(Context context, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/study");


        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }



    /**
     * 获取apk更新信息
     *
     * @param context
     * @param rspCls
     * @param netCallBack
     */
    public static void getUpdate(Context context, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/version");


        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }



    /**
     * 获取列表信息byid
     *
     * @param context
     * @param userid
     * @param currentPageStr
     * @param maxCountStr
     * @param rspCls
     * @param netCallBack    http://121.40.90.171/cstimes/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getDataList(Context context, int id, int type, int userid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/articles?");
            sb.append("userid=").append(userid);
            sb.append("&currentPageStr=").append(currentPageStr).append("&maxCountStr=").append(maxCountStr).append("&id=").append(id)
                    .append("&userAgent=")
                    .append("android").append("&type=").append(type);

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }




    /**
     * 预习课文
     *
     * @param context
     * @param userid
     * @param currentPageStr
     * @param maxCountStr
     * @param rspCls
     * @param netCallBack    http://121.40.90.171/cstimes/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getDatayykw(Context context, int id, int type, int userid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/articles?");
            sb.append("userid=").append(userid);
            sb.append("&currentPageStr=").append(currentPageStr).append("&maxCountStr=").append(maxCountStr).append("&parentid=").append(id)
                    .append("&userAgent=")
                    .append("android").append("&type=").append(type);

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }



    /**
     * 提交评论
     *
     * @param context
     * @param userid
     * @param id
     * @param rspCls
     * @param netCallBack
     */
    public static void submitplun(Context context, int userid, int id,  String score, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", Integer.toString(id));
        map.put("score", score);
        map.put("userAgent", "android");

        NetUtils.post(context, BASE_URL + "cstimes/app/courseTask", map, null, netCallBack, rspCls);


    }


    /**
     * 获取我的课程
     *
     * @param context
     * @param userid
     * @param rspCls
     * @param netCallBack http://121.40.90.171/cstimes/app/course?id=2245&userid=715
     */
    public static void getmyKeDetail(Context context, int userid,int catid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("cstimes/app/myCourses?");
            sb.append("userid=").append(userid);
            sb.append("&userAgent=")
                    .append("android");
            if (catid==87)
            {
                sb.append("&catid=").append(catid);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }
}
