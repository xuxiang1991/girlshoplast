package com.daocheng.girlshop.activity.shidai;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.SmzyBean;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;

import java.io.IOException;

/**
 * 类名称：书面作业
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class SmzyActivity extends BaseActivity implements View.OnClickListener {


    private ImageView tv_left;
    private TextView tv_center;
    private ListView list;
    private TextView tv_complete, tv_head;
    private SmzyBean smzyBean;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;

        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smzy;
    }

    @Override
    protected void setupViews() {
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_head = (TextView) findViewById(R.id.tv_head);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);


    }

    @Override
    protected void initialized() {
        tv_center.setText("书写作业");
        getData();
    }

    private void initview() {
        if (smzyBean != null) {
            if (!TextUtils.isEmpty(smzyBean.getFriends())) {
                tv_head.setText(smzyBean.getFriends());
            }
        }
    }

    private void getData() {
        ShidaiApi.getSmzy(self, Config.getShidaiUserInfo().getUserid(), SmzyBean.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    smzyBean = (SmzyBean) rspData;
                    initview();
                } else {
                    tv_head.setText(rspData.getMessage());
                }
            }

            @Override
            public void failed(String msg) {
                Toast.makeText(self, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
