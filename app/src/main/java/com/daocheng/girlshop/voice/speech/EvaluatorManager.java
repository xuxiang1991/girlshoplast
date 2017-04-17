package com.daocheng.girlshop.voice.speech;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.dialog.SpeechDialog;
import com.daocheng.girlshop.utils.Constant;
import com.daocheng.girlshop.voice.ise.result.FinalResult;
import com.daocheng.girlshop.voice.ise.result.Result;
import com.daocheng.girlshop.voice.ise.result.xml.XmlResultParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;
import com.iflytek.cloud.ui.RecognizerDialog;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：jdd
 * 创建时间：2016/6/25 9:49
 * 修改人：jdd
 * 修改时间：2016/6/25 9:49
 * 修改备注：
 */

public class EvaluatorManager {


    private static String TAG = "EvaluatorManager";

    private final static String PREFER_NAME = "ise_settings";
    private final static int REQUEST_CODE_SETTINGS = 1;

    private Toast mToast;

    // 评测语种
    private String language;
    // 评测题型
    private String category;
    // 结果等级
    private String result_level;

    private String mLastResult;
    private SpeechEvaluator mIse;

    private Context mContext;

    private int position;

    private SpeechDialog speechdialog;

    private static EvaluatorManager evaluatorManager;

//    private boolean isInit=false;

    @SuppressLint("ShowToast")
    public EvaluatorManager(Context context)
    {
        mContext=context;
        if (mIse==null)
        {
            mIse = SpeechEvaluator.createEvaluator(mContext, null);
        }
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
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
//                isInit=true;
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    public static EvaluatorManager getInstance(Context context)
    {
    if (evaluatorManager==null)
    {
        evaluatorManager=new EvaluatorManager(context);

    }
        return evaluatorManager;
    }




    public void play(String text,int position,int id)
    {

//        if (!isInit)
//        {
//            showTip("语音评测初始化失败");
//            return;
//        }

        if (mIse == null) {
            showTip("语音评测启动失败，请重启软件");
            return;
        }

        String evaText =text;
        mLastResult = null;


        setParams(id);
        mIse.startEvaluating(evaText, null, mEvaluatorListener);
        this.position=position;

//        speechdialog=new SpeechDialog(mContext);
//        speechdialog.show();
    }


    public void paurse()
    {
//        speechdialog.dismiss();
        if (!TextUtils.isEmpty(mLastResult)) {
            XmlResultParser resultParser = new XmlResultParser();
            FinalResult result =(FinalResult) resultParser.parse(mLastResult);
            if (null != result) {
                sendresult((int) (result.total_score / 5 * 100));

            } else {
                showTip("结析结果为空");
            }
        }

    }


    private void sendresult(int code)
    {
        Intent intent = new Intent();
        intent.setAction(Constant.YUYINPINCE);
        intent.putExtra("index", position);
        intent.putExtra("code", code);
        mContext.sendBroadcast(intent);
    }

    public void stop()
    {
        if (mIse.isEvaluating()) {

            mIse.stopEvaluating();
        }
    }

    public void cancel()
    {
        mIse.cancel();

        mLastResult = null;
    }

    public void Destroy()
    {
        if (null != mIse) {
            mIse.destroy();
            mIse = null;
        }
        evaluatorManager=null;
    }




    // 评测监听接口
    private EvaluatorListener mEvaluatorListener = new EvaluatorListener() {

        @Override
        public void onResult(EvaluatorResult result, boolean isLast) {
            Log.d(TAG, "evaluator result :" + isLast);

            if (isLast) {
                StringBuilder builder = new StringBuilder();
                builder.append(result.getResultString());

//                if(!TextUtils.isEmpty(builder)) {
//                    showTip(builder.toString());
//
//                }

                mLastResult = builder.toString();

//                showTip("评测成功");
                paurse();
            }
        }

        @Override
        public void onError(SpeechError error) {
            if(error != null) {
                showTip("error:"+ error.getErrorCode() + "," + error.getErrorDescription());

            } else {
                Log.d(TAG, "evaluator over");
            }
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            Log.d(TAG, "evaluator begin");
//            showTip("evaluator begin");
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            Log.d(TAG, "evaluator stoped");
//            showTip("evaluator stoped");

//            speechdialog.paruse();
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
//            showTip("当前音量：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }

    };

//    // 设置评测试题
//    public void setEvaText(String En_text) {
//        SharedPreferences pref = mContext.getSharedPreferences(PREFER_NAME, mContext.MODE_PRIVATE);
//        language = pref.getString(SpeechConstant.LANGUAGE, "en_us");
//        category = pref.getString(SpeechConstant.ISE_CATEGORY, "read_sentence");
//
//        String text = "";
//        if ("en_us".equals(language)) {
//            if ("read_word".equals(category)) {
//                text =En_text;
//            } else if ("read_sentence".equals(category)) {
//                text = En_text;
//            }
//        }
//
////        else {
////            // 中文评测
////            if ("read_syllable".equals(category)) {
////                text = getString(R.string.text_cn_syllable);
////            } else if ("read_word".equals(category)) {
////                text = getString(R.string.text_cn_word);
////            } else if ("read_sentence".equals(category)) {
////                text = getString(R.string.text_cn_sentence);
////            }
////        }
//
//
//        mLastResult = null;
//    }

    private void showTip(String str) {
        if(!TextUtils.isEmpty(str)) {
            mToast.setText(str);
            mToast.show();
        }
    }
    private void setParams(int id) {
        SharedPreferences pref = mContext.getSharedPreferences(PREFER_NAME, mContext.MODE_PRIVATE);
        // 设置评测语言
        language = pref.getString(SpeechConstant.LANGUAGE, "en_us");
        // 设置需要评测的类型
        category = pref.getString(SpeechConstant.ISE_CATEGORY, "read_sentence");
        // 设置结果等级（中文仅支持complete）
        result_level = pref.getString(SpeechConstant.RESULT_LEVEL, "plain");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        String vad_bos = pref.getString(SpeechConstant.VAD_BOS, "5000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        String vad_eos = pref.getString(SpeechConstant.VAD_EOS, "1800");
        // 语音输入超时时间，即用户最多可以连续说多长时间；
        String speech_timeout = pref.getString(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1");

        mIse.setParameter(SpeechConstant.LANGUAGE, language);
        mIse.setParameter(SpeechConstant.ISE_CATEGORY, category);
        mIse.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mIse.setParameter(SpeechConstant.VAD_BOS, vad_bos);
        mIse.setParameter(SpeechConstant.VAD_EOS, vad_eos);
        mIse.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, speech_timeout);
        mIse.setParameter(SpeechConstant.RESULT_LEVEL, result_level);

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIse.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIse.setParameter(SpeechConstant.ISE_AUDIO_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + "/msc/"+id+"ise.wav");
    }
}
