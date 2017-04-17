package com.daocheng.girlshop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.entity.Update;
import com.daocheng.girlshop.net.DownloadManager;
import com.daocheng.girlshop.utils.Config;
import com.duowan.mobile.netroid.Listener;


import java.io.File;
import java.sql.Timestamp;

/**
 * 自定义弹出框
 * daiye
 * 2015/07/16
 */
public class ApkUpdateDialog
        extends Dialog implements View.OnClickListener {

    private Context context;
    private String url;
    private TextView content;

    public ApkUpdateDialog(Context context, String url) {
        super(context);
        this.context = context;
        this.url =url;
    }


    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        View rootView = LayoutInflater.from(context).inflate(R.layout.setting_asr_dialog, null);
//        LinearLayout content =(LinearLayout) rootView.findViewById(R.id.contentView);
//        content.addView(view);

        setContentView(R.layout.setting_update_dialog);

        ((TextView)findViewById(R.id.alertTitle)).setText(context.getResources().getString(R.string.about_update_text));//update.getVER_NAME() +
                content=  ((TextView)findViewById(R.id.content));
        content.setText("点击下载最新版apk");
        findViewById(R.id.button_cancel).setOnClickListener(this);
        findViewById(R.id.button_confrim).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_confrim:
                downloadApk(url);
                content.setText("0%");
                Config.setshidaishownew(false);
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

    private void downloadApk(String url)
    {

        DownloadManager.getInstance().download(null,DownloadManager.UPDATE, url, new Listener<Void>() {
            @Override
            public void onSuccess(Void response) {
                ApkUpdateDialog.this.dismiss();
                Uri uri = Uri.fromFile(new File(DownloadManager.mSaveAPkFile));
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                context.startActivity(intent);

            }

            @Override
            public void onProgressChange(long fileSize, long downloadedSize) {
                super.onProgressChange(fileSize, downloadedSize);
//                progress.setProgress((int) (downloadedSize * 1.0f / fileSize * 100));
                content.setGravity(Gravity.CENTER);
                content.setText((int) (downloadedSize * 1.0f / fileSize * 100)+"%");
            }
        });
    }



}
