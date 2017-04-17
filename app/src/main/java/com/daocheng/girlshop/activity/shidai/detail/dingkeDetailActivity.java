package com.daocheng.girlshop.activity.shidai.detail;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;

import com.daocheng.girlshop.dialog.MessageDialog;

import com.daocheng.girlshop.entity.ServiceResult;

import com.daocheng.girlshop.entity.shdiai.dingkeDetail;

import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：考试订课详情页
 * 创建人：jdd
 * 创建时间：2016/6/21 18:27
 * 修改人：jdd
 * 修改时间：2016/6/21 18:27
 * 修改备注：
 */

public class dingkeDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView makeSureTv;
    private ImageView tv_left;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<dingkeDetail.RecordBean> baseobjects;
    private baseObRecycleAdapter sRecyclerViewAdapter;

    private dingkeDetail advertorialList;

    private TextView tv_center;

    private TextView tv_time_start, tv_time_end, tv_level, tv_beizhu, tv_keding, tv_yiding;
    private RadioButton rb_teacher, rb_location;


    private int id;
    private TextView tv_name_title;

    private static final String DINGKE = "订课";
    private static final String TUIKE = "退课";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shidai_ke_detail;
    }

    @Override
    protected void setupViews() {
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);
        tv_name_title = (TextView) findViewById(R.id.tv_name_title);
        makeSureTv = (TextView) findViewById(R.id.tv_yy_ok);
        makeSureTv.setText("订阅");
        makeSureTv.setOnClickListener(this);

        tv_time_start = (TextView) findViewById(R.id.tv_time_start);
        tv_time_end = (TextView) findViewById(R.id.tv_time_end);
        tv_level = (TextView) findViewById(R.id.tv_level);
        tv_beizhu = (TextView) findViewById(R.id.tv_beizhu);
        tv_keding = (TextView) findViewById(R.id.tv_keding);
        tv_yiding = (TextView) findViewById(R.id.tv_yiding);

        rb_teacher = (RadioButton) findViewById(R.id.rb_teacher);
        rb_location = (RadioButton) findViewById(R.id.rb_location);

        tv_center = (TextView) findViewById(R.id.tv_center);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<dingkeDetail.RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        mRecyclerView.setAdapter(sRecyclerViewAdapter);

        tv_left.setOnClickListener(this);
    }

    @Override
    protected void initialized() {

        id = getIntent().getIntExtra("id", 0);

        tv_center.setText("课程详情");
        setData();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_yy_ok:
                String info = "";
                if (makeSureTv.getText().toString().contains(DINGKE)) {
                    info = "是否订阅该课程";
                } else {
                    info = "是否退课";
                }
                MessageDialog mg = new MessageDialog(self, "提示", info, MessageDialog.MESSAGE, new MessageDialog.onRequest() {
                    @Override
                    public void back() {
                        joinInke();
                    }
                });
                mg.show();
                break;
        }
    }


    private void joinInke() {
        ShidaiApi.JoinInKe(self, Config.getShidaiUserInfo().getUserid(), id, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {

                    if (makeSureTv.getText().toString().contains("立即预定")) {
                        makeSureTv.setText("退课");
                    } else {
                        makeSureTv.setText("立即预定");
                    }
                    setData();

                }else
                {
                    showShortToast(rspData.getErrmsg());
                }

//                else if ("E20003".equals(rspData.getErrcode())) {
//                    showShortToast(rspData.getErrmsg());
//                } else if ("E20010".equals(rspData.getErrcode())) {
//                    showShortToast(rspData.getErrmsg());
//                } else if ("E20008".equals(rspData.getErrcode())) {
//                    showShortToast(rspData.getErrmsg());
//                }
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    private void setData() {

        ShidaiApi.getKeDetail(self, Config.getShidaiUserInfo().getUserid(), id, dingkeDetail.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    advertorialList = (dingkeDetail) rspData;
                    baseobjects = advertorialList.getRecord();
                    sRecyclerViewAdapter.notifyDataSetChanged();
                    Initdata(advertorialList);
                }

            }

            @Override
            public void failed(String msg) {

            }
        });
    }


    private void Initdata(dingkeDetail detail) {
        tv_name_title.setText(detail.getTitle() == null ? "" : detail.getTitle());
        tv_time_start.setText(Html.fromHtml("<font color='#333333'>课程开始： </font>" + detail.getStarttime()));
        tv_time_end.setText(Html.fromHtml("<font color='#333333'>课程结束： </font>" + detail.getEndtime()));
        tv_level.setText(Html.fromHtml("<font color='#333333'>当前级别： </font>" + detail.getLevel()));
        tv_beizhu.setText(Html.fromHtml("<font color='#333333'>备注： </font>" + detail.getNote()));
        tv_keding.setText("可订人数\n" + detail.getAmount());
        tv_yiding.setText("已订人数\n" + detail.getAmountd());

        rb_teacher.setText("教师：" + detail.getTeacher());
        rb_location.setText("教室：" + detail.getClassroom());
        makeSureTv.setText(detail.getAction());
    }


    public class baseObRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class arViewHolder extends RecyclerView.ViewHolder {
            TextView tv_user, tv_enname, tv_mobile;
            TextView tv_no;
            RelativeLayout rl_student;


            public arViewHolder(View itemView) {
                super(itemView);
                tv_no = (TextView) itemView.findViewById(R.id.tv_no);
                rl_student = (RelativeLayout) itemView.findViewById(R.id.rl_student);
                tv_user = (TextView) itemView.findViewById(R.id.tv_user);
                tv_enname = (TextView) itemView.findViewById(R.id.tv_enname);
                tv_mobile = (TextView) itemView.findViewById(R.id.tv_mobile);


            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(self).inflate(R.layout.item_ke_detail, parent, false);
            return new arViewHolder(inflatedView);

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof arViewHolder) {
                final dingkeDetail.RecordBean ob = baseobjects.get(position);
                ((arViewHolder) holder).tv_no.setText(ob.getUsername());
                ((arViewHolder) holder).tv_user.setText(ob.getChinesename());
                ((arViewHolder) holder).tv_enname.setText(ob.getEnglishname());
//                ((arViewHolder) holder).tv_mobile.setText(ob.getMobile());

            }

        }

        public dingkeDetail.RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }
    }
}
