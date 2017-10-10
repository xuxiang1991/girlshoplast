package com.daocheng.girlshop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.daocheng.girlshop.R;

/**
 * 类名称：填写生词
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class SendWordDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String content;

    private Button bt_send;
    private EditText ed_word;

    private CallBack callBack;

    public interface CallBack {
        void onBack(String word);
    }

    public SendWordDialog(Context context, String content, CallBack callBack) {
        super(context, R.style.dialog_actionsheet);
        this.context = context;
        this.content = content;
        this.callBack = callBack;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_word_send);
        initialize();
        Window dialogWindow = this.getWindow();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = width;
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setAttributes(layoutParams);


        this.setCanceledOnTouchOutside(false);
    }

    private void initialize() {

        bt_send = (Button) findViewById(R.id.bt_send);
        ed_word = (EditText) findViewById(R.id.ed_word);
        bt_send.setOnClickListener(this);

        if (!TextUtils.isEmpty(content)) {
            ed_word.setText(content);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send:
                callBack.onBack(ed_word.getText().toString());
                dismiss();
                break;
        }

    }
}
