package com.daocheng.girlshop.fragment.shidai;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.mykeSource;
import com.daocheng.girlshop.fragment.BaseFragment;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/21.
 */
public class myKeFragment extends BaseFragment implements View.OnClickListener{

    private TextView tv_center;
    private TextView tv_level;
    private TextView tv_last_days;
    private TextView tv_time;
    private TextView tv_yiding;
    private TextView tv_keding;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<mykeSource.RecordBean> baseobjects;
    private baseObRecycleAdapter sRecyclerViewAdapter;

    private mykeSource advertorialList;

    private boolean isPrepared=false;



    @Override
    protected int getLayoutId() {
        return R.layout.frag_shidai_myke;
    }

    @Override
    protected void setupViews(View view) {

        tv_center=(TextView)view.findViewById(R.id.tv_center);
        tv_level=(TextView)view.findViewById(R.id.tv_level);
        tv_last_days=(TextView)view.findViewById(R.id.tv_last_days);
        tv_time=(TextView)view.findViewById(R.id.tv_time);
        tv_yiding=(TextView)view.findViewById(R.id.tv_yiding);
        tv_keding=(TextView)view.findViewById(R.id.tv_keding);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<mykeSource.RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        mRecyclerView.setAdapter(sRecyclerViewAdapter);
    }

    @Override
    protected void initialized() {

        tv_center.setText("我的课程");

        isPrepared=true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }

        setData();
    }

    @Override
    public void onClick(View v) {

    }


    private void setData()
    {
        ShidaiApi.getmyKeDetail(self, Config.getShidaiUserInfo().getUserid(), mykeSource.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode()))
                {
                    advertorialList=(mykeSource)rspData;
                    baseobjects=advertorialList.getRecord();
                    sRecyclerViewAdapter.notifyDataSetChanged();
                    Initdata(advertorialList);
                }
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    private void Initdata(mykeSource detail)
    {
        tv_level.setText("当前级别："+detail.getLevel());
        tv_last_days.setText(detail.getSpareDay()+"");
        tv_time.setText(detail.getStarttime()+" 至 "+detail.getEndtime());
        tv_yiding.setText("必修课已预订次数\n"+detail.getNumber56());
        tv_keding.setText("选修课已预订次数\n"+detail.getNumber87());
    }

    public class baseObRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class arViewHolder extends RecyclerView.ViewHolder {

            TextView tv_name;
            RadioButton rb_teacher;
            RadioButton rb_time;
            RadioButton rb_location;
            LinearLayout item_ke;

            public arViewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                item_ke = (LinearLayout) itemView.findViewById(R.id.item_ke);
                rb_teacher = (RadioButton) itemView.findViewById(R.id.rb_teacher);
                rb_time = (RadioButton) itemView.findViewById(R.id.rb_time);
                rb_location = (RadioButton) itemView.findViewById(R.id.rb_location);


            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(self).inflate(R.layout.item_dingke, parent, false);
            return new arViewHolder(inflatedView);

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof arViewHolder) {
                final mykeSource.RecordBean ob = getItem(position);
                ((arViewHolder)holder).tv_name.setText(ob.getTitle());
                ((arViewHolder)holder).rb_teacher.setText("教师："+ob.getTeacher());
                ((arViewHolder)holder).rb_location.setText("教室："+ob.getClassroom());
                ((arViewHolder)holder).rb_time.setText(ob.getTime() + " " + ob.getStart() + "~" + ob.getEnd());


            }

        }

        public mykeSource.RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }
    }
}
