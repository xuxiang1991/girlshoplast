package com.daocheng.girlshop.video.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daocheng.girlshop.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Nathen
 * On 2016/04/22 00:54
 */
public class JCVideoPlayerStandardShowShareButtonAfterFullscreen extends JCVideoPlayerStandard {

  public ImageView shareButton;

  public JCVideoPlayerStandardShowShareButtonAfterFullscreen(Context context) {
    super(context);
  }

  public JCVideoPlayerStandardShowShareButtonAfterFullscreen(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void init(Context context) {
    super.init(context);
    shareButton = (ImageView) findViewById(R.id.share);
    shareButton.setOnClickListener(this);

  }

  @Override
  public int getLayoutId() {
    return R.layout.jc_layout_standard_with_share_button;
  }

  @Override
  public void onClick(View v) {
    super.onClick(v);
    if (v.getId() == R.id.share) {
      Toast.makeText(getContext(), "Whatever the icon means", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  protected void setStateAndUi(int state) {
    super.setStateAndUi(state);
    if (mIfCurrentIsFullscreen) {
      shareButton.setVisibility(View.VISIBLE);
    } else {
      shareButton.setVisibility(View.INVISIBLE);
    }
  }
}
