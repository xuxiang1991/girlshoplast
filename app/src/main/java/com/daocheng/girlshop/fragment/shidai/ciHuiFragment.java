package com.daocheng.girlshop.fragment.shidai;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;
import com.daocheng.girlshop.R;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.ciHui;
import com.daocheng.girlshop.fragment.BaseFragment;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;
import com.daocheng.girlshop.voice.speech.SpeechManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class ciHuiFragment extends BaseFragment implements View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<ciHui.RecordBean> baseobjects;
    private baseObRecycleAdapter sRecyclerViewAdapter;

    private ciHui advertorialList;

    private TextView tv_center;

    private int pageNo = 1;

    private int lastVisibleItem;

    private boolean isPrepared = false;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_shidai_cihui;
    }

    @Override
    protected void setupViews(View view) {

        tv_center = (TextView) view.findViewById(R.id.tv_center);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<ciHui.RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        sRecyclerViewAdapter.setMode(Attributes.Mode.Single);
        mRecyclerView.setAdapter(sRecyclerViewAdapter);

    }

    @Override
    protected void initialized() {
        tv_center.setText("我的词汇");


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

        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

        setData();
    }

    @Override
    public void onClick(View v) {

    }


    private void setData() {
        ShidaiApi.getMycihuiList(self, Config.getShidaiUserInfo().getUserid(), pageNo, Constant.found_pageNum, ciHui.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {

                if ("0".equals(rspData.getErrcode())) {
                    ciHui alist = (ciHui) rspData;
                    if (pageNo == 1)
                        baseobjects = alist.getRecord();
                    else
                        baseobjects.addAll(alist.getRecord());
                    Log.v("ere", "fdfdf");

                    sRecyclerViewAdapter.notifyDataSetChanged();
                    sRecyclerViewAdapter.mItemManger.closeAllItems();

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


    public class baseObRecycleAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {


        private void deleteCihui(int id, final int position, final arViewHolder viewHolder) {
            ShidaiApi.deleteCihuiFromMy(self, Config.getShidaiUserInfo().getUserid(), id, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
                @Override
                public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                    if ("0".equals(rspData.getErrcode())) {
                        {

                            mItemManger.removeShownLayouts(viewHolder.test_swipe_swipe);
                            baseobjects.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, baseobjects.size());
                            mItemManger.closeAllItems();


//                            sRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void failed(String msg) {

                }
            });
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.test_swipe_swipe;
        }

        public class arViewHolder extends RecyclerView.ViewHolder {

            TextView tv_word;
            ImageView trash;
            TextView tv_translate;
            SwipeLayout test_swipe_swipe;
            ImageView voice;

            public arViewHolder(View itemView) {
                super(itemView);
                tv_word = (TextView) itemView.findViewById(R.id.tv_word);
                tv_translate = (TextView) itemView.findViewById(R.id.tv_translate);
                trash = (ImageView) itemView.findViewById(R.id.trash);
                test_swipe_swipe = (SwipeLayout) itemView.findViewById(R.id.test_swipe_swipe);
                voice= (ImageView) itemView.findViewById(R.id.voice);
                tv_word.setTextIsSelectable(true);


            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(self).inflate(R.layout.sample_nested_parent, parent, false);
            return new arViewHolder(inflatedView);

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof arViewHolder) {
                final ciHui.RecordBean ob = getItem(position);
                ((arViewHolder) holder).test_swipe_swipe.setShowMode(SwipeLayout.ShowMode.LayDown);
                ((arViewHolder) holder).tv_word.setText(ob.getWord());
                ((arViewHolder) holder).tv_translate.setText(ob.getTransword());
                ((arViewHolder) holder).trash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteCihui(ob.getId(), position, (arViewHolder) holder);
                        Log.v("cihui", "delete success");

                    }
                });
                ((arViewHolder) holder).voice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SpeechManager.getInstance(self).play(ob.getWord());
                    }
                });
                mItemManger.bind(((arViewHolder) holder).itemView, position);

            }

        }

        public ciHui.RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }
    }
}
