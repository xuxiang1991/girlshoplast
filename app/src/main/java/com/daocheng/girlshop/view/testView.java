package com.daocheng.girlshop.view;

import android.content.Context;
import android.view.View;

/**
 * 项目名称：girlshop
 * 类描述：测试自定义view
 * 创建人：Dove
 * 创建时间：2016/3/29 17:06
 * 修改人：Dove
 * 修改时间：2016/3/29 17:06
 * 修改备注：
 */
public class testView extends View {


    public testView(Context context) {
        super(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
