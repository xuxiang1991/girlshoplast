package com.daocheng.girlshop.jpush;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：jdd
 * 创建时间：2016/10/24 9:57
 * 修改人：jdd
 * 修改时间：2016/10/24 9:57
 * 修改备注：
 */

public class DialogActivity extends BaseActivity {


    private TextView tvtitle;
    private TextView tvcontent;
    private TextView tvok;
    private String title = "";
    private String content = "";

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_activity;
    }

    @Override
    protected void setupViews() {


        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null)
            {
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
            }
        }


        tvtitle = (TextView) findViewById(R.id.tv_title);
        tvcontent = (TextView) findViewById(R.id.tv_content);
        tvok = (TextView) findViewById(R.id.tv_ok);
        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvtitle.setText(title);
        tvcontent.setText(content);

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        //设置窗口的大小及透明度
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = layoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
//        layoutParams.alpha = 0.5f;
        window.setAttributes(layoutParams);
    }

    @Override
    protected void initialized() {

    }
}
