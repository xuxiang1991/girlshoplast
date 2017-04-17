package com.daocheng.girlshop.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.shidai.loginActivity;
import com.daocheng.girlshop.net.DownloadManager;
import com.daocheng.girlshop.utils.Config;

import java.io.File;


/**
 * 广告页
 * Created by Dove on 2015/9/10.
 */
public class SplashActivity extends BaseActivity {

    private ImageView iv_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







//        ImageLoaderManager.getImage(iv_logo, DownloadManager.mSaveLogoFile);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextPage();
            }
        }, 2000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setupViews() {
        iv_logo=(ImageView)findViewById(R.id.iv_logo);
    }

    @Override
    protected void initialized() {


//        ImageLoader.getInstance().displayImage("http://www.csufo.com:8099/up/2015041122424880064807a928a1b83111.jpg",iv_logo);

        final File file = new File(DownloadManager.mSaveLogoFile);
        if (file.exists()) {
            iv_logo.setImageURI(Uri.fromFile(file));
        } else {
            com.daocheng.girlshop.utils.Config.setCurrentLOGO("");
            iv_logo.setImageResource(R.drawable.time_welcome);
        }
    }

    private void nextPage() {
        if (self.isFinishing()) {
            return;
        }
        if (Config.isFirst()) {
//            createShortcut();
        }
        if (Config.getShidaiUserInfo()!=null)
        {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }else
        {
            startActivity(new Intent(SplashActivity.this, loginActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        finish();
    }



    /**
     * 创建桌面快捷方式
     */
    private void createShortcut() {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        // 不允许重复创建
        shortcut.putExtra("duplicate", false);
        // 指定快捷方式的启动对象
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(this.getPackageName(), this.getClass().getName());
        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        // 快捷方式的图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.logo);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        // 发出广播
        sendBroadcast(shortcut);
        // 将第一次启动的标识设置为false
//        Config.setFirst(false);
    }
}
