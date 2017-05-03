package com.daocheng.girlshop.activity.shidai.detail;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.activity.shidai.list.DataListActivity;
import com.daocheng.girlshop.activity.shidai.list.ZipinglunActivity;
import com.daocheng.girlshop.dialog.Sharedialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.Vrecoder;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.daocheng.girlshop.entity.shdiai.punlun;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;
import com.daocheng.girlshop.view.Bookends;
import com.daocheng.girlshop.view.ClipTextView;
import com.daocheng.girlshop.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：时代英语
 * 类名称：热门推荐
 * 类描述：
 */
public class RmtjActivity extends BaseActivity implements View.OnClickListener {
    private dataListResult.RecordBean data;
    private int dataflag;

    private ImageView iv_banner;
    private ClipTextView tv_title;
    private TextView tv_time;
    private TextView tv_center;
    private ImageView tv_left;
    //    private WebView vb_content;
    private ClipTextView tv_content;
    private TextView tv_share;
    private String url;

    private LinearLayout headview;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<Vrecoder.RecordBean> baseobjects;
    private baseObRecycleAdapter sRecyclerViewAdapter;
    private int pageNo = 1;
    private Vrecoder advertorialList;
    private int lastVisibleItem;
    //
    private Bookends<baseObRecycleAdapter> mBookends;
    private static final String TYPE_TXT = "txt";
    private static final String TYPE_VOICE = "amr";


