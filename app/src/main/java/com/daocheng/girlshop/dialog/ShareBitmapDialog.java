package com.daocheng.girlshop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.utils.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 类名称：
 * 类描述：
 * 创建人：
 * 修改人：
 */
public class ShareBitmapDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private TextView s_wx, s_wxcircle, s_sina, s_qq, s_qzone;
    private ImageView s_off;
    private String url;
    private Bitmap bitmap;

    public ShareBitmapDialog(Context context, Bitmap bitmap) {
        super(context);

        this.context = context;
        this.bitmap = bitmap;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_share);

        s_off = (ImageView) findViewById(R.id.s_off);
        s_wx = (TextView) findViewById(R.id.s_wx);
        s_wxcircle = (TextView) findViewById(R.id.s_wxcircle);
        s_sina = (TextView) findViewById(R.id.s_sina);
        s_qq = (TextView) findViewById(R.id.s_qq);
        s_qzone = (TextView) findViewById(R.id.s_qzone);

        s_off.setOnClickListener(this);
        s_wx.setOnClickListener(this);
        s_wxcircle.setOnClickListener(this);
        s_sina.setOnClickListener(this);
        s_qq.setOnClickListener(this);
        s_qzone.setOnClickListener(this);
        s_qq.setVisibility(View.GONE);
        s_qzone.setVisibility(View.GONE);

        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
//        dialogWindow.setWindowAnimations(R.style.dateDialog);  //添加动画

////        int screenwith = Config.width;
        lp.width = (int) (Config.width * 0.8); // 宽度
//
//        lp.height = (int) (Config.height * 0.4f);
        dialogWindow.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);


    }

    @Override
    public void onClick(View v) {

        UMImage image = new UMImage(context, bitmap);

//        String url = "http://www.dig-data.com";
        //UMImage image = new UMImage(context, "http://blog.thegmic.com/wp-content/uploads/2012/05/umeng.jpg");
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.info_icon_1);
//        UMImage image = new UMImage(context, bitmap);
//        UMusic music = new UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
//        music.setTitle("sdasdasd");
//        music.setThumb(new UMImage(context, "http://www.umeng.com/images/pic/social/chart_1.png"));
//        UMVideo video = new UMVideo("http://video.sina.com.cn/p/sports/cba/v/2013-10-22/144463050817.html");
        image.setThumb(image);



        com.umeng.socialize.Config.OpenEditor = true;//打开编辑页
        switch (v.getId()) {
            case R.id.s_off:
                dismiss();
                break;
            case R.id.s_wx:
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                        .withMedia(image)
                        .share();
                break;
            case R.id.s_wxcircle:
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                        .withMedia(image)
                        .share();
                break;
            case R.id.s_sina:
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                        .withMedia(image)
                        .share();
                break;
            case R.id.s_qq:
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                        .withMedia(image)
                        .share();
                break;
            case R.id.s_qzone:
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener)
                        .withMedia(image)
                        .share();
                break;


        }

    }



    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            //分享成功增加积分
            Toast.makeText(context, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
