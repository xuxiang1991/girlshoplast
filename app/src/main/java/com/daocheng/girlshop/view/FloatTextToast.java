package com.daocheng.girlshop.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;


/**
 * 浮动的文本显示。根据一个提供的View，可以把文本显示到该View的下面.
 * 可以设置显示的时间，多了该时间后自动消失。目前只支持纯文本{@link String}类型的显示
 * 因为要计算显示文本的宽度。
 * @author michael_li(飞雪无情)
 * @since 2011-12-10 下午04:57:36
 */
public class FloatTextToast {
	public static final int LENGTH_LONG=Toast.LENGTH_LONG;
	public static final int LENGTH_SHORT=Toast.LENGTH_SHORT;
	private static final int WHAT_SHOW=1;
	
	private Context mContext;
	private View mTargetView;
	private Toast mToast;
	private  TextView mContentView;
	
	private HandlerThread mHandlerThread;
	private FloatTextToastHandler mHandler;
	private FloatTextToast(Context context,View targetView) {
		this.mTargetView = targetView;
		this.mContext= context;
		mToast=new Toast(mContext);
		mContentView=new TextView(mContext);
		mContentView.setBackgroundResource(R.drawable.float_text_toast_bg);
		mContentView.setTextColor(Color.BLACK);
		mContentView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
		mToast.setView(mContentView);
		
		//初始化一个Handler线程
		mHandlerThread=new HandlerThread("FloatTextToast");
		mHandlerThread.start();
		mHandler=new FloatTextToastHandler(mHandlerThread.getLooper());
	}
	/**
	 * 生成一个FloatTextToast
	 * @param context Activity 上下文
	 * @param targetView  目标View，浮动文本要显示在哪个View下面
	 * @param text 要显示的文本
	 * @param duration 浮动文本显示的时间 {@link #LENGTH_LONG} {@link #LENGTH_SHORT}
	 * @return 一个FloatTextToast，可以调用{@link #show()}显示
	 */
	public static FloatTextToast makeText(Context context,View targetView, String text, int duration) {
		final FloatTextToast floatToast=new FloatTextToast(context,targetView);
		final TextView contentView=floatToast.mContentView;
		contentView.setText(text);
		contentView.setPadding(3,3,3,3);
		floatToast.mToast.setDuration(duration);
		return floatToast;
	}
	/**
	 * 显示浮动文本
	 */
	public void show(){
		mHandler.sendEmptyMessage(WHAT_SHOW);
	}
	/**在Handler调用的show方法，主要为了等待{@link #mTargetView}的位置*/
	private void showInHandler(){
		int[] targetPos=getTargetViewPos();
		if(targetPos[0]==0&&targetPos[1]==0){
			mHandler.sendEmptyMessageDelayed(WHAT_SHOW, 100);
		}else{
			final Rect contentPos=getContentViewPos(targetPos);
			mToast.setGravity(Gravity.LEFT|Gravity.TOP, contentPos.left, contentPos.top);
			mToast.show();
		}
	}
	private int[] getTargetViewPos(){
		final int[] targetPos=new int[2];
		mTargetView.getLocationInWindow(targetPos);
		return targetPos;
	}
	/**
	 * 计算获取浮动文本显示的位置，把浮动文本放在targetView的中心处
	 * @return 一个包含top和left的Rect
	 */
	private  Rect getContentViewPos(int[] targetPos){
		final Rect windowVisibleRect=new Rect();
		final View targetView=mTargetView;
		final TextView contentView=mContentView;
		//状态栏高度
		targetView.getWindowVisibleDisplayFrame(windowVisibleRect);
		int statusBarHeight=windowVisibleRect.top;
		
		//背景图那个三角箭头的位置
		final TextPaint textPaint=contentView.getPaint();
		int contentW=(int)textPaint.measureText((String)contentView.getText());
		int arrowPos=(int)(contentW*(30.0/160));
		
		final Rect rect = new Rect();
        rect.left = targetPos[0]+targetView.getWidth()/4-arrowPos;
        rect.top = targetPos[1]-statusBarHeight + targetView.getHeight();
        return rect;
	}
	private class FloatTextToastHandler extends Handler{

		public FloatTextToastHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case WHAT_SHOW:
				showInHandler();
			}
		}
		
		
	}
}
