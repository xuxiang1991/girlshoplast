package com.daocheng.girlshop.fragment.shidai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.shidai.JincaiActivity;
import com.daocheng.girlshop.activity.shidai.list.DataListActivity;
import com.daocheng.girlshop.activity.shidai.loginActivity;
import com.daocheng.girlshop.adapter.AdvertImagePagerAdapter;
import com.daocheng.girlshop.dialog.MessageDialog;
import com.daocheng.girlshop.entity.BannerInfo;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.homedata;
import com.daocheng.girlshop.fragment.BaseFragment;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Utils;
import com.daocheng.girlshop.video.View.JCVideoPlayerStandardShowShareButtonAfterFullscreen;
import com.daocheng.girlshop.view.AutoScrollViewPager;
import com.daocheng.girlshop.view.Bookends;
import com.daocheng.girlshop.view.CirclePageIndicatorB;
import com.daocheng.girlshop.view.PageIndicator;
import com.daocheng.girlshop.voice.speech.EvaluatorManager;
import com.daocheng.girlshop.voice.speech.SpeechManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;

/**
 * 项目名称：girlshop
 * 类描述：首页fragment
 * 创建人：jdd
 * 创建时间：2016/6/23 9:36
 * 修改人：jdd
 * 修改时间：2016/6/23 9:36
 * 修改备注：
 */

public class shidaiHomeFragment extends BaseFragment implements View.OnClickListener {


    private ImageView tv_left;
    private TextView tv_center;
    private ImageView tv_right;


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private homedata datainfo;

    private List<RecordBean> baseobjectsPK;
    private List<RecordBean> baseobjectsstamina;

    private List<RecordBean> baseobjects;

    private baseObRecycleAdapter sRecyclerViewAdapter;


    //header
    private AutoScrollViewPager viewPager;
    private PageIndicator mIndicator;
    private List<BannerInfo.DataEntity> imageIdList = new ArrayList<BannerInfo.DataEntity>();
    private AdvertImagePagerAdapter bannerAdapter;

    private boolean flag = false;


    private int lastVisibleItem;

    private int page = 1;

    private LinearLayoutManager layoutManager;

    private Bookends<baseObRecycleAdapter> mBookends;

    private LinearLayout headview;

    private boolean isPrepared;


    public int dataflag = 1000;//初始化耐力排行榜

    private int TYPE_NAILI = 1000;
    private int TYPE_PK = 1001;

    private RadioButton rb_rmtj, rb_mryj, rb_flxx, rb_tzwj, rb_spxx, rb_xcjc, rb_eca, rb_xsbx;

    private TextView tv_bottom_left, tv_bottom_right;

    private View line_bottom_left, line_bottom_right;

