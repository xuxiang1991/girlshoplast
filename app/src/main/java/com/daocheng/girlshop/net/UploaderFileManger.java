//package com.daocheng.girlshop.net;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.daocheng.girlshop.R;
//import com.daocheng.girlshop.dialog.CProgressDialog;
//import com.daocheng.girlshop.entity.Advertorial;
//import com.daocheng.girlshop.entity.ServiceResult;
//import com.daocheng.girlshop.entity.UploadImage;
//import com.daocheng.girlshop.utils.Config;
//import com.daocheng.girlshop.utils.Utils;
//import com.google.gson.Gson;
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.BaseJsonHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//import com.loopj.android.http.SyncHttpClient;
//
//import org.apache.http.Header;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//
///**
// * 项目名称：girlshop
// * 类描述：
// * 创建人：Dove
// * 创建时间：2016/3/28 16:04
// * 修改人：Dove
// * 修改时间：2016/3/28 16:04
// * 修改备注：
// */
//public class UploaderFileManger {
//
//
//    public interface UpCallBack<T> {
//        public void success(T rspData) throws IOException, ClassNotFoundException;
//
//        public void failed(String msg);
//    }
//
//    public interface UpfilesCallBack<T> {
//        public void success(T rspData, int index) throws IOException, ClassNotFoundException;
//
//        public void failed(String msg, int index);
//    }
//
//    private static final String BASE_URL = "http://nzh.dig-data.com";
//
//    private static AsyncHttpClient client = new AsyncHttpClient();
//
//    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.get(getAbsoluteUrl(url), params, responseHandler);
//    }
//
//    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.post(getAbsoluteUrl(url), params, responseHandler);
//    }
//
//    private static String getAbsoluteUrl(String relativeUrl) {
//        return BASE_URL + relativeUrl;
//    }
//
////必须， 图片类型标识， U: 头像； A: 文章； O: 其他
//    public static RequestParams getParams(Context content, File file,String type) throws FileNotFoundException {
//        RequestParams params = new RequestParams();
//        params.put("upImage", file);
//        params.put("path",type);
//        params.put("macId", Utils.getIMEI(content));//传输的字符数据
//        params.put("token", Config.getUserInfo().getData().getToken());//传输的字符数据
//        return params;
//    }
//
//
//    //单个文件上传
//    public static void postUpload(final Context self, File file,String type, final UpCallBack<UploadImage> callBack) {
//        try {
//
//
//            UploaderFileManger.post("/nzh/u/mobile/1.0/img/upload", UploaderFileManger.getParams(self, file,type), new BaseJsonHttpResponseHandler<UploadImage>() {
//
//                final CProgressDialog progressDialog = new CProgressDialog(self, R.style.CustomDialog);
//
//
//                @Override
//                public void onStart() {
//                    progressDialog.setMessage("上传中");
//                    progressDialog.setCancelable(true);
//                    progressDialog.setCanceledOnTouchOutside(false);
//                    try {
//                        if (!progressDialog.isShowing()) {
//                            progressDialog.show();
//                        }
//                    } catch (Exception e) {
//
//                    }
//                }
//
//                @Override
//                public void onFinish() {
//                    super.onFinish();
//                    progressDialog.dismiss();
//                }
//
//                @Override
//                public void onSuccess(int stateCode, Header[] headers, String s, UploadImage uploadImage) {
//                    if (stateCode == 200 && uploadImage != null) {
//
//                        Log.v("pic", uploadImage.getData());
//                        try {
//                            callBack.success(uploadImage);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace();
//                        }
//
//
//                        Log.v("pic", "上传成功" + s);
//                    } else {
//                        callBack.failed("上传成功，但未取得图片地址");
//                    }
//                }
//
//                /**可以重写UpCallBack 增加上传百分比
//                 * @param bytesWritten
//                 * @param totalSize
//                 */
//                @Override
//                public void onProgress(long bytesWritten, long totalSize) {
//
//                    Log.v("pic", Double.toString(Double.valueOf(totalSize > 0L ? (double) bytesWritten * 1.0D / (double) totalSize * 100.0D : -1.0D)));
//                }
//
//                @Override
//                public void onFailure(int i, Header[] headers, Throwable throwable, String s, UploadImage uploadImage) {
//
//                    callBack.failed(throwable.toString());
//                    Log.v("pic", throwable.toString());
//                }
//
//                @Override
//                protected UploadImage parseResponse(String s, boolean b) throws Throwable {
//
//                    UploadImage img = null;
//                    if (!TextUtils.isEmpty(s))
//
//                        img = (UploadImage) new Gson().fromJson(s, UploadImage.class);
//
//                    return img;
//                }
//
//                @Override
//                public void onRetry(int retryNo) {
//                    super.onRetry(2);//重复两次
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
////    private static SyncHttpClient Syncclient = new SyncHttpClient();
////
////    //多个个文件上传
////    public static void postUploadfiles(final Context self, final List<Advertorial> advertorials, final UpfilesCallBack<UploadImage> callBack) {
////        final CProgressDialog progressDialog = new CProgressDialog(self, R.style.CustomDialog);
////        for (int i = 0; i < advertorials.size(); i++) {
////            if (advertorials.get(i).getImage() == null)
////                continue;
////            final File file = new File(advertorials.get(i).getImage());
////            if (!file.exists() && file.length() == 0)
////                continue;
////            try {
////
////                final int index = i;
////
////
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////
////
////                        try {
////                            Syncclient.post(getAbsoluteUrl("/nzh/article/mobile/1.0/img/upload"), UploaderFileManger.getParams(self, file), new BaseJsonHttpResponseHandler<UploadImage>() {
////
////
////                                @Override
////                                public void onStart() {
////                                    progressDialog.setMessage("图片上传中");
////                                    progressDialog.setCancelable(false);
////                                    try {
////                                        if (!progressDialog.isShowing()) {
////                                            progressDialog.show();
////                                        }
////                                    } catch (Exception e) {
////
////                                    }
////                                }
////
////                                @Override
////                                public void onFinish() {
////                                    super.onFinish();
////                                    progressDialog.dismiss();
////
////                                }
////
////                                @Override
////                                public void onSuccess(int stateCode, Header[] headers, String s, UploadImage uploadImage) {
////                                    if (stateCode == 200 && uploadImage != null) {
////
////
////                                        try {
////                                            callBack.success(uploadImage, index);
////                                        } catch (IOException e) {
////                                            e.printStackTrace();
////                                        } catch (ClassNotFoundException e) {
////                                            e.printStackTrace();
////                                        }
////
////                                        Log.v("pic", "上传成功" + s);
////                                    } else {
////
////                                    }
////                                }
////
////                                /**
////                                 * 可以重写UpCallBack 增加上传百分比
////                                 *
////                                 * @param bytesWritten
////                                 * @param totalSize
////                                 */
////                                @Override
////                                public void onProgress(long bytesWritten, long totalSize) {
////                                    try {
////                                        if (!progressDialog.isShowing()) {
////                                            progressDialog.show();
////                                        }
////                                    } catch (Exception e) {
////
////                                    }
////                                    Log.v("pic", Double.toString(Double.valueOf(totalSize > 0L ? (double) bytesWritten * 1.0D / (double) totalSize * 100.0D : -1.0D)));
////                                }
////
////                                @Override
////                                public void onFailure(int i, Header[] headers, Throwable throwable, String s, UploadImage uploadImage) {
////
////                                    callBack.failed(throwable.toString(), index);
////                                    Log.v("pic", throwable.toString());
////                                }
////
////                                @Override
////                                protected UploadImage parseResponse(String s, boolean b) throws Throwable {
////
////                                    UploadImage img = null;
////                                    if (!TextUtils.isEmpty(s))
////
////                                        img = (UploadImage) new Gson().fromJson(s, UploadImage.class);
////
////                                    return img;
////                                }
////
////                                @Override
////                                public void onRetry(int retryNo) {
////                                    super.onRetry(2);//重复两次
////                                }
////                            });
////                        } catch (FileNotFoundException e) {
////                            e.printStackTrace();
////                        }
////
////
////                    }
////                }).start();
////
////
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////
////
////    }
//
//
//}