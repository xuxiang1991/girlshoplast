package com.daocheng.girlshop.activity.shidai;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;

/**
 * 类名称：书面作业和语音作业入口
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class ZyActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_write,tv_left;
    private ImageView iv_listen;

    private TextView tv_center,tv_write,tv_listen;

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_write:
            case R.id.iv_write:
                startActivity(new Intent(self, SmzyActivity.class));
                break;
            case R.id.tv_listen:
            case R.id.iv_listen:
                startActivity(new Intent(self, TjzyActivity.class));
                break;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_zy;
    }

    @Override
    protected void setupViews() {
        iv_listen=(ImageView)findViewById(R.id.iv_listen);
        iv_write=(ImageView)findViewById(R.id.iv_write);
        tv_left=(ImageView)findViewById(R.id.tv_left);
        tv_center=(TextView)findViewById(R.id.tv_center);
        tv_write=(TextView)findViewById(R.id.tv_write);
        tv_listen=(TextView)findViewById(R.id.tv_listen);

        tv_write.setOnClickListener(this);
        tv_listen.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        tv_left.setVisibility(View.VISIBLE);
        iv_write.setOnClickListener(this);
        iv_listen.setOnClickListener(this);


    }

    @Override
    protected void initialized() {
        tv_center.setText("选择作业");

    }
}
