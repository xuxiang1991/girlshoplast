package com.daocheng.girlshop.activity.shidai;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.view.MultiLineRadioGroup;

import java.io.IOException;

/**
 * 项目名称：girlshop
 * 类描述：评价页面
 * 创建人：xuxiang
 * 创建时间：2016/10/11 14:55
 * 修改人：xuxiang
 * 修改时间：2016/10/11 14:55
 * 修改备注：
 */

public class pingjiaActivity extends BaseActivity implements View.OnClickListener {

    private ImageView tv_left;
    private TextView tv_center;
    private LinearLayout rg_90, rg_75, rg_60, rg_30;
    private Button bt_submit;
    private int id;

    private int feedback = 1;//1满意2一般3不满意
    private String scope = "0";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.bt_submit:
                Submit();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pjia;
    }

    @Override
    protected void setupViews() {
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_center = (TextView) findViewById(R.id.tv_center);
        rg_90 = (LinearLayout) findViewById(R.id.rg_90);
        rg_75 = (LinearLayout) findViewById(R.id.rg_75);
        rg_60 = (LinearLayout) findViewById(R.id.rg_60);
        rg_30 = (LinearLayout) findViewById(R.id.rg_30);
        bt_submit = (Button) findViewById(R.id.bt_submit);
    }

    @Override
    protected void initialized() {
        id = getIntent().getIntExtra("id", 0);
        bt_submit.setOnClickListener(this);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);
        tv_center.setText("评价课程");

        initAllbox();

    }

   CheckBox.OnCheckedChangeListener clistener=new CompoundButton.OnCheckedChangeListener() {
       @Override
       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
           if (isChecked)
           {
               cleanAllviews(buttonView.getId());
               scope=buttonView.getText().toString().replace("分","");
           }else
           {
               scope="0";
           }
       }
   };


    private void initbox(LinearLayout layout)
    {
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {
            CheckBox rb = (CheckBox) layout.getChildAt(i);
            rb.setOnCheckedChangeListener(clistener);
        }
    }

    private void initAllbox()
    {
        initbox(rg_90);
        initbox(rg_75);
        initbox(rg_60);
        initbox(rg_30);
    }

    private void clean(LinearLayout rg, int checkedId) {
        int count = rg.getChildCount();
        for (int i = 0; i < count; i++) {
            CheckBox rb = (CheckBox) rg.getChildAt(i);
            if (rb.getId() != checkedId)
                ((CheckBox) rg.getChildAt(i)).setChecked(false);
            else
                ((CheckBox) rg.getChildAt(i)).setChecked(true);
        }
    }

    private void cleanAllviews(int CheckId) {
        clean(rg_90, CheckId);
        clean(rg_75, CheckId);
        clean(rg_60, CheckId);
        clean(rg_30, CheckId);
    }

    private void Submit() {
        if (scope.equals("0")) {
            Toast.makeText(self, "请选择评分", Toast.LENGTH_SHORT).show();
            return;
        }
        ShidaiApi.submitplun(self, Config.getShidaiUserInfo().getUserid(), id,  scope, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    Toast.makeText(self, "评论成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(self, rspData.getErrmsg()+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failed(String msg) {
                Toast.makeText(self, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
