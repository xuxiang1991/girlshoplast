package com.daocheng.girlshop.activity.shidai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.activity.shidai.detail.dingkeDetailActivity;
import com.daocheng.girlshop.activity.shidai.list.DataListActivity;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.daocheng.girlshop.entity.shdiai.dingkeList;
import com.daocheng.girlshop.fragment.shidai.dingKeFragment;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCUtils;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by XX on 2016/8/28.
 */
public class YykwActivity extends BaseActivity implements View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<dataListResult.RecordBean> baseobjects;
    private baseObRecycleAdapter sRecyclerViewAdapter;

    private dataListResult advertorialList;

    private TextView tv_center;
    private ImageView tv_left;


    private int pageNo = 1;

    private int lastVisibleItem;

    private String pic, content;
    private ImageView iv_pic;
    private TextView tv_main;

    private String cUrl = "";
    private long startTime = 0;
    private long endTime = 0;
    private long duration = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_yykw_list;
    }

    @Override
    protected void setupViews() {

        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);

        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        tv_main = (TextView) findViewById(R.id.tv_main);
        tv_center = (TextView) findViewById(R.id.tv_center);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<dataListResult.RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        mRecyclerView.setAdapter(sRecyclerViewAdapter);

        tv_left.setOnClickListener(this);

    }

    @Override
    protected void initialized() {
        pic = getIntent().getStringExtra("pic");
        content = getIntent().getStringExtra("content");

        ImageLoader.getInstance().displayImage(pic, iv_pic);
        if (!TextUtils.isEmpty(content))
            tv_main.setText(content);

        tv_center.setText("在线课程");

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
                        && lastVisibleItem == sRecyclerViewAdapter.getItemCount() - 1
                        && lastVisibleItem == (pageNo * Constant.found_pageNum - 1)) {
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

        setData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
        }

    }


    private void setData() {
        ShidaiApi.getDataList(self, 13, Config.getShidaiUserInfo().getUserid(), pageNo, Constant.pageNum, dataListResult.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    advertorialList = (dataListResult) rspData;
                    Config.Default_Score = advertorialList.getScore();
                    if (pageNo == 1) {
                        baseobjects.clear();
                        baseobjects.addAll(advertorialList.getRecord());
                    } else
                        baseobjects.addAll(advertorialList.getRecord());
                    Log.v("ere", "fdfdf");

                    sRecyclerViewAdapter.notifyDataSetChanged();

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


    /**
     * 更新视频查看
     *
     * @param id
     */
    private void updateVideo(String id, int postion) {
        ShidaiApi.updateVideoSee(self, Config.getShidaiUserInfo().getUserid(), id, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    baseobjects.get(postion).setIsSeeVideo("2");

                } else {
                }
            }

            @Override
            public void failed(String msg) {
            }
        });
    }


    public class baseObRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class arViewHolder extends RecyclerView.ViewHolder {

            TextView tv_lesson;
            ImageView iv_play;
            ImageView iv_yuyin;


            public arViewHolder(View itemView) {
                super(itemView);
                tv_lesson = itemView.findViewById(R.id.tv_lesson);
                iv_play = itemView.findViewById(R.id.iv_play);
                iv_yuyin = itemView.findViewById(R.id.iv_yuyin);

            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(self).inflate(R.layout.item_kewen, parent, false);
            return new arViewHolder(inflatedView);

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            if (holder instanceof arViewHolder) {
                final dataListResult.RecordBean ob = getItem(position);
                if (ob != null) {
                    if (!TextUtils.isEmpty(ob.getStudyMp4Url())) {
                        ((arViewHolder) holder).iv_play.setVisibility(View.VISIBLE);
                    } else {
                        ((arViewHolder) holder).iv_play.setVisibility(View.GONE);
                    }

                    ((arViewHolder) holder).tv_lesson.setText(ob.getTitle() + "  " + ob.getContent());
                    ((arViewHolder) holder).iv_yuyin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(self, YykwDetailActivity.class);
                            i.putExtra("id", ob.getId());
                            startActivity(i);
                        }
                    });
                    ((arViewHolder) holder).iv_play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String mp4Url = ob.getStudyMp4Url() == null ? "" : ob.getStudyMp4Url();
                            JCFullScreenActivity.startActivity(self,
                                    mp4Url,
                                    JCVideoPlayerStandard.class, "", new JCVideoPlayer.Callback() {
                                        @Override
                                        public void prepare() {
                                            Log.e("xuxiang", mp4Url + "");
                                            Log.e("xuxiang", "开始");
                                            Config.setVideoTime(mp4Url, System.currentTimeMillis());
                                            Log.e("xuxiang", "当前时间:" + Config.getVideoTime(mp4Url));

                                        }

                                        @Override
                                        public void compelte() {
                                            Log.e("xuxiang", "结束");
                                            if ("1".equals(ob.getIsSeeVideo())) {
                                                long cTime = System.currentTimeMillis();
                                                if ((cTime - Config.getVideoTime(mp4Url)) > (duration - 10000)) {
                                                    Log.e("xuxiang", "到时间了");
                                                    updateVideo(ob.getId() + "", position);
                                                } else {
                                                    Log.e("xuxiang", "还没到时间");
                                                    showToast("您还没有看完视频，请重新观看");
                                                }
                                            }

                                        }

                                        @Override
                                        public void getTotal(int totalTime) {

                                            Log.e("xuxiang", totalTime + "");
                                            Log.e("xuxiang", JCUtils.stringForTime(totalTime) + "");
                                            duration = totalTime;


                                        }
                                    });
                        }
                    });
                }


            }

        }

        public dataListResult.RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }
    }


}
