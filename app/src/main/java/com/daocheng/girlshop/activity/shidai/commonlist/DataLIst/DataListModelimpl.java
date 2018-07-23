package com.daocheng.girlshop.activity.shidai.commonlist.DataLIst;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.dataListResult;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;

import java.io.IOException;
import java.util.List;

/**
 * 类名称：
 * 类描述：
 * 创建人：
 * 修改人：
 */
public class DataListModelimpl implements DataListContract.DataListModel {

    public List<dataListResult.RecordBean> baseobjects;
    public dataListResult advertorialList;


    public int pageNo = 1;

    public int lastVisibleItem;

    public int dataflag = TYPE_HOT;

    public static final int TYPE_HOT = 3;
    public static final int TYPE_MRIRIYIJU = 4;
    public static final int TYPE_FENLEIXUEXI = 5;
    public static final int TYPE_TZWJ = 6;
    public static final int TYPE_ECA = 9;
    public static final int TYPE_CHANGGE = 8;
    public static final int TYPE_SHIPINGLIANXI = 7;
    public static final int TYPE_XINSHENGBIXIU = 10;

    public String paget_title = "";


    @Override
    public void Init(Intent intent) {
        dataflag = intent.getIntExtra("type", TYPE_HOT);
        paget_title = intent.getStringExtra("title");
    }

    @Override
    public void nextPage() {
        pageNo = pageNo + 1;

    }

    @Override
    public void refreshPage() {
        pageNo = 1;

    }

    @Override
    public void setFlag(int flag) {
        dataflag = flag;
    }

    @Override
    public void getData(Context context,final DataListContract.DataListView.callBack callback) {
        ShidaiApi.getDataList(context, dataflag, Config.getShidaiUserInfo().getUserid(), pageNo, Constant.pageNum, dataListResult.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    advertorialList = (dataListResult) rspData;
                    if (pageNo == 1)
                        baseobjects = advertorialList.getRecord();
                    else
                        baseobjects.addAll(advertorialList.getRecord());
                    Log.v("ere", "fdfdf");

                    callback.upDate(DataListModelimpl.this);
                } else {
                    callback.onError(rspData.getErrmsg());
                }

            }

            @Override
            public void failed(String msg) {
                callback.onError(msg);
            }
        });
    }


}
