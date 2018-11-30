package com.daocheng.girlshop.activity.shidai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.dialog.ShareBitmapDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 类名称：分享页面
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class shareActivity extends BaseActivity implements View.OnClickListener {


    private ImageView tvLeft;
    private TextView tvCenter;
    private TextView makeSureTv;
    private CardView cardview;
    private ImageView ivBg;
    private ImageView ivScan;
    private TextView tvName;
    private TextView tvIntroduce;
    private TextView tvSubmit;
    private TextView tv_point;
    private LinearLayout ll_card;

    private String ivbg = "";
    private String ivscan = "";
    private String ivtroduce = "";
    private String name = "";
    private String posint = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                ShareBitmapDialog dialog = new ShareBitmapDialog(self, getBitmapFromScrollView(ll_card));
                dialog.show();
                break;
            case R.id.makeSureTv:
                break;
            case R.id.tv_left:
                finish();
                break;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected void setupViews() {
        tvLeft = (ImageView) findViewById(R.id.tv_left);
        tvCenter = (TextView) findViewById(R.id.tv_center);
        makeSureTv = (TextView) findViewById(R.id.makeSureTv);
        cardview = (CardView) findViewById(R.id.cardview);
        ivBg = (ImageView) findViewById(R.id.iv_bg);
        ivScan = (ImageView) findViewById(R.id.iv_scan);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvIntroduce = (TextView) findViewById(R.id.tv_introduce);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        ll_card=findViewById(R.id.ll_card);
        tv_point = findViewById(R.id.tv_point);
        tvSubmit.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
        makeSureTv.setOnClickListener(this);
        makeSureTv.setVisibility(View.GONE);
    }

    @Override
    protected void initialized() {
        getIntentParams();
        ImageLoader.getInstance().displayImage(ivbg, ivBg);
        ImageLoader.getInstance().displayImage(ivscan, ivScan);
        tvName.setText(name + "");
        tvIntroduce.setText(ivtroduce + "");
        tv_point.setText("好友加入，立得" + posint + "积分");

    }

    private void getIntentParams() {
        Intent i = getIntent();
        ivbg = i.getStringExtra("ivbg");
        ivscan = i.getStringExtra("ivscan");
        ivtroduce = i.getStringExtra("ivtroduce");
        name = i.getStringExtra("name");
        posint = i.getStringExtra("point");
    }


    /**
     * 截取晒带图的bitmap
     */
    private Bitmap getBitmapFromScrollView(View scrollView) {
        Bitmap bitmap = null;
        // 获取listView实际高度
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), scrollView.getHeight(),
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }


}
