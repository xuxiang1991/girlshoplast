package com.daocheng.girlshop.activity.shidai.commonlist.DataLIst;

import android.util.Log;
import android.widget.Toast;

import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;

import java.io.IOException;

/**
 * 类名称：
 * 类描述：
 * 创建人：
 * 修改人：
 */
public class DataListPresenterimpl extends DataListContract.DataListPresenter {


    @Override
    public void onStart() {
        mModel.Init(((DataLIstActivity) mView.getContext()).getIntent());
    }


    @Override
    public void setFlag(int type) {
        mModel.setFlag(type);
    }

    @Override
    public void nextPage() {
        mModel.nextPage();
    }

    @Override
    public void refreshPage() {
        mModel.refreshPage();
    }


    @Override
    public void getData() {
        mModel.getData(mView.getContext(),new DataListContract.DataListView.callBack() {
            @Override
            public void upDate(DataListContract.DataListModel model) {

                mView.showContent();
            }

            @Override
            public void onError(String content) {
                mView.showError(content);
            }
        });

    }

    @Override
    public void startInterval() {

    }
}
