package com.daocheng.girlshop.voice.speech;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.voice.TtsDemo;
import com.daocheng.girlshop.voice.speech.setting.TtsSettings;
import com.daocheng.girlshop.voice.speech.util.ApkInstaller;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：jdd
 * 创建时间：2016/6/24 15:38
 * 修改人：jdd
 * 修改时间：2016/6/24 15:38
 * 修改备注：
 */

public class SpeechManager {

    private static String TAG = "SpeechManager";

    // 语音合成对象
    private SpeechSynthesizer mTts;

    private static SpeechManager speechmanager;

    private Context mcontext;

    // 默认发音人
    private String voicer = "henry";

    private String[] mCloudVoicersEntries;
    private String[] mCloudVoicersValue;

    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;

    // 云端/本地单选按钮
    private RadioGroup mRadioGroup;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 语记安装助手类
    ApkInstaller mInstaller;

    private Toast mToast;
    private SharedPreferences mSharedPreferences;

    private boolean isInit = false;

    @SuppressLint("ShowToast")
    public SpeechManager(Context context) {
        this.mcontext = context;
        mToast = Toast.makeText(mcontext, "", Toast.LENGTH_SHORT);
        if (mTts == null) {
            // 初始化合成对象
            mTts = SpeechSynthesizer.createSynthesizer(mcontext, mTtsInitListener);
        }

        // 云端发音人名称列表
        mCloudVoicersEntries = mcontext.getResources().getStringArray(R.array.voicer_cloud_entries);
        mCloudVoicersValue = mcontext.getResources().getStringArray(R.array.voicer_cloud_values);

        mSharedPreferences = mcontext.getSharedPreferences(TtsSettings.PREFER_NAME, mcontext.MODE_PRIVATE);


        mInstaller = new ApkInstaller((Activity) mcontext);
    }

    public static SpeechManager getInstance(Context context) {
        if (speechmanager == null) {
            speechmanager = new SpeechManager(context);
        }
        return speechmanager;

    }


    public SpeechSynthesizer getSpeecher() {
        if (mTts == null) {
            // 初始化合成对象
            mTts = SpeechSynthesizer.createSynthesizer(mcontext, mTtsInitListener);
        }
        return mTts;
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败,错误码：" + code);
            } else {
//                showTip("初始化成功");

                isInit = true;
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };


    public void Destroy() {
        mTts.stopSpeaking();
        // 退出时释放连接
        mTts.destroy();
        mTts = null;
        speechmanager = null;
    }

    public void play(String data) {
        // 移动数据分析，收集开始合成事件
        FlowerCollector.onEvent(mcontext, "tts_play");
        // 设置参数
        setParam();
        int code = mTts.startSpeaking(data, mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
//			String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
//			int code = mTts.synthesizeToUri(text, path, mTtsListener);

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //未安装则跳转到提示安装页面
                mInstaller.install();
            } else {
                showTip("语音合成失败,错误码: " + code);
            }
        }
    }

    public void stop() {
        mTts.stopSpeaking();
    }

    public void pause() {
        mTts.pauseSpeaking();
    }

    public void reStart() {
        mTts.resumeSpeaking();
    }

    private int selectedNum = 3;

    public void showVoicePlayerDialog() {
        new AlertDialog.Builder(mcontext).setTitle("语音技术由科大讯飞提供")
                .setSingleChoiceItems(mCloudVoicersEntries, // 单选框有几项,各是什么名字
                        selectedNum, // 默认的选项
                        new DialogInterface.OnClickListener() { // 点击单选框后的处理
                            public void onClick(DialogInterface dialog,
                                                int which) { // 点击了哪一项
                                voicer = mCloudVoicersValue[which];

                                selectedNum = which;
                                dialog.dismiss();
                            }
                        }).show();
    }

    public int changePlayer()
    {
        if (selectedNum==3)
        {
            selectedNum=4;

        }else if (selectedNum==4)
        {
            selectedNum=3;
        }
        voicer = mCloudVoicersValue[selectedNum];
        return selectedNum;
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
//            showTip("开始播放");
        }

        @Override
        public void onSpeakPaused() {
//            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
//            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            mPercentForBuffering = percent;
//            showTip(String.format(mcontext.getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            mPercentForPlaying = percent;
//            showTip(String.format(mcontext.getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
//                showTip("播放完成");
            } else if (error != null) {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 参数设置
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }
}
