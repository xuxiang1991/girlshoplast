package com.daocheng.girlshop.activity.shidai;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.utils.Utils;

/**
 * 注册收验证码页面
 * Created by Dove on 2016/6/18.
 */
public class registLaterActivity extends BaseActivity implements View.OnClickListener{


    private ImageView tv_left;
    private TextView tv_back;
    private EditText ed_username,ed_secret;
    private TextView bt_getCode;
    private Button bt_register;
    private TextView tv_xieyi;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_shidai_regiterlater;
    }

    @Override
    protected void setupViews() {

        tv_left=(ImageView)findViewById(R.id.tv_left);
        tv_back=(TextView)findViewById(R.id.tv_back);
        ed_username=(EditText)findViewById(R.id.ed_username);
        ed_secret=(EditText)findViewById(R.id.ed_secret);
        bt_getCode=(TextView)findViewById(R.id.bt_getCode);
        bt_register=(Button)findViewById(R.id.bt_register);
        tv_xieyi=(TextView)findViewById(R.id.tv_xieyi);



    }

    @Override
    protected void initialized() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_getCode:
                if (null != ed_username.getText().toString() && Utils.isMobileNO(ed_username.getText().toString()) && ed_username.getText().toString().length() == 11) {
                    getCode(ed_username.getText().toString());

                } else {
                    showShortToast("请输入正确的号码");

                }
        }

    }



    private void getCode(String phoneNum)
    {

    }
}
