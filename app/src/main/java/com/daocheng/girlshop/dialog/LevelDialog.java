package com.daocheng.girlshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.entity.shdiai.WordLevel;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.view.citywheel.widget.WheelView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

/**
 * 类名称：
 * 类描述：
 * 创建人：
 * 修改人：
 */
public class LevelDialog extends Dialog implements View.OnClickListener {

    private Context context;


    private TagFlowLayout mFlowLayout;
    private List<WordLevel.RecordBean> list;
    private LayoutInflater mInflater;

    private ShowLevel showLevel;

    @Override
    public void onClick(View v) {

    }

    public interface ShowLevel {
        void back(WordLevel.RecordBean level);

    }

    public LevelDialog(Context context, List<WordLevel.RecordBean> list, ShowLevel callBack) {
        super(context, R.style.dialog_right_actionsheet);
        this.context = context;
        this.list = list;
        this.showLevel = callBack;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_flowlayout);


        setUpViews();


        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.RIGHT);  //此处可以设置dialog显示的位置

//        int screenwith = Config.width;
        lp.width = (int) (Config.width * 0.8f); // 宽度

        lp.height = (int) (Config.height);
        dialogWindow.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);


    }


    private void setUpViews() {
        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
        //mFlowLayout.setMaxSelectCount(3);
        mFlowLayout.setAdapter(new TagAdapter<WordLevel.RecordBean>(list) {

            @Override
            public View getView(FlowLayout parent, int position, WordLevel.RecordBean item) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv_choice,
                        mFlowLayout, false);
                tv.setText(item.getLevelname());
                if (item.getPass() == 0) {
                    tv.setBackgroundResource(R.drawable.shape_choice_item_enable);
                    tv.setTextColor(context.getResources().getColor(R.color.text_light_grey));
                } else {
                    tv.setBackgroundResource(R.drawable.shape_choice_item_able);
                    tv.setTextColor(context.getResources().getColor(R.color.home_fragment_title));
                    tv.setCompoundDrawables(null, null, null, null);
                }

                return tv;
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (list.get(position).getPass() == 1) {
                    showLevel.back( list.get(position));
                    dismiss();
                }


                return true;
            }
        });


        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
//                getActivity().setTitle("choose:" + selectPosSet.toString());
            }
        });

    }

}
