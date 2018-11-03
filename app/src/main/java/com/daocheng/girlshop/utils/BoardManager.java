package com.daocheng.girlshop.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;

import java.io.IOException;

/**
 * Created by XX on 2016/6/25.
 */
public class BoardManager {


    public static String clipboard = "";


    public synchronized static void init(final Context mcontext) {
        final ClipboardManager cm = (ClipboardManager) mcontext.getSystemService(mcontext.CLIPBOARD_SERVICE);

        cm.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if (((Activity) mcontext) != ActivityManager.getScreenManager().currentActivity())
                    return;


                ClipData data = cm.getPrimaryClip();
                if (data == null) {
                    return;
                }
                ClipData.Item item = data.getItemAt(0);
                // 监听剪切板内容变化，获得文字
                final String text = item.getText().toString();
                if (text.equals(clipboard))
                    return;
                clipboard = text;
//                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                ActionSheet.createBuilder(mcontext, ((FragmentActivity) mcontext).getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles("加入到生词本")
                        .setCancelableOnTouchOutside(true)
                        .setListener(new ActionSheet.ActionSheetListener() {
                            @Override
                            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                                clipboard = "";
                            }

                            @Override
                            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                                // 调用服务器接口上传
                                ShidaiApi.addtoWordbooke(mcontext, Config.getShidaiUserInfo().getUserid(), text, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
                                    @Override
                                    public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                                        if ("0".equals(rspData.getErrcode())) {

//                                            Toast.makeText(mcontext,"加入生词表成功",Toast.LENGTH_SHORT).show();


                                        }
                                    }

                                    @Override
                                    public void failed(String msg) {

                                    }
                                });
                            }
                        }).show();
            }
        });
    }

}
