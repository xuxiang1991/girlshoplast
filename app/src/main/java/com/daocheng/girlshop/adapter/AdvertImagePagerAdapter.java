/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.daocheng.girlshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.daocheng.girlshop.R;
import com.daocheng.girlshop.entity.BannerInfo;
import com.daocheng.girlshop.net.Api;
import com.daocheng.girlshop.utils.Constant;

import java.util.List;


/**
 * AdvertImagePagerAdapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class AdvertImagePagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<BannerInfo.DataEntity> imageIdList;

    private int size;
    private boolean isInfiniteLoop;
    private boolean flag;


    public AdvertImagePagerAdapter(Context context, List<BannerInfo.DataEntity> imageIdList, boolean flag) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.flag = flag;
        this.size = imageIdList.size();
        isInfiniteLoop = false;


    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return position % size;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

//		holder.imageView.setImageResource(R.drawable.banner1);
//		holder.imageView.setImageUrl(imageIdList.get(getPosition(position)).getImgUrl(), ImageLoaderManager.getInstance().mDownloder);
//		Log.v("image", imageIdList.get(getPosition(position)).getImgUrl());
        Log.v("image", getPosition(position) + "");
        if (imageIdList.get(getPosition(position)).getShowImg() == null) {
            if (getPosition(position) % 2 == 0)
                holder.imageView.setImageResource(R.drawable.ban1);
            else
                holder.imageView.setImageResource(R.drawable.ban2);
        } else
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(imageIdList.get(getPosition(position)).getShowImg(), holder.imageView);
        if (flag == true) {
            holder.imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {


                        if (TextUtils.isEmpty(imageIdList.get(getPosition(position)).getUrl()))
                            return;
//						Intent oinfo=new Intent(context, MessageActivity.class);
//						oinfo.putExtra("type",imageIdList.get(getPosition(position)).getAction());
//						oinfo.putExtra("name","");
//						context.startActivity(oinfo);

                }
            });
        }

//		ImageLoaderManager.getImage(holder.imageView,imageIdList.get(getPosition(position)).getImgUrl());

        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    public void setData(List<BannerInfo.DataEntity> imageIdList) {
        this.imageIdList = imageIdList;
        this.size = imageIdList.size();
    }


    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public AdvertImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
