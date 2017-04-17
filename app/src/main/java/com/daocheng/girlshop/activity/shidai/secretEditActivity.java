package com.daocheng.girlshop.activity.shidai;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.view.FloatTextToast;

import java.io.IOException;

/**
 * 项目名称：girlshop
 * 类描述：密码修改界面
 * 创建人：jdd
 * 创建时间：2016/7/4 14:27
 * 修改人：jdd
 * 修改时间：2016/7/4 14:27
 * 修改备注：
 */

public class secretEditActivity extends BaseActivity implements View.OnClickListener {

    private ImageView tv_left;
    private TextView tv_center;
    private EditText ed_secret;
    private TextView tv_sure;

    private String phone;

    @Override
    protected int getLayoutId() {
        return R.layout.shidai_activity_xiugaimima;
    }

    @Override
    protected void setupViews() {

        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_center = (TextView) findViewById(R.id.tv_center);
        ed_secret = (EditText) findViewById(R.id.ed_secret);
        tv_sure = (TextView) findViewById(R.id.tv_sure);

        tv_sure.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        tv_left.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initialized() {
        tv_center.setText("密码修改");
        phone = getIntent().getStringExtra("phone");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_sure:
                if (TextUtils.isEmpty(ed_secret.getText().toString())) {
                    FloatTextToast.makeText(self, ed_secret, "密码不能为空", FloatTextToast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    showShortToast("号码有误，请返回重新验证手机号码");
                    return;
                }
                eidtSercet(phone,ed_secret.getText().toString());
                break;

        }
    }


    private void eidtSercet(String phone,String secret)
    {
        ShidaiApi.editSecret(self, phone, secret, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode()))
                {
                    showShortToast("密码修改成功，请返回登录");
                    finish();
                }else
                    showShortToast("密码修改失败");
            }

            @Override
            public void failed(String msg) {
                showShortToast("网络出错");
            }
        });
    }


}
