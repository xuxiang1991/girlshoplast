package com.daocheng.girlshop.net;

import android.os.Environment;

import com.daocheng.girlshop.myApplication;
import com.daocheng.girlshop.utils.Netroid;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.RequestQueue;
import com.duowan.mobile.netroid.request.FileDownloadRequest;
import com.duowan.mobile.netroid.toolbox.FileDownloader;


import java.io.File;

/**
 * Created by eajon on 2015/7/9.
 */
public class DownloadManager {

    private static FileDownloader mDownloder;
    public static final String mSaveDirPath = Environment.getExternalStorageDirectory() + "/cooke/";

    public static final String mSaveLogoDirPath = Environment.getExternalStorageDirectory() + "/cooke/logo/";
    public static final String mSaveAPKDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cooke/update/";
    public static final String mSaveVedioDirPath = Environment.getExternalStorageDirectory() + "/cooke/video/";
    public static final String mSaveRecodeDirPath = Environment.getExternalStorageDirectory() + "/cooke/netrecoder/";
    public static final String mSaveLogoFile = mSaveLogoDirPath + "logo.jpg";
    public static final String mSaveAPkFile = mSaveAPKDirPath + "cooke.apk";
    //    private static String downloadBaseUri = "http://192.168.1.225:8088/imuslim/Public/res/quran/";
    private static String downloadBaseUri = "http://www.csufo.com:8001/Public/res/quran/";


    public static final int LOGO = 0;
    public static final int UPDATE = 4;
    public static final int VIDEO=5;
    public static final int RECODER=6;

    private static DownloadManager self = null;

    public DownloadManager() {
        init();
    }

    public static DownloadManager getInstance() {
        if (null == self) {
            self = new DownloadManager();
        }
        return self;
    }

    private void init() {
        RequestQueue queue = Netroid.newRequestQueue(myApplication.getContext(), null);
        mDownloder = new FileDownloader(queue, 1) {
            @Override
            public FileDownloadRequest buildRequest(String storeFilePath, String url) {
                return new FileDownloadRequest(storeFilePath, url) {
                    @Override
                    public void prepare() {
                        addHeader("Accept-Encoding", "identity");
                        super.prepare();
                    }
                };
            }
        };

        createFile();
    }

    private static void createFile() {
        File downloadDir = new File(mSaveDirPath);
        if (!downloadDir.exists()) downloadDir.mkdir();
        File downloadLogoDir = new File(mSaveLogoDirPath);
        if (!downloadLogoDir.exists()) downloadLogoDir.mkdir();
        File downloadAPKDir = new File(mSaveAPKDirPath);
        if (!downloadAPKDir.exists()) downloadAPKDir.mkdir();
        File downloadVideoDir=new File(mSaveVedioDirPath);
        if (!downloadVideoDir.exists()) downloadVideoDir.mkdir();
        File downloadRecoderDir=new File(mSaveRecodeDirPath);
        if (!downloadRecoderDir.exists()) downloadRecoderDir.mkdir();
        if (new File(mSaveAPkFile).exists()) {
            new File(mSaveAPkFile).delete();
        }
        if (new File(mSaveLogoFile).exists()) {
            new File(mSaveLogoFile).delete();
        }
    }



    public static FileDownloader.DownloadController download(String local,int type, String fileName, Listener<Void> listener) {
        FileDownloader.DownloadController controller = null;
        createFile();
        switch (type) {

            case LOGO:
                controller = mDownloder.add(mSaveLogoFile, fileName, listener);
                break;
            case UPDATE:
                controller = mDownloder.add(mSaveAPkFile, fileName, listener);
                break;
            case VIDEO:
                controller = mDownloder.add(local, fileName, listener);
                break;
            case RECODER:
                if (!fileName.contains(ShidaiApi.Pic_BASE_URL))
                {
                    fileName=ShidaiApi.Pic_BASE_URL+fileName;
                }
                controller = mDownloder.add(local, fileName, listener);
                break;
        }
        return controller;
    }

    public void clearAllDownloadTask() {
        mDownloder.clearAll();
    }

    public static void makeDir(String directorypath) {
        File f = new File(directorypath);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
    }

    public static void removeAllTask() {
        mDownloder.clearAll();
    }
}