    private EditText ed_text;
    private TextView tv_send;
    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rmtj_detail;
    }

    @Override
    protected void setupViews() {
        LayoutInflater inflater = LayoutInflater.from(self);
        headview = (LinearLayout) inflater.inflate(R.layout.head_rmtj, mRecyclerView, false);

        iv_banner = (ImageView) headview.findViewById(R.id.iv_banner);
        tv_title = (ClipTextView) headview.findViewById(R.id.tv_title);
        tv_time = (TextView) headview.findViewById(R.id.tv_time);
        tv_content = (ClipTextView) headview.findViewById(R.id.tv_content);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_share = (TextView) findViewById(R.id.tv_share);
//        vb_content=(WebView)findViewById(R.id.vb_content);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<Vrecoder.RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        mBookends = new Bookends<>(sRecyclerViewAdapter);
        mBookends.addHeader(headview);
        mRecyclerView.setAdapter(mBookends);

        tv_send = (TextView) findViewById(R.id.tv_send);
        ed_text = (EditText) findViewById(R.id.ed_text);
        tv_send.setOnClickListener(this);
        tv_content.setTextIsSelectable(true);
        tv_left.setOnClickListener(this);
        tv_title.setTextIsSelectable(true);
        tv_share.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        data = (dataListResult.RecordBean) getIntent().getSerializableExtra("data");
        id = data.getId();
        dataflag = getIntent().getIntExtra("type", 0);
        tv_center.setText(data.getTitle());

        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));

        //加载内容
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem == mBookends.getItemCount()-1
                        && lastVisibleItem == (pageNo * Constant.found_pageNum)) {
                    pageNo = pageNo + 1;
                    setData();
                    mSwipeRefreshLayout.setRefreshing(true);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });


        mSwipeRefreshLayout.setColorSchemeResources(R.color.App_back_orange, R.color.green, R.color.blue, R.color.red);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mSwipeRefreshLayout.setRefreshing(true);
                setData();


            }
        });
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

            setData();
        }
        getShareUrl(dataflag);
    }


    private void setData() {
        ShidaiApi.getpingList(self, Config.getShidaiUserInfo().getUserid(), id, pageNo, Constant.pageNum, Vrecoder.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    advertorialList = (Vrecoder) rspData;
                    if (pageNo == 1)
                        baseobjects = advertorialList.getRecord();
                    else
                        baseobjects.addAll(advertorialList.getRecord());
                    Log.v("ere", "fdfdf");

                    mBookends.notifyDataSetChanged();

                } else {
                    showShortToast(rspData.getMessage());
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failed(String msg) {
                Toast.makeText(self, msg, Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void sendText() {
        if (!TextUtils.isEmpty(ed_text.getText().toString())) {
            if (Config.getShidaiUserInfo() != null && Config.getShidaiUserInfo().getLevel().contains("游客")) {
                showShortToast("游客不能发表");
                return;
            }
            sendMessge(TYPE_TXT, 0, "", ed_text.getText().toString());
            ed_text.setText("");
        }
    }

    private void sendMessge(final String type, final int length, final String url, final String content) {


        ShidaiApi.sendPinglun(self, Config.getShidaiUserInfo().getUserid(), id, type, length, url, content, punlun.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {

                if ("0".equals(rspData.getErrcode())) {

                    punlun pl = (punlun) rspData;

                    Vrecoder.RecordBean recordBean = new Vrecoder.RecordBean();
                    recordBean.setContent(content);
                    recordBean.setHead(Config.getShidaiUserInfo().getHead());
                    recordBean.setLength(length);
                    recordBean.setNickname(Config.getShidaiUserInfo().getNickname());
                    recordBean.setType(type);
                    recordBean.setUrl(ShidaiApi.Pic_BASE_URL + url);
                    recordBean.setUpdatetime("现在");
                    recordBean.setZan(0);
                    recordBean.setId(pl.getId());
                    recordBean.setUserid(Config.getShidaiUserInfo().getUserid());

                    baseobjects.add(0, recordBean);
                    mBookends.notifyDataSetChanged();
                    mRecyclerView.smoothScrollToPosition(1);
                }
            }

            @Override
            public void failed(String msg) {

            }
        });
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
                url = "http://www.cs66club.com/app/share_hot?id=";
                break;
            case DataListActivity.TYPE_MRIRIYIJU:
                break;
            case DataListActivity.TYPE_FENLEIXUEXI:
                url = "http://www.cs66club.com/app/share_branch?id=";
                break;
            case DataListActivity.TYPE_ECA:
                break;
            case DataListActivity.TYPE_XINSHENGBIXIU:
                break;

            default:
                url = null;
                break;
        }
        if (url != null) {
            tv_share.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_share:
                Sharedialog sd = new Sharedialog(self, url + data.getId(), data.getTitle());
                sd.show();
                break;
            case R.id.tv_send:
                sendText();
                break;
        }

    }


    public class baseObRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class arViewHolder extends RecyclerView.ViewHolder {

            RoundImageView iv_head;
            TextView tv_name;
            TextView tv_time;

            LinearLayout ll_text;
            TextView tv_message;


            public arViewHolder(View itemView) {
                super(itemView);
                iv_head = (RoundImageView) itemView.findViewById(R.id.iv_head);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_time = (TextView) itemView.findViewById(R.id.tv_time);

                ll_text = (LinearLayout) itemView.findViewById(R.id.ll_text);
                tv_message = (TextView) itemView.findViewById(R.id.tv_message);


            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View inflatedView = LayoutInflater.from(self).inflate(R.layout.shidai_rmtj_item, parent, false);
            return new baseObRecycleAdapter.arViewHolder(inflatedView);


        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final Vrecoder.RecordBean ob = getItem(position);
            if (holder instanceof baseObRecycleAdapter.arViewHolder) {
                if (!TextUtils.isEmpty(ob.getHead()))
                    ImageLoader.getInstance().displayImage(ob.getHead(), ((arViewHolder) holder).iv_head);
                else
                    ((arViewHolder) holder).iv_head.setImageResource(R.drawable.head_sculpture);

                ((arViewHolder) holder).tv_name.setText(ob.getNickname());
                ((arViewHolder) holder).tv_time.setText(ob.getUpdatetime());
                ((arViewHolder) holder).ll_text.setVisibility(View.VISIBLE);
                ((arViewHolder) holder).tv_message.setText(ob.getContent());


            }


        }

        public Vrecoder.RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }
    }

}
