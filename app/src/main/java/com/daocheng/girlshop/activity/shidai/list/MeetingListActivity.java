package com.daocheng.girlshop.activity.shidai.list;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.activity.shidai.detail.WebActivity;
import com.daocheng.girlshop.dialog.MessageDialog;
import com.daocheng.girlshop.dialog.SecretDialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.MeetingBean;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.view.ClipTextView;
import com.inpor.fastmeetingcloud.sdk.HstLoginManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 类名称：会议列表
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class MeetingListActivity extends BaseActivity implements View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<MeetingBean.RecordBean> baseobjects;


    private MeetingBean advertorialList;

    private TextView tv_center;

    private int pageNo = 1;

    private int lastVisibleItem;


    public static final int TYPE_FENLEIXUEXI = 5;

    private ImageView tv_left;
    private String paget_title = "";


    private baseHotRecycleAdapter shotRecyclerViewAdapter;
    private MeetingBean meetingBean;

    private HstLoginManager loginManager;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_shidai_home;
    }

    @Override
    protected void setupViews() {
        LayoutInflater inflater = LayoutInflater.from(self);
        paget_title = getIntent().getStringExtra("title");
        loginManager = HstLoginManager.getInstance();
        loginManager.hstRequestPermissions(this);
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);

        tv_center = (TextView) findViewById(R.id.tv_center);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);


        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
//        headview = (LinearLayout) inflater.inflate(R.layout.head_vedio, mRecyclerView, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<MeetingBean.RecordBean>();
        shotRecyclerViewAdapter = new baseHotRecycleAdapter();
//        mBookends.addHeader(headview);
        mRecyclerView.setAdapter(shotRecyclerViewAdapter);

        tv_left.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
//        tv_center.setText(getTitle(dataflag));
        tv_center.setText(paget_title);

        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));

