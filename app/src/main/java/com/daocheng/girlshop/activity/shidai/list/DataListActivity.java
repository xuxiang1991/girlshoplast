package com.daocheng.girlshop.activity.shidai.list;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.activity.shidai.detail.RmtjActivity;
import com.daocheng.girlshop.activity.shidai.detail.SingActivity;
import com.daocheng.girlshop.activity.shidai.detail.dataDetailActivity;
import com.daocheng.girlshop.activity.shidai.detail.videoDetailActivity;
import com.daocheng.girlshop.activity.shidai.detail.waijiaoActivity;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;
import com.daocheng.girlshop.view.Bookends;
import com.daocheng.girlshop.view.ClipTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：jdd
 * 创建时间：2016/6/24 10:46
 * 修改人：jdd
 * 修改时间：2016/6/24 10:46
 * 修改备注：
 */

public class DataListActivity extends BaseActivity implements View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<dataListResult.RecordBean> baseobjects;
    private baseObRecycleAdapter sRecyclerViewAdapter;

    private dataListResult advertorialList;

    private TextView tv_center;

    private int pageNo = 1;

    private int lastVisibleItem;

    private int dataflag = TYPE_HOT;

    public static final int TYPE_HOT = 3;
    public static final int TYPE_MRIRIYIJU = 4;
    public static final int TYPE_FENLEIXUEXI = 5;
    public static final int TYPE_TZWJ = 6;
    public static final int TYPE_ECA = 9;
    public static final int TYPE_CHANGGE = 8;
    public static final int TYPE_SHIPINGLIANXI = 7;
    public static final int TYPE_XINSHENGBIXIU = 10;

    private ImageView tv_left;
    private String paget_title = "";
    private LinearLayout headview;
    private JCVideoPlayerStandard custom_videoplayer;

    private Bookends<baseObRecycleAdapter> mBookends;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_shidai_home;
    }

    @Override
    protected void setupViews() {
        LayoutInflater inflater = LayoutInflater.from(self);
        dataflag = getIntent().getIntExtra("type", TYPE_HOT);
        paget_title = getIntent().getStringExtra("title");

        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);

        tv_center = (TextView) findViewById(R.id.tv_center);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);


        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
//        headview = (LinearLayout) inflater.inflate(R.layout.head_vedio, mRecyclerView, false);
        custom_videoplayer = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<dataListResult.RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        mBookends = new Bookends<>(sRecyclerViewAdapter);
//        mBookends.addHeader(headview);
        mRecyclerView.setAdapter(mBookends);

        tv_left.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
