package com.daocheng.girlshop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

/**
 * 项目名称：girlshop
 * 类描述：自定义view
 * 创建人：Dove
 * 创建时间：2016/3/3 9:46
 * 修改人：Dove
 * 修改时间：2016/3/3 9:46
 * 修改备注：
 */
public class demoview extends View {

    private int width;//view的寬度
    private int height;//view的高度
    private int top;
    private int left;
    //监听双击行为可以用GestureDetector。

    public demoview(Context context) {
        super(context);

        width=getRight()-getLeft();
        height=getBottom()-getTop();

        Scroller scroller=new Scroller(context);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float  x=left+getTranslationX();
        float  y=top+getTranslationY();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        VelocityTracker velocityTracker= VelocityTracker.obtain();
        velocityTracker.addMovement(event);//加載速度

        velocityTracker.computeCurrentVelocity(1000);
        int xVeloctity=(int)velocityTracker.getXVelocity();
        int yVeloctity=(int)velocityTracker.getYVelocity();



        Log.v("ViewTag","水平速度"+xVeloctity+"/垂直速度"+yVeloctity);

        velocityTracker.clear();
        velocityTracker.recycle();

        return super.onTouchEvent(event);


    }
}
