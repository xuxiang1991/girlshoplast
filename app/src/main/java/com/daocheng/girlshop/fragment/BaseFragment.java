package com.daocheng.girlshop.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daocheng.girlshop.Interface.RequestWebListener;
import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.utils.Constant;


/**
 * 项目名称：Cooke
 * 类描述：基础类
 * 创建人：Dove
 * 创建时间：2015/9/23 16:19
 * 修改人：Dove
 * 修改时间：2015/9/23 16:19
 * 修改备注：
 */
public abstract class BaseFragment extends Fragment {


    protected Activity self;//所在actvity的context
    protected BaseActivity mActivity;
    protected int width, height;
    protected Bundle args;




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        self = getActivity();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        args = getArguments();//传递过来的值
        mActivity = (BaseActivity) getActivity();

        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        setupViews(view);



        return view;


    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialized();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.gc();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    /**
     * 布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();


    /**
     * 初始化组件
     */
    protected abstract void setupViews(View view);

    /**
     * 初始化数据
     */
    protected abstract void initialized();

    /**
     * 长时间显示Toast提示(来自String)
     *
     * @param message
     */
    protected void showToast(String message) {
        Toast.makeText(self, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast提示(来自res)
     *
     * @param resId
     */
    protected void showToast(int resId) {
        Toast.makeText(self, getString(resId), Toast.LENGTH_LONG).show();
    }

    /**
     * 短暂显示Toast提示(来自res)
     *
     * @param resId
     */
    protected void showShortToast(int resId) {
        Toast.makeText(self, getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 短暂显示Toast提示(来自String)
     *
     * @param text
     */
    protected void showShortToast(String text) {
        Toast.makeText(self, text, Toast.LENGTH_SHORT).show();
    }


    protected boolean isVisible;

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad() ;

    protected void onInvisible() {
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

        self.overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    /**
     * 加载动画
     */
    RequestWebListener l = new RequestWebListener() {

        @Override
        public void requestWeb() {
            initialized();
        }
    };
}
