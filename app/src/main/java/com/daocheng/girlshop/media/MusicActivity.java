package com.daocheng.girlshop.media;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.shidai.detail.pinglunActivity;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.daocheng.girlshop.utils.Utils;

import java.util.List;

import co.mobiwise.playerview.MusicPlayerView;


public class MusicActivity extends Activity {

    private MusicPlayerView mpv;
    private ImageView next;
    private ImageView previous;
    private ImageView viewPlay;
    private ImageView tv_left;
    private ImageView iv_comment;

    private int position;
    private dataListResult datalist;
    private List<dataListResult.RecordBean> musicgroup;
    private int musiclength = 0;
    private TextView tv_progress;
    private TextView tv_total;


    private TextView textViewSong;
    private TextView textViewSinger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shidai_activity_music);

        //定义一个新线程，用来发送消息，通知更新UI
        myThread = new Thread(new MyThread());
        position = getIntent().getIntExtra("position", 0);
        datalist = (dataListResult) getIntent().getSerializableExtra("data");
        musicgroup = datalist.getRecord();


        //初始化播放按钮
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewSong=(TextView)findViewById(R.id.textViewSong);
        textViewSinger=(TextView)findViewById(R.id.textViewSinger);
        textViewSong.setText(datalist.getRecord().get(position).getTitle());
        textViewSinger.setText(datalist.getRecord().get(position).getUpdatetime());
        iv_comment = (ImageView) findViewById(R.id.tv_right);
        iv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicActivity.this, pinglunActivity.class);
                intent.putExtra("id", musicgroup.get(position).getId());
                startActivity(intent);
            }
        });
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_total = (TextView) findViewById(R.id.tv_total);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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

        previous = (ImageView) findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                play();
            }
        });
        viewPlay = (ImageView) findViewById(R.id.like);
        viewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mpv.isRotating()) {
                    pause();
                } else {
                    play();
                }
            }
        });
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position++;
                play();
            }
        });
        mpv = (MusicPlayerView) findViewById(R.id.mpv);
        mpv.setCoverURL(musicgroup.get(position).getLogo());
        mpv.setProgressVisibility(false);
        mpv.onToggled();
        mpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mpv.isRotating()) {
                    pause();
                } else {
                    play();
                }
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


    private void play() {
        if (position < 0)
            position = 0;
        else if (position > musicgroup.size() - 1)
            position = musicgroup.size() - 1;
        if (mBound)
            mService.play(musicgroup.get(position).getMp3());
        mpv.start();
        viewPlay.setImageResource(R.drawable.sing_icon_stop);

        textViewSong.setText(datalist.getRecord().get(position).getTitle());
        textViewSinger.setText(datalist.getRecord().get(position).getUpdatetime());
    }

    private void updatetotal() {
        musiclength = mService.getTotalwidth();
        tv_total.setText(Utils.secToTime(musiclength / 1000));
    }

    private void pause() {
        if (mBound)
            mService.pause();

        mpv.stop();
        viewPlay.setImageResource(R.drawable.sing_icon_play);


    }


    Boolean mBound = false;

    MusicService mService;

    SeekBar seekBar;

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
                    int max = seekBar.getMax();
                    int position = (int) (max * progress);

                    //设置seekbar的实际位置
                    seekBar.setProgress(position);

//                    if (progress>1000& musiclength == 0) {
                        updatetotal();
//                    }
                    tv_progress.setText(Utils.secToTime((int) (progress * musiclength / 1000)));

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();


    }

    @Override
    protected void onResume() {
        super.onResume();
        play();
    }
}
