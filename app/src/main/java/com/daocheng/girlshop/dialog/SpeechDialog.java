package com.daocheng.girlshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.utils.Config;

/**
 * 项目名称：girlshop
 * 类描述：语音输入对话框
 * 创建人：jdd
 * 创建时间：2016/6/25 17:18
 * 修改人：jdd
 * 修改时间：2016/6/25 17:18
 * 修改备注：
 */

public class SpeechDialog extends Dialog{

    private AnimationDrawable animationDrawable;

    private ImageView iv_image;
    private RelativeLayout ll_voice;
    private ImageView waiting;
    private Context mcontext;
    private AnimationDrawable animationDrawable1;

    public SpeechDialog(Context context) {
        super(context);
        mcontext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shidai_dialog_voice);


        setUpViews();


        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        dialogWindow.setWindowAnimations(R.style.dateDialog);  //添加动画


//        int screenwith = Config.width;
        lp.width = (int) (Config.width*0.6f); // 宽度

        lp.height = (int) (Config.width * 0.6f);
        dialogWindow.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);


    }


    private void setUpViews()
    {
        iv_image=(ImageView)findViewById(R.id.iv_imageview);
        ll_voice=(RelativeLayout)findViewById(R.id.ll_voice);
        waiting=(ImageView) findViewById(R.id.waiting);

        ll_voice.getBackground().setAlpha(100);

        animationDrawable = (AnimationDrawable) iv_image.getBackground();
        animationDrawable.start();
    }


    public void paruse()
    {
        iv_image.setVisibility(View.GONE);
        waiting.setVisibility(View.VISIBLE);
        animationDrawable1 = (AnimationDrawable) waiting.getBackground();
        animationDrawable1.start();
    }


}
