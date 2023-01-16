package com.daocheng.girlshop.net;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.shidai.loginActivity;
import com.daocheng.girlshop.dialog.CProgressDialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.myApplication;
import com.daocheng.girlshop.utils.Config;
import com.duowan.mobile.netroid.AuthFailureError;
import com.duowan.mobile.netroid.DefaultRetryPolicy;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.RequestQueue;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class NetUtils {
    public interface NetCallBack<T> {
        public void success(T rspData) throws IOException, ClassNotFoundException;

        public void failed(String msg);
    }

    //	public static final String BASE_URL = "http://192.168.1.225:8088/imuslim/index.php/admin/ServicesEngine/DataService";


    private static RequestQueue mQueue = com.daocheng.girlshop.utils.Netroid.newRequestQueue(myApplication.getContext(), null);


    // POST
    public static void post(final Context context, final String type, final Map<String, String> map, final String msg, final NetCallBack<ServiceResult> callBack, final Class<?> rspCls) {
        Log.v("NetType",type);
        if (!com.daocheng.girlshop.utils.Utils.isNetworkConnected(context)) {
            if (callBack != null)
                callBack.failed(context.getResources().getString(R.string.networkerror));
            return;
        }
        final CProgressDialog progressDialog = new CProgressDialog(context, R.style.CustomDialog);
        progressDialog.setCanceledOnTouchOutside(false);
        if (msg != null) {
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(true);
            try {
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
            } catch (Exception e) {

            }
        }
        JSONObject params=null;
        if (map!=null)
        {
            params = new JSONObject(map);
        }








        JsonObjectRequest request = new JsonObjectRequest( type, params, new com.duowan.mobile.netroid.Listener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.e("xx_api",jsonObject.toString());
                progressDialog.dismiss();
//				int retcode = getRetCode(jsonObject);
//				if (retcode != 0) {
//					if (callBack != null)
//						callBack.failed(context.getResources().getString(R.string.networkerror));
//					return;
//				}
                ServiceResult rsp = null;
                try {
                    Gson gson = new Gson();
                    String jsonstr=jsonObject.toString();
                    Log.v("NetUtils",jsonObject.toString());
                    rsp = (ServiceResult) gson.fromJson(jsonstr, rspCls);
                } catch (Exception e) {
                    Log.e("NetUtils", "post RspMsgError !");
                    Log.v("NetUtils", e.getLocalizedMessage());
                    e.printStackTrace();
                }
                if (callBack != null)
                    try {
                        if (rsp != null) {
                            //会员过期，强制退出
                            if ("E10008".equals(rsp.getErrcode())){
                                Config.setShidaiUserInfo(null);
                                context.startActivity(new Intent(context, loginActivity.class));
                                myApplication.getInstance().exitExceptforMain();
                                return;
                            }
                            callBack.success(rsp);
                        } else {
                            callBack.failed(context.getResources().getString(R.string.dataerror));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onError(NetroidError error) {
                if (callBack != null) {
                    callBack.failed(context.getResources().getString(R.string.networkerror));
                }
                progressDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {


                return map ;
            }

            @Override
            public byte [] getBody() {
                Map<String, String> params = map;
                if (params != null && params.size() > 0) {
                    return encodeParameters(params, getParamsEncoding()) ;
                }
                return null;
            }
        };
//        request.addHeader("Accept-Charset", "UTF-8");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        request.setRetryPolicy(new DefaultRetryPolicy(4 * 1000, 4, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));//DefaultRetryPolicy.DEFAULT_MAX_RETRIES


        mQueue.add(request);
    }

    // POST
    public static void upload(final Context context, final String type, final Map<String, String> map, final ArrayList<String> filePaths, final String msg, final NetCallBack<ServiceResult> callBack, final Class<?> rspCls) {
        if (!com.daocheng.girlshop.utils.Utils.isNetworkConnected(context)) {
            if (callBack != null)
                callBack.failed(context.getResources().getString(R.string.networkerror));
            return;
        }
        final CProgressDialog progressDialog = new CProgressDialog(context, R.style.CustomDialog);
        if (msg != null) {
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(true);
            try {
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
            } catch (Exception e) {
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (final String filePath : filePaths) {
                        URL url = new URL(type);
                        JSONObject params = new JSONObject(map);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("content-type", "text/html");
                        //读取文件上传到服务器
                        File file = new File(filePath);
                        if (file.exists()) {
                            BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
                            FileInputStream fileInputStream = new FileInputStream(file);
                            byte[] bytes = new byte[1024];
                            int numReadByte = 0;
                            while ((numReadByte = fileInputStream.read(bytes, 0, 1024)) > 0) {
                                out.write(bytes, 0, numReadByte);
                            }
                            out.flush();
                            fileInputStream.close();
                            //读取URLConnection的响应
                            int code = connection.getResponseCode();
                            if (code == 200) {
                                String jsonString = "";
                                InputStream inputStream = connection.getInputStream();
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                int len = 0;
                                byte[] data = new byte[1024];
                                try {
                                    while ((len = inputStream.read(data)) != -1) {
                                        outputStream.write(data, 0, len);
                                    }
                                    jsonString = new String(outputStream.toByteArray());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                // 文件上传成功
                                ServiceResult rsp = null;
                                try {
                                    Gson gson = new Gson();
                                    rsp = (ServiceResult) gson.fromJson(jsonString, rspCls);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (callBack != null) {
                                    try {
                                        if (rsp != null) {
                                            callBack.success(rsp);
                                        } else {
                                            callBack.failed(context.getResources().getString(R.string.dataerror));
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                // 网络错误
                                if (callBack != null) {
                                    callBack.failed(context.getResources().getString(R.string.networkerror));
                                }
                            }
                        } else {
                            // 文件不存在
                            if (callBack != null) {
                                callBack.failed("文件不存在");
                            }
                        }
                    }
                    progressDialog.dismiss();

                } catch (Exception e) {
                    // 未知错误
                    progressDialog.dismiss();
                    if (callBack != null) {
                        callBack.failed("未知错误");
                    }
                }
            }
        }).start();
    }

    public static int getRetCode(JSONObject jsonObject) {
        try {
            return jsonObject.optInt("retcode");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
