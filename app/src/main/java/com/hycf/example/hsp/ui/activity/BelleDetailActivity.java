package com.hycf.example.hsp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hycf.example.hsp.R;
import com.hycf.example.hsp.constant.AppConstant;
import com.hycf.example.hsp.ui.weight.PullBackLayout;
import com.hycf.example.hsp.utils.SystemUiVisibilityUtil;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonwidget.StatusBarCompat;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片查看器
 */
public class BelleDetailActivity extends BaseActivity implements PullBackLayout.Callback {
    @Bind(R.id.photo_touch_iv)
    PhotoView photoTouchIv;
    @Bind(R.id.pull_back_layout)
    PullBackLayout pullBackLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.background)
    RelativeLayout background;


    private boolean mIsToolBarHidden;
    private boolean mIsStatusBarHidden;
    private ColorDrawable mBackground;

    /**
     * Activity入口
     *
     * @param context
     * @param url
     */
    public static void startAction(Context context, String url) {
        Intent intent = new Intent(context, BelleDetailActivity.class);
        intent.putExtra(AppConstant.PHOTO_DETAIL, url);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_belle_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        //初始化透明状态栏
        StatusBarCompat.translucentStatusBar(this);
        //下滑关闭图片
        pullBackLayout.setCallback(this);
        toolBarFadeIn();
        //初始化toolbar
        toolbar.setTitle(getString(R.string.girl));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //加载图片
        String url = getIntent().getStringExtra(AppConstant.PHOTO_DETAIL);
        Glide.with(this).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                .crossFade().into(photoTouchIv);
        //点击图片
        setPhotoViewClickEvent();
    }

    private void setPhotoViewClickEvent() {
        photoTouchIv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                    toolbar.animate()
                            .alpha(mIsToolBarHidden ? 1.0f : 0.0f)
                            .setInterpolator(new DecelerateInterpolator(2))
                            .start();
                    mIsToolBarHidden = !mIsToolBarHidden;
                if (mIsStatusBarHidden) {
                    SystemUiVisibilityUtil.enter(BelleDetailActivity.this);
                } else {
                    SystemUiVisibilityUtil.exit(BelleDetailActivity.this);
                }
                mIsStatusBarHidden = !mIsStatusBarHidden;
            }
        });
    }

    private void toolBarFadeIn() {
        mIsToolBarHidden = true;
        toolbar.animate()
                .alpha(mIsToolBarHidden ? 1.0f : 0.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsToolBarHidden = !mIsToolBarHidden;
    }


    /**
     * 下滑关闭图片
     */
    @Override
    public void onPullStart() {
        mIsToolBarHidden = false;
        toolbar.animate()
                .alpha(mIsToolBarHidden ? 1.0f : 0.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsToolBarHidden = !mIsToolBarHidden;
        mIsStatusBarHidden = true;
        if (mIsStatusBarHidden) {
            SystemUiVisibilityUtil.enter(BelleDetailActivity.this);
        } else {
            SystemUiVisibilityUtil.exit(BelleDetailActivity.this);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f, progress * 3f);
        mBackground.setAlpha((int) (0xff/*255*/ * (1f - progress)));
    }

    @Override
    public void onPullCancel() {
        toolBarFadeIn();
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    /**
     * 加载完成回调
     */
    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }
}
