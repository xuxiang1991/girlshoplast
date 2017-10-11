package com.daocheng.girlshop.activity.shidai;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daocheng.girlshop.R;
import com.daocheng.girlshop.activity.BaseActivity;
import com.daocheng.girlshop.dialog.SendWordDialog;
import com.daocheng.girlshop.entity.ServiceResult;
import com.daocheng.girlshop.entity.shdiai.SmzyBean;
import com.daocheng.girlshop.net.NetUtils;
import com.daocheng.girlshop.net.ShidaiApi;
import com.daocheng.girlshop.utils.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类名称：书面作业
 * 类描述：
 * 创建人：xuxiang
 * 修改人：
 */
public class SmzyActivity extends BaseActivity implements View.OnClickListener {


    private ImageView tv_left;
    private TextView tv_center;
    private ListView list;
    private TextView tv_complete, tv_head;
    private SmzyBean smzyBean;
    private List<SmzyBean.RecordBean> beans = new ArrayList<>();
    private ZyAdapter mAdapter;
    private LayoutInflater mInflater;
    private int itemposition = -1;
    private int itemanswerpos = -1;

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
        return R.layout.activity_smzy;
    }

    @Override
    protected void setupViews() {
        mInflater = (LayoutInflater) self.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tv_left = (ImageView) findViewById(R.id.tv_left);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_head = (TextView) findViewById(R.id.tv_head);
        list = (ListView) findViewById(R.id.list);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);


    }

    @Override
    protected void initialized() {
        tv_center.setText("书写作业");
        getData();
    }

    private void initview() {
        if (smzyBean != null) {
            if (!TextUtils.isEmpty(smzyBean.getFriends())) {
                tv_head.setText(smzyBean.getFriends());
            }

            if (smzyBean.getRecord() != null && smzyBean.getRecord().size() > 0) {
                beans.addAll(smzyBean.getRecord());
            }
            mAdapter = new ZyAdapter();
            list.setAdapter(mAdapter);

            showComplete();
        }

    }


    private void showComplete()
    {
        boolean isComplete = true;
        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i).getPass() != 1) {
                isComplete = false;
                break;
            }
        }
        tv_complete.setVisibility(isComplete ? View.VISIBLE : View.GONE);
    }

    private void getData() {
        ShidaiApi.getSmzy(self, Config.getShidaiUserInfo().getUserid(), SmzyBean.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                    smzyBean = (SmzyBean) rspData;
                    initview();
                } else {
                    tv_head.setText(rspData.getMessage());
                }
            }

            @Override
            public void failed(String msg) {
                Toast.makeText(self, msg, Toast.LENGTH_LONG).show();
            }
        });
    }


    class ZyAdapter extends BaseAdapter {
        public ZyAdapter() {

        }

        @Override
        public int getCount() {
            return beans.size();
        }

        @Override
        public SmzyBean.RecordBean getItem(int position) {
            return beans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_smzy, null);
                holder = new ViewHolder();
                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                holder.tv_no = (TextView) convertView.findViewById(R.id.tv_no);
                holder.iv_submit = (ImageView) convertView.findViewById(R.id.iv_submit);
                holder.ll_main = (LinearLayout) convertView.findViewById(R.id.ll_main);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final SmzyBean.RecordBean bean = getItem(position);
            if (!TextUtils.isEmpty(bean.getContent())) {
                holder.tv_content.setText(bean.getContent());
            }
            holder.tv_no.setText((position+1) + ". ");
            if (bean.getPass() == 1) {// TODO 已经提交的状态
                holder.ll_main.setBackgroundColor(getResources().getColor(R.color.green_light_bg));
                holder.iv_submit.setImageResource(R.drawable.write_btn_right);
                String[] answer = bean.getAnswer().split(",");
                String content = bean.getContent().replace("[BlankArea]", "^");
                for (int i = 0; i < answer.length; i++) {
                    if (content.indexOf("^") != -1)
                        content = content.substring(0, content.indexOf("^")) + "<font  color='#479A53'><u>  " + answer[i] + "  </u></font>" + content.substring(content.indexOf("^") + 1, content.length());

                }
                holder.tv_content.setText(Html.fromHtml(content));

            } else if (bean.getPass() == 0) {// TODO 正常的状态
                SpannableStringBuilder mSpanText = new SpannableStringBuilder("");
                holder.ll_main.setBackgroundColor(getResources().getColor(R.color.white));
                holder.iv_submit.setImageResource(R.drawable.write_btn_enter);
                final String[] answer = bean.getAnswer().split(",");
                List<String> subbuffter = new ArrayList<>();
                final HashMap<Integer, String> temp;
                if (bean.getTemp() != null)
                    temp = bean.getTemp();
                else {
                    temp = new HashMap<>();
                    bean.setTemp(temp);
                }

                String content = bean.getContent().replace("[BlankArea]", "^");
                for (int i = 0; i < answer.length; i++) {
                    if (content.indexOf("^") != -1) {

                        int textpos = content.indexOf("^");

                        subbuffter.add(content.substring(0, textpos + 1));
                        content = content.substring(textpos + 1, content.length());
                    }
                }

                for (int i = 0; i < subbuffter.size(); i++) {
                    final int answerpos = i;
                    String subcontent = subbuffter.get(i);
                    int textpos = subcontent.indexOf("^");
                    final String temptxt = temp.get(i);
                    if (!TextUtils.isEmpty(temptxt)) {
                        subcontent = subcontent.replace("^", temptxt);
                    } else {
                        subcontent = subcontent.replace("^", "______");
                    }

                    SpannableStringBuilder spanText = new SpannableStringBuilder(subcontent);
                    spanText.setSpan(new ClickableSpan() {

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            if (position == itemposition && answerpos == itemanswerpos)
                                ds.setColor(getResources().getColor(R.color.baby_blue));       //设置文件颜色
                            else
                                ds.setColor(getResources().getColor(R.color.text_light_dark));       //设置文件颜色
                            ds.setUnderlineText(true);
                            //设置下划线
                        }

                        @Override
                        public void onClick(View view) {
                            Log.e("n", "点了");
                            SendWordDialog dialog = new SendWordDialog(self, temptxt, new SendWordDialog.CallBack() {
                                @Override
                                public void onBack(String word) {
                                    bean.getTemp().put(answerpos, word);
                                    notifyDataSetChanged();
                                }
                            });
                            dialog.show();

                            itemposition = position;
                            itemanswerpos = answerpos;
                            notifyDataSetChanged();

//                            showToast(temptxt + answerpos);
                        }
                    }, textpos, subcontent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mSpanText.append(spanText);
                }
                mSpanText.append(content);
                holder.tv_content.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
                holder.tv_content.setText(mSpanText);
                holder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
                holder.iv_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemposition = -1;
                        itemanswerpos = -1;
                        if (isRight(temp, answer)) {
                            ZyRight(bean.getId() + "");
                            bean.setPass(1);
                        } else {
                            bean.setPass(2);
                        }
                        notifyDataSetChanged();
                        showComplete();
                    }
                });
            } else {// TODO 提交错误状态的状态
                SpannableStringBuilder mSpanText = new SpannableStringBuilder("");
                holder.ll_main.setBackgroundColor(getResources().getColor(R.color.red_light_bg));
                holder.iv_submit.setImageResource(R.drawable.write_btn_enter);
                final String[] answer = bean.getAnswer().split(",");
                List<String> subbuffter = new ArrayList<>();
                final HashMap<Integer, String> temp;
                if (bean.getTemp() != null)
                    temp = bean.getTemp();
                else {
                    temp = new HashMap<>();
                    bean.setTemp(temp);
                }

                String content = bean.getContent().replace("[BlankArea]", "^");
                for (int i = 0; i < answer.length; i++) {
                    if (content.indexOf("^") != -1) {

                        int textpos = content.indexOf("^");

                        subbuffter.add(content.substring(0, textpos + 1));
                        content = content.substring(textpos + 1, content.length());
                    }
                }

                for (int i = 0; i < subbuffter.size(); i++) {
                    final int answerpos = i;
                    String subcontent = subbuffter.get(i);
                    int textpos = subcontent.indexOf("^");
                    final String temptxt = temp.get(i);
                    if (!TextUtils.isEmpty(temptxt)) {
                        subcontent = subcontent.replace("^", temptxt);
                    } else {
                        subcontent = subcontent.replace("^", "______");
                    }

                    SpannableStringBuilder spanText = new SpannableStringBuilder(subcontent);
                    spanText.setSpan(new ClickableSpan() {

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            if (position == itemposition && answerpos == itemanswerpos)
                                ds.setColor(getResources().getColor(R.color.baby_blue));       //设置文件颜色
                            else
                                ds.setColor(getResources().getColor(R.color.red_text));       //设置文件颜色
                            ds.setUnderlineText(true);
                            //设置下划线
                        }

                        @Override
                        public void onClick(View view) {
                            Log.e("n", "点了");
                            SendWordDialog dialog = new SendWordDialog(self, temptxt, new SendWordDialog.CallBack() {
                                @Override
                                public void onBack(String word) {
                                    bean.getTemp().put(answerpos, word);
                                    notifyDataSetChanged();
                                }
                            });
                            dialog.show();
                            itemposition = position;
                            itemanswerpos = answerpos;
                            notifyDataSetChanged();
//                            showToast(temptxt + answerpos);
                        }
                    }, textpos, subcontent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mSpanText.append(spanText);
                }
                mSpanText.append(content);
                holder.tv_content.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
                holder.tv_content.setText(mSpanText);
                holder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
                holder.iv_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemposition = -1;
                        itemanswerpos = -1;
                        if (isRight(temp, answer)) {
                            ZyRight(bean.getId() + "");
                            bean.setPass(1);
                            notifyDataSetChanged();
                        } else {
                            bean.setPass(2);

                        }
                        notifyDataSetChanged();
                        showComplete();

                    }
                });
            }


            return convertView;
        }

        class ViewHolder {
            public TextView tv_content;
            public TextView tv_no;
            public ImageView iv_submit;
            public LinearLayout ll_main;
        }
    }

    private boolean isRight(HashMap<Integer, String> temp, String[] answer) {
        boolean isright = true;
        for (int i = 0; i < answer.length; i++) {
            if (!answer[i].equals(temp.get(i)))
                isright = false;

        }

        return isright;
    }


    private void ZyRight(String id) {
        ShidaiApi.RightSmzy(self, Config.getShidaiUserInfo().getUserid(), id, SmzyBean.class, new NetUtils.NetCallBack<ServiceResult>() {
            @Override
            public void success(ServiceResult rspData) throws IOException, ClassNotFoundException {
                if ("0".equals(rspData.getErrcode())) {
                } else {
                }
            }

            @Override
            public void failed(String msg) {
                Toast.makeText(self, msg, Toast.LENGTH_LONG).show();
            }
        });
    }


}
