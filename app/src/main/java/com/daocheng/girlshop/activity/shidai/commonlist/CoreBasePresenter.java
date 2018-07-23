package com.daocheng.girlshop.activity.shidai.commonlist;

/**
 * 类名称：基础Presenter
 * 类描述：
 * 创建人：
 * 修改人：
 */
public abstract class CoreBasePresenter<M, T> {
    public M mModel;
    public T mView;

    public void attachVM(T v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void detachVM() {
        mView = null;
        mModel = null;
    }

    public abstract void onStart();
}
