package com.daocheng.girlshop.activity.shidai.detail;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.activity.shidai.list.ZipinglunActivity;
import com.daocheng.girlshop.dialog.CProgressDialog;
import com.daocheng.girlshop.dialog.Sharedialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.Vrecoder;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.daocheng.girlshop.entity.shdiai.punlun;
import com.daocheng.girlshop.entity.shdiai.qiniuToke;
import com.daocheng.girlshop.fragment.shidai.shidaiHomeFragment;
import com.daocheng.girlshop.media.MusicActivity;
import com.daocheng.girlshop.media.MusicService;
import com.daocheng.girlshop.media.recoder.MediaManager;
import com.daocheng.girlshop.media.recoder.view.AudioRecordButton;
import com.daocheng.girlshop.myApplication;
import com.daocheng.girlshop.net.DownloadManager;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;
import com.daocheng.girlshop.utils.Utils;
import com.daocheng.girlshop.view.Bookends;
import com.daocheng.girlshop.view.RoundImageView;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.umeng.socialize.ShareAction;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.mobiwise.playerview.MusicPlayerView;

/**
 * 项目名称：girlshop
 * 类描述：想唱就唱
 * 创建人：jdd
 * 创建时间：2016/9/2 14:47
 * 修改人：jdd
 * 修改时间：2016/9/2 14:47
 * 修改备注：
 */

public class SingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView tv_left;
    private TextView tv_right;
    private TextView tv_center;
    private TextView tv_name;
    private TextView tv_time;
    private ImageView iv_play;
    private SeekBar seekbar;
    private TextView tv_length;
    private ImageView iv_ban;
    private TextView tv_songword;
    private TextView tv_comment;
    private LinearLayout ll_plun;

    private int position;
    private dataListResult datalist;
    private List<dataListResult.RecordBean> musicgroup;
    private int musiclength = 0;

    private boolean isplay = false;

    private static String shareUrl="http://www.cs66club.com/app/share?id=";



    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<Vrecoder.RecordBean> baseobjects;
    private baseObRecycleAdapter sRecyclerViewAdapter;
    private int pageNo = 1;
    private Vrecoder advertorialList;
    private int lastVisibleItem;

    private Bookends<baseObRecycleAdapter> mBookends;
    private static final String TYPE_TXT = "txt";
    private static final String TYPE_VOICE = "amr";

    private RelativeLayout headview;

    private int id;


    //底部
    private CheckBox cb_isvoice;
    private AudioRecordButton recordButton;
    private EditText ed_text;
    private TextView tv_send;
    private String dir = Environment.getExternalStorageDirectory()
            + "/cooke/recoder/";

    private String pl_share_url="http://www.cs66club.com/app/share_sing?id=";


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_send:
                sendText();
                break;
            case R.id.ll_plun:
            case R.id.tv_comment:
                ll_plun.setVisibility(View.GONE);
                if (mService.getState()) {
                    pause();
                }
//                Intent intent = new Intent(self, pinglunActivity.class);
//                intent.putExtra("id", musicgroup.get(position).getId());
//                startActivity(intent);
                break;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_detail;
    }

    @Override
    protected void setupViews() {
        LayoutInflater inflater = LayoutInflater.from(self);
        ll_plun = (LinearLayout) findViewById(R.id.ll_plun);
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.makeSureTv);
        tv_center = (TextView) findViewById(R.id.tv_center);

        headview = (RelativeLayout) inflater.inflate(R.layout.head_changge, mRecyclerView, false);
        tv_name = (TextView) headview.findViewById(R.id.tv_name);
        tv_time = (TextView) headview.findViewById(R.id.tv_time);
        iv_play = (ImageView) headview.findViewById(R.id.iv_play);
        seekbar = (SeekBar) headview.findViewById(R.id.seekbar);
        tv_length = (TextView) headview.findViewById(R.id.tv_length);
        iv_ban = (ImageView) headview.findViewById(R.id.iv_ban);
        tv_songword = (TextView) headview.findViewById(R.id.tv_songword);
