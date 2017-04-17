package com.daocheng.girlshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.entity.CookerListinfo;
import com.daocheng.girlshop.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


import java.util.ArrayList;
import java.util.List;

public class cookersRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int mBackground;
    private final TypedValue mTypedValue = new TypedValue();
    private Context mContext;
    private List<CookerListinfo.CookerItem> CookerList;
    private SpannableString msp = null;

    private static final int TYPE_ITEM = 0;
//    private static final int TYPE_FOOTER = 1;

    public static int TYPE_FAVCOOK = 0;
    public static int TYPE_NORCOOKE = 1;
    private int type;


    public cookersRecyclerViewAdapter(Context context, List<CookerListinfo.CookerItem> CookerList, int type) {
        mContext = context;
        this.CookerList = new ArrayList<>(CookerList);
        this.type = type;
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;

    }


//    public class FooterViewHolder extends RecyclerView.ViewHolder {
//
//        public FooterViewHolder(View view) {
//            super(view);
//        }
//
//    }


    public class cookersViewHolder extends RecyclerView.ViewHolder {
        ImageView rl_img_1;
        TextView tv_title;

        TextView tv_content;


        public cookersViewHolder(View itemView) {
            super(itemView);
            rl_img_1 = (ImageView) itemView.findViewById(R.id.rl_img_1);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);


        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == TYPE_ITEM) {
            View inflatedView = LayoutInflater.from(mContext).inflate(R.layout.item_search, viewGroup, false);
            return new cookersViewHolder(inflatedView);
        }
        // type == TYPE_FOOTER ����footerView
//        else if (viewType == TYPE_FOOTER) {
//            View view = LayoutInflater.from(mContext).inflate(
//                    R.layout.footer_view, null);
//
//            return new FooterViewHolder(view);
//        }

        return null;
    }


    private static final int ANIMATED_ITEMS_COUNT = 4;

    private boolean animateItems = false;
    private int lastAnimatedPosition = -1;

    private void runEnterAnimation(View view, int position) {
        if (!animateItems || position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenHeight(mContext));
            view.animate()
                    .translationY(0)
                    .setStartDelay(100 * position)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(500)
                    .start();
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        if (holder instanceof cookersViewHolder) {
            final CookerListinfo.CookerItem cooker = getItem(position);
            ((cookersViewHolder) holder).tv_title.setText(cooker.getName());
            ((cookersViewHolder) holder).tv_content.setText(cooker.getCOMMENTS());
            ((cookersViewHolder) holder).rl_img_1.setImageResource(R.drawable.banner2);

        }
    }


    public CookerListinfo.CookerItem getItem(int position) {
        return CookerList.get(position);
    }

    // RecyclerView��count����Ϊ���������+ 1��footerView��
    @Override
    public int getItemCount() {
//        if (isShow) {
//            return CookerList.size() + 1;
//        }
        return CookerList.size();
    }

    //获取item的类型
    @Override
    public int getItemViewType(int position) {
        // ���һ��item����ΪfooterView
//        if (isShow) {
//            if (position + 1 == getItemCount())
//                return TYPE_FOOTER;
//            }


        return TYPE_ITEM;
    }


    public void addItem(CookerListinfo.CookerItem item) {
        int i = CookerList.size();
        CookerList.add(i, item);
        notifyItemInserted(i);
    }


    public void removeItem(int position) {


        CookerList.remove(position);
        notifyItemRemoved(position);
    }


    public void setItemCount(List<CookerListinfo.CookerItem> generObjects) {

        CookerList.addAll(generObjects);

        notifyDataSetChanged();
    }

    public void refreshitems(List<CookerListinfo.CookerItem> generObjects) {
        CookerList.clear();
        animateItems = true;
        lastAnimatedPosition = -1;
        CookerList.addAll(generObjects);

        notifyDataSetChanged();
    }

//    public void showLoading() {
//        isShow = true;
//        notifyDataSetChanged();
//    }
//
//    public void unshowLoading() {
//        isShow = false;
//        notifyDataSetChanged();
//        Toast.makeText(mContext, "no more information", Toast.LENGTH_SHORT).show();
//    }


    private DisplayImageOptions getWholeOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.banner1) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.banner1)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.banner1)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                        //.decodingOptions(BitmapFactory.Options decodingOptions)//设置图片的解码配置
                .delayBeforeLoading(0)//int delayInMillis为你设置的下载前的延迟时间
                        //设置图片加入缓存前，对bitmap进行设置
                        //.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(16))//不推荐用！！！！是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(200))//是否图片加载好后渐入的动画时间，可能会出现闪动
                .build();//构建完成

        return options;
    }

    private void startActivity(CookerListinfo.CookerItem cooker) {
//        Intent it = new Intent(mContext, CookerDetailActvity.class);
//        if (type == TYPE_FAVCOOK) {
//            it.putExtra("ID", cooker.getChefID());
//} else if (type == TYPE_NORCOOKE) {
//        it.putExtra("ID", cooker.getID());
//        }
//
//        it.putExtra("ty", cooker.getTy());
//        it.putExtra("type", cooker.getType());
//        mContext.startActivity(it);
    }

}
