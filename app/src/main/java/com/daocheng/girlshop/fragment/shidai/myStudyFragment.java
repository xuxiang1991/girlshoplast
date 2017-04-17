package com.daocheng.girlshop.fragment.shidai;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.shidai.RdkcActivity;
import com.daocheng.girlshop.activity.shidai.TjzyActivity;
import com.daocheng.girlshop.activity.shidai.YyksActivity;
import com.daocheng.girlshop.activity.shidai.YykwActivity;
import com.daocheng.girlshop.adapter.AdvertImagePagerAdapter;
import com.daocheng.girlshop.entity.BannerInfo;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.StudyData;
import com.daocheng.girlshop.fragment.BaseFragment;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.view.AutoScrollViewPager;
import com.daocheng.girlshop.view.CirclePageIndicatorB;
import com.daocheng.girlshop.view.PageIndicator;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：我的学习
 * 创建人：xuxiagn
 * 创建时间：2016/8/22 11:06
 * 修改人：xuxiang
 * 修改时间：2016/8/22 11:06
 * 修改备注：
 */

public class myStudyFragment extends BaseFragment implements View.OnClickListener {

    private ImageView tv_left;
    private TextView tv_center;

    //header
    private AutoScrollViewPager viewPager;
    private PageIndicator mIndicator;
    private List<BannerInfo.DataEntity> imageIdList = new ArrayList<BannerInfo.DataEntity>();
    private AdvertImagePagerAdapter bannerAdapter;

    private ImageView iv_yykc, iv_tjzy, iv_ydkc, iv_yyks;

    private boolean isprepare = false;
    private StudyData studydata;
    private boolean flag = false;

    private boolean isReady = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mystudy;
    }

    @Override
    protected void setupViews(View view) {

        tv_left = (ImageView) view.findViewById(R.id.tv_left);
        tv_center = (TextView) view.findViewById(R.id.tv_center);

        viewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager_advert);
        viewPager.setInterval(3000);//切换速度
        viewPager.setAutoScrollDurationFactor(2.5);//动画速度
        mIndicator = (CirclePageIndicatorB) view.findViewById(R.id.indicator);

        iv_yykc = (ImageView) view.findViewById(R.id.iv_yykc);
        iv_tjzy = (ImageView) view.findViewById(R.id.iv_tjzy);
        iv_ydkc = (ImageView) view.findViewById(R.id.iv_ydkc);
        iv_yyks = (ImageView) view.findViewById(R.id.iv_yyks);
        iv_yykc.setOnClickListener(this);
        iv_tjzy.setOnClickListener(this);
        iv_ydkc.setOnClickListener(this);
        iv_yyks.setOnClickListener(this);

    }

    @Override
    protected void initialized() {
        tv_left.setVisibility(View.VISIBLE);
        tv_center.setText("我的学习");
        tv_left.setImageResource(R.drawable.home_bg_logo);
        isprepare = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isprepare || !isVisible) {
            return;
        }

        setDate();
    }


    private void setDate() {
        ShidaiApi.getmyStudy(self, StudyData.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    studydata = (StudyData) rspData;

                    imageIdList.clear();
                    if (!TextUtils.isEmpty(studydata.getPic1())) {
                        BannerInfo.DataEntity de = new BannerInfo.DataEntity();
                        de.setShowImg(studydata.getPic1());
                        imageIdList.add(de);
                    }
                    if (!TextUtils.isEmpty(studydata.getPic2())) {
                        BannerInfo.DataEntity de = new BannerInfo.DataEntity();
                        de.setShowImg(studydata.getPic2());
                        imageIdList.add(de);
                    }
                    if (!TextUtils.isEmpty(studydata.getPic3())) {
                        BannerInfo.DataEntity de = new BannerInfo.DataEntity();
                        de.setShowImg(studydata.getPic3());
                        imageIdList.add(de);
                    }

                    setContent();
                    initBanner();
                    isReady = true;
                }
                isReady = true;
            }

            @Override
            public void failed(String msg) {
                showShortToast(msg);
                isReady = true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!isReady)
            return;
        switch (v.getId()) {
            case R.id.iv_yykc:
                Intent intent = new Intent(self, YykwActivity.class);
                intent.putExtra("pic", studydata.getSubpic4());
                intent.putExtra("content", studydata.getContent4());
                startActivity(intent);
                break;
            case R.id.iv_tjzy:
                startActivity(new Intent(self, TjzyActivity.class));
                break;
            case R.id.iv_ydkc:
                startActivity(new Intent(self, RdkcActivity.class));
                break;
            case R.id.iv_yyks:
                Intent i = new Intent(self, YyksActivity.class);
                i.putExtra("time",studydata.getDuration());
                startActivity(i);
                break;
        }
    }

    private void initBanner() {


        bannerAdapter = new AdvertImagePagerAdapter(self, imageIdList, flag);
        viewPager.setAdapter(bannerAdapter);
        mIndicator.setViewPager(viewPager);
        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(0);


    }

    private void setContent() {
        if (!TextUtils.isEmpty(studydata.getPic4())) {
            ImageLoader.getInstance().displayImage(studydata.getPic4(), iv_yykc);
        }
        if (!TextUtils.isEmpty(studydata.getPic5())) {
            ImageLoader.getInstance().displayImage(studydata.getPic5(), iv_tjzy);
        }
        if (!TextUtils.isEmpty(studydata.getPic6())) {
            ImageLoader.getInstance().displayImage(studydata.getPic6(), iv_ydkc);
        }
        if (!TextUtils.isEmpty(studydata.getPic7())) {
            ImageLoader.getInstance().displayImage(studydata.getPic7(), iv_yyks);
        }
    }
}
