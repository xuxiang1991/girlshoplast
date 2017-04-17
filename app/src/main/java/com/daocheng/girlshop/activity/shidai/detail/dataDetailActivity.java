package com.daocheng.girlshop.activity.shidai.detail;

import android.graphics.Bitmap;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.activity.shidai.list.DataListActivity;
import com.daocheng.girlshop.dialog.Sharedialog;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

/**
 * 项目名称：girlshop
 * 类描述：详情页面
 * 创建人：jdd
 * 创建时间：2016/6/28 9:40
 * 修改人：jdd
 * 修改时间：2016/6/28 9:40
 * 修改备注：
 */

public class dataDetailActivity extends BaseActivity implements View.OnClickListener {

    private dataListResult.RecordBean data;
    private int dataflag;

    private ImageView iv_banner;
    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_center;
    private ImageView tv_left;
    //    private WebView vb_content;
    private TextView tv_content;
    private TextView tv_share;
    private String url;



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_share:
                Sharedialog sd=new Sharedialog(self,url+data.getId(),data.getTitle());
                sd.show();
                break;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.shidai_activity_data;
    }

    @Override
    protected void setupViews() {

        iv_banner = (ImageView) findViewById(R.id.iv_banner);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_share = (TextView) findViewById(R.id.tv_share);
//        vb_content=(WebView)findViewById(R.id.vb_content);

        tv_content.setTextIsSelectable(true);
        tv_left.setOnClickListener(this);
        tv_title.setTextIsSelectable(true);
        tv_share.setOnClickListener(this);


    }

    @Override
    protected void initialized() {
        data = (dataListResult.RecordBean) getIntent().getSerializableExtra("data");
        dataflag = getIntent().getIntExtra("type", 0);
        tv_center.setText(data.getTitle());
        if (data != null) {
            ImageLoader.getInstance().displayImage(data.getLogo(), iv_banner);
            tv_title.setText(data.getTitle());
            tv_time.setText(data.getUpdatetime());
            tv_content.setText(Html.fromHtml(data.getContent()));
//            vb_content.loadDataWithBaseURL(null, data.getContent(), "text/html",
//                    "utf-8", null);
//            vb_content.getSettings().setJavaScriptEnabled(true);
//
//            vb_content.setWebViewClient(new MyWebViewClient());
        }
        getShareUrl(dataflag);

    }


    /**
     * 设置分享链接
     *
     * @param type
     */
    private void getShareUrl(int type) {
         url = null;
        switch (dataflag) {
            case DataListActivity.TYPE_HOT:
                url="http://www.cs66club.com/app/share_hot?id=";
                break;
            case DataListActivity.TYPE_MRIRIYIJU:
                break;
            case DataListActivity.TYPE_FENLEIXUEXI:
                url="http://www.cs66club.com/app/share_branch?id=";
                break;
            case DataListActivity.TYPE_ECA:
                break;
            case DataListActivity.TYPE_XINSHENGBIXIU:
                break;

            default:
                 url = null;
                break;
        }
        if (url!=null)
        {
            tv_share.setVisibility(View.VISIBLE);
        }
    }


    private String getTitle(int type) {
        String title = "热门推荐";
        switch (dataflag) {
            case DataListActivity.TYPE_HOT:
                title = "热门推荐";
                break;
            case DataListActivity.TYPE_MRIRIYIJU:
                title = "师生风采";
                break;
            case DataListActivity.TYPE_FENLEIXUEXI:
                title = "分类学习";
                break;
            case DataListActivity.TYPE_ECA:
                title = "少儿英语";
                break;
            case DataListActivity.TYPE_XINSHENGBIXIU:
                title = "新生必修课";
                break;

            default:
                title = "热门推荐";
                break;
        }
        return title;
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
