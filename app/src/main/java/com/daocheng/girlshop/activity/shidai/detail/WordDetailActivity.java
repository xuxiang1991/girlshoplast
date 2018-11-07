package com.daocheng.girlshop.activity.shidai.detail;

import android.content.Intent;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.WordDetailBean;
import com.daocheng.girlshop.entity.shdiai.WordLevel;
import com.daocheng.girlshop.entity.shdiai.WordList;
import com.daocheng.girlshop.media.recoder.MediaManager;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

/**
 * 类名称：我的单词
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class WordDetailActivity extends BaseActivity implements View.OnClickListener {


    private ImageView tv_left;
    private TextView tv_center;

    private TextView tv_count;
    private TextView tv_word;

    private TextView tv_play1, tv_info1;
    private TextView tv_play2, tv_info2;
    private TextView tv_trans;
    private TextView tv_know, tv_unknow, tv_next;
    private RelativeLayout rl_info;
    private LinearLayout rl_complete;

    private WordDetailBean wordDetail;
    private List<WordList.RecordBean> wordList;
    private int position;

    /**
     * 详情指针
     */
    private int positionDetail = 0;
    private WordDetailBean.RecordBean currentBean;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;


        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_worddetail;
    }

    @Override
    protected void setupViews() {

        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);

        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setText("课程详情");
        rl_info = findViewById(R.id.rl_info);
        rl_complete = findViewById(R.id.rl_complete);
        tv_count = findViewById(R.id.tv_count);
        tv_word = findViewById(R.id.tv_word);
        tv_play1 = findViewById(R.id.tv_play1);
        tv_info1 = findViewById(R.id.tv_info1);
        tv_play2 = findViewById(R.id.tv_play2);
        tv_info2 = findViewById(R.id.tv_info2);
        tv_trans = findViewById(R.id.tv_trans);
        tv_know = findViewById(R.id.tv_know);
        tv_unknow = findViewById(R.id.tv_unknow);
        tv_next = findViewById(R.id.tv_next);
        tv_know.setOnClickListener(this);
        tv_unknow.setOnClickListener(this);
        tv_next.setOnClickListener(this);

    }


    private void getIntentData() {

        String wordDetailstr = getIntent().getStringExtra("wordlist");
        if (!TextUtils.isEmpty(wordDetailstr)) {
            wordList = new Gson().fromJson(wordDetailstr, new TypeToken<List<WordList.RecordBean>>() {
            }.getType());
        }
        position = getIntent().getIntExtra("position", 0);


    }

    @Override
    protected void initialized() {
        getIntentData();
        if (wordList != null && wordList.size() > 0 && position < wordList.size()) {
            getWordDetail(wordList.get(position));
        }


    }


    private void setData() {
        if (wordDetail != null && positionDetail < wordDetail.getRecord().size()) {
            WordDetailBean.RecordBean bean = wordDetail.getRecord().get(positionDetail);
            tv_count.setText((positionDetail + 1) + "/" + wordDetail.getRecord().size());
            currentBean = bean;
            setWordInfo(bean);

        }
        setWordFoot();
    }


    private void setWordInfo(final WordDetailBean.RecordBean bean) {

        tv_word.setText(bean.getWord_name());
        tv_info1.setText("英[" + bean.getPh_en() + "]");
        tv_info2.setText("美[" + bean.getPh_am() + "]");
        String trans = "";
        for (int i = 0; i < bean.getParts().size(); i++) {
            WordDetailBean.RecordBean.PartsBean pBean = bean.getParts().get(i);
            trans += pBean.getPart();
            for (int j = 0; j < pBean.getMeans().size(); j++) {
                trans += pBean.getMeans().get(j) + ";";
            }
            trans = trans.substring(0, trans.length() - 1) + "\r\n";
        }
        tv_trans.setText(trans);
        tv_trans.setVisibility(View.INVISIBLE);
        tv_play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaManager.playSound(bean.getPh_en_mp3(),
                        new MediaPlayer.OnCompletionListener() {

                            @Override
                            public void onCompletion(MediaPlayer mp) {
                            }
                        });
            }
        });
        tv_play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaManager.playSound(bean.getPh_am_mp3(),
                        new MediaPlayer.OnCompletionListener() {

                            @Override
                            public void onCompletion(MediaPlayer mp) {
                            }
                        });
            }
        });

    }

    private void setWordFoot() {
        if (positionDetail < wordDetail.getRecord().size()) {
            rl_info.setVisibility(View.VISIBLE);
            rl_complete.setVisibility(View.GONE);
            tv_know.setText("认识");
            tv_unknow.setText("不认识");
            tv_know.setVisibility(View.VISIBLE);
            tv_unknow.setVisibility(View.VISIBLE);
            tv_next.setVisibility(View.GONE);
            tv_know.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionDetail++;
                    setData();

                }
            });
            tv_unknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_trans.setVisibility(View.VISIBLE);
                    tv_unknow.setVisibility(View.GONE);
                    tv_know.setVisibility(View.GONE);
                    tv_next.setVisibility(View.VISIBLE);
                    tv_next.setText("下一步");
                }
            });
            tv_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionDetail++;
                    setData();
                }
            });
        } else {
            rl_info.setVisibility(View.GONE);
            rl_complete.setVisibility(View.VISIBLE);

            tv_know.setText("重测");
            tv_unknow.setText("下一课程");
            tv_know.setVisibility(View.VISIBLE);
            tv_unknow.setVisibility(View.VISIBLE);
            tv_next.setVisibility(View.GONE);
            tv_know.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionDetail = 0;
                    setData();

                }
            });
            tv_unknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position++;
                    if (wordList != null && wordList.size() > 0 && position < wordList.size()) {
                        getWordDetail(wordList.get(position));
                    } else {
                        showShortToast("已是最新课程，等待更新。。。");
                        tv_unknow.setVisibility(View.GONE);
                    }
                }
            });


        }

    }


    private void getWordDetail(WordList.RecordBean ob) {
        ShidaiApi.getWordDetail(self, ob.getLessonId(), Config.getShidaiUserInfo().getUserid(), 1, ob.getNum(), WordDetailBean.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    wordDetail = (WordDetailBean) rspData;
                    positionDetail = 0;
                    setData();


                } else {
                    showShortToast(rspData.getMessage());
                }
            }

            @Override
            public void failed(String msg) {
                Toast.makeText(self, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}