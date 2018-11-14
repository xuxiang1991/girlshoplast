package com.daocheng.girlshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.utils.Config;

/**
 * 类名称：
 * 类描述：
 * 创建人：
 * 修改人：
 */
public class SecretDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private EditText ed_secret;
    private TextView tv_cancel,tv_ok;


    private GetSecret getSecret;
   public interface GetSecret{
        void CallBack(String str);
    }

    public SecretDialog(Context context,GetSecret getSecret) {
        super(context,R.style.dialog_jczq);

        this.context = context;
        this.getSecret=getSecret;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_secret);

        ed_secret=findViewById(R.id.ed_secret);
        tv_cancel=findViewById(R.id.tv_cancel);
        tv_ok=findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置

        lp.width = (int) (Config.width * 0.7); // 宽度
//
        dialogWindow.setAttributes(lp);
        this.setCanceledOnTouchOutside(false);


    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.tv_ok:
               if (ed_secret.getText().toString().length()==0){
                   Toast.makeText(context,"请输入密码",Toast.LENGTH_LONG).show();
                   return;
               }
               getSecret.CallBack(ed_secret.getText().toString());
               dismiss();
               break;
           case R.id.tv_cancel:
               dismiss();
               break;

       }

    }
}