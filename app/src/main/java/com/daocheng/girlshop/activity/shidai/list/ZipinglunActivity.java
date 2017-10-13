package com.daocheng.girlshop.activity.shidai.list;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.dialog.CProgressDialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.Vrecoder;
import com.daocheng.girlshop.entity.shdiai.punlun;
import com.daocheng.girlshop.entity.shdiai.qiniuToke;
import com.daocheng.girlshop.media.recoder.MediaManager;
import com.daocheng.girlshop.media.recoder.view.AudioRecordButton;
import com.daocheng.girlshop.myApplication;
import com.daocheng.girlshop.net.DownloadManager;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.daocheng.girlshop.utils.Constant;
import com.daocheng.girlshop.view.RoundImageView;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XX on 2016/7/16.
 */
public class ZipinglunActivity extends BaseActivity implements View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private List<Vrecoder.RecordBean> baseobjects;
    private baseObRecycleAdapter sRecyclerViewAdapter;

    private Vrecoder advertorialList;

    private TextView tv_center;

    private int pageNo = 1;

    private int lastVisibleItem;
    private ImageView tv_left;

    //底部
    private CheckBox cb_isvoice;
    private AudioRecordButton recordButton;
    private EditText ed_text;
    private TextView tv_send;

    private int id;

    private String dataflag;
    private static final String TYPE_TXT = "txt";
    private static final String TYPE_VOICE = "amr";

    private String dir = Environment.getExternalStorageDirectory()
            + "/cooke/recoder/";


    @Override
    protected int getLayoutId() {
        return R.layout.shidai_pinlun_acitivity;
    }

    @Override
    protected void setupViews() {

        tv_send = (TextView) findViewById(R.id.tv_send);
        cb_isvoice = (CheckBox) findViewById(R.id.cb_isvoice);
        recordButton = (AudioRecordButton) findViewById(R.id.recordButton);
        ed_text = (EditText) findViewById(R.id.ed_text);
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);
        tv_send.setOnClickListener(this);

        tv_center = (TextView) findViewById(R.id.tv_center);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(self);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        baseobjects = new ArrayList<Vrecoder.RecordBean>();
        sRecyclerViewAdapter = new baseObRecycleAdapter();
        mRecyclerView.setAdapter(sRecyclerViewAdapter);

        tv_left.setOnClickListener(this);
    }

    @Override
    protected void initialized() {

        tv_center.setText("评论");

        id = getIntent().getIntExtra("id", 0);
        recordButton.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
            @Override
            public void onFinished(float seconds, String filePath) {
//                if (Config.getShidaiUserInfo()!=null&&Config.getShidaiUserInfo().getLevel().contains("游客"))
//                {
//                    showShortToast("游客不能发表");
//                    return;
//                }
                Log.v("recoderVoice", filePath + seconds);
                uploadhead(filePath, ((int) seconds) > 1 ? (int) seconds : 1);
            }
        });

        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));

        //加载内容
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem == sRecyclerViewAdapter.getItemCount() - 1
                        && lastVisibleItem == (pageNo * Constant.found_pageNum - 1)) {
                    pageNo = pageNo + 1;
                    setData();
                    mSwipeRefreshLayout.setRefreshing(true);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });


        mSwipeRefreshLayout.setColorSchemeResources(R.color.App_back_orange, R.color.green, R.color.blue, R.color.red);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mSwipeRefreshLayout.setRefreshing(true);
                setData();


            }
        });

        setData();
        cb_isvoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ed_text.setVisibility(View.VISIBLE);
                    recordButton.setVisibility(View.GONE);
                    tv_send.setVisibility(View.VISIBLE);
                } else {
                    ed_text.setVisibility(View.GONE);
                    tv_send.setVisibility(View.GONE);
                    recordButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_send:
                sendText();
                break;
        }
    }


    private void sendText() {
        if (!TextUtils.isEmpty(ed_text.getText().toString())) {
//            if (Config.getShidaiUserInfo()!=null&&Config.getShidaiUserInfo().getLevel().contains("游客"))
//            {
//                showShortToast("游客不能发表");
//                return;
//            }
            sendMessge(TYPE_TXT, 0, "", ed_text.getText().toString());
            ed_text.setText("");
        }
    }


    private void setData() {
        ShidaiApi.getzipingList(self, Config.getShidaiUserInfo().getUserid(), id, pageNo, Constant.pageNum, Vrecoder.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    advertorialList = (Vrecoder) rspData;
                    if (pageNo == 1)
                        baseobjects = advertorialList.getRecord();
                    else
                        baseobjects.addAll(advertorialList.getRecord());
                    Log.v("ere", "fdfdf");

                    sRecyclerViewAdapter.notifyDataSetChanged();

                } else {
                    showShortToast(rspData.getMessage());
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failed(String msg) {
                Toast.makeText(self, msg, Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public class baseObRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class arViewHolder extends RecyclerView.ViewHolder {

            RoundImageView iv_head;
            TextView tv_name;
            TextView tv_time;

            LinearLayout ll_text;
            TextView tv_message;

            RelativeLayout rl_voice;
            LinearLayout ll_voice;
            ImageView iv_voice;
            TextView tv_voicesize;

            CheckBox bt_zan;
            TextView bt_pinglun;


            public arViewHolder(View itemView) {
                super(itemView);
                iv_head = (RoundImageView) itemView.findViewById(R.id.iv_head);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_time = (TextView) itemView.findViewById(R.id.tv_time);

                ll_text = (LinearLayout) itemView.findViewById(R.id.ll_text);
                tv_message = (TextView) itemView.findViewById(R.id.tv_message);

                rl_voice = (RelativeLayout) itemView.findViewById(R.id.rl_voice);
                ll_voice = (LinearLayout) itemView.findViewById(R.id.ll_voice);
                iv_voice = (ImageView) itemView.findViewById(R.id.iv_voice);
                tv_voicesize = (TextView) itemView.findViewById(R.id.tv_voicesize);

                bt_zan = (CheckBox) itemView.findViewById(R.id.bt_zan);
                bt_pinglun = (TextView) itemView.findViewById(R.id.bt_pinglun);
                bt_zan.setVisibility(View.GONE);
                bt_pinglun.setVisibility(View.GONE);
            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View inflatedView = LayoutInflater.from(self).inflate(R.layout.shidai_item_message, parent, false);
            return new arViewHolder(inflatedView);


        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final Vrecoder.RecordBean ob = getItem(position);
            if (holder instanceof arViewHolder) {
                if (!TextUtils.isEmpty(ob.getHead()))
                    ImageLoader.getInstance().displayImage(ob.getHead(), ((arViewHolder) holder).iv_head);
                else
                    ((arViewHolder) holder).iv_head.setImageResource(R.drawable.head_sculpture);
                ((arViewHolder) holder).tv_name.setText(ob.getNickname());
                ((arViewHolder) holder).tv_time.setText(ob.getUpdatetime());


                if (ob.getType().equals(TYPE_TXT)) {
                    ((arViewHolder) holder).ll_text.setVisibility(View.VISIBLE);
                    ((arViewHolder) holder).rl_voice.setVisibility(View.GONE);
                    ((arViewHolder) holder).tv_message.setText(ob.getContent());
                    ((arViewHolder) holder).ll_text.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (ob.getUserid() == Config.getShidaiUserInfo().getUserid())
                                showdelete(ob.getId(), position);
                            return false;
                        }
                    });
                } else {
                    ((arViewHolder) holder).ll_text.setVisibility(View.GONE);
                    ((arViewHolder) holder).rl_voice.setVisibility(View.VISIBLE);
                    ((arViewHolder) holder).tv_voicesize.setText(ob.getLength() + "s");
                    ((arViewHolder) holder).rl_voice.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (ob.getUserid() == Config.getShidaiUserInfo().getUserid())
                                showdelete(ob.getId(), position);
                            return false;
                        }
                    });
                    ((arViewHolder) holder).ll_voice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(ob.getUrl())) {
//                                showShortToast("没有语音信息");
                                return;
                            }
                            final String localFile = DownloadManager.mSaveRecodeDirPath + ob.getUrl().replace(ShidaiApi.Pic_BASE_URL, "");
                            File localfile = new File(localFile);
                            ((arViewHolder) holder).iv_voice.setBackgroundResource(R.drawable.play);
                            AnimationDrawable drawable = (AnimationDrawable) ((arViewHolder) holder).iv_voice
                                    .getBackground();
                            drawable.start();
                            if (localfile.exists()) {
                                MediaManager.playSound(localFile,
                                        new MediaPlayer.OnCompletionListener() {

                                            @Override
                                            public void onCompletion(MediaPlayer mp) {
//                                                showShortToast("播放完成");
                                                ((arViewHolder) holder).iv_voice.setBackgroundResource(R.drawable.pk_icon_voice3);

                                            }
                                        });
                            } else

                                DownloadManager.getInstance().download(localFile, DownloadManager.RECODER, ob.getUrl(), new Listener<Void>() {
                                    @Override
                                    public void onSuccess(Void response) {

                                        MediaManager.playSound(localFile,
                                                new MediaPlayer.OnCompletionListener() {


                                                    @Override
                                                    public void onCompletion(MediaPlayer mp) {
//                                                        showShortToast("播放完成");
                                                        ((arViewHolder) holder).iv_voice.setBackgroundResource(R.drawable.pk_icon_voice3);

                                                    }
                                                });
                                    }

                                    @Override
                                    public void onProgressChange(long fileSize, long downloadedSize) {
                                        super.onProgressChange(fileSize, downloadedSize);

                                    }

                                    @Override
                                    public void onError(NetroidError error) {
                                        super.onError(error);
                                        ((arViewHolder) holder).iv_voice.setBackgroundResource(R.drawable.pk_icon_voice3);
                                    }
                                });


                        }
                    });

                }


            }


        }

        public Vrecoder.RecordBean getItem(int position) {
            return baseobjects.get(position);
        }

        @Override
        public int getItemCount() {
            return baseobjects.size();
        }
    }


    private void uploadhead(final String file, final int length) {
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
                    String data = file;
                    String token = recode;
                    String key = file.replace(dir, "");

                    final CProgressDialog progressDialog = new CProgressDialog(self, R.style.CustomDialog);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("");
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

                                    sendMessge(TYPE_VOICE, length, key, "");
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


    private void sendMessge(final String type, final int length, final String url, final String content) {


        ShidaiApi.sendZiPinglun(self, Config.getShidaiUserInfo().getUserid(), id, type, length, url, content, punlun.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {

                if ("0".equals(rspData.getErrcode())) {

                    punlun pl = (punlun) rspData;

                    Vrecoder.RecordBean recordBean = new Vrecoder.RecordBean();
                    recordBean.setContent(content);
                    recordBean.setHead(Config.getShidaiUserInfo().getHead());
                    recordBean.setLength(length);
                    recordBean.setNickname(Config.getShidaiUserInfo().getNickname());
                    recordBean.setType(type);
                    recordBean.setUrl(ShidaiApi.Pic_BASE_URL + url);
                    recordBean.setUpdatetime("现在");
                    recordBean.setZan(0);
                    recordBean.setId(pl.getId());
                    recordBean.setUserid(Config.getShidaiUserInfo().getUserid());

                    baseobjects.add(0, recordBean);
                    sRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.smoothScrollToPosition(0);
                }
            }

            @Override
            public void failed(String msg) {

            }
        });
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        MediaManager.release();
    }


    private void showdelete(final int id, final int position) {
        ActionSheet.createBuilder(self, self.getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("删除")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        // 调用服务器接口上传
                        ShidaiApi.deleteZiPinglun(self, Config.getShidaiUserInfo().getUserid(), id, ServiceResult.class, new NetUtils.NetCallBack<ServiceResult>() {
                            @Override
                            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                                if ("0".equals(rspData.getErrcode())) {
                                    showToast("评论删除成功");
                                    baseobjects.remove(position);
                                    sRecyclerViewAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void failed(String msg) {
                                showToast("评论删除失败");
                            }
                        });
                    }
                }).show();
    }

}