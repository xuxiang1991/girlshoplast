package com.daocheng.girlshop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.entity.Update;
import com.daocheng.girlshop.net.DownloadManager;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.video.View.JCVideoPlayerStandardShowShareButtonAfterFullscreen;
import com.duowan.mobile.netroid.Listener;


import java.io.File;
import java.sql.Timestamp;

import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 自定义弹出框
 * daiye
 * 2015/07/16
 */
public class UpdateDialog
        extends Dialog implements View.OnClickListener {

    private Context context;
    private String url;
    private Button button_confrim;
    private boolean haslocal;
    private TextView loading_tv_progress;
    private ProgressBar loading_pb_bar;


    public UpdateDialog(Context context, String url) {
        super(context);
        this.context = context;
        this.url = url;
    }


    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        View rootView = LayoutInflater.from(context).inflate(R.layout.setting_asr_dialog, null);
//        LinearLayout content =(LinearLayout) rootView.findViewById(R.id.contentView);
//        content.addView(view);

        setContentView(R.layout.setting_update_dialog);

        ((TextView) findViewById(R.id.alertTitle)).setText("精彩视频");
        button_confrim = (Button) findViewById(R.id.button_confrim);
        loading_pb_bar = (ProgressBar) findViewById(R.id.loading_pb_bar);
        loading_tv_progress = (TextView) findViewById(R.id.loading_tv_progress);
        findViewById(R.id.button_cancel).setOnClickListener(this);
        button_confrim.setOnClickListener(this);

        loading_pb_bar.setMax(100);
        loading_pb_bar.setProgress(0);

        setCanceledOnTouchOutside(false);
        if (url.equals(Config.getshidaiVideo()))

            haslocal = true;


        if (haslocal) {
            loading_pb_bar.setProgress(100);
            button_confrim.setText("播放");
        }

        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置

        lp.width = (int) (Config.width * 0.8); // 宽度

        dialogWindow.setAttributes(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_confrim:
                if (!haslocal)
                    downloadApk(url);
                else
                    JCFullScreenActivity.startActivity(context,
                            Config.getshidaiVideoL(),
                            JCVideoPlayerStandard.class, "精彩视频");
                break;


            case R.id.button_cancel:
                Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
                Config.setUpdateTIMESTAMP(now.getTime());//记录取消的当前时间
                this.dismiss();
                break;
        }
    }

    @Override
    public void setTitle(CharSequence title) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void show() {
        if (context != null && ((Activity) context).isFinishing()) {
            return;
        }
        setCanceledOnTouchOutside(true);
        super.show();
    }

    private void downloadApk(final String url) {
        button_confrim.setClickable(false);
        final String fileName = DownloadManager.mSaveVedioDirPath + System.currentTimeMillis() + "_video.mp4";

        DownloadManager.getInstance().download(fileName, DownloadManager.VIDEO, url, new Listener<Void>() {
            @Override
            public void onSuccess(Void response) {

                Config.setshidaiVideo(url);
                Config.setshidaiVideoL(fileName);
                button_confrim.setClickable(true);
                button_confrim.setText("播放");
                button_confrim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JCFullScreenActivity.startActivity(context,
                                Config.getshidaiVideoL(),
                                JCVideoPlayerStandard.class, "精彩视频");
                    }
                });

            }

            @Override
            public void onProgressChange(long fileSize, long downloadedSize) {
                super.onProgressChange(fileSize, downloadedSize);
//                progress.setProgress((int) (downloadedSize * 1.0f / fileSize * 100));

                loading_pb_bar.setProgress((int) (downloadedSize * 1.0f / fileSize * 100));
                loading_tv_progress.setText((int) (downloadedSize * 1.0f / fileSize * 100) + "%");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                button_confrim.setClickable(true);
            }


        });
    }


}
