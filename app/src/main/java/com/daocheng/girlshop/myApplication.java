package com.daocheng.girlshop;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;


import com.daocheng.girlshop.activity.MainActivity;
import com.daocheng.girlshop.entity.Location;
import com.daocheng.girlshop.utils.BoardManager;
import com.daocheng.girlshop.utils.Constant;
import com.daocheng.girlshop.utils.CrashHandler;
import com.iflytek.cloud.SpeechUtility;
import com.inpor.fastmeetingcloud.receiver.HstApplication;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.SMSSDK;

/**
 * 项目名称：girlshop
 * 类描述：app属性，应用入口
 * 创建人：Dove
 * 创建时间：2016/2/29 15:55
 * 修改人：Dove
 * 修改时间：2016/2/29 15:55
 * 修改备注：
 */
public class myApplication extends Application {
    public File cacheFile = null;
    private static Context mContext;

    public Vibrator mVibrator;


//    private static myApplication sApp;
//
//    public synchronized static myApplication getApp() {
//        return sApp;
//    }


    private List<Activity> mList = new LinkedList<Activity>();
    private static myApplication instance;

    private static myApplication self;


    private CrashHandler catchHandler;


    @Override
    public void onCreate() {


//        SMSSDK.initSDK(this, "1406c7fa416d9", "089ea3f88c8e64efe54972088c838352");

        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        SpeechUtility.createUtility(myApplication.this, "appid=" + getString(R.string.app_id));

        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);


        super.onCreate();
        MobSDK.init(this);

        self = this;
        mContext = getApplicationContext();

        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());//用来获取全局的错误处理


        initCache();//imageloader 初始化
        initPlatform();

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        HstApplication.initHstApplication(this);


    }


    private void initCache() {
        File cacheDir = StorageUtils.getCacheDirectory(self);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true)
//                .showImageOnLoading(R.drawable.loading_photo)
                .showImageOnFail(R.drawable.loading_photo)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .discCache(new com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache(cacheDir))
                .discCacheFileCount(10000).threadPoolSize(5).build();


        // default
        ImageLoader.getInstance().init(config);
    }

    private void initPlatform() {

        UMConfigure.init(this,"56d56cb967e58e3d7a001308"
                ,"umeng", UMConfigure.DEVICE_TYPE_PHONE,"");

        PlatformConfig.setWeixin("wx462d77ba9d7383c6", "f3faf974207b28499ba906257df74284");
        //微信 appid appsecret
        //新浪微博 appkey appsecret    appid appkey appsecret
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        // qq qzone appid appkey

    }







    public static Location location;

    public static Location getLocationMessage() {
        return location;
    }

    public void setLocationMessage(Location str) {
        location = str;
    }


//    public static myApplication getInstance() {
//        return self;
//    }

    public static Context getContext() {
        return mContext;
    }




    public synchronized static myApplication getInstance() {
        if (null == instance) {
            instance = new myApplication();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }


    public void exitExceptforMain() {
        try {
            for (Activity activity : mList) {
                if (activity != null && !(activity instanceof MainActivity))
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void restart() {
        exitExceptforMain();

    }


    public static DisplayImageOptions getDefaultDisplayImageOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(new ColorDrawable(Color.parseColor("#f0f0f0")))
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(500)) // 设置图片渐显的时间
//                .delayBeforeLoading(300)  // 下载前的延迟时间
                .build();
        return options;
    }


    private UploadManager mUploadManager;

    public UploadManager getUploadManager() {
        if (mUploadManager == null) {
            Configuration config = new Configuration.Builder()
                    .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认 256K
                    .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认 512K
                    .connectTimeout(10) // 链接超时。默认 10秒
                    .responseTimeout(60) // 服务器响应超时。默认 60秒
//                    .recorder(recorder)  // recorder 分片上传时，已上传片记录器。默认 null
//                    .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                    .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                    .build();
            // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
            mUploadManager = new UploadManager(config);
        }
        return mUploadManager;
    }

}
