package com.daocheng.girlshop.activity.shidai.detail;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.activity.shidai.list.DataListActivity;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.daocheng.girlshop.entity.shdiai.hotSongData;
import com.daocheng.girlshop.media.MusicService;
import com.daocheng.girlshop.media.recoder.MediaManager;
import com.daocheng.girlshop.utils.Utils;
import com.daocheng.girlshop.view.Bookends;
import com.daocheng.girlshop.view.ClipTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * 类名称：热门歌曲
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class hotSongActivity extends BaseActivity implements View.OnClickListener {


    private TextView tv_center;
    private ImageView tv_left;
    private String paget_title = "";

    private String musicData;

    /**
     * 热门歌曲
     */
    private hotSongData hotsongdata;

    private List<hotSongData.RecordBean.LyricBean> basehotobjects;
    private baseHotRecycleAdapter shotRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;

    private hotSongData.RecordBean cData;

    private TextView tv_song_start, tv_song_total;
    private SeekBar seekbar;
    private ImageView iv_play, play_back, play_ahead;

    private int musiclength = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot_song;
    }

    @Override
    protected void setupViews() {
        LayoutInflater inflater = LayoutInflater.from(self);
        musicData = getIntent().getStringExtra("musicData");
        if (!TextUtils.isEmpty(musicData)) {
            cData = new Gson().fromJson(musicData, hotSongData.RecordBean.class);
        }

        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);

        tv_center = (TextView) findViewById(R.id.tv_center);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);


        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        basehotobjects = new ArrayList<hotSongData.RecordBean.LyricBean>();
        shotRecyclerViewAdapter = new baseHotRecycleAdapter();
        mRecyclerView.setAdapter(shotRecyclerViewAdapter);


        tv_song_start = (TextView) findViewById(R.id.tv_song_start);
        tv_song_total = (TextView) findViewById(R.id.tv_song_total);

        seekbar = (SeekBar) findViewById(R.id.seekbar);
        iv_play = findViewById(R.id.iv_play);
        play_back = findViewById(R.id.play_back);
        play_ahead = findViewById(R.id.play_ahead);

        play_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cdest = seekbar.getProgress();

                if ((cdest - 5) < 0) {
                    cdest = 0;
                } else {
                    cdest = cdest - 5;
                }

                mService.setProgress(100, cdest);
                iv_play.setImageResource(R.drawable.icon_stop_hot);
            }
        });

        play_ahead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cdest = seekbar.getProgress();

                if ((cdest + 5) > 95) {
                } else {
                    cdest = cdest + 5;
                }

                mService.setProgress(100, cdest);
                iv_play.setImageResource(R.drawable.icon_stop_hot);
            }
        });


    }

    @Override
    protected void initialized() {
        if (cData != null) {
            tv_center.setText(cData.getTitle());
            basehotobjects = cData.getLyric();
            shotRecyclerViewAdapter.notifyDataSetChanged();
        }


        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));
        mSwipeRefreshLayout.setEnabled(false);

        myThread = new Thread(new MyThread());

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
                iv_play.setImageResource(R.drawable.icon_stop_hot);
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
                mService.playRepeat();
            }

        });


        //绑定service;
        Intent serviceIntent = new Intent(this, MusicService.class);

        serviceIntent.setAction("co.mobiwise.musicplayerprogressview.MusicService");
        //如果未绑定，则进行绑定
        if (!mBound) {
            bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
        }


    }


    private void pause() {
        if (mBound)
            mService.pause();


        iv_play.setImageResource(R.drawable.icon_play_hot);


    }


    Boolean mBound = false;

    MusicService mService;


    //多线程，后台更新UI
    Thread myThread;

    //控制后台线程退出
    boolean playStatus = true;

    private int currentPoint = 0;


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
                    tv_song_start.setText(Utils.secToTime((int) (progress * musiclength / 1000)));
                    currentPoint = (int) (progress * musiclength);
                    shotRecyclerViewAdapter.notifyDataSetChanged();
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
//        pause();
//        MediaManager.pause();


    }

    @Override
    protected void onResume() {
        super.onResume();
//        play();
//        MediaManager.resume();
    }

    private void play() {
        if (mBound)
            mService.playRepeat(cData.getMp3url());

        iv_play.setImageResource(R.drawable.icon_stop_hot);

    }


    private void updatetotal() {
        musiclength = mService.getTotalwidth();
        tv_song_total.setText(Utils.secToTime(musiclength / 1000));
    }


    public class baseHotRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class hotViewHolder extends RecyclerView.ViewHolder {

            ClipTextView tv_content_orginal;
            ClipTextView tv_content;
            View ll_item;

            public hotViewHolder(View itemView) {
                super(itemView);
                ll_item = itemView.findViewById(R.id.ll_item);
                tv_content_orginal = (ClipTextView) itemView.findViewById(R.id.tv_content_orginal);
                tv_content = (ClipTextView) itemView.findViewById(R.id.tv_content);
                tv_content_orginal.setTextIsSelectable(true);
                tv_content.setTextIsSelectable(true);


            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(self).inflate(R.layout.item_hot_song, parent, false);
            return new baseHotRecycleAdapter.hotViewHolder(inflatedView);


        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            final hotSongData.RecordBean.LyricBean ob = getItem(position);
            ((hotViewHolder) holder).tv_content_orginal.setText(ob.getWord());
            ((hotViewHolder) holder).tv_content.setText(ob.getWord_ch());

            int positionfont= position+1;
            if (positionfont>=getItemCount())
            {
                positionfont=position;
            }

            int itemTIme = (int) Utils.parseToTime(ob.getTime());
            int itemTimeBefo=(int) Utils.parseToTime(getItem(positionfont).getTime());

            Log.e("xx_time", position + "__" + itemTIme + "__" + currentPoint);
            if ((currentPoint > itemTIme&&currentPoint<itemTimeBefo)||(position==positionfont&&currentPoint > itemTIme)) {
                ((hotViewHolder) holder).tv_content_orginal.setTextColor(getResources().getColor(R.color.dlg_bule_text_normal_color));
                ((hotViewHolder) holder).tv_content.setTextColor(getResources().getColor(R.color.dlg_bule_text_normal_color));
            } else {
                ((hotViewHolder) holder).tv_content_orginal.setTextColor(getResources().getColor(R.color.text_light_grey));
                ((hotViewHolder) holder).tv_content.setTextColor(getResources().getColor(R.color.text_light_grey));
            }

            ((hotViewHolder) holder).ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }

        public hotSongData.RecordBean.LyricBean getItem(int position) {
            return basehotobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return basehotobjects.size();
        }
    }


}
