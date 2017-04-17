package com.daocheng.girlshop.fragment.shidai;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.activity.shidai.PlunActivity;
import com.daocheng.girlshop.activity.shidai.loginActivity;
import com.daocheng.girlshop.activity.shidai.pingjiaActivity;
import com.daocheng.girlshop.dialog.MessageDialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.MyClass;
import com.daocheng.girlshop.entity.shdiai.mykeSource;
import com.daocheng.girlshop.fragment.BaseFragment;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Utils;
import com.daocheng.girlshop.view.Bookends;
import com.daocheng.girlshop.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XX on 2016/9/1.
 */
public class myClassFragment extends BaseFragment implements View.OnClickListener {

    private ImageView tv_left;
    private TextView tv_center;
    private TextView tv_right;


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private MyClass myclass;

    private List<MyClass.RecordBean> baseobjects;

    private int lastVisibleItem;

    private int page = 1;

    private LinearLayoutManager layoutManager;

    private Bookends<baseObRecycleAdapter> mBookends;

    private LinearLayout headview;

    private boolean isPrepared;

    private int MUST_CLASS = 0;
    private int CHOSE_CLASS = 87;

    private int class_type = 0;

    private RoundImageView iv_head;
    private TextView tv_name, tv_number, tv_scope, tv_level, tv_left_days, tv_start_time, tv_end_time, tv_b_class, tv_x_class,tv_levelscope;
    private TextView tv_bottom_left, tv_bottom_right;
    private View line_bottom_left, line_bottom_right;
    private baseObRecycleAdapter sRecyclerViewAdapter;
    private TextView tv_exit;
    private boolean isShowPinLun = false;
    private TextView tv_total_class;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_shidai_home;
    }

    @Override
    protected void setupViews(View view) {
        LayoutInflater inflater = LayoutInflater.from(self);
        tv_right = (TextView) view.findViewById(R.id.makeSureTv);
        tv_right.setOnClickListener(this);
        tv_left = (ImageView) view.findViewById(R.id.tv_left);
        tv_center = (TextView) view.findViewById(R.id.tv_center);
        tv_right.setVisibility(View.GONE);


        headview = (LinearLayout) inflater.inflate(R.layout.head_mykecheng, mRecyclerView, false);


        tv_bottom_left = (TextView) headview.findViewById(R.id.tv_bottom_left);
        tv_bottom_right = (TextView) headview.findViewById(R.id.tv_bottom_right);
        tv_bottom_left.setOnClickListener(this);
        tv_bottom_right.setOnClickListener(this);

        line_bottom_left = headview.findViewById(R.id.line_bottom_left);
        line_bottom_right = headview.findViewById(R.id.line_bottom_right);


        line_bottom_left.setVisibility(View.VISIBLE);
        line_bottom_right.setVisibility(View.INVISIBLE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<MyClass.RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        mBookends = new Bookends<>(sRecyclerViewAdapter);
        mBookends.addHeader(headview);
        mRecyclerView.setAdapter(mBookends);

        iv_head = (RoundImageView) headview.findViewById(R.id.iv_head);
        tv_name = (TextView) headview.findViewById(R.id.tv_name);
        tv_number = (TextView) headview.findViewById(R.id.tv_number);
        tv_scope = (TextView) headview.findViewById(R.id.tv_scope);
        tv_exit = (TextView) headview.findViewById(R.id.tv_exit);
        tv_level = (TextView) headview.findViewById(R.id.tv_level);
        tv_left_days = (TextView) headview.findViewById(R.id.tv_left_days);
        tv_start_time = (TextView) headview.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) headview.findViewById(R.id.tv_end_time);
        tv_b_class = (TextView) headview.findViewById(R.id.tv_b_class);
        tv_x_class = (TextView) headview.findViewById(R.id.tv_x_class);
        tv_total_class=(TextView)headview.findViewById(R.id.tv_total_class);
        tv_levelscope=(TextView)headview.findViewById(R.id.tv_levelscope);


        tv_exit.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        tv_center.setText("我的课程");


        if (Config.getShidaiUserInfo() == null) {
            startActivity(new Intent(self, loginActivity.class));
            self.finish();
            return;
        }
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));


        mSwipeRefreshLayout.setColorSchemeResources(R.color.App_back_orange, R.color.green, R.color.blue, R.color.red);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                setDate();
            }
        });

        isPrepared = true;

        lazyLoad();

        mSwipeRefreshLayout.setEnabled(false);

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

        setDate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bottom_left:
                if (class_type == CHOSE_CLASS) {
                    class_type = MUST_CLASS;
                    setDate();
                    line_bottom_left.setVisibility(View.VISIBLE);
                    line_bottom_right.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_bottom_right:
                if (class_type == MUST_CLASS) {
                    class_type = CHOSE_CLASS;
                    setDate();
                    line_bottom_left.setVisibility(View.INVISIBLE);
                    line_bottom_right.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_exit:
                MessageDialog md = new MessageDialog(self, "提示", "是否退出当前登录", MessageDialog.HOME, new MessageDialog.onRequest() {
                    @Override
                    public void back() {
                        Config.setShidaiUserInfo(null);
                        startActivity(new Intent(self, loginActivity.class));
                        self.finish();
                    }
                });
                md.show();
                break;


        }

    }


    private void setDate() {
        ShidaiApi.getmyKeDetail(self, Config.getShidaiUserInfo().getUserid(), class_type, MyClass.class, new NetUtils.NetCallBack<ServiceResult>() {

            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    myclass = (MyClass) rspData;
                    Initdata(myclass);
                    baseobjects = myclass.getRecord();
                    mBookends.notifyDataSetChanged();

                }
            }

            @Override
            public void failed(String msg) {
                Toast.makeText(self, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void Initdata(MyClass myclass) {
        ImageLoader.getInstance().displayImage(myclass.getHead(), iv_head);
        tv_name.setText(myclass.getName());
        tv_number.setText("学号：" + myclass.getNumber());
        tv_scope.setText("积分：" + myclass.getPoint());
        tv_level.setText(Html.fromHtml("当前级别：<font color='#30bb73'>" + myclass.getLevel() + "</font>"));
        tv_left_days.setText(Html.fromHtml("学期剩余天数：<font color='#30bb73'>" + myclass.getSpareDay() + "</font>"));
        tv_start_time.setText("开始时间：" + myclass.getStarttime());
        tv_end_time.setText("结束时间：" + myclass.getEndtime());
        tv_b_class.setText("必修课已预订次数:" + myclass.getNumber56());
        tv_x_class.setText("选修课已预订次数:" + myclass.getNumber87());
        tv_total_class.setText("必修课可预订总次数:"+myclass.getTotal56());
        tv_levelscope.setText("报名级别："+myclass.getLevelscope());

//        try
//        {
//            long ct = System.currentTimeMillis();
//            long et=0;
//            if (!TextUtils.isEmpty(myclass.getEndtime()))
//            {
//                et= Utils.stringToLong(myclass.getEndtime(),"yyyy-MM-dd HH:mm:ss");
//            }
//            if (et<ct)
//            {
//                isShowPinLun=true;
//            }
//        }catch (Exception e)
//        {
//
//        }

    }


    public class baseObRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class arViewHolder extends RecyclerView.ViewHolder {

            TextView tv_name;
            RadioButton rb_teacher;
            RadioButton rb_time;
            RadioButton rb_location;
            LinearLayout item_ke;
            TextView tv_plun;

            public arViewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                item_ke = (LinearLayout) itemView.findViewById(R.id.item_ke);
                rb_teacher = (RadioButton) itemView.findViewById(R.id.rb_teacher);
                rb_time = (RadioButton) itemView.findViewById(R.id.rb_time);
                rb_location = (RadioButton) itemView.findViewById(R.id.rb_location);
                tv_plun = (TextView) itemView.findViewById(R.id.tv_plun);
                tv_plun.setVisibility(View.VISIBLE);

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
                final MyClass.RecordBean ob = getItem(position);
                ((arViewHolder) holder).tv_name.setText(ob.getTitle());
                ((arViewHolder) holder).rb_teacher.setText("教师：" + ob.getTeacher());
                ((arViewHolder) holder).rb_location.setText("教室：" + ob.getClassroom());
                ((arViewHolder) holder).rb_time.setText(ob.getTime() + " " + ob.getStart() + "~" + ob.getEnd());

                if (class_type == MUST_CLASS) {
                    ((arViewHolder) holder).tv_plun.setVisibility(View.VISIBLE);
                } else {
                    ((arViewHolder) holder).tv_plun.setVisibility(View.GONE);
                }
                ((arViewHolder) holder).tv_plun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (System.currentTimeMillis() > ob.getEndtime()) {
                            Intent i = new Intent(self, pingjiaActivity.class);
                            i.putExtra("id", ob.getId());
                            startActivity(i);
                        } else {
                            showShortToast("请课程结束后再评价");
                        }

                    }
                });
            }

        }

        public MyClass.RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }
    }


}
