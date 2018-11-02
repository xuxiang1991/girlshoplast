package com.daocheng.girlshop.fragment.shidai;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;
import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.shidai.detail.WordDetailActivity;
import com.daocheng.girlshop.dialog.LevelDialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.WordLevel;
import com.daocheng.girlshop.entity.shdiai.WordList;
import com.daocheng.girlshop.entity.shdiai.ciHui;
import com.daocheng.girlshop.fragment.BaseFragment;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;
import com.daocheng.girlshop.voice.speech.SpeechManager;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：单词课程
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class WordClassFragment extends BaseFragment implements View.OnClickListener{


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<WordList.RecordBean> baseobjects;
    private baseObRecycleAdapter sRecyclerViewAdapter;
    private TextView tv_level,tv_changeLevel;


    private TextView tv_center;

    private int pageNo = 1;

    private int lastVisibleItem;

    private boolean isPrepared = false;
    private WordLevel.RecordBean cLevel=null;


    @Override
    protected int getLayoutId() {
        return R.layout.frg_wordclass;
    }

    @Override
    protected void setupViews(View view) {

        tv_center = (TextView) view.findViewById(R.id.tv_center);
        tv_level=view.findViewById(R.id.tv_level);
        tv_changeLevel=view.findViewById(R.id.tv_changeLevel);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<WordList.RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        sRecyclerViewAdapter.setMode(Attributes.Mode.Single);
        mRecyclerView.setAdapter(sRecyclerViewAdapter);
        tv_changeLevel.setOnClickListener(this);

    }

    @Override
    protected void initialized() {
        tv_center.setText("单词课程");


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

        switch (v.getId()){
            case R.id.tv_changeLevel:
                getLeveDialog();

                break;
        }
    }


    private void setData() {
//        if (cLevel==null)
//        {
//            mSwipeRefreshLayout.setRefreshing(false);
//            return;
//        }
        ShidaiApi.getWordList(self,"vip1", Config.getShidaiUserInfo().getUserid(), pageNo, Constant.found_pageNum, WordList.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {

                if ("0".equals(rspData.getErrcode())) {
                    WordList alist = (WordList) rspData;
                    if (pageNo == 1)
                        baseobjects = alist.getRecord();
                    else
                        baseobjects.addAll(alist.getRecord());
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


    private void getLeveDialog(){
        ShidaiApi.getLevel(self, Config.getShidaiUserInfo().getUserid(), WordLevel.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {

                if ("0".equals(rspData.getErrcode())) {
                    WordLevel alist = (WordLevel) rspData;

                    LevelDialog dialog=new LevelDialog(self, alist.getRecord(), new LevelDialog.ShowLevel() {
                        @Override
                        public void back(WordLevel.RecordBean level) {
                            cLevel=level;
                            setData();
                        }
                    });
                    dialog.show();

                } else {
                    showShortToast(rspData.getMessage());
                }
            }

            @Override
            public void failed(String msg) {
                Toast.makeText(self, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public class baseObRecycleAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {



        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.test_swipe_swipe;
        }

        public class arViewHolder extends RecyclerView.ViewHolder {

            TextView tv_lesson1;
            TextView tv_wordcount;
            LinearLayout itemview;
            public arViewHolder(View itemView) {
                super(itemView);
                tv_lesson1 = (TextView) itemView.findViewById(R.id.tv_lesson1);
                tv_wordcount = (TextView) itemView.findViewById(R.id.tv_wordcount);
                itemview= (LinearLayout) itemView.findViewById(R.id.itemview);

            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(self).inflate(R.layout.item_wordclass, parent, false);
            return new baseObRecycleAdapter.arViewHolder(inflatedView);

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof baseObRecycleAdapter.arViewHolder) {
                final WordList.RecordBean ob = getItem(position);
                ((baseObRecycleAdapter.arViewHolder) holder).tv_lesson1.setText(ob.getTitle());
                ((baseObRecycleAdapter.arViewHolder) holder).tv_wordcount.setText(ob.getNum()+"个单词");
                ((baseObRecycleAdapter.arViewHolder) holder).itemview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(self,WordDetailActivity.class));

                    }
                });

            }

        }

        public WordList.RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }
    }
    
    
}
