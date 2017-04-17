package com.daocheng.girlshop.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewExtend extends ListView{

    private Context mContext = null;

    public ListViewExtend(Context pContext) {
        super(pContext);
        init(pContext);
    }

    public ListViewExtend(Context pContext, AttributeSet pAttrs) {
        super(pContext, pAttrs);
        init(pContext);
    }

    public ListViewExtend(Context pContext, AttributeSet pAttrs, int pDefStyle) {
        super(pContext, pAttrs, pDefStyle);
        init(pContext);
    }

    private void init(Context pContext) {
        this.mContext = pContext;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
