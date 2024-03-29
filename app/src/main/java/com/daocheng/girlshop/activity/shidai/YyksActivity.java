package com.daocheng.girlshop.activity.shidai;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.dialog.AddWordDialog;
import com.daocheng.girlshop.dialog.MessageDialog;
import com.daocheng.girlshop.dialog.scoreDialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.fuxiList;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;
import com.daocheng.girlshop.utils.Utils;
import com.daocheng.girlshop.view.ClipTextView;
import com.daocheng.girlshop.view.FlowLayout;
import com.daocheng.girlshop.view.newTextView;
import com.daocheng.girlshop.voice.speech.EvaluatorManager;
import com.daocheng.girlshop.voice.speech.SpeechManager;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by XX on 2016/8/31.
 */
public class YyksActivity extends BaseActivity implements View.OnClickListener {
    private ImageView tv_left;
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

    private static int TYPE_ks = 15;

    private RelativeLayout currentRL;
    private BroadcastReceiver indexofcast;
    private int id;
    private ImageView lastEvl;
    private TextView tv_yy_ok;

    private LinearLayout ll_start;
    private TextView tv_time, tv_start;
    private LinearLayout ll_list;
    private long time = 20 * 60 * 1000;


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            String formatLongToTimeStr = formatLongToTimeStr(time);
            tv_time.setText(formatLongToTimeStr);
            if (time > 0) {
                handler.postDelayed(this, 1000);
            } else {
                MessageDialog md = new MessageDialog(self, "提示", "考试结束！！！", MessageDialog.ORDER, new MessageDialog.onRequest() {
                    @Override
                    public void back() {
                        finish();
                    }
                });
                md.show();
            }
        }
    };

    public String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue();
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = String.format("%d:%d:%d", hour, minute, second);
        return strtime;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yyks;
    }

    @Override
    protected void setupViews() {
        ll_start = (LinearLayout) findViewById(R.id.ll_start);
        ll_list = (LinearLayout) findViewById(R.id.ll_list);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_start.setOnClickListener(this);

        tv_yy_ok = (TextView) findViewById(R.id.tv_yy_ok);
        tv_yy_ok.setOnClickListener(this);
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);
        tv_right = (TextView) findViewById(R.id.makeSureTv);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextColor(self.getResources().getColor(R.color.App_back_orange));
        tv_right.setOnClickListener(this);

        tv_center = (TextView) findViewById(R.id.tv_center);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
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
        time = getIntent().getIntExtra("time", 20) * 60;
        id = getIntent().getIntExtra("id", 0);
        tv_right.setText("男声");
        tv_center.setText("升级考试");
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

        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

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
            case R.id.tv_left:
                if (tv_yy_ok.getVisibility() == View.GONE) {
                    MessageDialog md = new MessageDialog(self, "提示", "是否退出考试", MessageDialog.HOME, new MessageDialog.onRequest() {
                        @Override
                        public void back() {
                            finish();
                        }
                    });
                    md.show();
                } else {
                    finish();
                }


                break;
            case R.id.tv_yy_ok:
