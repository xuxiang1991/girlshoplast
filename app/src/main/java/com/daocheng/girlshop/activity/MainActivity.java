package com.daocheng.girlshop.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.shidai.loginActivity;
import com.daocheng.girlshop.dialog.ApkUpdateDialog;
import com.daocheng.girlshop.dialog.MessageDialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.Logo;
import com.daocheng.girlshop.entity.shdiai.UpdateApk;
import com.daocheng.girlshop.fragment.Frag;
import com.daocheng.girlshop.fragment.shidai.WordClassFragment;
import com.daocheng.girlshop.fragment.shidai.ciHuiFragment;
import com.daocheng.girlshop.fragment.shidai.dingKeFragment;
import com.daocheng.girlshop.fragment.shidai.fuxiFragment;
import com.daocheng.girlshop.fragment.shidai.myClassFragment;
import com.daocheng.girlshop.fragment.shidai.myKeFragment;
import com.daocheng.girlshop.fragment.shidai.myStudyFragment;
import com.daocheng.girlshop.fragment.shidai.shidaiHomeFragment;
import com.daocheng.girlshop.jpush.ExampleUtil;
import com.daocheng.girlshop.myApplication;
import com.daocheng.girlshop.net.DownloadManager;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.BoardManager;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Utils;
import com.daocheng.girlshop.view.NoScrollViewPager;
import com.daocheng.girlshop.voice.speech.EvaluatorManager;
import com.daocheng.girlshop.voice.speech.SpeechManager;
import com.duowan.mobile.netroid.Listener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import java.io.IOException;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by xuxiang1 on 2015/9/10.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {


    private Fragment homeFragment;

    private myKeFragment mykefragment;
    private WordClassFragment cihuifragment;
    private shidaiHomeFragment shidaihomefragment;
    private fuxiFragment fuxifragment;

    private dingKeFragment dingke;
    private myStudyFragment mystudy;
    private myClassFragment myclass;


    private NoScrollViewPager mPager;
    private RadioGroup mGroup;

    private BroadcastReceiver indexofcast;

    public static boolean isForeground = false;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void setupViews() {

        mPager = (NoScrollViewPager) findViewById(R.id.content);
        mGroup = (RadioGroup) findViewById(R.id.group);
        mGroup.setOnCheckedChangeListener(new CheckedChangeListener());
        mGroup.check(R.id.one);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mPager.setOnPageChangeListener(new PageChangeListener());
        mPager.setOffscreenPageLimit(4);

    }

    @Override
    protected void initialized() {

//        loadLogo();

        initIndexBroadcast();
//        BoardManager.init(this);
//        SpeechManager.getInstance(self);
//        EvaluatorManager.getInstance(self);

        registerMessageReceiver();  // used for receive msg
        loadLogo();
        updateApk();
    }

    //logo图片下载
    private void loadLogo() {
        ShidaiApi.getLogo(self, Logo.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) {
                final Logo logo = (Logo) rspData;
                if (!com.daocheng.girlshop.utils.Config.getCurrentLOGO().equals(logo.getPic())) {
                    DownloadManager.getInstance().download(null, DownloadManager.LOGO, logo.getPic(), new Listener<Void>() {
                        @Override
                        public void onSuccess(Void response) {
                            com.daocheng.girlshop.utils.Config.setCurrentLOGO(logo.getPic());
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }

                        @Override
                        public void onProgressChange(long fileSize, long downloadedSize) {

                        }
                    });
                }
            }

            @Override
            public void failed(String msg) {
                Toast.makeText(self, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateApk() {
        ShidaiApi.getUpdate(self, UpdateApk.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if (rspData != null) {
                    UpdateApk ua = (UpdateApk) rspData;
                    if (Utils.getClientVersionCode(self) < ua.getVersion() && !TextUtils.isEmpty(ua.getUrl())) {
                        ApkUpdateDialog dialog = new ApkUpdateDialog(self, ua.getUrl());
                        dialog.show();

                    }
                }
            }

            @Override
            public void failed(String msg) {


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
    }


    @Override
    protected void onStop() {

        super.onStop();
    }

    private class CheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (Config.getShidaiUserInfo() != null && Config.getShidaiUserInfo().getLevel().contains("游客")) {
                showToast("对不起，您是游客身份，解锁更多功能，请联系0512-52952008");
                return;
            }

            switch (checkedId) {
                case R.id.one:
                    mPager.setCurrentItem(0);

                    break;
                case R.id.two:
                    mPager.setCurrentItem(1);
                    break;
                case R.id.three:
                    mPager.setCurrentItem(2);
                    break;
//                case R.id.four:
//                    mPager.setCurrentItem(3);
//                    break;
                case R.id.five:
                    mPager.setCurrentItem(3);
                    break;

            }
        }
    }


    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mGroup.check(R.id.one);
                    break;
                case 1:
                    mGroup.check(R.id.two);
                    break;
                case 2:
                    mGroup.check(R.id.three);
                    break;
//                case 3:
//                    mGroup.check(R.id.four);
//                    break;
                case 3:
                    mGroup.check(R.id.five);
                    break;

            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if (shidaihomefragment == null)
                    shidaihomefragment = new shidaiHomeFragment();
                return shidaihomefragment;
            } else if (position == 1) {
                if (cihuifragment == null)
                    cihuifragment = new WordClassFragment();
                return cihuifragment;
            } else if (position == 2) {
                if (mystudy == null) {
                    mystudy = new myStudyFragment();
                }
                return mystudy;
            }
//            else if (position == 3) {
//                if (dingke == null)
//                    dingke = new dingKeFragment();
//                return dingke;
//            }
            else if (position == 3) {
                if (myclass == null) {
                    myclass = new myClassFragment();
                }
                return myclass;
            } else {
                Frag frag = new Frag();
                Bundle bundle = new Bundle();
                bundle.putString("key", "hello world " + position);
                frag.setArguments(bundle);
                return frag;
            }


        }

        @Override
        public int getCount() {
            return 5;
        }
    }


    //注册接收订单广播
    private void initIndexBroadcast() {
        indexofcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent != null) {
                    int index = intent.getIntExtra("index", 0);
                    mPager.setCurrentItem(index);
//                    ActivityManager.getScreenManager().popTopActivity();
                    myApplication.getInstance().exitExceptforMain();
                }

            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(com.daocheng.girlshop.utils.Constant.HOMEINDEX);
        self.registerReceiver(indexofcast, filter);
    }

    @Override
    protected void onResume() {
        isForeground = true;


        if (Config.getRegister() == null) {
            String rid = JPushInterface.getRegistrationID(getApplicationContext());
            if (!rid.isEmpty()) {
                Config.setRegister(rid);
//               showShortToast(rid);
            } else {
                Toast.makeText(this, "Get registration fail, JPush init failed!", Toast.LENGTH_SHORT).show();
            }
        }

        super.onResume();

    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpeechManager.getInstance(self).Destroy();//语音读写进程销毁
        EvaluatorManager.getInstance(self).Destroy();//语音评测销毁
        self.unregisterReceiver(indexofcast);
        unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onBackPressed() {
        MessageDialog dialog = new MessageDialog(self, "", "再学5分钟吧？", MessageDialog.EXIT, new MessageDialog.onRequest() {
            @Override
            public void back() {
                MobclickAgent.onKillProcess(self);

                System.exit(0);
            }
        });

        dialog.show();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                setCostomMsg(showMsg.toString());
            }
        }
    }

    private void setCostomMsg(String msg) {
        showToast(msg);
    }
}

