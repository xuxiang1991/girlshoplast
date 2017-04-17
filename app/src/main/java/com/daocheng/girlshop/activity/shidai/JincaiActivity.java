package com.daocheng.girlshop.activity.shidai;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.dialog.UpdateDialog;
import com.daocheng.girlshop.net.DownloadManager;
import com.daocheng.girlshop.video.View.JCVideoPlayerStandardShowShareButtonAfterFullscreen;
import com.nostra13.universalimageloader.core.ImageLoader;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 项目名称：girlshop
 * 类描述：精彩下载
 * 创建人：jdd
 * 创建时间：2016/6/27 18:49
 * 修改人：jdd
 * 修改时间：2016/6/27 18:49
 * 修改备注：
 */

public class JincaiActivity extends BaseActivity implements View.OnClickListener {

    private JCVideoPlayerStandard custom_videoplayer;
    private TextView tv_down;

    private TextView tv_center;
    private ImageView tv_left;

    private String video;

    @Override
    protected int getLayoutId() {
        return R.layout.shidai_jingcai_activity;
    }

    @Override
    protected void setupViews() {

        tv_left=(ImageView)findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_center=(TextView)findViewById(R.id.tv_center);
        custom_videoplayer = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer);
        tv_down = (TextView) findViewById(R.id.tv_down);
        tv_down.setOnClickListener(this);
        tv_left.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_down:
                UpdateDialog ud = new UpdateDialog(self, video);
                ud.show();
                break;
            case R.id.tv_left:
                finish();
                break;
        }

    }

    @Override
    protected void initialized() {
        tv_center.setText("精彩下载");
        video = getIntent().getStringExtra("video");
        custom_videoplayer.setUp(video
                , "精彩视频");
//        ImageLoader.getInstance().displayImage("http://p.qpic.cn/videoyun/0/2449_bfbbfa3cea8f11e5aac3db03cda99974_1/640",
//                custom_videoplayer.thumbImageView);
    }
}