//        tv_center.setText(getTitle(dataflag));
        tv_center.setText(paget_title);

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


    private String getTitle(int type) {
        String title = "热门推荐";
        switch (dataflag) {
            case TYPE_HOT:
                title = "热门推荐";
                break;
            case TYPE_MRIRIYIJU:
                title = "师生风采";
                break;
            case TYPE_FENLEIXUEXI:
                title = "分类学习";
                break;
            case TYPE_ECA:
                title = "少儿英语";
                break;
            case TYPE_XINSHENGBIXIU:
                title = "新生必修课";
                break;
            case TYPE_SHIPINGLIANXI:
                title = "视频学习";
                break;
            case TYPE_CHANGGE:
                title = "想唱就唱";
                break;
            case TYPE_TZWJ:
                title = "挑战外教";
                break;
        }
        return title;
    }


    private void setData() {
        ShidaiApi.getDataList(self, dataflag, Config.getShidaiUserInfo().getUserid(), pageNo, Constant.pageNum, dataListResult.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    advertorialList = (dataListResult) rspData;
                    if (pageNo == 1)
                        baseobjects = advertorialList.getRecord();
                    else
                        baseobjects.addAll(advertorialList.getRecord());
                    Log.v("ere", "fdfdf");

                    mBookends.notifyDataSetChanged();

                    inithead();
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

    private void inithead() {
        if (!TextUtils.isEmpty(advertorialList.getVideo())) {

            if (!TextUtils.isEmpty(advertorialList.getVedio_pic()))
                ImageLoader.getInstance().displayImage(advertorialList.getVedio_pic(), custom_videoplayer.thumbImageView);


            custom_videoplayer.backButton.setVisibility(View.GONE);
            custom_videoplayer.setVisibility(View.VISIBLE);
            custom_videoplayer.thumbImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JCFullScreenActivity.startActivity(self,
                            advertorialList.getVideo(),
                            JCVideoPlayerStandard.class, "挑战外教");
                }
            });
            custom_videoplayer.startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JCFullScreenActivity.startActivity(self,
                            advertorialList.getVideo(),
                            JCVideoPlayerStandard.class, "挑战外教");
                }
            });
        } else {
            custom_videoplayer.setVisibility(View.GONE);
        }

    }

    public class baseObRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class hotViewHolder extends RecyclerView.ViewHolder {

            ClipTextView tv_title;
            ImageView iv_image;
            ClipTextView tv_content;
            TextView tv_time;
            RelativeLayout rl_item;

            public hotViewHolder(View itemView) {
                super(itemView);
                rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
                tv_title = (ClipTextView) itemView.findViewById(R.id.tv_title);
                iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
                tv_content = (ClipTextView) itemView.findViewById(R.id.tv_content);
                tv_time = (TextView) itemView.findViewById(R.id.tv_time);
                tv_title.setTextIsSelectable(true);
                tv_content.setTextIsSelectable(true);


            }
        }


        public class arViewHolder extends RecyclerView.ViewHolder {

            TextView tv_title;
            ImageView iv_image;
            TextView tv_content;
            TextView tv_time;
            TextView bt_pinglun;
            TextView bt_zan;
            RelativeLayout rl_item;

            public arViewHolder(View itemView) {
                super(itemView);
                rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
                tv_content = (TextView) itemView.findViewById(R.id.tv_content);
                tv_time = (TextView) itemView.findViewById(R.id.tv_time);
                bt_pinglun = (TextView) itemView.findViewById(R.id.bt_pinglun);
                bt_zan = (TextView) itemView.findViewById(R.id.bt_zan);
                tv_title.setTextIsSelectable(true);
                tv_content.setTextIsSelectable(true);

            }
        }


        @Override
        public int getItemViewType(int position) {
            // TODO Auto-generated method stub
            if (dataflag == TYPE_HOT) {
                return TYPE_HOT;
            } else if (dataflag == TYPE_MRIRIYIJU) {
                return TYPE_MRIRIYIJU;

            } else if (dataflag == TYPE_FENLEIXUEXI) {
                return TYPE_FENLEIXUEXI;
            } else if (dataflag == TYPE_ECA) {
                return TYPE_ECA;
            } else if (dataflag == TYPE_XINSHENGBIXIU) {
                return TYPE_XINSHENGBIXIU;
            } else if (dataflag == TYPE_SHIPINGLIANXI) {
                return TYPE_SHIPINGLIANXI;
            } else if (dataflag == TYPE_CHANGGE) {
                return TYPE_CHANGGE;
            } else if (dataflag == TYPE_TZWJ) {
                return TYPE_TZWJ;
            }

            return TYPE_HOT;

        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (getItemViewType(viewType) == TYPE_HOT || getItemViewType(viewType) == TYPE_FENLEIXUEXI || getItemViewType(viewType) == TYPE_ECA || getItemViewType(viewType) == TYPE_XINSHENGBIXIU || getItemViewType(viewType) == TYPE_SHIPINGLIANXI || getItemViewType(viewType) == TYPE_CHANGGE || getItemViewType(viewType) == TYPE_TZWJ || getItemViewType(viewType) == TYPE_MRIRIYIJU) {
                View inflatedView = LayoutInflater.from(self).inflate(R.layout.item_shidai_article_simple, parent, false);
                return new hotViewHolder(inflatedView);
            } else {
                View inflatedView = LayoutInflater.from(self).inflate(R.layout.item_shidai_article_simple, parent, false);
                return new hotViewHolder(inflatedView);
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            final dataListResult.RecordBean ob = getItem(position);

            if (holder instanceof arViewHolder) {
                ImageLoader.getInstance().displayImage(ob.getLogo(), ((arViewHolder) holder).iv_image);
                ((arViewHolder) holder).tv_content.setText(ob.getContent50());
                ((arViewHolder) holder).tv_content.setTextIsSelectable(true);
                ((arViewHolder) holder).tv_title.setText(ob.getTitle());
                ((arViewHolder) holder).tv_time.setText(ob.getUpdatetime());
                ((arViewHolder) holder).bt_zan.setText(ob.getCount() + "");
                ((arViewHolder) holder).bt_zan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShidaiApi.addZan(self, Config.getShidaiUserInfo().getUserid(), ob.getId(), ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
                            @Override
                            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                                if ("0".equals(rspData.getErrcode())) {
                                    baseobjects.get(position).setCount(ob.getCount() + 1);
                                    mBookends.notifyDataSetChanged();
//                                    showShortToast("评论成功");
                                }
                            }

                            @Override
                            public void failed(String msg) {

                            }
                        });
                    }
                });