//                Intent i=new Intent(self,PlunActivity.class);
//
//                startActivity(i);
                break;
            case R.id.tv_start:
                handler.postDelayed(runnable, 1000);
                ll_list.setVisibility(View.VISIBLE);
                tv_start.setVisibility(View.GONE);
                break;
        }
    }


    private void setData() {
        ShidaiApi.getDataList(self, TYPE_ks, Config.getShidaiUserInfo().getUserid(), pageNo, Constant.pageNum, fuxiList.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    advertorialList = (fuxiList) rspData;
                    Config.Default_Score = advertorialList.getScore();
                    if (pageNo == 1)
                        baseobjects = advertorialList.getRecord();
                    else
                        baseobjects.addAll(advertorialList.getRecord());
                    Log.v("ere", "fdfdf");

                    sRecyclerViewAdapter.notifyDataSetChanged();
                    updateBottom();
                } else {
                    showShortToast(rspData.getErrmsg());
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


    public class baseObRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class arViewHolder extends RecyclerView.ViewHolder {

            RelativeLayout rl_text_scope;
            TextView tv_scope;
            ClipTextView tv_words;
            ClipTextView tv_info;
            RelativeLayout rl_play;
            FlowLayout tv_wordlist;
            ImageView iv_danci;
            ImageView iv_yuyin;
            ImageView iv_play;


            public arViewHolder(View itemView) {
                super(itemView);
                rl_text_scope = (RelativeLayout) itemView.findViewById(R.id.rl_text_scope);
                tv_scope = (TextView) itemView.findViewById(R.id.tv_scope);
                tv_words = (ClipTextView) itemView.findViewById(R.id.tv_words);
                tv_info = (ClipTextView) itemView.findViewById(R.id.tv_info);
                rl_play = (RelativeLayout) itemView.findViewById(R.id.rl_play);
                iv_danci = (ImageView) itemView.findViewById(R.id.iv_danci);
                iv_yuyin = (ImageView) itemView.findViewById(R.id.iv_yuyin);
                iv_play = (ImageView) itemView.findViewById(R.id.iv_play);
                tv_wordlist = (FlowLayout) itemView.findViewById(R.id.tv_wordlist);

                tv_words.setTextIsSelectable(true);


            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(self).inflate(R.layout.item_shidai_tjzy, parent, false);
            return new arViewHolder(inflatedView);

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof arViewHolder) {
                final fuxiList.RecordBean ob = getItem(position);
                ((arViewHolder) holder).tv_words.setText(ob.getContent().replace("\\r\\n", "").replace("\r\n", ""));
//                ((arViewHolder) holder).tv_words.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        AddWordDialog dialog = new AddWordDialog(self, ob.getContent().replace("\\r\\n", "").replace("\r\n", ""));
//                        dialog.show();
//                        return false;
//                    }
//                });
                ((arViewHolder) holder).tv_info.setText(ob.getContent1());
                ((arViewHolder) holder).tv_scope.setText(ob.getScore() + "");
                ((arViewHolder) holder).iv_danci.setVisibility(View.GONE);
                getWordlist(ob.getContent(), ((arViewHolder) holder).tv_wordlist);
                if (ob.getScore() >= Config.Default_Score)
                    ((arViewHolder) holder).tv_scope.setBackgroundResource(R.drawable.shape_round_green);
                else
                    ((arViewHolder) holder).tv_scope.setBackgroundResource(R.drawable.shape_round_red);

                if (ob.getScore() < 90) {
                    ((arViewHolder) holder).tv_words.setVisibility(View.GONE);
//                    ((arViewHolder) holder).tv_wordlist.setVisibility(View.GONE);
                } else {
                    ((arViewHolder) holder).tv_words.setVisibility(View.VISIBLE);
                    ((arViewHolder) holder).tv_words.setTextIsSelectable(true);
//                    ((arViewHolder) holder).tv_wordlist.setVisibility(View.VISIBLE);
                }

                ((arViewHolder) holder).tv_wordlist.setOnClickListener(new View.OnClickListener() {
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
                        EvaluatorManager.getInstance(self).stop();
                        SpeechManager.getInstance(self).play(ob.getContent());
                    }
                });

                ((arViewHolder) holder).iv_yuyin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SpeechManager.getInstance(self).stop();
                        if (lastEvl != null) {
                            lastEvl.setBackgroundResource(R.drawable.icon_talk);
                        }
                        lastEvl = (ImageView) v;
                        EvaluatorManager.getInstance(self).play(ob.getContent(), position, ob.getId());
                        lastEvl.setBackgroundResource(R.drawable.play_list);
                        AnimationDrawable drawable = (AnimationDrawable) lastEvl
                                .getBackground();
                        drawable.start();
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

    private void getWordlist(String content, FlowLayout parent) {

        parent.removeAllViews();
        if (content == null)
            return;
        JSONArray list = null;
        try {
            list = new JSONArray(content);

            //        String ready = content.replace("\\r\\n", "").replace("\r\n", "").replace(",", "").replace(".", "").replace("!", "");
//        String[] listString = ready.split(" ");
//        List list = Arrays.asList(listString);
//        Collections.shuffle(list);
            String result = "";
            for (int i = 0; i < list.length(); i++) {
                TextView tv = new TextView(self);
                tv.setPadding((int) Utils.dp2px(getResources(), 4), (int) Utils.dp2px(getResources(), 6), (int) Utils.dp2px(getResources(), 6), (int) Utils.dp2px(getResources(), 4));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins((int) Utils.dp2px(getResources(), 8), (int) Utils.dp2px(getResources(), 8), 0, 0);
                tv.setLayoutParams(lp);
                tv.setBackgroundColor(self.getResources().getColor(R.color.color_meat));
                tv.setGravity(Gravity.CENTER);
                tv.setText(list.getString(i));
                tv.setTextSize(16);
                tv.setTextColor(self.getResources().getColor(R.color.black));
                parent.addView(tv);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                        updateBottom();
                        CompleteExam();
                        scoreDialog md = new scoreDialog(self, "提示", currentscope + "");
                        md.show();
                        lastEvl.setBackgroundResource(R.drawable.icon_talk);
                        lastEvl = null;
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
        ShidaiApi.updateScope(self, Config.getShidaiUserInfo().getUserid(), id, scope, 15, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
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

    private boolean isComplete() {
        for (int i = 0; i < baseobjects.size(); i++) {
            fuxiList.RecordBean rb = baseobjects.get(i);
            if (rb.getScore() < Config.Default_Score)
                return false;
        }
        return true;
    }

    /**
     * 调用完成接口
     */
    private void CompleteExam() {
        if (isComplete()) {
            ShidaiApi.CompleteExam(self, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
                @Override
                public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {

                }

                @Override
                public void failed(String msg) {

                }
            });
        }
    }

    private void updateBottom() {
        if (isComplete() || (advertorialList != null && "1".equals(advertorialList.getType15flag()))) {
            tv_yy_ok.setBackgroundColor(getResources().getColor(R.color.App_calendar_big));
            tv_yy_ok.setTextColor(getResources().getColor(R.color.white));
            tv_yy_ok.setText("已完成");
            tv_yy_ok.setClickable(false);
            tv_yy_ok.setVisibility(View.VISIBLE);
        }
//        else {
//            tv_yy_ok.setBackgroundColor(getResources().getColor(R.color.App_backgroud_grey));
//            tv_yy_ok.setTextColor(getResources().getColor(R.color.App_calendar_yinli));
//            tv_yy_ok.setText("未完成");
//            tv_yy_ok.setClickable(false);
//        }
    }

}
