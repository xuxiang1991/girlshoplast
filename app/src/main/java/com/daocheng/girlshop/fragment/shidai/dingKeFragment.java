package com.daocheng.girlshop.fragment.shidai;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.shidai.detail.dingkeDetailActivity;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.dingkeList;
import com.daocheng.girlshop.fragment.BaseFragment;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：
 * 创建人：jdd
 * 创建时间：2016/6/21 14:47
 * 修改人：jdd
 * 修改时间：2016/6/21 14:47
 * 修改备注：
 */

public class dingKeFragment extends BaseFragment implements View.OnClickListener{


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<dingkeList.RecordBean> baseobjects;
    private baseObRecycleAdapter sRecyclerViewAdapter;

    private dingkeList advertorialList;

    private TextView tv_center;

    private int pageNo = 1;

    private int lastVisibleItem;
    private RadioGroup rg_type;

    private boolean isPrepared=false;

    private static int TYPE_BIXUAN=56;
    private static int TYPE_XUANXIU=87;
    private int dataflag=56;



    @Override
    protected int getLayoutId() {
        return R.layout.frg_dingke;
    }

    @Override
    protected void setupViews(View view) {
        tv_center = (TextView) view.findViewById(R.id.tv_center);
        rg_type=(RadioGroup) view.findViewById(R.id.rg_type);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<dingkeList.RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        mRecyclerView.setAdapter(sRecyclerViewAdapter);
    }

    @Override
    protected void initialized() {


        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_bixiu:
                        if (dataflag == TYPE_XUANXIU) {
                            pageNo = 1;
                            dataflag = TYPE_BIXUAN;
                            setData();

                        }
                        break;
                    case R.id.rb_xuanxiu:
                        if (dataflag == TYPE_BIXUAN) {
                            pageNo = 1;
                            dataflag = TYPE_XUANXIU;
                            setData();

                        }
                        break;
                }
            }
        });

        tv_center.setText("订课考试");


        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));

        //加载内容
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem == sRecyclerViewAdapter.getItemCount()-1
                        && lastVisibleItem ==(pageNo*Constant.found_pageNum-1)) {
                    pageNo = pageNo + 1;
                    setData();
                    mSwipeRefreshLayout.setRefreshing(true);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });


        mSwipeRefreshLayout.setColorSchemeResources(R.color.App_back_orange, R.color.green, R.color.blue, R.color.red);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mSwipeRefreshLayout.setRefreshing(true);
                setData();


            }
        });

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




    private void setData() {

        ShidaiApi.getKeweekcoursesList(self, Config.getShidaiUserInfo().getUserid(), dataflag, pageNo, Constant.found_pageNum, dingkeList.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {

                if ("0".equals(rspData.getErrcode()))
                {    dingkeList alist = (dingkeList) rspData;
                    if (pageNo == 1)
                        baseobjects = alist.getRecord();
                    else
                        baseobjects.addAll(alist.getRecord());
                    Log.v("ere", "fdfdf");

                    sRecyclerViewAdapter.notifyDataSetChanged();

                } else {
                    showShortToast(rspData.getMessage());
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failed(String msg) {
                Toast.makeText(self, msg, Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
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
                tv_name.setTextIsSelectable(true);

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
                final dingkeList.RecordBean ob = getItem(position);
                ((arViewHolder)holder).tv_name.setText(ob.getTitle());
                ((arViewHolder)holder).rb_teacher.setText("教师："+ob.getTeacher());
                ((arViewHolder)holder).rb_location.setText("教室："+ob.getClassroom());
                ((arViewHolder)holder).rb_time.setText(ob.getTime()+" "+ob.getStart()+"~"+ob.getEnd());
                ((arViewHolder)holder).item_ke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(self,dingkeDetailActivity.class);
                        intent.putExtra("id",ob.getId());
                        startActivity(intent);

                    }
                });

            }

        }

        public dingkeList.RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }
    }



}
