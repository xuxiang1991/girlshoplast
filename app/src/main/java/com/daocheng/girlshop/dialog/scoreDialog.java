package com.daocheng.girlshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;

/**
 * 项目名称：girlshop
 * 类描述：分数对话框
 * 创建人：dove
 * 创建时间：2016/7/15 16:43
 * 修改人：dove
 * 修改时间：2016/7/15 16:43
 * 修改备注：
 */

public class scoreDialog extends Dialog implements View.OnClickListener {
    private Context mcontext;
    private TextView tvtitle;
    private TextView tvcontent;
    private ImageView ivdismiss;
    private TextView tvok;
    private String title;
    private String content;
    private TextView tv_share;

    private String url;
    private boolean iscanshare = false;
    private String sharetitle;


    public scoreDialog(Context context, String title, String content) {
        super(context);
        mcontext = context;
        this.title = title;
        this.content = content;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dismiss:
            case R.id.tv_ok:
                dismiss();
                break;
            case R.id.tv_share:
              if (iscanshare)
              {
                  if (!TextUtils.isEmpty(url))
                  {
                      Sharedialog sd = new Sharedialog(mcontext, url, sharetitle);
                      sd.show();
                  }else
                  {
                      Toast.makeText(mcontext,"正在上传服务器。。",Toast.LENGTH_SHORT).show();
                  }
              }else
              {
                  Toast.makeText(mcontext,"正在上传服务器。。",Toast.LENGTH_SHORT).show();

              }
                break;

        }

    }

    public void setCanshare(boolean iscanshare) {
        this.iscanshare = iscanshare;
    }

    public void setUrl(String url,String title) {
        this.url = url;
        sharetitle=title;
        tv_share.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scope_dialog);
        initialize();


        tvcontent.setText(content);

        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        this.setCanceledOnTouchOutside(false);
    }

    private void initialize() {

        tvtitle = (TextView) findViewById(R.id.tv_title);
        tvcontent = (TextView) findViewById(R.id.tv_content);
        ivdismiss = (ImageView) findViewById(R.id.iv_dismiss);
        tvok = (TextView) findViewById(R.id.tv_ok);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tvok.setOnClickListener(this);
        ivdismiss.setOnClickListener(this);
        tv_share.setOnClickListener(this);
    }
}
