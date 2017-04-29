package com.daocheng.girlshop.activity.shidai.detail;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.dialog.Sharedialog;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.nostra13.universalimageloader.core.ImageLoader;

import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：jdd
 * 创建时间：2016/6/28 11:24
 * 修改人：jdd
 * 修改时间：2016/6/28 11:24
 * 修改备注：
 */

public class videoDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_center;
    private ImageView tv_left;
    private WebView vb_content;
    private TextView makeSureTv;
    private JCVideoPlayerStandard custom_videoplayer;

    private dataListResult.RecordBean data;
    private int dataflag;
    private String url = "http://www.cs66club.com/app/share_video?id=";

    @Override
    protected int getLayoutId() {
        return R.layout.shidai_video_activity;
    }

    @Override
    protected void setupViews() {

        custom_videoplayer = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        vb_content = (WebView) findViewById(R.id.vb_content);
        makeSureTv = (TextView) findViewById(R.id.makeSureTv);

        tv_left.setOnClickListener(this);
        makeSureTv.setOnClickListener(this);
        makeSureTv.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initialized() {

        makeSureTv.setText("分享");
        data = (dataListResult.RecordBean) getIntent().getSerializableExtra("data");

        if (data != null) {


            tv_center.setText(data.getTitle());
            custom_videoplayer.setUp(data.getMp4()
                    , data.getTitle());

            ImageLoader.getInstance().displayImage(data.getLogo(), custom_videoplayer.thumbImageView);
            tv_title.setText(data.getTitle());
            tv_time.setText(data.getUpdatetime());
            vb_content.loadDataWithBaseURL(null, data.getContent(), "text/html",
                    "utf-8", null);
            vb_content.getSettings().setJavaScriptEnabled(true);

            vb_content.setWebViewClient(new MyWebViewClient());


            JCFullScreenActivity.startActivity(this,
                    data.getMp4(),
                    JCVideoPlayerStandard.class, data.getTitle());
//            JCFullScreenActivity.startActivityFromNormal(this, 1,
//                    data.getMp4(),
//                    JCVideoPlayerStandard.class, data.getTitle());
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.makeSureTv:
                Sharedialog sd = new Sharedialog(self, url + data.getId(), data.getTitle());
                sd.show();
                break;

        }

    }


    // 监听
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            view.getSettings().setJavaScriptEnabled(true);
//            view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//            settings.setUseWideViewPort(true);
//            settings.setLoadWithOverviewMode(true);
//            view.getSettings().setUseWideViewPort(true);
//            view.getSettings().setLoadWithOverviewMode(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
//            addImageClickListner();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    }

}
