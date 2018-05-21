package com.hycf.example.hsp.ui.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hycf.example.hsp.R;
import com.hycf.example.hsp.ui.activity.AboutActivity;
import com.hycf.example.hsp.ui.activity.CircleFriendActivity;
import com.hycf.example.hsp.ui.activity.WeChatActivity;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.WaveView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 空间Fragment
 */
public class SpaceFragment extends BaseFragment {
    @Bind(R.id.wave_view)
    WaveView waveView;
    @Bind(R.id.img_logo)
    ImageView imgLogo;
    @Bind(R.id.ll_friend_zone)
    LinearLayout llFriendZone;
    @Bind(R.id.ll_daynight_toggle)
    LinearLayout llDaynightToggle;
    @Bind(R.id.ll_about)
    LinearLayout llAbout;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_space;
    }


    @Override
    public void initPresenter() {

    }

    /**
     * 初始化UI
     */
    @Override
    protected void initView() {
        ImageLoaderUtils.displayRound(getContext(),imgLogo,R.drawable.justhsp_logo);
        //设置头像随着波浪背景滚动
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
        lp.gravity = Gravity.CENTER;
        waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0, 0, 0, (int) y + 2);
                imgLogo.setLayoutParams(lp);
            }
        });
    }


    @OnClick({R.id.ll_friend_zone,R.id.ll_wechat_circle, R.id.ll_daynight_toggle, R.id.ll_about})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ll_friend_zone:
                CircleFriendActivity.startAction(getContext());
                break;
            case R.id.ll_wechat_circle:
                WeChatActivity.startAction(getContext());
                break;
            case R.id.ll_daynight_toggle:
                ToastUitl.showToastWithImg(getString(R.string.toast_expect),R.drawable.ic_success);
                break;
            case R.id.ll_about:
                AboutActivity.startAction(getContext());
                break;
                default:
                    break;
        }
    }

}
