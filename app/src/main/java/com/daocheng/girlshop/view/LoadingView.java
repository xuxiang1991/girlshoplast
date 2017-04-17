package com.daocheng.girlshop.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daocheng.girlshop.Interface.RequestWebListener;
import com.daocheng.girlshop.R;


public class LoadingView extends RelativeLayout implements OnClickListener {
	
	private AnimationDrawable animationDrawable;
	private ImageView loading_img;
	private LinearLayout error_layout;
//	private TextView txt_error;
	private LinearLayout empty_layout;
	private View v;
	
	RequestWebListener l;
	public RequestWebListener getL() {
		return l;
	}
	public void setL(RequestWebListener l) {
		this.l = l;
	}

	public LoadingView(Context context) {
		super(context);
		init(context);
	}

	public LoadingView(Context context, AttributeSet attributeset) {
		super(context, attributeset);
		init(context);
	}

	private void init(Context context) {
		v = View.inflate(context, R.layout.layout_loading, this);
		
		loading_img = ((ImageView) v.findViewById(R.id.loading_img));//杯子图案， 加载数据
		animationDrawable = (AnimationDrawable) loading_img.getBackground();
		animationDrawable.start();
		
		error_layout = ((LinearLayout) v.findViewById(R.id.error_layout));
//		txt_error = ((TextView) v.findViewById(R.id.txt_error));
		empty_layout = (LinearLayout) v.findViewById(R.id.empty_layout);
		error_layout.setOnClickListener(this);
		empty_layout.setOnClickListener(this);
		
		showLoading();
	}

	private void showLoading() {
		loading_img.setVisibility(View.VISIBLE);
		loading_img.post(new Runnable1(this));
	}

	private void hiddenLoading() {
		loading_img.setVisibility(View.GONE);
		loading_img.post(new Runnable2(this));
	}

	public static final int interfaceerror = 1;
	public static final int network_error = 2;
	public static final int showloading = 3;
	public static final int success = 4;
	
	public void postHandle(int state) {
		switch (state) {
		case 1:
			hiddenLoading();
			error_layout.setVisibility(View.GONE);
			empty_layout.setVisibility(View.VISIBLE);
			break;
		case 2:
			hiddenLoading();
			error_layout.setVisibility(View.VISIBLE);
			empty_layout.setVisibility(View.GONE);
			break;
		case 3:
			showLoading();
			error_layout.setVisibility(View.GONE);
			empty_layout.setVisibility(View.GONE);
			break;
		case 4:
			hiddenLoading();
			error_layout.setVisibility(View.GONE);
			empty_layout.setVisibility(View.GONE);
			setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	class Runnable1 implements Runnable {
		Runnable1(LoadingView paramLoadingView) {
		}

		public void run() {
			try {
				if (animationDrawable.isRunning()) {
					animationDrawable.stop();
				}
				animationDrawable.start();
				return;
			} catch (Exception localException) {
			}
		}
	}

	class Runnable2 implements Runnable {
		Runnable2(LoadingView paramLoadingView) {
		}

		public void run() {
			try {
				animationDrawable.stop();
				return;
			} catch (Exception localException) {
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.error_layout:
		case R.id.empty_layout:
			if (l != null) {
				postHandle(showloading);
				l.requestWeb();
			}
			break;
		default:
			break;
		}
	}


//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//		return true;
//	}


//	/**
//	 * 拦截touch事件，不分发给下层的
//	 * @param ev
//	 * @return
//	 */
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		return true;
//	}


//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		return true;
//	}
}
