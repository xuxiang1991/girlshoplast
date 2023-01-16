package com.daocheng.girlshop.net;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;

import com.daocheng.girlshop.BuildConfig;
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


    public static final String BASE_URL = "https://www.cs66club.com/";//"http://114.55.5.20:8080"

    public static final String Pic_BASE_URL = "http://7vijhu.com1.z0.glb.clouddn.com/";//"http://114.55.5.20:8080"

//userid 默认 720

    /**
     * 登录
     *
     * @param context
     * @param netCallBack
     * @param rspCls      http://121.40.90.171/app/login?mobile=13962325335&password=3380066xx&userAgent=android
     */
    public static void getLogin(Context context, String mobile, String password, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

//        HashMap map = new HashMap();
//        map.put("mobile", mobile);
//        map.put("password", password);
//        map.put("userAgent", "android");
//        NetUtils.post(context, BASE_URL + "app/login", map, null, netCallBack, rspCls);


        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/login?");
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
        String url = BASE_URL + "app/getToken";
        NetUtils.post(context, url, null, null, netCallBack, rspCls);
    }

    public static void register(Context context, String mobile, String password, String head, String nickname, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {
        HashMap map = new HashMap();
        map.put("mobile", mobile);
        map.put("nickname", nickname);
        map.put("password", password);
        map.put("userAgent", "Android");
        if (!TextUtils.isEmpty(head))
            map.put("head", head);


//        StringBuilder sb = new StringBuilder(BASE_URL);
//        try {
//            sb.append("app/register?");
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
        NetUtils.post(context, BASE_URL + "app/register", map, "注册", netCallBack, rspCls);
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
            sb.append("app/mobileForUser?");
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
     * @param netCallBack    http://121.40.90.171/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getKeweekcoursesList(Context context, int userid, int catid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/weekcourses?");
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
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void getKeDetail(Context context, int userid, int id, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/course?");
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
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void JoinInKe(Context context, int userid, int id, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

//        StringBuilder sb = new StringBuilder(BASE_URL);
//        try {
//            sb.append("app/orderCourse?");
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

        NetUtils.post(context, BASE_URL + "app/orderCourse", map, null, netCallBack, rspCls);


    }


    /**
     * 获取我的课程
     *
     * @param context
     * @param userid
     * @param rspCls
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void getmyKeDetail(Context context, int userid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/myCourses?");
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
     * @param netCallBack    http://121.40.90.171/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getMycihuiList(Context context, int userid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/words?");
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
     * 获取我的词汇
     *
     * @param context
     * @param userid
     * @param currentPageStr
     * @param maxCountStr
     * @param rspCls
     * @param netCallBack    http://121.40.90.171/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getWordList(Context context, String level, int userid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/wordLesson?");
            sb.append("userid=").append(userid).append("&levelname=").append(level);
            sb.append("&currentPageStr=").append(currentPageStr).append("&maxCountStr=").append(maxCountStr)
                    .append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }

    /**
     * 获取等级
     *
     * @param context
     * @param userid
     * @param rspCls
     * @param netCallBack http://121.40.90.171/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getLevel(Context context, int userid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/wordLevel?");
            sb.append("userid=").append(userid);
            sb.append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, "加载中。。。", netCallBack, rspCls);


    }


    /**
     * 获取词汇详情
     *
     * @param context
     * @param userid
     * @param rspCls
     * @param netCallBack http://121.40.90.171/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getWordDetail(Context context, int lessonId, int userid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/passwords?");
            sb.append("userid=").append(userid).append("&lessonId=").append(lessonId);
            sb.append("&currentPageStr=").append(currentPageStr).append("&maxCountStr=").append(maxCountStr)
                    .append("&userAgent=")
                    .append("android");

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, "加载中。。。", netCallBack, rspCls);


    }


    /**
     * 删除词汇
     *
     * @param context
     * @param userid
     * @param id          词汇id
     * @param rspCls
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void deleteCihuiFromMy(Context context, int userid, int id, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", Integer.toString(id));
        map.put("userAgent", "android");

        NetUtils.post(context, BASE_URL + "app/delWord", map, null, netCallBack, rspCls);


    }

    /**
     * 首页内容
     *
     * @param context
     * @param userid
     * @param rspCls
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void getHomedata(Context context, int userid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/home?");
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
     * @param netCallBack    http://121.40.90.171/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getDataList(Context context, int type, int userid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/articles?");
            sb.append("userid=").append(userid);
            sb.append("&currentPageStr=").append(currentPageStr).append("&maxCountStr=").append(maxCountStr)
                    .append("&userAgent=")
                    .append("android").append("&type=").append(type);

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }

    public static void updateVideoSee(Context context, int userid, String id, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/updateScoreVedio?");
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
     * 获取热门歌曲列表
     * <p/>
     *
     * @param context
     * @param userid
     * @param currentPageStr
     * @param maxCountStr
     * @param rspCls
     * @param netCallBack    http://121.40.90.171/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getSongList(Context context, int type, int userid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/songs?");
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
     * 会议列表
     *
     * @param context
     * @param userid
     * @param rspCls
     * @param netCallBack http://121.40.90.171/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getMeetingList(Context context, int userid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/meeting?");
            sb.append("userid=").append(userid);


        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }


    /**
     * 获取是否作业做完
     *
     * @param context
     * @param type
     * @param userid
     * @param rspCls
     * @param netCallBack
     */
    public static void getworkOver(Context context, int type, int userid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/checkover?");
            sb.append("userid=").append(userid);
            sb.append("&userAgent=")
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
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void updateScope(Context context, int userid, int id, int updateScore, int type, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", Integer.toString(id));
        map.put("score", Integer.toString(updateScore));
        map.put("userAgent", "android");
        if (type != 0)
            map.put("type", Integer.toString(type));

        NetUtils.post(context, BASE_URL + "app/updateScore", map, null, netCallBack, rspCls);


    }


    /**
     * 考试结束
     *
     * @param context
     * @param rspCls
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void CompleteExam(Context context, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Config.getShidaiUserInfo().getUserid() + "");
        map.put("userAgent", "android");
        NetUtils.post(context, BASE_URL + "app/examfinish", map, null, netCallBack, rspCls);


    }

    /**
     * 获取书面作业
     *
     * @param context
     * @param userid
     * @param rspCls
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void getSmzy(Context context, int userid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("userAgent", "android");
//        if (BuildConfig.DEBUG)
//            map.put("debug", "1");

        NetUtils.post(context, BASE_URL + "app/writtenWork", map, null, netCallBack, rspCls);


    }


    /**
     * 书面作业提交
     *
     * @param context
     * @param userid
     * @param rspCls
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void RightSmzy(Context context, int userid, String id, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("userAgent", "android");
        map.put("id", id);

        NetUtils.post(context, BASE_URL + "app/right", map, null, netCallBack, rspCls);


    }

    /**
     * 加入生词表
     *
     * @param context
     * @param userid
     * @param word
     * @param rspCls
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void addtoWordbooke(Context context, int userid, String word, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("word", word);
        map.put("userAgent", "android");

        NetUtils.post(context, BASE_URL + "app/newWord", map, null, netCallBack, rspCls);


    }


    /**
     * 删除评论
     *
     * @param context
     * @param userid
     * @param id
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void deletePinglun(Context context, int userid, int id, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", id + "");
        map.put("userAgent", "android");

        NetUtils.post(context, BASE_URL + "app/delComment", map, null, netCallBack, rspCls);


    }

    /**
     * 删除子评论
     *
     * @param context
     * @param userid
     * @param id
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void deleteZiPinglun(Context context, int userid, int id, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", id + "");
        map.put("userAgent", "android");

        NetUtils.post(context, BASE_URL + "app/delSubComment", map, null, netCallBack, rspCls);


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
            sb.append("app/comments?");
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
            sb.append("app/subcomments?");
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

        NetUtils.post(context, BASE_URL + "app/publishcomment", map, null, netCallBack, rspCls);


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

        NetUtils.post(context, BASE_URL + "app/publishsubcomment", map, null, netCallBack, rspCls);


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


        NetUtils.post(context, BASE_URL + "app/zancomment", map, null, netCallBack, rspCls);


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


        NetUtils.post(context, BASE_URL + "app/resetPassword", map, "注册", netCallBack, rspCls);
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
            sb.append("app/start");


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
            sb.append("app/study");


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
            sb.append("app/version");


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
     * @param netCallBack    http://121.40.90.171/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getDataList(Context context, int id, int type, int userid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/articles?");
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
     * @param netCallBack    http://121.40.90.171/app/weekcourses?catid=87&currentPageStr=1&maxCountStr=1&userAgent=android
     */
    public static void getDatayykw(Context context, int id, int type, int userid, int currentPageStr, int maxCountStr, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/articles?");
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
    public static void submitplun(Context context, int userid, int id, String score, String content, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        HashMap map = new HashMap();
        map.put("userid", Integer.toString(userid));
        map.put("id", Integer.toString(id));
        map.put("score", score);
        map.put("userAgent", "android");
        map.put("content", content);

        NetUtils.post(context, BASE_URL + "app/courseTask", map, null, netCallBack, rspCls);


    }


    /**
     * 获取我的课程
     *
     * @param context
     * @param userid
     * @param rspCls
     * @param netCallBack http://121.40.90.171/app/course?id=2245&userid=715
     */
    public static void getmyKeDetail(Context context, int userid, int catid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("app/myCourses?");
            sb.append("userid=").append(userid);
            sb.append("&userAgent=")
                    .append("android");
            if (catid == 87) {
                sb.append("&catid=").append(catid);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);


    }
}