//        //加载内容
//        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView,
//                                             int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE
//                        && lastVisibleItem == shotRecyclerViewAdapter.getItemCount() - 1
//                        && lastVisibleItem == (pageNo * Constant.found_pageNum - 1)) {
//                    pageNo = pageNo + 1;
//                    setData();
//                    mSwipeRefreshLayout.setRefreshing(true);
//
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
//            }
//        });


        mSwipeRefreshLayout.setColorSchemeResources(R.color.App_back_orange, R.color.green, R.color.blue, R.color.red);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mSwipeRefreshLayout.setRefreshing(true);
                setData();


            }
        });

        setData();


    }


    private void setData() {

        ShidaiApi.getMeetingList(self, Config.getShidaiUserInfo().getUserid(), MeetingBean.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {

                    meetingBean = (MeetingBean) rspData;
                    if (pageNo == 1) {
                        shotRecyclerViewAdapter = new baseHotRecycleAdapter();
                        mRecyclerView.setAdapter(shotRecyclerViewAdapter);
                        baseobjects = meetingBean.getRecord();
                    } else
                        baseobjects.addAll(meetingBean.getRecord());
                    Log.v("ere", "fdfdf");

                    shotRecyclerViewAdapter.notifyDataSetChanged();

                } else {
                    if (!TextUtils.isEmpty(rspData.getErrmsg())) {
                        MessageDialog mg = new MessageDialog(self, "提示", rspData.getErrmsg(), MessageDialog.MESSAGE, new MessageDialog.onRequest() {
                            @Override
                            public void back() {
                            }
                        });
                        mg.show();
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failed(String msg) {
                if (!TextUtils.isEmpty(msg)) {
                    MessageDialog mg = new MessageDialog(self, "提示", msg, MessageDialog.MESSAGE, new MessageDialog.onRequest() {
                        @Override
                        public void back() {
                        }
                    });
                    mg.show();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    public class baseHotRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class hotViewHolder extends RecyclerView.ViewHolder {

            ClipTextView tv_title;
            ImageView iv_image;
            ClipTextView tv_content;
            TextView tv_time;
            RelativeLayout rl_item;

            public hotViewHolder(View itemView) {
                super(itemView);
                rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
                tv_title = (ClipTextView) itemView.findViewById(R.id.tv_title);
                iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
                tv_content = (ClipTextView) itemView.findViewById(R.id.tv_content);
                tv_time = (TextView) itemView.findViewById(R.id.tv_time);
                tv_title.setTextIsSelectable(true);
                tv_content.setTextIsSelectable(true);


            }
        }


        @Override
        public int getItemViewType(int position) {
            // TODO Auto-generated method stub

            return TYPE_FENLEIXUEXI;

        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(self).inflate(R.layout.item_shidai_article_simple, parent, false);
            return new hotViewHolder(inflatedView);


        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            final MeetingBean.RecordBean ob = getItem(position);

            ImageLoader.getInstance().displayImage(ob.getLogo(), ((hotViewHolder) holder).iv_image);
            ((hotViewHolder) holder).tv_content.setText("");
            ((hotViewHolder) holder).tv_content.setTextIsSelectable(true);
            ((hotViewHolder) holder).tv_title.setText(ob.getTitle());
            ((hotViewHolder) holder).tv_time.setVisibility(View.GONE);
            ((hotViewHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(ob.getLive())) {
                        if (!TextUtils.isEmpty(ob.getPassword())) {
                            SecretDialog secretDialog = new SecretDialog(self, new SecretDialog.GetSecret() {
                                @Override
                                public void CallBack(String str) {
                                    if (!str.equals(ob.getPassword())) {
                                        showShortToast("密码错误");
                                        return;
                                    }
                                    gotoMeeting(str, ob.getNumber());
                                }
                            });
                            secretDialog.show();
                        } else {
                            gotoMeeting("", ob.getNumber());
                        }
                    } else {
                        Intent intent = new Intent(self, WebActivity.class);
                        intent.putExtra("url", ob.getLive());
                        intent.putExtra("title", ob.getTitle());
                        startActivity(intent);

//                        Uri uri = Uri.parse(ob.getLive());
//                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                        startActivity(intent);
                    }


                }
            });


        }

        public MeetingBean.RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }
    }



    //权限请求回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        loginManager.onHstRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    /**
     * 去往会议
     */
    private void gotoMeeting(String password, String roomId) {
//        Intent intent = new Intent(self, StartTheMiddleTierActivity.class);
//        intent.setAction(Constant.INTENT_APP_ACTION_ROOMID_PASSWORD);
//
//        //Tv
////                Intent intent = new Intent(RoomIdPasswordActivity.this, PremeetingActivity.class);
////                intent.setAction(StaticString.INTENT_APP_ACTION_ROOMID_PASSWORD);
//
//        //外部启动
////                Intent intent = new Intent();
////                //intent.setComponent(new ComponentName("com.inpor.fastmeetingcloud","com.inpor.fastmeetingcloud.ui.StartTheMiddleTierActivity"));
////                intent.setComponent(new ComponentName("com.inpor.cloudmeeting","com.inpor.fastmeetingcloud.ui.StartTheMiddleTierActivity"));
////                intent.setAction(StaticString.INTENT_APP_ACTION_ROOMID_PASSWORD);
//
//        Bundle bundle = new Bundle();
//        bundle.putString(ThirdLoginConstant.BUNDLE_NICKNAME, Config.getShidaiUserInfo().getNickname());
//        bundle.putString(ThirdLoginConstant.BUNDLE_SERVER_ROOMID, roomId);
//        bundle.putString(ThirdLoginConstant.BUNDLE_ROOM_PASSWORD, password);
//        bundle.putString(ThirdLoginConstant.BUNDLE_SERVER_PORT, meetingBean.getPort());
//        bundle.putString(ThirdLoginConstant.BUNDLE_SERVER_ADDRESS, meetingBean.getServer());
//        bundle.putString(ThirdLoginConstant.BUNDLE_UPLOAD_VIDEO_SEZE, "50");
//        bundle.putString(ThirdLoginConstant.BUNDLE_UP_FILE, "upFile");
//        bundle.putString(ThirdLoginConstant.BUNDLE_WEB_SERVICE_URL, "http://dbdc.gxzf.gov.cn/videoMeeting/service/meetingFile?wsdl");
//        bundle.putString(ThirdLoginConstant.BUNDLE_NAMESPACE, "http://impl.service.webservice.sinosoft.com.cn");
//        intent.putExtras(bundle);
//        startActivity(intent);
//

        try {
            loginManager.joinMeeting(this, meetingBean.getServer(), meetingBean.getPort(), Config.getShidaiUserInfo().getNickname(), roomId, password);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginManager.hstRelease();
    }


}