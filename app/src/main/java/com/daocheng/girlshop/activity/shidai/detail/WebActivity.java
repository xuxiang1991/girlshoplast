package com.daocheng.girlshop.activity.shidai.detail;

import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.utils.Utils;

import java.io.File;

/**
 * 类名称：
 * 类描述：
 * 创建人：
 * 修改人：
 */
public class WebActivity  extends BaseActivity implements View.OnClickListener{

    private ImageView tv_left;
    private TextView tv_center;
    private WebView webView;

    private String url;
    private String title;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.shidai_activity_web;
    }

    @Override
    protected void setupViews() {
        tv_left=findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_center=findViewById(R.id.tv_center);
        webView=findViewById(R.id.webView);
        tv_left.setOnClickListener(this);

    }

    @Override
    protected void initialized() {
        title=getIntent().getStringExtra("title");
        url=getIntent().getStringExtra("url");

        tv_center.setText(title);


        WebSettings webSettings = webView.getSettings();
        String ua = webView.getSettings().getUserAgentString();
        webSettings.setUserAgentString(ua + " " + "JSCP/Android");

        //js交互
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);

        //Storage和Local Storage两种，分别用于会话级别的存储（页面关闭即消失）和本地化存储
        webSettings.setDomStorageEnabled(true);
        //越权访问，跨域
        webSettings.setAllowFileAccess(true);
        //自适应屏幕
        webSettings.setUseWideViewPort(true);
        //webview 加载的模式，也是适应屏幕
        webSettings.setLoadWithOverviewMode(true);
        //支持缩放
        webSettings.setSupportZoom(true);
        //控制缩放
        webSettings.setBuiltInZoomControls(true);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            //隐藏webview缩放按钮
            webView.getSettings().setDisplayZoomControls(false);
        }
        //对Data中包含中文字需要加上，否则会乱码
        webSettings.setDefaultTextEncodingName("UTF-8");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
//        //开启第三方cookie支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        if (Utils.isNetworkConnected(self)) {
            // * 默认加载方式，使用这种方式，会实现快速前进后退，在同一个标签打开几个网页后，关闭网络时，可以通过前进后退来切换已经访问过的数据，同时新建网页需要网络
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //  * 这个方式不论如何都会从缓存中加载，除非缓存中的网页过期，出现的问题就是打开动态网页时，不能时时更新，会出现上次打开过的状态，除非清除缓存。
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上系统默认不支持混合渲染
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        setCachePath();

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });








        webView.loadUrl(url);

    }


    private String databasePatchCache = "";
    private String appCachePatchCache = "";

    /**
     * 初始化缓存配置
     */
    private void setCachePath() {

        File cacheFile = new File(webView.getContext().getCacheDir(), "appcache_name");
        databasePatchCache = cacheFile.getAbsolutePath();
        File fAppCache = new File(self.getCacheDir().getAbsolutePath(), "webview_cache");
        appCachePatchCache = fAppCache.getAbsolutePath();

        File dataFile = new File(databasePatchCache);
        if (!dataFile.exists()) {
            dataFile.mkdirs();
        }
        File file = new File(appCachePatchCache);
        if (!file.exists()) {
            file.mkdirs();
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(appCachePatchCache);//h5 缓存过程中的缓存文件
        webSettings.setDatabaseEnabled(true);//存储数据库数据
        webSettings.setDatabasePath(databasePatchCache);

//        Logger.e("webviewCache1", cacheFile.getAbsolutePath());
//        Logger.e("webviewCache2", appCachePatchCache);

    }
}