    private String mp4;
    private ImageView iv_new;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_shidai_home;
    }

    @Override
    protected void setupViews(View view) {
        LayoutInflater inflater = LayoutInflater.from(self);
        tv_right = (ImageView) view.findViewById(R.id.tv_right);
        tv_right.setOnClickListener(this);
        tv_right.setImageResource(R.drawable.home_icon_xiazai);
        tv_right.setVisibility(View.VISIBLE);
        tv_left = (ImageView) view.findViewById(R.id.tv_left);
        tv_center = (TextView) view.findViewById(R.id.tv_center);

        headview = (LinearLayout) inflater.inflate(R.layout.head_shidai_home_frg, mRecyclerView, false);
        viewPager = (AutoScrollViewPager) headview.findViewById(R.id.view_pager_advert);
        viewPager.setInterval(3000);//切换速度
        viewPager.setAutoScrollDurationFactor(2.5);//动画速度
        mIndicator = (CirclePageIndicatorB) headview.findViewById(R.id.indicator);


        iv_new = (ImageView) headview.findViewById(R.id.iv_new);
        rb_rmtj = (RadioButton) headview.findViewById(R.id.rb_rmtj);
        rb_mryj = (RadioButton) headview.findViewById(R.id.rb_mryj);
        rb_flxx = (RadioButton) headview.findViewById(R.id.rb_flxx);
        rb_tzwj = (RadioButton) headview.findViewById(R.id.rb_tzwj);
        rb_spxx = (RadioButton) headview.findViewById(R.id.rb_spxx);
        rb_xcjc = (RadioButton) headview.findViewById(R.id.rb_xcjc);
        rb_eca = (RadioButton) headview.findViewById(R.id.rb_eca);
        rb_xsbx = (RadioButton) headview.findViewById(R.id.rb_xsbx);
        rb_rmtj.setOnClickListener(this);
        rb_mryj.setOnClickListener(this);
        rb_flxx.setOnClickListener(this);
        rb_tzwj.setOnClickListener(this);
        rb_xcjc.setOnClickListener(this);
        rb_eca.setOnClickListener(this);
        rb_xsbx.setOnClickListener(this);
        rb_spxx.setOnClickListener(this);


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
        baseobjects = new ArrayList<RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        mBookends = new Bookends<>(sRecyclerViewAdapter);
        mBookends.addHeader(headview);
        mRecyclerView.setAdapter(mBookends);
    }


    @Override
    protected void initialized() {



        tv_center.setTextColor(getResources().getColor(R.color.home_fragment_title));
//        if (Config.getShidaiUserInfo()==null)
//        {
//            startActivity(new Intent(self, loginActivity.class));
//            self.finish();
//            return;
//        }
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
        mSwipeRefreshLayout.setRefreshing(true);
        lazyLoad();


    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (Config.getshidaishownew()) {
            iv_new.setVisibility(View.GONE);
        } else {
            iv_new.setVisibility(View.VISIBLE);
        }
        setDate();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bottom_left:
                if (dataflag == TYPE_PK) {
                    dataflag = TYPE_NAILI;
                    setContent();
                    line_bottom_left.setVisibility(View.VISIBLE);
                    line_bottom_right.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_bottom_right:
                if (dataflag == TYPE_NAILI) {
                    dataflag = TYPE_PK;
                    setContent();
                    line_bottom_left.setVisibility(View.INVISIBLE);
                    line_bottom_right.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rb_rmtj:
                iv_new.setVisibility(View.GONE);
                Config.setshidaishownew(true);
                goToNext(DataListActivity.TYPE_HOT);
                break;
            case R.id.rb_mryj:
                goToNext(DataListActivity.TYPE_MRIRIYIJU);
                break;
            case R.id.rb_flxx:
                goToNext(DataListActivity.TYPE_FENLEIXUEXI);
                break;
            case R.id.rb_eca:
                goToNext(DataListActivity.TYPE_ECA);
                break;
            case R.id.rb_xsbx:
                goToNext(DataListActivity.TYPE_XINSHENGBIXIU);
                break;
            case R.id.rb_spxx:
                goToNext(DataListActivity.TYPE_SHIPINGLIANXI);
                break;
            case R.id.rb_xcjc:
                goToNext(DataListActivity.TYPE_CHANGGE);
                break;
            case R.id.rb_tzwj:
                goToNext(DataListActivity.TYPE_TZWJ);
                break;
            case R.id.tv_right:
                if (!TextUtils.isEmpty(mp4)) {
                    Intent intent = new Intent(self, JincaiActivity.class);
                    intent.putExtra("video", mp4);
                    startActivity(intent);
                } else {
                    MessageDialog md = new MessageDialog(self, "提示", "本期没有精彩视频", MessageDialog.MESSAGE);
                    md.show();
                }
//                JCFullScreenActivity.startActivity(self,
//                        mp4,
//                        JCVideoPlayerStandardShowShareButtonAfterFullscreen.class, "精彩视频");


                break;
        }
    }

    private void setContent() {

        if (dataflag == TYPE_NAILI) {
            sRecyclerViewAdapter.setItemCount(baseobjectsstamina);
        } else {
            sRecyclerViewAdapter.setItemCount(baseobjectsPK);
        }

        mSwipeRefreshLayout.setRefreshing(false);


    }

    private void goToNext(int type) {
        Intent intent = new Intent(self, DataListActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);

    }


    private void initBanner() {


        bannerAdapter = new AdvertImagePagerAdapter(self, imageIdList, flag);
        viewPager.setAdapter(bannerAdapter);
        mIndicator.setViewPager(viewPager);
        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(0);


    }

    private void setDate() {
        ShidaiApi.getHomedata(self, Config.getShidaiUserInfo().getUserid(), homedata.class, new NetUtils.NetCallBack<ServiceResult>() {

            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    homedata hdata = (homedata) rspData;

                    baseobjectsPK = toBaseList(hdata.getRecord1());
                    baseobjectsstamina = toBaseList2(hdata.getRecord2());
                    imageIdList.clear();
                    if (!TextUtils.isEmpty(hdata.getPic1())) {
                        BannerInfo.DataEntity de = new BannerInfo.DataEntity();
                        de.setShowImg(hdata.getPic1());
                        imageIdList.add(de);
                    }
                    if (!TextUtils.isEmpty(hdata.getPic2())) {
                        BannerInfo.DataEntity de = new BannerInfo.DataEntity();
                        de.setShowImg(hdata.getPic2());
                        imageIdList.add(de);
                    }
                    if (!TextUtils.isEmpty(hdata.getPic3())) {
                        BannerInfo.DataEntity de = new BannerInfo.DataEntity();
                        de.setShowImg(hdata.getPic3());
                        imageIdList.add(de);
                    }

                    if (!TextUtils.isEmpty(hdata.getTitle9())) {
                        rb_eca.setText(hdata.getTitle9());
                    }
                    if (!TextUtils.isEmpty(hdata.getTitle10())) {
                        rb_xsbx.setText(hdata.getTitle10());
                    }

                    if (!TextUtils.isEmpty(hdata.getIcon9())) {
                        loadImage(hdata.getIcon9(), rb_eca);

                    }
                    if (!TextUtils.isEmpty(hdata.getIcon10())) {
                        loadImage(hdata.getIcon9(), rb_xsbx);

                    }


                    initBanner();
                    setContent();

                    tv_left.setImageResource(R.drawable.home_bg_logo);
                    tv_left.setVisibility(View.VISIBLE);


                    tv_center.setText("时代国际英语");

                    mp4 = hdata.getMp4();
                }
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    private void loadImage(String url, final RadioButton radioButton) {
        ImageLoader.getInstance().loadImage(url, new ImageSize(147, 147), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                Drawable dr = new BitmapDrawable(bitmap);
                dr.setBounds(0, 0, 108, 108);
                radioButton.setCompoundDrawables(null, dr, null, null);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }


    public class baseObRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class obViewHolder extends RecyclerView.ViewHolder {
            ImageView iv_cup;
            TextView tv_name;
            TextView tv_pk;


            public obViewHolder(View itemView) {
                super(itemView);
                iv_cup = (ImageView) itemView.findViewById(R.id.iv_cup);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_pk = (TextView) itemView.findViewById(R.id.tv_pk);


            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View inflatedView = LayoutInflater.from(self).inflate(R.layout.item_shidai_home, parent, false);
            return new obViewHolder(inflatedView);


        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof obViewHolder) {
                final RecordBean ob = baseobjects.get(position);
                if (position == 0) {
                    ((obViewHolder) holder).iv_cup.setImageResource(R.drawable.home_icon_1);
                    ((obViewHolder) holder).tv_pk.setTextSize(22);
                    ((obViewHolder) holder).tv_pk.setTextColor(getResources().getColor(R.color.red));
                } else if (position == 1) {
                    ((obViewHolder) holder).iv_cup.setImageResource(R.drawable.home_icon_2);
                    ((obViewHolder) holder).tv_pk.setTextSize(20);
                    ((obViewHolder) holder).tv_pk.setTextColor(getResources().getColor(R.color.orange));
                } else if (position == 2) {
                    ((obViewHolder) holder).iv_cup.setImageResource(R.drawable.home_icon_3);
                    ((obViewHolder) holder).tv_pk.setTextSize(18);
                    ((obViewHolder) holder).tv_pk.setTextColor(getResources().getColor(R.color.main_circle_yellow_color));
                } else if (position == 3) {
                    ((obViewHolder) holder).iv_cup.setImageResource(R.drawable.home_icon_4);
                    ((obViewHolder) holder).tv_pk.setTextSize(15);
                    ((obViewHolder) holder).tv_pk.setTextColor(getResources().getColor(R.color.text_light_grey));
                } else {
                    ((obViewHolder) holder).iv_cup.setImageResource(R.drawable.home_icon_5);
                    ((obViewHolder) holder).tv_pk.setTextSize(15);
                    ((obViewHolder) holder).tv_pk.setTextColor(getResources().getColor(R.color.text_light_grey));
                }

                ((obViewHolder) holder).tv_name.setText(ob.getNickname());
                if (dataflag == TYPE_NAILI)
                    ((obViewHolder) holder).tv_pk.setText(ob.getStamina1());
                else
                    ((obViewHolder) holder).tv_pk.setText(ob.getStamina1());
            }


        }

        public RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }


        public void setItemCount(List<RecordBean> generObjects) {

            baseobjects = generObjects;

            mBookends.notifyDataSetChanged();
        }
    }


    private List<RecordBean> toBaseList(List<homedata.Record1Bean> hr1) {
        List<RecordBean> dlist = new ArrayList<RecordBean>();
        for (homedata.Record1Bean hr : hr1) {
            RecordBean rb = new RecordBean();
            rb.setHead(hr.getHead());
            rb.setNickname(hr.getNickname());
            rb.setStamina(hr.getPk());
            rb.setStamina1(hr.getPk1());
            dlist.add(rb);
        }
        return dlist;
    }

    private List<RecordBean> toBaseList2(List<homedata.Record2Bean> hr2) {
        List<RecordBean> dlist = new ArrayList<RecordBean>();
        for (homedata.Record2Bean hr : hr2) {
            RecordBean rb = new RecordBean();
            rb.setHead(hr.getHead());
            rb.setNickname(hr.getNickname());
            rb.setStamina(hr.getStamina());
            rb.setStamina1(hr.getStamina1());
            dlist.add(rb);
        }
        return dlist;
    }


    public class RecordBean {
        private String nickname;
        private Object head;
        private int stamina;
        private String stamina1;

        public String getStamina1() {
            return stamina1;
        }

        public void setStamina1(String stamina1) {
            this.stamina1 = stamina1;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getHead() {
            return head;
        }

        public void setHead(Object head) {
            this.head = head;
        }

        public int getStamina() {
            return stamina;
        }

        public void setStamina(int stamina) {
            this.stamina = stamina;
        }
    }
}
