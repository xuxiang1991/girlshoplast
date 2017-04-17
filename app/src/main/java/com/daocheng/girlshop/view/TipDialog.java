package com.daocheng.girlshop.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.daocheng.girlshop.R;


/**
 * Created by Administrator on 2015/10/23 0023.
 */
public class TipDialog extends Dialog {

    private Context context;
    private Button mOkBt = null;
    private Button mCancelBt = null;
    private ImageView mCloseIv = null;
 private OnBtClickListener mOnBtClickListener = null;

    public TipDialog(Context context) {
        super(context);
        this.context = context;
    }

    public TipDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);

        mOkBt = (Button) findViewById(R.id.ok_bt);
        mCancelBt = (Button) findViewById(R.id.cancel_bt);
        mCloseIv = (ImageView)findViewById(R.id.close_iv);
        mCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnBtClickListener!=null)
                    mOnBtClickListener.cancel();
                dismiss();
            }
        });
        mOkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnBtClickListener!=null)
                    mOnBtClickListener.ok();
                dismiss();
            }
        });
        mCancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnBtClickListener!=null)
                    mOnBtClickListener.cancel();
                dismiss();
            }
        });
    }

    public void setLisntener(OnBtClickListener listener) {
        this.mOnBtClickListener = listener;
    }

    public static abstract interface OnBtClickListener {
        public abstract void ok();
        public abstract void cancel();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void show() {
        if (context != null && ((Activity) context).isFinishing()) {
            return;
        }
        setCanceledOnTouchOutside(true);
        super.show();
    }
}
