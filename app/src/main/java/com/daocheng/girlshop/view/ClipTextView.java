package com.daocheng.girlshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatEditText;

/**
 * 类名称：可复制的不可修改的Editview
 * 类描述：
 * 创建人：
 * 修改人：
 */
public class ClipTextView extends AppCompatEditText {
    public ClipTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ClipTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ClipTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected boolean getDefaultEditable() {//禁止EditText被编辑
        return false;
    }
}
