package com.daocheng.girlshop.media;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * 项目名称：MusicPlayerView-master
 * 类描述：
 * 创建人：jdd
 * 创建时间：2016/6/20 17:44
 * 修改人：jdd
 * 修改时间：2016/6/20 17:44
 * 修改备注：
 */

public class MusicService extends Service {


    IBinder musicBinder  = new MyBinder();

    //获取到activity的Handler，用来通知更新进度条
    Handler mHandler;

    //播放音乐的媒体类
    MediaPlayer mediaPlayer;

    //本地歌曲的路径
    String path = Environment.getExternalStorageDirectory() + "/cooke/yang.mp3" ;

    private String TAG = "MyService";

    private String lastPath="";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");

        init();

    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        //当绑定后，返回一个musicBinder
        return musicBinder;
    }

   public class MyBinder extends Binder {

        public Service getService(){
            return MusicService.this;
        }
    }

    //初始化音乐播放
  private   void init(){
        //进入Idle

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        try {
            File f=new File(path);
            boolean fd=f.exists();
            //初始化
            mediaPlayer.setDataSource(path);

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // prepare 通过异步的方式装载媒体资源
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Toast.makeText(MusicService.this,"准备好了",Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //返回当前的播放进度，是double类型，即播放的百分比
    public double getProgress(){
        int position = mediaPlayer.getCurrentPosition();
        int time = mediaPlayer.getDuration();

        double progress = (double)position / (double)time;

        return progress;
    }


    /**
     * 音乐长度
     *
     * @return
     */
    public int getTotalwidth()
    {
        return mediaPlayer.getDuration();
    }

    //通过activity调节播放进度
    public void setProgress(int max , int dest){
        int time = mediaPlayer.getDuration();
        mediaPlayer.seekTo(time*dest/max);
    }

    //测试播放音乐
    public void play(){
        if(mediaPlayer != null){
            mediaPlayer.start();
        }

    }

    public void playRepeat(){
        if(mediaPlayer != null){
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

    }
    public void playRepeat(String path){
        if (lastPath.equals(path))
        {
            play();
            return;
        }

        lastPath=path;

        if (mediaPlayer != null) {
            mediaPlayer.reset();

        }
        try {
//            File f=new File(path);
//            boolean fd=f.exists();
            //初始化
            mediaPlayer.setDataSource(path);

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // prepare 通过异步的方式装载媒体资源
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void play(String path)
    {
        if (lastPath.equals(path))
        {
            play();
            return;
        }

       lastPath=path;

        if (mediaPlayer != null) {
            mediaPlayer.reset();

        }
        try {
//            File f=new File(path);
//            boolean fd=f.exists();
            //初始化
            mediaPlayer.setDataSource(path);

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // prepare 通过异步的方式装载媒体资源
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //暂停音乐
    public void pause() {
        if (mediaPlayer != null ) {
            mediaPlayer.pause();
        }
    }

    public void pauseTonext()
    {
        if (mediaPlayer != null ) {
            mediaPlayer.stop();
            mediaPlayer.release();
            lastPath="";
        }
    }

    /**
     * 音乐播放状态
     *
     * @return
     */
    public boolean getState()
    {
        if (mediaPlayer!=null)
        return mediaPlayer.isPlaying();
        else
            return false;
    }

    //service 销毁时，停止播放音乐，释放资源
    @Override
    public void onDestroy() {
        // 在activity结束的时候回收资源
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}