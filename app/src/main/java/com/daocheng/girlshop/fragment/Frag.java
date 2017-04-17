package com.daocheng.girlshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;



/**
 * 项目名称：Cooke
 * 类描述：
 * 创建人：Dove
 * 创建时间：2015/9/23 17:04
 * 修改人：Dove
 * 修改时间：2015/9/23 17:04
 * 修改备注：
 */
public class Frag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getActivity());
        tv.setText(getArguments().getString("key"));
        tv.setTextColor(getActivity().getResources().getColor(android.R.color.white));

        return tv;
    }
}

////  DateDialog dialog=new DateDialog(MainActivity.this,R.style.dateDialog, new DateDialog.onDateRequest() {
//@Override
//public void back(int year, int month, int day) {
//    Toast.makeText(MainActivity.this,year+"-"+month+"-"+day,Toast.LENGTH_LONG).show();
//}
//});
//显示时间框
//        dialog.show();