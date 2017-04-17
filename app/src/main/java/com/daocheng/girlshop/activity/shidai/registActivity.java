package com.daocheng.girlshop.activity.shidai;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.dialog.CProgressDialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.qiniuToke;
import com.daocheng.girlshop.myApplication;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.view.FloatTextToast;
import com.daocheng.girlshop.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 注册页面
 * Created by Dove on 2016/6/18.
 */
public class registActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_login;
    private RoundImageView iv_head;
    private EditText ed_username, ed_secret;
    private Button bt_register;

    private String headpath;//头像地址

    private int RESULT_LOAD_IMAGE = 1001;
    private String phone;
    private String headpathResponse;

    private static final int REQUEST_TOUXIANG_SELECT_PHOTO = 101;
    private static final int REQUEST_TOUXIANG_TAKE_PHOTO = 102;
    private static final int REQUEST_TOUXIANG_CLIP_PHOTO = 103;

    String mBufferPath = (new File(Environment.getExternalStorageDirectory(), "shidaitouxiang.jpg")).toString();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shidai_regist;
    }

    @Override
    protected void setupViews() {

        tv_login = (TextView) findViewById(R.id.tv_login);
        iv_head = (RoundImageView) findViewById(R.id.iv_head);
        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_secret = (EditText) findViewById(R.id.ed_secret);
        bt_register = (Button) findViewById(R.id.bt_register);

        tv_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        iv_head.setOnClickListener(this);
    }

    @Override
    protected void initialized() {

        phone = getIntent().getStringExtra("phone");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                if (TextUtils.isEmpty(ed_username.getText().toString())) {
                    FloatTextToast.makeText(self, ed_username, "姓名不能为空", FloatTextToast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ed_secret.getText().toString()) || ed_secret.getText().toString().length() < 6) {
                    FloatTextToast.makeText(self, ed_secret, "请输入6-16位正确的密码", FloatTextToast.LENGTH_SHORT).show();
                    return;
                }
                if (headpathResponse == null) {
                    Toast.makeText(self, "请添加头像", Toast.LENGTH_SHORT).show();
                    return;
                }


                submitUserInfo(ed_username.getText().toString(), ed_secret.getText().toString());

                break;
            case R.id.iv_head:
//                showPics();
                showPickDialog();
                break;
            case R.id.tv_login:
                finish();
                break;


        }

    }

    private void submitUserInfo(String userNmae, String secret) {

        ShidaiApi.register(self, phone, secret, headpathResponse, userNmae, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".endsWith(rspData.getErrcode())) {
                    showShortToast("您已注册，请登录");
                    return;
                } else {
                    showShortToast(rspData.getErrmsg());
                }
            }

            @Override
            public void failed(String msg) {
                showShortToast(msg);
            }
        });
    }


    private void uploadhead() {
        ShidaiApi.getQiNiuUptoken(self, qiniuToke.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {

                if ("0".endsWith(rspData.getErrcode())) {
                    qiniuToke qtoken = (qiniuToke) rspData;
                    String recode = qtoken.getRecord();
                    if (TextUtils.isEmpty(recode)) {
                        failed("服务器上传失败");
                        return;
                    }

                    UploadManager uploadManager = myApplication.getInstance().getUploadManager();
                    String data = headpath;
                    String token = recode;
                    String key = phone.substring(phone.length() - 4, phone.length()) + System.currentTimeMillis();

                    final CProgressDialog progressDialog = new CProgressDialog(self, R.style.CustomDialog);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("图片上传");
                    progressDialog.setCancelable(true);
                    try {
                        if (!progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                    } catch (Exception e) {

                    }


                    uploadManager.put(data, key, token,
                            new UpCompletionHandler() {

                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    //  res 包含hash、key等信息，具体字段取决于上传策略的设置。
                                    Log.v("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                                    progressDialog.dismiss();
                                    headpathResponse = key;
                                }
                            }, new UploadOptions(null, null, false, null, new UpCancellationSignal() {
                                public boolean isCancelled() {
                                    return false;
                                }
                            }));
                }
            }

            @Override
            public void failed(String msg) {
                showShortToast(msg);
            }
        });
    }

    private void showPics() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch (requestCode) {
            // 从相册获取
            case REQUEST_TOUXIANG_SELECT_PHOTO:
                if (data != null) {
                    // 将裁剪后的图片保存到mBufferPath
                    saveFile(mBufferPath, getAbsolutePathFromUri(data.getData()));

                    startPhotoZoom(data.getData());
                }
                break;

            // 拍照获取
            case REQUEST_TOUXIANG_TAKE_PHOTO:
                File temp = new File(mBufferPath);
                if (temp.exists()) {
                    startPhotoZoom(Uri.fromFile(temp));
                }
                break;

            // 取得裁剪后的图片
            case REQUEST_TOUXIANG_CLIP_PHOTO:
                if (data != null) {
                    // 保存裁剪后的图片到文件夹下
                    saveAfterCrop(data);
                    // 上传头像

                    headpath = mBufferPath;
                    ImageLoader.getInstance().displayImage("file://" + headpath, iv_head);
                    uploadhead();
                } else {
                    deleteFiles(mBufferPath);
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void deleteFiles(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    private String getImageType(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        return opts.outMimeType;
    }

    private String createZeroFrameIfGif(String path) {
        Movie movie = null;
        int width = 0;
        int height = 0;
        movie = Movie.decodeFile(path);
        if (movie != null) {
            movie.setTime(0);
            width = movie.width();
            height = movie.height();
            if (width == 0 || height == 0) {
                return null;
            }
        }
        Bitmap bitmap = null;
        FileOutputStream fos = null;
        try {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            movie.draw(canvas, 0, 0);
            fos = new FileOutputStream(mBufferPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
        }
        return mBufferPath;
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        try {
            String path = getAbsolutePathFromUri(uri);
            if (path != null) {
                String type = getImageType(path);
                // 如果是gif图，以gif的第0帧生成临时图片ktemp.jpg，再进行裁剪
                if ("image/gif".equals(type)) {
                    String tempPath = createZeroFrameIfGif(path);
                    if (tempPath != null) {
                        File file = new File(tempPath);
                        if (file.exists()) {
                            uri = Uri.fromFile(file);
                        }
                    }
                }
            }


            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 150);
            intent.putExtra("outputY", 150);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_TOUXIANG_CLIP_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPickDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(self).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.user_center_take_photo_dialog);

        View.OnClickListener dialogListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_dialog_select_photo:
                        alertDialog.dismiss();
                        Intent intentSelectPhoto = new Intent(Intent.ACTION_PICK);
                        intentSelectPhoto.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intentSelectPhoto, REQUEST_TOUXIANG_SELECT_PHOTO);
                        break;

                    case R.id.tv_dialog_take_photo:
                        alertDialog.dismiss();

                        Intent intentTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intentTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(mBufferPath)));

                        startActivityForResult(intentTakePhoto, REQUEST_TOUXIANG_TAKE_PHOTO);
                        break;

                    default:
                        break;
                }
            }
        };

        ((TextView) window.findViewById(R.id.tv_dialog_select_photo)).setOnClickListener(dialogListener);
        ((TextView) window.findViewById(R.id.tv_dialog_take_photo)).setOnClickListener(dialogListener);
    }


    private void saveFile(String dest, String source) {
        File oldFile = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            int byteRead = 0;
            oldFile = new File(source);
            if (oldFile.exists()) {
                is = new FileInputStream(oldFile);
                fos = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                while ((byteRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private String getAbsolutePathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        String path = null;
        try {
            cursor = self.getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
            } else {
                path = uri.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }


    private void saveAfterCrop(Intent data) {

        Bundle extras = data.getExtras();
        if (extras != null) {
            FileOutputStream photo = null;
            Bitmap bitmap = null;
            try {
                bitmap = extras.getParcelable("data");
                photo = new FileOutputStream(mBufferPath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, photo);
                photo.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                }
                if (photo != null) {
                    try {
                        photo.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
