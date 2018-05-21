package com.hycf.example.hsp.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.hycf.example.hsp.R;
import com.hycf.example.hsp.bean.NewsPhotoDetail;
import com.hycf.example.hsp.constant.AppConstant;
import com.hycf.example.hsp.ui.fragment.BelleDetailFragment;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.base.BaseFragmentAdapter;
import com.jaydenxiao.common.commonwidget.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 图文新闻详情
 */
public class NewsDetailPhotoActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPagerFixed viewpager;
    @Bind(R.id.photo_detail_title_tv)
    TextView photoDetailTitleTv;

    private List<Fragment> mPhotoDetailFragmentList = new ArrayList<>();
    private NewsPhotoDetail mNewsPhotoDetail;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail_photo;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mNewsPhotoDetail = getIntent().getParcelableExtra(AppConstant.PHOTO_DETAIL);
        createFragment(mNewsPhotoDetail);
        initViewPager();
        setPhotoDetailTitle(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void createFragment(NewsPhotoDetail newsPhotoDetail) {
        mPhotoDetailFragmentList.clear();
        for (NewsPhotoDetail.Picture picture : newsPhotoDetail.getPictures()) {
            BelleDetailFragment fragment = new BelleDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.PHOTO_DETAIL_IMGSRC, picture.getImgSrc());
            fragment.setArguments(bundle);
            mPhotoDetailFragmentList.add(fragment);
        }
    }


    private void initViewPager() {
        BaseFragmentAdapter photoPagerAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mPhotoDetailFragmentList);
        viewpager.setAdapter(photoPagerAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPhotoDetailTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void setPhotoDetailTitle(int position) {
        String title = getTitle(position);
        photoDetailTitleTv.setText(getString(R.string.photo_detail_title, position + 1,
                mPhotoDetailFragmentList.size(), title));
    }

    private String getTitle(int position) {
        String title = mNewsPhotoDetail.getPictures().get(position).getTitle();
        if (title == null) {
            title = mNewsPhotoDetail.getTitle();
        }
        return title;
    }



    @Override
    protected void onStart() {
        super.onStart();
        //监听图片tab点击
        mRxManager.on(AppConstant.PHOTO_TAB_CLICK, new Action1<Object>() {

            @Override
            public void call(Object o) {
                if (photoDetailTitleTv.getVisibility() == View.VISIBLE) {
                    startAnimation(View.GONE, 0.9f, 0.5f);
                } else {
                    photoDetailTitleTv.setVisibility(View.VISIBLE);
                    startAnimation(View.VISIBLE, 0.5f, 0.9f);
                }
            }
        });
    }

    private void startAnimation(final int endState, float startValue, float endValue) {
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(photoDetailTitleTv, "alpha", startValue, endValue)
                .setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                photoDetailTitleTv.setVisibility(endState);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }


    /**
     * 入口
     *
     * @param context
     * @param mNewsPhotoDetail
     */
    public static void startAction(Context context, NewsPhotoDetail mNewsPhotoDetail) {
        Intent intent = new Intent(context, NewsDetailPhotoActivity.class);
        intent.putExtra(AppConstant.PHOTO_DETAIL, mNewsPhotoDetail);
        context.startActivity(intent);
    }

}
