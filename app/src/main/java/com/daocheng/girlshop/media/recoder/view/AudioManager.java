package com.daocheng.girlshop.media.recoder.view;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AudioManager {

	private MediaRecorder mRecorder;
	private String mDirString;
	private String mCurrentFilePathString;

	private boolean isPrepared;// 是否准备好了

	/**
	 * 单例化的方法 1 先声明一个static 类型的变量a 2 在声明默认的构造函数 3 再用public synchronized static
	 * 类名 getInstance() { if(a==null) { a=new 类();} return a; } 或者用以下的方法
	 */

	/**
	 * 单例化这个类
	 */
	private static AudioManager mInstance;

	private AudioManager(String dir) {
		mDirString=dir;
	}

	public static AudioManager getInstance(String dir) {
		if (mInstance == null) {
			synchronized (AudioManager.class) {
				if (mInstance == null) {
					mInstance = new AudioManager(dir);
				
				}
			}
		}
		return mInstance;

	}

	/**
	 * 回调函数，准备完毕，准备好后，button才会开始显示录音框
	 * 
	 * @author nickming
	 *
	 */
	public interface AudioStageListener {
		void wellPrepared();
	}

	public AudioStageListener mListener;

	public void setOnAudioStageListener(AudioStageListener listener) {
		mListener = listener;
	}

	// 准备方法
	public void prepareAudio() {
		try {

			if (mRecorder!=null)
			release();
			// 一开始应该是false的
			isPrepared = false;

			File dir = new File(mDirString);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String fileNameString = generalFileName();
			File file = new File(dir, fileNameString);

			mCurrentFilePathString = file.getAbsolutePath();

			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			// 设置文件音频的输出格式为amr
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);//THREE_GPP
			// 设置音频的编码格式为amr
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			// 设置输出文件

			mRecorder.setAudioChannels(1);
			mRecorder.setAudioSamplingRate(8000);//8000
			mRecorder.setAudioEncodingBitRate(64);
			mRecorder.setOutputFile(file.getAbsolutePath());
			// 设置meidaRecorder的音频源是麦克风




			// 严格遵守google官方api给出的mediaRecorder的状态流程图
			mRecorder.prepare();

			mRecorder.start();
			// 准备结束
			isPrepared = true;
			// 已经准备好了，可以录制了
			if (mListener != null) {
				mListener.wellPrepared();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 随机生成文件的名称
	 * 
	 * @return
	 */
	private String generalFileName() {
		// TODO Auto-generated method stub

		return UUID.randomUUID().toString() + ".amr";
	}

	// 获得声音的level
	public int getVoiceLevel(int maxLevel) {
		// mRecorder.getMaxAmplitude()这个是音频的振幅范围，值域是1-32767
		if (isPrepared) {
			try {
				// 取证+1，否则去不到7
				return maxLevel * mRecorder.getMaxAmplitude() / 32768 + 1;
			} catch (Exception e) {
				// TODO Auto-generated catch block

			}
		}

		return 1;
	}

	// 释放资源
	public void release() {
		// 严格按照api流程进行

		if (mRecorder != null) {
			//added by ouyang start
			try {
				//下面三个参数必须加，不加的话会奔溃，在mediarecorder.stop();
				//报错为：RuntimeException:stop failed
//				mRecorder.setOnErrorListener(null);
//				mRecorder.setOnInfoListener(null);
//				mRecorder.setPreviewDisplay(null);
				mRecorder.stop();

			} catch (IllegalStateException e) {
				mRecorder = null;
				mRecorder = new MediaRecorder();
				mRecorder.stop();
				// TODO: handle exception
				Log.i("Exception", Log.getStackTraceString(e));
			} catch (RuntimeException e) {
				// TODO: handle exception
				Log.i("Exception", Log.getStackTraceString(e));
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("Exception", Log.getStackTraceString(e));
			}
			//added by ouyang end

		}
		mRecorder.release();
		mRecorder = null;

	}

	// 取消,因为prepare时产生了一个文件，所以cancel方法应该要删除这个文件，
	// 这是与release的方法的区别
	public void cancel() {
		release();
		if (mCurrentFilePathString != null) {
			File file = new File(mCurrentFilePathString);
			file.delete();
			mCurrentFilePathString = null;
		}

	}

	public String getCurrentFilePath() {
		// TODO Auto-generated method stub
		return mCurrentFilePathString;
	}

}
