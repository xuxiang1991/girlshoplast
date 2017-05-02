package com.daocheng.girlshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import static com.daocheng.girlshop.activity.shidai.detail.waijiaoActivity.TYPE_WAIJIAO;

/**
 * 类名称：用户信息对话框
 * 类描述：
 * 创建人：
 * 修改人：
 */
public class PersonInfoDialog extends Dialog implements View.OnClickListener {
    private Context mcontext;
    private TextView tv_name;
    private TextView tv_content;
    private ImageView iv_dismiss;
    private RoundImageView iv_head;
    private int dataflag;


    private dataListResult.RecordBean data;

    public PersonInfoDialog(Context context, dataListResult.RecordBean data,int dataflag) {
        super(context);//,R.style.myTransparent
        mcontext = context;
        this.data = data;
        this.dataflag=dataflag;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dismiss:
                dismiss();
                break;

        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_person_info);
        initialize();



        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setBackgroundDrawable(new ColorDrawable());
        dialogWindow.setGravity(Gravity.CENTER);
        lp.width = (int) (Config.width*0.8f); // 宽度

        lp.height = (int) (Config.height * 0.9f);
        dialogWindow.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);
    }

    private void initialize() {

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_content = (TextView) findViewById(R.id.tv_content);
        iv_dismiss = (ImageView) findViewById(R.id.iv_dismiss);
        iv_head = (RoundImageView) findViewById(R.id.iv_head);
        iv_dismiss.setOnClickListener(this);

        if (data!=null)
        {
            if (dataflag==TYPE_WAIJIAO)
            {
                ImageLoader.getInstance().displayImage(data.getIcon(), iv_head);
            }else {
                ImageLoader.getInstance().displayImage(data.getLogo(), iv_head);
            }

            tv_name.setText(data.getTitle());
            tv_content.setText(Html.fromHtml(data.getContent()));//Html.fromHtml(detaDetail.getContent())
        }
    }
}