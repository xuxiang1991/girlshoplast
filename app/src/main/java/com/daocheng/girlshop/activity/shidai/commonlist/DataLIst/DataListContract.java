package com.daocheng.girlshop.activity.shidai.commonlist.DataLIst;

import android.content.Context;
import android.content.Intent;
import android.database.Observable;

import com.daocheng.girlshop.activity.shidai.commonlist.CoreBaseModel;
import com.daocheng.girlshop.activity.shidai.commonlist.CoreBasePresenter;
import com.daocheng.girlshop.activity.shidai.commonlist.CoreBaseView;
import com.daocheng.girlshop.entity.shdiai.dataListResult;

import java.util.List;

/**
 * 类名称：Datalist基础类衍生
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public interface DataListContract {


    //First接口
    abstract class DataListPresenter extends CoreBasePresenter<DataListModel, DataListView> {

        public abstract void setFlag(int type);

        public abstract void nextPage();

        public abstract void refreshPage();

        public abstract void getData();

        public abstract void startInterval();


    }

    interface DataListModel extends CoreBaseModel {

        void Init(Intent intent);

        void nextPage();

        void refreshPage();

        void setFlag(int flag);

        void getData(Context context,DataListView.callBack callback);
    }

    interface DataListView extends CoreBaseView {
        void showContent();

        interface callBack {
            void upDate(DataListModel model);

            void onError(String content);
        }

    }
}
