package com.daocheng.girlshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 项目名称：girlshop
 * 类描述：自定义Textview
 * 创建人：jdd
 * 创建时间：2016/7/2 13:42
 * 修改人：jdd
 * 修改时间：2016/7/2 13:42
 * 修改备注：
 */

public class newTextView extends EditText {
    public newTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public newTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public newTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected boolean getDefaultEditable() {//禁止EditText被编辑
        return false;
    }
}