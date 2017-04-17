package com.daocheng.girlshop.activity.shidai;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;

import java.io.IOException;

/**
 * 项目名称：girlshop
 * 类描述：评论页面
 * 创建人：Dove
 * 创建时间：2016/8/30 14:29
 * 修改人：Dove
 * 修改时间：2016/8/30 14:29
 * 修改备注：
 */

public class PlunActivity extends BaseActivity implements View.OnClickListener {

    private ImageView tv_left;
    private TextView tv_center;
    private RadioGroup rg_isgood;
    private EditText tv_content;
    private Button bt_submit;
    private int id;

    private int feedback = 1;//1满意2一般3不满意

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
        return R.layout.activity_plun;
    }

    @Override
    protected void setupViews() {
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_center = (TextView) findViewById(R.id.tv_center);
        rg_isgood = (RadioGroup) findViewById(R.id.rg_isgood);
        tv_content = (EditText) findViewById(R.id.tv_content);
        bt_submit = (Button) findViewById(R.id.bt_submit);
    }

    @Override
    protected void initialized() {
        id=getIntent().getIntExtra("id",0);
        bt_submit.setOnClickListener(this);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);
        tv_center.setText("评价课程");
        rg_isgood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_top:
                        feedback = 1;
                        break;
                    case R.id.rb_middle:
                        feedback = 2;
                        break;
                    case R.id.rb_bottom:
                        feedback = 3;
                        break;
                }
            }
        });

    }

    private void Submit() {
//        if (TextUtils.isEmpty(tv_content.getText().toString()))
//        {
//            Toast.makeText(self,"请填写评论内容",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        ShidaiApi.submitplun(self, Config.getShidaiUserInfo().getUserid(), id, feedback, tv_content.getText().toString(), ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
//            @Override
//            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
//                if ("0".equals(rspData.getErrcode())) {
//                    Toast.makeText(self,"评论成功",Toast.LENGTH_SHORT).show();
//                    finish();
//                }else
//                    Toast.makeText(self,"评论失败",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void failed(String msg) {
//                Toast.makeText(self,"网络错误",Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
