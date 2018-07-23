package com.daocheng.girlshop.activity.shidai.commonlist.DataLIst;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.shidai.commonlist.CoreBaseActivtiy;
import com.daocheng.girlshop.activity.shidai.list.DataListActivity;
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
import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 类名称：列表数据类
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class DataLIstActivity extends CoreBaseActivtiy<DataListPresenterimpl, DataListModelimpl> implements DataListContract.DataListView, View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;

    private baseObRecycleAdapter sRecyclerViewAdapter;


    private TextView tv_center;


    private ImageView tv_left;

    private LinearLayout headview;
    private JCVideoPlayerStandard custom_videoplayer;

    private Bookends<baseObRecycleAdapter> mBookends;


    @Override
    protected int getLayoutId() {
        return R.layout.frg_shidai_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(self);
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);

        tv_center = (TextView) findViewById(R.id.tv_center);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        headview = (LinearLayout) inflater.inflate(R.layout.head_vedio, mRecyclerView, false);
        custom_videoplayer = (JCVideoPlayerStandard) headview.findViewById(R.id.custom_videoplayer);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        sRecyclerViewAdapter = new baseObRecycleAdapter();
        mBookends = new Bookends<>(sRecyclerViewAdapter);
        mBookends.addHeader(headview);
        mRecyclerView.setAdapter(mBookends);

        tv_left.setOnClickListener(this);


        //        tv_center.setText(getTitle(dataflag));
        tv_center.setText(mModel.paget_title);

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
                        && mModel.lastVisibleItem == sRecyclerViewAdapter.getItemCount() - 1
                        && mModel.lastVisibleItem == (mModel.pageNo * Constant.found_pageNum - 1)) {
                    mPresenter.nextPage();
                    mPresenter.getData();
                    mSwipeRefreshLayout.setRefreshing(true);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mModel.lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });


        mSwipeRefreshLayout.setColorSchemeResources(R.color.App_back_orange, R.color.green, R.color.blue, R.color.red);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshPage();
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.getData();


            }
        });


        mPresenter.getData();

    }

    @Override
    public Context getContext() {
        return self;
    }

    @Override
    public void showError(String msg) {
        showShortToast(msg);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showContent() {
        inithead();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {

    }


    private void inithead() {
        if (!TextUtils.isEmpty(mModel.advertorialList.getVideo())) {

            if (!TextUtils.isEmpty(mModel.advertorialList.getVedio_pic()))
                ImageLoader.getInstance().displayImage(mModel.advertorialList.getVedio_pic(), custom_videoplayer.thumbImageView);


            custom_videoplayer.backButton.setVisibility(View.GONE);
            custom_videoplayer.setVisibility(View.VISIBLE);
            custom_videoplayer.thumbImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JCFullScreenActivity.startActivity(self,
                            mModel.advertorialList.getVideo(),
                            JCVideoPlayerStandard.class, "挑战外教");
                }
            });
            custom_videoplayer.startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JCFullScreenActivity.startActivity(self,
                            mModel.advertorialList.getVideo(),
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
            if (mModel.dataflag == DataListModelimpl.TYPE_HOT) {
                return DataListModelimpl.TYPE_HOT;
            } else if (mModel.dataflag == DataListModelimpl.TYPE_MRIRIYIJU) {
                return mModel.TYPE_MRIRIYIJU;

            } else if (mModel.dataflag == DataListModelimpl.TYPE_FENLEIXUEXI) {
                return DataListModelimpl.TYPE_FENLEIXUEXI;
            } else if (mModel.dataflag == DataListModelimpl.TYPE_ECA) {
                return DataListModelimpl.TYPE_ECA;
            } else if (mModel.dataflag == DataListModelimpl.TYPE_XINSHENGBIXIU) {
                return DataListModelimpl.TYPE_XINSHENGBIXIU;
            } else if (mModel.dataflag == DataListModelimpl.TYPE_SHIPINGLIANXI) {
                return DataListModelimpl.TYPE_SHIPINGLIANXI;
            } else if (mModel.dataflag == DataListModelimpl.TYPE_CHANGGE) {
                return DataListModelimpl.TYPE_CHANGGE;
            } else if (mModel.dataflag == DataListModelimpl.TYPE_TZWJ) {
                return DataListModelimpl.TYPE_TZWJ;
            }

            return DataListModelimpl.TYPE_HOT;

        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (getItemViewType(viewType) == DataListModelimpl.TYPE_HOT || getItemViewType(viewType) == DataListModelimpl.TYPE_FENLEIXUEXI || getItemViewType(viewType) == DataListModelimpl.TYPE_ECA || getItemViewType(viewType) == DataListModelimpl.TYPE_XINSHENGBIXIU || getItemViewType(viewType) == DataListModelimpl.TYPE_SHIPINGLIANXI || getItemViewType(viewType) == DataListModelimpl.TYPE_CHANGGE || getItemViewType(viewType) == DataListModelimpl.TYPE_TZWJ || getItemViewType(viewType) == DataListModelimpl.TYPE_MRIRIYIJU) {
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
                                    mModel.baseobjects.get(position).setCount(ob.getCount() + 1);
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

//

            } else if (holder instanceof hotViewHolder) {
                ImageLoader.getInstance().displayImage(ob.getLogo(), ((hotViewHolder) holder).iv_image);
                ((hotViewHolder) holder).tv_content.setText(ob.getContent50());
                ((hotViewHolder) holder).tv_content.setTextIsSelectable(true);
                ((hotViewHolder) holder).tv_title.setText(ob.getTitle());
                ((hotViewHolder) holder).tv_time.setText(ob.getUpdatetime());


            }

        }

        public dataListResult.RecordBean getItem(int position) {
            return mModel.baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return mModel.baseobjects.size();
        }
    }


}