//                if (dataflag==TYPE_MRIRIYIJU)
//                {
//                    ((arViewHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(self, dataDetailActivity.class);
//                            intent.putExtra("type", dataflag);
//                            intent.putExtra("data", ob);
//                            startActivity(intent);
//                        }
//                    });
//                    ((arViewHolder) holder).bt_pinglun.setVisibility(View.VISIBLE);
//                    ((arViewHolder) holder).bt_pinglun.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(self, pinglunActivity.class);
//                            intent.putExtra("id", ob.getId());
//                            startActivity(intent);
//                        }
//                    });
//                }


            } else if (holder instanceof hotViewHolder) {
                ImageLoader.getInstance().displayImage(ob.getLogo(), ((hotViewHolder) holder).iv_image);
                ((hotViewHolder) holder).tv_content.setText(ob.getContent50());
                ((hotViewHolder) holder).tv_content.setTextIsSelectable(true);
                ((hotViewHolder) holder).tv_title.setText(ob.getTitle());
                ((hotViewHolder) holder).tv_time.setText(ob.getUpdatetime());
                if (dataflag == TYPE_CHANGGE) {
                    ((hotViewHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(self, SingActivity.class);//MusicActivity.class
                            intent.putExtra("position", position);
                            intent.putExtra("data", (Serializable) baseobjects);
                            startActivity(intent);
                        }
                    });
                } else if (dataflag == TYPE_SHIPINGLIANXI) {
                    ((hotViewHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(self, videoDetailActivity.class);
                            intent.putExtra("type", dataflag);
                            intent.putExtra("data", ob);
                            startActivity(intent);
                        }
                    });
                } else if (dataflag == TYPE_TZWJ) {
                    ((hotViewHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(self, waijiaoActivity.class);
                            intent.putExtra("dataflag", waijiaoActivity.TYPE_WAIJIAO);
                            intent.putExtra("data", ob);
                            startActivity(intent);
                        }
                    });
                } else if (dataflag == TYPE_MRIRIYIJU) {
                    ((hotViewHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(self, waijiaoActivity.class);
                            intent.putExtra("dataflag", waijiaoActivity.TYPE_MEIRIYIJU);
                            intent.putExtra("data", ob);
                            startActivity(intent);
                        }
                    });

                } else if (dataflag == TYPE_HOT) {
                    ((hotViewHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(self, RmtjActivity.class);
                            intent.putExtra("type", dataflag);
                            intent.putExtra("data", ob);
                            startActivity(intent);
                        }
                    });
                } else {
                    ((hotViewHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(self, dataDetailActivity.class);
                            intent.putExtra("type", dataflag);
                            intent.putExtra("data", ob);
                            startActivity(intent);
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
