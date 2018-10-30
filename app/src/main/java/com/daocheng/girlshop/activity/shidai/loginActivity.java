package com.daocheng.girlshop.activity.shidai;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.activity.MainActivity;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.shidaiUser;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.view.FloatTextToast;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.CommonDialog;
import cn.smssdk.gui.RegisterPage;

/**
 * 登录界面
 * Created by Dove on 2016/6/18.
 */
public class loginActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    private TextView tv_regist;
    private EditText ed_username, ed_secret;
    private CheckBox cb_rememberSecret;
    private Button bt_login;
    private TextView tv_getsecret;

    //此APPKEY仅供测试使用，且不定期失效，请到mob.com后台申请正式APPKEY
    private static String APPKEY = "1406c7fa416d9";

    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "089ea3f88c8e64efe54972088c838352";

    private Dialog pd;

    private boolean ready;

    private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 11001;

    public static final int FRMOREJEST = 1001;

    // 短信注册，随机产生头像
    private static final String[] AVATARS = {
            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shidai_login;
    }

    @Override
    protected void setupViews() {
        tv_regist = (TextView) findViewById(R.id.tv_regist);
        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_secret = (EditText) findViewById(R.id.ed_secret);
        cb_rememberSecret = (CheckBox) findViewById(R.id.cb_rememberSecret);
        bt_login = (Button) findViewById(R.id.bt_login);
        tv_getsecret = (TextView) findViewById(R.id.tv_getsecret);

        tv_regist.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        tv_getsecret.setOnClickListener(this);


    }

    @Override
    protected void initialized() {

        initSDK();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission_group.MICROPHONE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
//        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
//                != PackageManager.PERMISSION_GRANTED) {
//            Uri packageURI = Uri.parse("package:" + self.getPackageName());
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
//            startActivity(intent);
//        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_regist:
//                startActivity(new Intent(self,registActivity.class));

                // 打开注册页面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            // 提交用户信息
                            registerUser(country, phone);

                            hasUser(phone);

                        }
                    }
                });
                registerPage.show(this);
                break;
            case R.id.bt_login:
                if (TextUtils.isEmpty(ed_username.getText().toString())) {
                    FloatTextToast.makeText(self, ed_username, "用户名不能为空", FloatTextToast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ed_secret.getText().toString())) {
                    FloatTextToast.makeText(self, ed_secret, "密码不能为空", FloatTextToast.LENGTH_SHORT).show();
                    return;
                }
                Login(ed_username.getText().toString(), ed_secret.getText().toString());
                break;
            case R.id.tv_getsecret:
                RegisterPage registerPage2 = new RegisterPage();
                registerPage2.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            // 提交用户信息
                            registerUser(country, phone);

                            Intent intent = new Intent(self, secretEditActivity.class);
                            intent.putExtra("phone", phone);
                            startActivity(intent);

                        }
                    }
                });
                registerPage2.show(this);
                break;
        }

    }


    /**
     * 判断是否存在手机用户，不存在即进入下一步
     *
     * @param mobile
     */
    private void hasUser(final String mobile) {
        ShidaiApi.gethasUserbyMobile(self, mobile, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {

                if ("0".equals(rspData.getErrcode())) {
                    Intent i = new Intent(self, registActivity.class);
                    i.putExtra("phone", mobile);
                    startActivityForResult(i, FRMOREJEST);
                } else {
                    showShortToast(rspData.getErrmsg());
                }

            }

            @Override
            public void failed(String msg) {
                showShortToast(msg);
            }
        });
    }


    private void initSDK() {
        // 初始化短信SDK
//        SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
        final Handler handler = new Handler(this);
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
        ready = true;

//        // 获取新好友个数
//        showDialog();
//        SMSSDK.getNewFriendsCount();
//        gettingFriends = true;
    }

    public boolean handleMessage(Message msg) {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }

        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        if (event == SMSSDK.EVENT_SUBMIT_USER_INFO) {
            // 短信注册成功后，返回MainActivity,然后提示新好友
            if (result == SMSSDK.RESULT_COMPLETE) {
//                Toast.makeText(this, R.string.smssdk_user_info_submited, Toast.LENGTH_SHORT).show();
            } else {
                ((Throwable) data).printStackTrace();
            }
        }

        return false;
    }

    // 提交用户信息
    private void registerUser(String country, String phone) {
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        String uid = String.valueOf(id);
        String nickName = "SmsSDK_User_" + uid;
        String avatar = AVATARS[id % 12];
        SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
    }

    // 弹出加载框
    private void showDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
        pd = CommonDialog.ProgressDialog(this);
        pd.show();
    }

    private void Login(String mobile, String password) {
        ShidaiApi.getLogin(self, mobile, password, shidaiUser.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".endsWith(rspData.getErrcode())) {
                    shidaiUser user = (shidaiUser) rspData;


                    Config.setShidaiUserInfo(new Gson().toJson(user));
//                    showShortToast(new Gson().toJson(user));

                    startActivity(new Intent(self, MainActivity.class));
                    finish();
                } else {
                    showShortToast(rspData.getErrmsg());
                }
            }

            @Override
            public void failed(String msg) {
                showShortToast(msg);
            }
        });
    }

    protected void onDestroy() {
        if (ready) {
            // 销毁回调监听接口
            SMSSDK.unregisterAllEventHandler();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FRMOREJEST && data != null) {
            ed_username.setText(data.getStringExtra("username"));
            ed_secret.setText(data.getStringExtra("secret"));

        } else
            showShortToast("权限获取成功");
    }
}