//        tv_zan=(TextView)findViewById(R.id.tv_zan);
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        tv_left.setVisibility(View.VISIBLE);
        tv_comment.setOnClickListener(this);
        ll_plun.setOnClickListener(this);

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
        cb_isvoice = (CheckBox) findViewById(R.id.cb_isvoice);
        recordButton = (AudioRecordButton) findViewById(R.id.recordButton);
        ed_text = (EditText) findViewById(R.id.ed_text);
        tv_send.setOnClickListener(this);
        tv_left.setOnClickListener(this);
    }

    @Override
    protected void initialized() {

        tv_right.setText("分享");
        tv_right.setVisibility(View.VISIBLE);



        //定义一个新线程，用来发送消息，通知更新UI
        myThread = new Thread(new MyThread());
        position = getIntent().getIntExtra("position", 0);
        datalist = (dataListResult) getIntent().getSerializableExtra("data");
        musicgroup = datalist.getRecord();

        tv_center.setText(datalist.getRecord().get(position).getTitle());


        tv_name.setText(datalist.getRecord().get(position).getTitle());
        tv_time.setText(datalist.getRecord().get(position).getUpdatetime());
        tv_songword.setText(Html.fromHtml(datalist.getRecord().get(position).getContent()));
        ImageLoader.getInstance().displayImage(musicgroup.get(position).getLogo(),iv_ban);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sharedialog sd=new Sharedialog(self,shareUrl+datalist.getRecord().get(position).getId(),datalist.getRecord().get(position).getTitle());
                sd.show();

            }
        });


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //手动调节进度
                // TODO Auto-generated method stub
                //seekbar的拖动位置
                int dest = seekBar.getProgress();
                //seekbar的最大值
                int max = seekBar.getMax();
                //调用service调节播放进度
                mService.setProgress(max, dest);
                iv_play.setImageResource(R.drawable.sing_icon_stop);
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
                mService.play();
            }

        });


        //绑定service;
        Intent serviceIntent = new Intent(this, MusicService.class);

        serviceIntent.setAction("co.mobiwise.musicplayerprogressview.MusicService");
        //如果未绑定，则进行绑定
        if (!mBound) {
            bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
        }

        id=musicgroup.get(position).getId();

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

        setData();
        recordButton.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
            @Override
            public void onFinished(float seconds, String filePath) {
                Log.v("recoderVoice", filePath + seconds);
//                if (mService.getState()) {
//                }else
//                {
//                    play();
//                }
                if (Config.getShidaiUserInfo()!=null&&Config.getShidaiUserInfo().getLevel().contains("游客"))
                {
                    showShortToast("游客不能发表");
                    return;
                }
                uploadhead(filePath, ((int) seconds)>1?(int) seconds:1 );
            }
        });
        recordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    if (mService.getState()) {
                        pause();
                    }
                }
                return false;
            }
        });
        cb_isvoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ed_text.setVisibility(View.VISIBLE);
                    recordButton.setVisibility(View.GONE);
                    tv_send.setVisibility(View.VISIBLE);
                } else {
                    ed_text.setVisibility(View.GONE);
                    tv_send.setVisibility(View.GONE);
                    recordButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setData() {
        ShidaiApi.getpingList(self,Config.getShidaiUserInfo().getUserid(), id, pageNo, Constant.pageNum, Vrecoder.class, new NetUtils.NetCallBack<ServiceResult>() {
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
            if (Config.getShidaiUserInfo()!=null&&Config.getShidaiUserInfo().getLevel().contains("游客"))
            {
                showShortToast("游客不能发表");
                return;
            }
            sendMessge(TYPE_TXT, 0, "", ed_text.getText().toString());
            ed_text.setText("");
        }
    }



    private void uploadhead(final String file, final int length) {
        ShidaiApi.getQiNiuUptoken(self, qiniuToke.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {

                if ("0".endsWith(rspData.getErrcode())) {
                    qiniuToke qtoken = (qiniuToke) rspData;
                    String recode = qtoken.getRecord();
                    if (TextUtils.isEmpty(recode)) {
                        failed("服务器上传失败");
                        return;
                    }

                    UploadManager uploadManager = myApplication.getInstance().getUploadManager();
                    String data = file;
                    String token = recode;
                    String key = file.replace(dir, "");

                    final CProgressDialog progressDialog = new CProgressDialog(self, R.style.CustomDialog);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("");
                    progressDialog.setCancelable(true);
                    try {
                        if (!progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                    } catch (Exception e) {

                    }


                    uploadManager.put(data, key, token,
                            new UpCompletionHandler() {

                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    //  res 包含hash、key等信息，具体字段取决于上传策略的设置。
                                    Log.v("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                                    progressDialog.dismiss();

                                    sendMessge(TYPE_VOICE, length, key, "");
                                }
                            }, new UploadOptions(null, null, false, null, new UpCancellationSignal() {
                                public boolean isCancelled() {
                                    return false;
                                }
                            }));
                }
            }

            @Override
            public void failed(String msg) {
                showShortToast(msg);
            }
        });
    }



    private void sendMessge(final String type, final int length, final String url, final String content) {


        ShidaiApi.sendPinglun(self, Config.getShidaiUserInfo().getUserid(), id, type, length, url, content,  punlun.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {

                if ("0".equals(rspData.getErrcode())) {

                    punlun pl=(punlun)rspData;

                    Vrecoder.RecordBean recordBean = new Vrecoder.RecordBean();
                    recordBean.setContent(content);
                    recordBean.setHead(Config.getShidaiUserInfo().getHead());
                    recordBean.setLength(length);
                    recordBean.setNickname(Config.getShidaiUserInfo().getNickname());
                    recordBean.setType(type);
                    recordBean.setUrl( ShidaiApi.Pic_BASE_URL +url);
                    recordBean.setUpdatetime("现在");
                    recordBean.setZan(0);
                    recordBean.setId(pl.getId());
                    recordBean.setUserid(Config.getShidaiUserInfo().getUserid());

                    baseobjects.add(0,recordBean);
                    mBookends.notifyDataSetChanged();
                    mRecyclerView.smoothScrollToPosition(0);
                }
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    private void play() {
        if (position < 0)
            position = 0;
        else if (position > musicgroup.size() - 1)
            position = musicgroup.size() - 1;
        if (mBound)
            mService.play(musicgroup.get(position).getMp3());

        iv_play.setImageResource(R.drawable.sing_icon_stop);

        tv_name.setText(datalist.getRecord().get(position).getTitle());
        tv_time.setText(datalist.getRecord().get(position).getUpdatetime());
    }

    private void updatetotal() {
        musiclength = mService.getTotalwidth();
        tv_length.setText(Utils.secToTime(musiclength / 1000));
    }

    private void pause() {
        if (mBound)
            mService.pause();


        iv_play.setImageResource(R.drawable.sing_icon_play);


    }


    Boolean mBound = false;

    MusicService mService;


    //多线程，后台更新UI
    Thread myThread;

    //控制后台线程退出
    boolean playStatus = true;


    //处理进度条更新
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //从bundle中获取进度，是double类型，播放的百分比
                    double progress = msg.getData().getDouble("progress");

                    //根据播放百分比，计算seekbar的实际位置
                    int max = seekbar.getMax();
                    int position = (int) (max * progress);

                    //设置seekbar的实际位置
                    seekbar.setProgress(position);

//                    if (progress>1000& musiclength == 0) {
                    updatetotal();
//                    }
//                    tv_progress.setText(Utils.secToTime((int) (progress * musiclength / 1000)));

                    break;
                default:
                    break;
            }

        }
    };


    //实现runnable接口，多线程实时更新进度条
    public class MyThread implements Runnable {


        //通知UI更新的消息


        //用来向UI线程传递进度的值
        Bundle data = new Bundle();

        //更新UI间隔时间
        int milliseconds = 100;
        double progress;

        @Override
        public void run() {
            // TODO Auto-generated method stub

            //用来标识是否还在播放状态，用来控制线程退出
            while (playStatus) {

                try {
                    //绑定成功才能开始更新UI
                    if (mBound) {

                        //发送消息，要求更新UI

                        Message msg = new Message();
                        data.clear();

                        progress = mService.getProgress();
                        msg.what = 0;

                        data.putDouble("progress", progress);
                        msg.setData(data);
                        mHandler.sendMessage(msg);
                    }
                    Thread.sleep(milliseconds);
                    //Thread.currentThread().sleep(milliseconds);
                    //每隔100ms更新一次UI

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder binder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MusicService.MyBinder myBinder = (MusicService.MyBinder) binder;

            //获取service
            mService = (MusicService) myBinder.getService();

//            mpv.setProgress(mService.getTotalwidth());
            iv_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mService.getState()) {
                        pause();
                    } else {
                        play();
                    }
                }
            });

            //绑定成功
            mBound = true;

            //开启线程，更新UI
            myThread.start();
            play();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


    @Override
    public void onDestroy() {
        //销毁activity时，要记得销毁线程
        playStatus = false;
        super.onDestroy();
        MediaManager.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();
        MediaManager.pause();


    }

    @Override
    protected void onResume() {
        super.onResume();
        play();
        MediaManager.resume();
    }



    public class baseObRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class arViewHolder extends RecyclerView.ViewHolder {

            RoundImageView iv_head;
            TextView tv_name;
            TextView tv_time;

            LinearLayout ll_text;
            TextView tv_message;

            RelativeLayout rl_voice;
            LinearLayout ll_voice;
            ImageView iv_voice;
            TextView tv_voicesize;

            CheckBox bt_zan;
            TextView bt_pinglun;
            TextView bt_share;


            public arViewHolder(View itemView) {
                super(itemView);
                iv_head = (RoundImageView) itemView.findViewById(R.id.iv_head);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_time = (TextView) itemView.findViewById(R.id.tv_time);

                ll_text = (LinearLayout) itemView.findViewById(R.id.ll_text);
                tv_message = (TextView) itemView.findViewById(R.id.tv_message);

                rl_voice = (RelativeLayout) itemView.findViewById(R.id.rl_voice);
                ll_voice = (LinearLayout) itemView.findViewById(R.id.ll_voice);
                iv_voice = (ImageView) itemView.findViewById(R.id.iv_voice);
                tv_voicesize = (TextView) itemView.findViewById(R.id.tv_voicesize);

                bt_zan = (CheckBox) itemView.findViewById(R.id.bt_zan);
                bt_pinglun = (TextView) itemView.findViewById(R.id.bt_pinglun);
                bt_share=(TextView)itemView.findViewById(R.id.bt_share);
            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View inflatedView = LayoutInflater.from(self).inflate(R.layout.shidai_item_message, parent, false);
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
                ((arViewHolder) holder).bt_zan.setText(" ("+ob.getZan() + ")");
                ((arViewHolder) holder).bt_zan.setChecked(ob.iszan());
                ((arViewHolder) holder).bt_zan.setEnabled(!ob.iszan());
                ((arViewHolder) holder).bt_pinglun.setText("评论 ("+ob.getSub()+")");

                ((arViewHolder) holder).bt_zan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShidaiApi.addZan(self, Config.getShidaiUserInfo().getUserid(), ob.getId(), ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
                            @Override
                            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                                if ("0".equals(rspData.getErrcode())) {
                                    baseobjects.get(position).setZan(ob.getZan() + 1);
                                    mBookends.notifyDataSetChanged();
//                                    showShortToast("点赞成功");
                                    ((arViewHolder) holder).bt_zan.setChecked(true);
                                    getItem(position).setIszan(true);
                                }
                            }

                            @Override
                            public void failed(String msg) {

                            }
                        });
                    }
                });

                ((arViewHolder) holder).bt_pinglun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(self, ZipinglunActivity.class);
                        intent.putExtra("id", ob.getId());
                        startActivity(intent);
                    }
                });


                if (ob.getType().equals(TYPE_TXT)) {
                    ((arViewHolder)holder).bt_share.setVisibility(View.GONE);
                    ((arViewHolder) holder).ll_text.setVisibility(View.VISIBLE);
                    ((arViewHolder) holder).rl_voice.setVisibility(View.GONE);
                    ((arViewHolder) holder).tv_message.setText(ob.getContent());
                } else {
                    ((arViewHolder)holder).bt_share.setVisibility(View.VISIBLE);
                    ((arViewHolder) holder).ll_text.setVisibility(View.GONE);
                    ((arViewHolder) holder).rl_voice.setVisibility(View.VISIBLE);
                    ((arViewHolder) holder).tv_voicesize.setText(ob.getLength()+"s");

                    ((arViewHolder)holder).bt_share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Sharedialog sd=new Sharedialog(self,pl_share_url+ob.getId(),ob.getNickname());
                            sd.show();
                        }
                    });


                    ((arViewHolder) holder).ll_voice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(ob.getUrl())) {
//                                showShortToast("没有语音信息");
                                return;
                            }
                            final String localFile = DownloadManager.mSaveRecodeDirPath + ob.getUrl().replace(ShidaiApi.Pic_BASE_URL, "");
                            File localfile = new File(localFile);
                            ((arViewHolder) holder).iv_voice.setBackgroundResource(R.drawable.play);
                            AnimationDrawable drawable = (AnimationDrawable) ((arViewHolder) holder).iv_voice
                                    .getBackground();
                            drawable.start();
                            if (localfile.exists()) {
                                MediaManager.playSound(localFile,
                                        new MediaPlayer.OnCompletionListener() {

                                            @Override
                                            public void onCompletion(MediaPlayer mp) {
//                                                showShortToast("播放完成");
                                                ((arViewHolder) holder).iv_voice.setBackgroundResource(R.drawable.pk_icon_voice3);

                                            }
                                        });
                            } else

                                DownloadManager.getInstance().download(localFile, DownloadManager.RECODER, ob.getUrl(), new Listener<Void>() {
                                    @Override
                                    public void onSuccess(Void response) {

                                        MediaManager.playSound(localFile,
                                                new MediaPlayer.OnCompletionListener() {

                                                    @Override
                                                    public void onCompletion(MediaPlayer mp) {
//                                                        showShortToast("播放完成");
                                                        ((arViewHolder) holder).iv_voice.setBackgroundResource(R.drawable.pk_icon_voice3);

                                                    }
                                                });
                                    }

                                    @Override
                                    public void onProgressChange(long fileSize, long downloadedSize) {
                                        super.onProgressChange(fileSize, downloadedSize);

                                    }

                                    @Override
                                    public void onError(NetroidError error) {
                                        super.onError(error);
                                        ((arViewHolder) holder).iv_voice.setBackgroundResource(R.drawable.pk_icon_voice3);
                                    }
                                });


                        }
                    });

                }


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
