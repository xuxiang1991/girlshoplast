package com.daocheng.girlshop.fragment.shidai;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.daocheng.girlshop.R;
import com.daocheng.girlshop.dialog.MessageDialog;
import com.daocheng.girlshop.dialog.scoreDialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.fuxiList;
import com.daocheng.girlshop.fragment.BaseFragment;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.BoardManager;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;
import com.daocheng.girlshop.view.newTextView;
import com.daocheng.girlshop.voice.speech.EvaluatorManager;
import com.daocheng.girlshop.voice.speech.SpeechManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 预习复习
 * Created by XX on 2016/6/23.
 */
public class fuxiFragment extends BaseFragment implements View.OnClickListener {


    private TextView tv_right;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<fuxiList.RecordBean> baseobjects;
    private baseObRecycleAdapter sRecyclerViewAdapter;

    private fuxiList advertorialList;

    private TextView tv_center;

    private int pageNo = 1;

    private int lastVisibleItem;

    private boolean isPrepared = false;

    private static int TYPE_FUXI = 11;

    private RelativeLayout currentRL;
    private BroadcastReceiver indexofcast;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_shidai_cihui;
    }

    @Override
    protected void setupViews(View view) {
        tv_right = (TextView) view.findViewById(R.id.makeSureTv);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextColor(self.getResources().getColor(R.color.App_back_orange));
        tv_right.setOnClickListener(this);

        tv_center = (TextView) view.findViewById(R.id.tv_center);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<fuxiList.RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        mRecyclerView.setAdapter(sRecyclerViewAdapter);

    }

    @Override
    protected void initialized() {
        tv_right.setText("男声");
        tv_center.setText("复习预习");
        initIndexBroadcast();

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
        SpeechManager.getInstance(self);
        EvaluatorManager.getInstance(self);

        setData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.makeSureTv:
                if (SpeechManager.getInstance(self).changePlayer() == 3) {
                    tv_right.setText("男声");
                } else {
                    tv_right.setText("女声");
                }
                break;
        }

    }


    private void setData() {
        ShidaiApi.getDataList(self, TYPE_FUXI, Config.getShidaiUserInfo().getUserid(), pageNo, Constant.pageNum, fuxiList.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    advertorialList = (fuxiList) rspData;
                    if (pageNo == 1)
                        baseobjects = advertorialList.getRecord();
                    else
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


    @Override
    public void onPause() {
        super.onPause();

    }

    public class baseObRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class arViewHolder extends RecyclerView.ViewHolder {

            RelativeLayout rl_text_scope;
            TextView tv_scope;
            newTextView tv_words;
            TextView tv_info;
            RelativeLayout rl_play;

            ImageView iv_danci;
            ImageView iv_yuyin;
            ImageView iv_play;


            public arViewHolder(View itemView) {
                super(itemView);
                rl_text_scope = (RelativeLayout) itemView.findViewById(R.id.rl_text_scope);
                tv_scope = (TextView) itemView.findViewById(R.id.tv_scope);
                tv_words = (newTextView) itemView.findViewById(R.id.tv_words);
                tv_info = (TextView) itemView.findViewById(R.id.tv_info);
                rl_play = (RelativeLayout) itemView.findViewById(R.id.rl_play);
                iv_danci = (ImageView) itemView.findViewById(R.id.iv_danci);
                iv_yuyin = (ImageView) itemView.findViewById(R.id.iv_yuyin);
                iv_play = (ImageView) itemView.findViewById(R.id.iv_play);
                tv_words.setTextIsSelectable(true);
                tv_words.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    public void onDestroyActionMode(ActionMode mode) {
                    }

                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        return false;
                    }
                });


            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(self).inflate(R.layout.item_shidai_fuxi, parent, false);
            return new arViewHolder(inflatedView);

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof arViewHolder) {
                final fuxiList.RecordBean ob = getItem(position);
                ((arViewHolder) holder).tv_words.setTextIsSelectable(true);
                ((arViewHolder) holder).tv_words.requestFocus();
                ((arViewHolder) holder).tv_words.setText(ob.getContent());
                ((arViewHolder) holder).tv_info.setText(ob.getContent1());
                ((arViewHolder) holder).tv_scope.setText(ob.getScore() + "");
                if (ob.getScore() >= 85)
                    ((arViewHolder) holder).tv_scope.setBackgroundResource(R.drawable.shape_round_green);
                else
                    ((arViewHolder) holder).tv_scope.setBackgroundResource(R.drawable.shape_round_red);

                ((arViewHolder) holder).tv_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentRL == null) {
                            currentRL = ((arViewHolder) holder).rl_play;
                            ((arViewHolder) holder).rl_play.setVisibility(View.VISIBLE);
                        } else {
                            currentRL.setVisibility(View.GONE);
                            currentRL = ((arViewHolder) holder).rl_play;
                            ((arViewHolder) holder).rl_play.setVisibility(View.VISIBLE);
                        }

                    }
                });

                ((arViewHolder) holder).tv_scope.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentRL == null) {
                            currentRL = ((arViewHolder) holder).rl_play;
                            ((arViewHolder) holder).rl_play.setVisibility(View.VISIBLE);
                        } else {
                            currentRL.setVisibility(View.GONE);
                            currentRL = ((arViewHolder) holder).rl_play;
                            ((arViewHolder) holder).rl_play.setVisibility(View.VISIBLE);
                        }

                    }
                });
                ((arViewHolder) holder).iv_play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SpeechManager.getInstance(self).play(ob.getContent());
                    }
                });

                ((arViewHolder) holder).iv_yuyin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EvaluatorManager.getInstance(self).play(ob.getContent(), position,ob.getId());

                    }
                });
                ((arViewHolder) holder).iv_danci.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clip(ob.getContent());
                    }
                });


            }

        }

        public fuxiList.RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }
    }


    //注册接收订单广播
    private void initIndexBroadcast() {
        try {
            indexofcast = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    if (intent != null) {
                        int index = intent.getIntExtra("index", 0);
                        int oldscope = baseobjects.get(index).getScore();
                        int currentscope = intent.getIntExtra("code", 0);
                        baseobjects.get(index).setScore(currentscope > oldscope ? currentscope : oldscope);
                        sRecyclerViewAdapter.notifyDataSetChanged();
                        scoreDialog md = new scoreDialog(self, "提示", currentscope + "");
                        md.show();

                        if (currentscope > oldscope)
                            updateScope(baseobjects.get(index).getId(), currentscope);
                    }

                }
            };
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constant.YUYINPINCE);
            self.registerReceiver(indexofcast, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateScope(int id, int scope) {
        ShidaiApi.updateScope(self, Config.getShidaiUserInfo().getUserid(), id, scope, 0, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    Log.e("fuxifragment", "分数更新成功");
                }
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        self.unregisterReceiver(indexofcast);
    }


    private void clip(final String txt) {
        ActionSheet.createBuilder(self, ((FragmentActivity) self).getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("加入到生词本")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        // 调用服务器接口上传
                        ShidaiApi.addtoWordbooke(self, Config.getShidaiUserInfo().getUserid(), txt, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
                            @Override
                            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                                if ("0".equals(rspData.getErrcode())) {

//                                    Toast.makeText(self, "加入生词表成功", Toast.LENGTH_SHORT).show();


                                }
                            }

                            @Override
                            public void failed(String msg) {

                            }
                        });
                    }
                }).show();
    }
}
