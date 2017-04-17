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

public class Api {


    public static final String BASE_URL = "http://nzh.dig-data.com";//"http://114.55.5.20:8080"
    public static final String URL_head = "http://192.168.2.200:8080";


    /**
     * 获取文章列表
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getAdvertorialList(Context context, String keyword, String classId, int pageNo, String macId, String token, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/article/mobile/1.0/list?");
            if (keyword != null) {
                sb.append("keyword=");
                sb.append(URLEncoder.encode(keyword, HTTP.UTF_8)).append("&");
            }

            sb.append("pageNo=")
                    .append(pageNo)
                    .append("&macId=")
                    .append(macId);
            if (!TextUtils.isEmpty(classId)) {
                sb.append("&classId=")
                        .append(classId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 登录
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getLogin(Context context, String phone, String macid, String vertifiyCode, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("phone", phone);
        map.put("macId", macid);
        map.put("vertifiyCode", vertifiyCode);
        NetUtils.post(context, BASE_URL + "/nzh/user/mobile/1.0/login", map, null, netCallBack, rspCls);

    }


    /**
     * 获取验证码
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getvertifiyCode(Context context, String phone, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {


        NetUtils.post(context, BASE_URL + "/nzh/user/mobile/1.0/verifity/code?phone=" + phone + "&macId=" + macid, null, null, netCallBack, rspCls);

    }


    /**
     * 获取文章详情
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getAdvertorialDetail(Context context, int id, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/article/mobile/1.0/detail?")
                    .append("id=")
                    .append(id)
                    .append("&macId=")
                    .append(macid);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }

    /**
     * 点击图片加入购物车
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void addshopcart(Context context, String commodityId, String phone, String macid, String token, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("commodityId", commodityId);
        map.put("phone", phone);
        map.put("macId", macid);
        map.put("token", token);

        NetUtils.post(context, BASE_URL + "/nzh/article/mobile/1.0/cart/add", map, null, netCallBack, rspCls);

    }


    /**
     * 更新用户信息
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void UpdateUserinfo(Context context, String address, String portraintImage, String nickName, String gender, String constellation, String bloodType, String macId, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
      try
      {
          map.put("portraitImage", portraintImage);
          map.put("nickName",nickName);
          map.put("gender", gender);
          map.put("address", address);
          map.put("constellation", constellation);
          map.put("bloodType", bloodType);
          map.put("macId", macId);
      }catch (Exception e)
      {
          e.printStackTrace();
      }
        if (Config.getUserInfo() != null) {
            map.put("phone", Config.getUserInfo().getData().getPhone());
            map.put("userSign", Config.getUserInfo().getData().getUserSign());
            map.put("token", Config.getUserInfo().getData().getToken());
        }

        NetUtils.post(context, BASE_URL + "/nzh/user/mobile/1.0/update", map, null, netCallBack, rspCls);

    }


    /**
     * 文章关注/呵呵
     * Constant.IConstants
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void updateLikeforAdvertial(Context context, int id, int type, String action, String phone, String userSign, String macId, String token, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("id", id + "");
        map.put("type", type + "");
        map.put("action", action);
        map.put("phone", phone);
        map.put("userSign", userSign);
        map.put("macId", macId);
        map.put("token", token);

        NetUtils.post(context, BASE_URL + "/nzh/article/mobile/1.0/update/like", map, null, netCallBack, rspCls);

    }


    /**
     * 增加文章评论
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void addCommondity(Context context, String id, String phone, String userSign, String comment, String macId, String token, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("id", id);
        map.put("phone", phone);
        map.put("comment", comment);
        map.put("userSign", userSign);
        map.put("macId", macId);
        map.put("token", token);

        NetUtils.post(context, BASE_URL + "/nzh/article/mobile/1.0/comment/add", map, null, netCallBack, rspCls);

    }


    /**
     * 软文提交
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void addAdvertial(Context context, String coverImg, String title, String content, String phone, String userSign, String macId, String token, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("coverImg", coverImg);
        map.put("title", title);
        map.put("content", content);
        map.put("phone", phone);
        map.put("userSign", userSign);
        map.put("macId", macId);
        map.put("token", token);

        NetUtils.post(context, BASE_URL + "/nzh/article/mobile/1.0/soft/add", map, null, netCallBack, rspCls);

    }


    /**
     * 获取商品
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getGoods(Context context, int articleId, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/article/mobile/1.0/package?")
                    .append("articleId=")
                    .append(articleId)
                    .append("&macId=")
                    .append(macid);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 商品集加入购物车
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void addgoodstoshopcart(Context context, int articalID, String commodityIds, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("articleId", Integer.toString(articalID));
        map.put("commodityIds", commodityIds);
        map.put("phone", Config.getUserInfo().getData().getPhone());
        map.put("macId", macid);
        map.put("token", Config.getUserInfo().getData().getToken());
        map.put("userSign", Config.getUserInfo().getData().getUserSign());

        NetUtils.post(context, BASE_URL + "/nzh/cart/mobile/1.0/add", map, null, netCallBack, rspCls);

    }


    /**
     * 获取地址列表
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getAddressList(Context context, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/user/mobile/1.0/address/list?")
                    .append("macId=")
                    .append(macid);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 新增地址
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void addAddress(Context context, boolean isDefault, String reciver, String recivePhone, String city, String address, String post_code, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("reciver", reciver);
        map.put("recivePhone", recivePhone);
        map.put("city", city);
        map.put("address", address);
        map.put("postCode", post_code);
        map.put("phone", Config.getUserInfo().getData().getPhone());
        map.put("isDefault", isDefault ? "Y" : "N");
        map.put("macId", macid);
        map.put("token", Config.getUserInfo().getData().getToken());
        map.put("userSign", Config.getUserInfo().getData().getUserSign());

        NetUtils.post(context, BASE_URL + "/nzh/user/mobile/1.0/address/add", map, "信息提交中", netCallBack, rspCls);

    }


    /**
     * 新增地址
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void updateAddress(Context context, boolean isDefault, String addressid, String reciver, String recivePhone, String city, String address, String post_code, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("addressId", addressid);
        map.put("reciver", reciver);
        map.put("recivePhone", recivePhone);
        map.put("city", city);
        map.put("isDefault", isDefault ? "Y" : "N");
        map.put("address", address);
        map.put("postCode", post_code);
        map.put("phone", Config.getUserInfo().getData().getPhone());
        map.put("macId", macid);
        map.put("token", Config.getUserInfo().getData().getToken());
        map.put("userSign", Config.getUserInfo().getData().getUserSign());

        NetUtils.post(context, BASE_URL + "/nzh/user/mobile/1.0/address/update", map, "信息提交中", netCallBack, rspCls);

    }


    /**
     * 获取个人购物车
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getshopCart(Context context, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/cart/mobile/1.0/view?")

                    .append("macId=")
                    .append(macid);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, "加载", netCallBack, rspCls);

    }


    /**
     * 删除购物车商品
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void deletegoodsfromshopcart(Context context, String commodityIds, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("commodityIds", commodityIds);
        map.put("phone", Config.getUserInfo().getData().getPhone());
        map.put("macId", macid);
        map.put("token", Config.getUserInfo().getData().getToken());
        map.put("userSign", Config.getUserInfo().getData().getUserSign());

        NetUtils.post(context, BASE_URL + "/nzh/cart/mobile/1.0/delete", map, "删除中", netCallBack, rspCls);

    }


    /**
     * 修改购物车商品
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void editgoodsfromshopcart(Context context, String commoditys, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("commoditys", commoditys);
        map.put("phone", Config.getUserInfo().getData().getPhone());
        map.put("macId", macid);
        map.put("token", Config.getUserInfo().getData().getToken());
        map.put("userSign", Config.getUserInfo().getData().getUserSign());

        NetUtils.post(context, BASE_URL + "/nzh/cart/mobile/1.0/update", map, "修改中", netCallBack, rspCls);

    }


    /**
     * 首屏内容推荐
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getFirstContent(Context context, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/article/mobile/1.0/home/primary?")

                    .append("macId=")
                    .append(macid);


        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 首屏内容推荐
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getSecondContent(Context context, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/article/mobile/1.0/home/second?")

                    .append("macId=")
                    .append(macid);


        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 生成订单
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void submitOrder(Context context, String commodityIds, String payType, String addressId, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("commodityIds", commodityIds);
        map.put("payType", payType);
        map.put("addressId", addressId);
        map.put("phone", Config.getUserInfo().getData().getPhone());
        map.put("macId", macid);
        map.put("token", Config.getUserInfo().getData().getToken());
        map.put("userSign", Config.getUserInfo().getData().getUserSign());

        NetUtils.post(context, BASE_URL + "/nzh/order/mobile/1.0/generate", map, null, netCallBack, rspCls);

    }


    /**
     * 确认订单
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void confirmOrder(Context context, String orderNo, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/order/mobile/1.0/status?")

                    .append("macId=")
                    .append(macid)
                    .append("&orderNo=")
                    .append(orderNo);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, "确认中", netCallBack, rspCls);

    }


    /**
     * 获取订单列表
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getOrderlist(Context context, String status, int pageNo, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/order/mobile/1.0/list?")

                    .append("macId=")
                    .append(macid)
                    .append("&pageNo=")
                    .append(pageNo);
            if (status != null) {
                sb.append("&status=")
                        .append(status);
            }
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }

    /**
     * 获取订单详情
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getOrderDetail(Context context, String orderNo, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/order/mobile/1.0/detail?")

                    .append("macId=")
                    .append(macid)
                    .append("&orderNo=")
                    .append(orderNo);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 订单付款
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void payfororder(Context context, String payType, String orderNo, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("payType", payType);
        map.put("orderNo", orderNo);
        map.put("phone", Config.getUserInfo().getData().getPhone());
        map.put("macId", macid);
        map.put("token", Config.getUserInfo().getData().getToken());
        map.put("userSign", Config.getUserInfo().getData().getUserSign());

        NetUtils.post(context, BASE_URL + "/nzh/order/mobile/1.0/pay", map, null, netCallBack, rspCls);

    }


    /**
     * 获取达人列表
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getDarenData(Context context, String keyword, int pageNo, String macId, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/user/mobile/1.0/character/dr?");


            sb.append("macId=")
                    .append(Utils.getIMEI(context))
                    .append("&keyword=")
                    .append(keyword)
                    .append("&pageNo=")
                    .append(pageNo);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 获取广告
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getAdvertis(Context context, String macId, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/user/mobile/1.0/advertisement/discovery/list?");


            sb.append("token=")
                    .append(Config.getUserInfo().getData().getToken())
                    .append("&macId=")
                    .append(macId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 获取时间轴
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getTimeLineById(Context context, int drId, int pageNo, String macId, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/user/mobile/1.0/character/timeline?");
            sb.append("macId=")
                    .append(macId)
                    .append("&drId=")
                    .append(drId)
                    .append("&pageNo=")
                    .append(pageNo);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 获取时间轴
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void changeAttach(Context context, int drId, boolean isattach, String macId, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("drId", Integer.toString(drId));
        map.put("macId", macId);
        if (Config.getUserInfo() != null) {
            map.put("phone", Config.getUserInfo().getData().getPhone());

            map.put("token", Config.getUserInfo().getData().getToken());
            map.put("userSign", Config.getUserInfo().getData().getUserSign());
        }
        String attachUrl = null;
        if (isattach)
            attachUrl = BASE_URL + "/nzh/user/mobile/1.0/dr/follow/focus";
        else
            attachUrl = BASE_URL + "/nzh/user/mobile/1.0/dr/follow/cancel";
        NetUtils.post(context, attachUrl, map, null, netCallBack, rspCls);

    }


    /**
     * 意见反馈
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void addfeedback(Context context, String content, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("content", content);
        map.put("macId", macid);
        if (Config.getUserInfo() != null) {
            map.put("phone", Config.getUserInfo().getData().getPhone());

            map.put("token", Config.getUserInfo().getData().getToken());
            map.put("userSign", Config.getUserInfo().getData().getUserSign());
        }
        NetUtils.post(context, BASE_URL + "/nzh/user/mobile/1.0/feedback", map, "提交", netCallBack, rspCls);

    }


    /**
     * 获取已关注达人
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getAttachDaren(Context context, String macId, String pageNo, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/user/mobile/1.0/dr/follow/list?");
            sb.append("macId=")
                    .append(macId)
                    .append("&pageNo=")
                    .append(pageNo);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 收藏
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void updateArticleFavourite(Context context, boolean isfav, String articleId, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("articleId", articleId);
        map.put("macId", macid);
        if (Config.getUserInfo() != null) {
            map.put("phone", Config.getUserInfo().getData().getPhone());

            map.put("token", Config.getUserInfo().getData().getToken());
            map.put("userSign", Config.getUserInfo().getData().getUserSign());
        }
        String str = null;
        if (isfav) {
            str = BASE_URL + "/nzh/article/mobile/1.0/favourite/add";
        } else {
            str = BASE_URL + "/nzh/article/mobile/1.0/favourite/cancel";
        }

        NetUtils.post(context, str, map, null, netCallBack, rspCls);

    }

    /**
     * 获取我的信息
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getUserDetail(Context context, String macId, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/user/mobile/1.0/my/info?");
            sb.append("macId=")
                    .append(macId);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }

    /**
     * 获取文章收藏列表
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getfavlist(Context context, String macId, String pageNo, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/article/mobile/1.0/favourite/list?");
            sb.append("macId=")
                    .append(macId)
                    .append("&pageNo=")
                    .append(pageNo);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 获取文章足迹
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getfootlist(Context context, String macId, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/user/mobile/1.0/foot/mark?");
            sb.append("macId=")
                    .append(macId);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 评论关注/呵呵
     * Constant.IConstants
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void updateLikeforComment(Context context, int id, int type, String action, String phone, String userSign, String macId, String token, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("commentId", Integer.toString(id));
        map.put("type", type + "");
        map.put("action", action);
        map.put("phone", phone);
        map.put("userSign", userSign);
        map.put("macId", macId);
        map.put("token", token);

        NetUtils.post(context, BASE_URL + "/nzh/article/mobile/1.0/update/comment/like", map, null, netCallBack, rspCls);

    }


    /**
     * 获取全文搜索
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getAllbyKeywords(Context context, String keyword, String macid, int pageNo, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/search/mobile/1.0/all?")
                    .append("keyword=")
                    .append(URLEncoder.encode(keyword, HTTP.UTF_8))
                    .append("&macId=")
                    .append(macid)
                    .append("&pageNo=")
                    .append(pageNo);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 获取个人信息
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getUserInfo(Context context, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/user/mobile/1.0/detail?")

                    .append("macId=")
                    .append(macid);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 取消订单
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void order_cancel(Context context, String action, String orderNo, String macId, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("orderNo", orderNo);
        map.put("action", action);
        if (Config.getUserInfo() != null) {
            map.put("phone", Config.getUserInfo().getData().getPhone());
            map.put("userSign", Config.getUserInfo().getData().getUserSign());
            map.put("macId", macId);
            map.put("token", Config.getUserInfo().getData().getToken());
        }


        NetUtils.post(context, BASE_URL + "/nzh/order/mobile/1.0/up", map, null, netCallBack, rspCls);

    }


    /**
     * 取消订单
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void comfirm_goods(Context context, String orderNo, String macId, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("orderNo", orderNo);
        if (Config.getUserInfo() != null) {
            map.put("phone", Config.getUserInfo().getData().getPhone());
            map.put("userSign", Config.getUserInfo().getData().getUserSign());
            map.put("macId", macId);
            map.put("token", Config.getUserInfo().getData().getToken());
        }


        NetUtils.post(context, BASE_URL + "/nzh/order/mobile/1.0/confirm", map, null, netCallBack, rspCls);

    }


    /**
     * 获取购物车信息
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void getMyOwnShopCart(Context context, String macid, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append("/nzh/cart/mobile/1.0/view?")

                    .append("macId=")
                    .append(macid);
            if (Config.getUserInfo() != null) {
                sb.append("&token=")
                        .append(Config.getUserInfo().getData().getToken())
                        .append("&phone=")
                        .append(Config.getUserInfo().getData().getPhone())
                        .append("&userSign=")
                        .append(Config.getUserInfo().getData().getUserSign());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        NetUtils.post(context, sb.toString(), null, null, netCallBack, rspCls);

    }


    /**
     * 删除地址
     *
     * @param context
     * @param netCallBack
     * @param rspCls
     */
    public static void deleteAddress(Context context, String addressId, String macId, final Class<?> rspCls, final NetUtils.NetCallBack<ServiceResult> netCallBack) {

        HashMap map = new HashMap();
        map.put("addressId", addressId);
        if (Config.getUserInfo() != null) {
            map.put("phone", Config.getUserInfo().getData().getPhone());
            map.put("userSign", Config.getUserInfo().getData().getUserSign());
            map.put("macId", macId);
            map.put("token", Config.getUserInfo().getData().getToken());
        }


        NetUtils.post(context, BASE_URL + "/nzh/user/mobile/1.0/address/delete", map, null, netCallBack, rspCls);

    }

}
