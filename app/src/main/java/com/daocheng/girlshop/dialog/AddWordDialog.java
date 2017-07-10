package com.daocheng.girlshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;

import java.io.IOException;

/**
 * 类名称：加入生词表
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class AddWordDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String content;

    private TextView tv_title;
    private EditText tv_content;
    private TextView tv_cancel, tv_ok;
    private int cLength;
    private String cStr;

    public AddWordDialog(Context context, String content) {
        super(context, R.style.dialog_jczq);
        this.context = context;
        this.content = content;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_addword);
        initialize();

        tv_content.setText(content);
        cLength = content.length();
        cStr = content;

        tv_content.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cLength = s.length();
                cStr = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (cLength < s.length()) {
                    tv_content.setText(cStr);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        this.setCanceledOnTouchOutside(false);
    }

    private void initialize() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (EditText) findViewById(R.id.tv_content);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_ok:
                ShidaiApi.addtoWordbooke(context, Config.getShidaiUserInfo().getUserid(), tv_content.getText().toString(), ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
                    @Override
                    public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                        if ("0".equals(rspData.getErrcode())) {
//                                    Toast.makeText(self, "加入生词表成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failed(String msg) {

                    }
                });
                dismiss();
                break;
        }

    }
}
