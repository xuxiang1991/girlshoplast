package com.daocheng.girlshop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.entity.Update;
import com.daocheng.girlshop.myApplication;
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
    private TextView loading_tv_progress;
    private ProgressBar loading_pb_bar;

    public ApkUpdateDialog(Context context, String url) {
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

        ((TextView) findViewById(R.id.alertTitle)).setText(context.getResources().getString(R.string.about_update_text));//update.getVER_NAME() +
        loading_pb_bar = (ProgressBar) findViewById(R.id.loading_pb_bar);
        loading_tv_progress = (TextView) findViewById(R.id.loading_tv_progress);
        findViewById(R.id.button_cancel).setOnClickListener(this);
        findViewById(R.id.button_confrim).setOnClickListener(this);
        loading_pb_bar.setMax(100);
        loading_pb_bar.setProgress(0);

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

                    downloadApk(url);
                    loading_tv_progress.setText("0%");
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

    private void downloadApk(String url) {

        DownloadManager.getInstance().download(null, DownloadManager.UPDATE, url, new Listener<Void>() {
            @Override
            public void onSuccess(Void response) {
                ApkUpdateDialog.this.dismiss();


                File apkfile = new File(DownloadManager.mSaveAPkFile);


                Intent intent = new Intent(Intent.ACTION_VIEW);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(
                            context, myApplication.getContext().getPackageName()+".fileProvider", apkfile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                }

                context.startActivity(intent);



//
//                File apkfile = new File(DownloadManager.mSaveAPkFile);
//                Uri uri = Uri.fromFile(apkfile);
//                Intent intent = new Intent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setAction(Intent.ACTION_VIEW);
//
//                if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
//                    //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
//                    Uri apkUri =
//                            FileProvider.getUriForFile(context, "com.daocheng.girlshop.fileProvider", apkfile);
//                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//                } else {
//                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
//                }
//
//                context.startActivity(intent);

            }

            @Override
            public void onProgressChange(long fileSize, long downloadedSize) {
                super.onProgressChange(fileSize, downloadedSize);
//                progress.setProgress((int) (downloadedSize * 1.0f / fileSize * 100));
                loading_pb_bar.setProgress((int) (downloadedSize * 1.0f / fileSize * 100));
                loading_tv_progress.setText((int) (downloadedSize * 1.0f / fileSize * 100) + "%");
            }
        });
    }


}
