package com.hycf.example.hsp.ui.activity;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hycf.example.hsp.R;
import com.hycf.example.hsp.bean.TabEntity;
import com.hycf.example.hsp.constant.AppConstant;
import com.hycf.example.hsp.ui.fragment.BelleFragment;
import com.hycf.example.hsp.ui.fragment.NewsFragment;
import com.hycf.example.hsp.ui.fragment.SpaceFragment;
import com.hycf.example.hsp.ui.fragment.VideoFragment;
import com.jaydenxiao.common.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {
    @Bind(R.id.tab_layout)
    CommonTabLayout tabLayout;

     //底部标题
    private String[] mTitles = {"新闻", "美女","视频","动态"};
    //底部Icon
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal,R.mipmap.ic_girl_normal,R.mipmap.ic_video_normal,R.mipmap.ic_care_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected,R.mipmap.ic_girl_selected, R.mipmap.ic_video_selected,R.mipmap.ic_care_selected};
    //存放Tab标题与Icon的集合
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private NewsFragment newsFragment;
    private BelleFragment belleFragment;
    private VideoFragment videoFragment;
    private SpaceFragment spaceFragment;
    //tablayout的高度
    private static int tabLayoutHeight;


    /**
     * 这里重写oncreate方法
     * 来初始化fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
        tabLayout.measure(0,0);
        tabLayoutHeight=tabLayout.getMeasuredHeight();
        //监听底部菜单显示或隐藏
        mRxManager.on(AppConstant.MENU_SHOW_HIDE, new Action1<Boolean>() {
            @Override
            public void call(Boolean hideOrShow) {
                startAnimation(hideOrShow);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        //初始化菜单
         initTab();
    }

    /**
     * 初始化Tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 切换fragment
     * @param position
     */
    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //新闻
            case 0:
                transaction.hide(belleFragment);
                transaction.hide(videoFragment);
                transaction.hide(spaceFragment);
                transaction.show(newsFragment);
                transaction.commitAllowingStateLoss();
                break;
            //美女
            case 1:
                transaction.hide(newsFragment);
                transaction.hide(videoFragment);
                transaction.hide(spaceFragment);
                transaction.show(belleFragment);
                transaction.commitAllowingStateLoss();
                break;
            //视频
            case 2:
                transaction.hide(newsFragment);
                transaction.hide(belleFragment);
                transaction.hide(spaceFragment);
                transaction.show(videoFragment);
                transaction.commitAllowingStateLoss();
                break;
            //空间
            case 3:
                transaction.hide(newsFragment);
                transaction.hide(belleFragment);
                transaction.hide(videoFragment);
                transaction.show(spaceFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化Fragment
     * @param savedInstanceState
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentByTag("newsFragment");
            belleFragment = (BelleFragment) getSupportFragmentManager().findFragmentByTag("belleFragment");
            videoFragment = (VideoFragment) getSupportFragmentManager().findFragmentByTag("videoFragment");
            spaceFragment = (SpaceFragment) getSupportFragmentManager().findFragmentByTag("spaceFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            newsFragment = new NewsFragment();
            belleFragment = new BelleFragment();
            videoFragment = new VideoFragment();
            spaceFragment = new SpaceFragment();

            transaction.add(R.id.fl_body, newsFragment, "newsFragment");
            transaction.add(R.id.fl_body, belleFragment, "belleFragment");
            transaction.add(R.id.fl_body, videoFragment, "videoFragment");
            transaction.add(R.id.fl_body, spaceFragment, "spaceFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }


    /**
     * 菜单显示隐藏动画
      * @param hideOrShow
     */
    private void startAnimation(Boolean hideOrShow) {
        final ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        if(!hideOrShow){
            valueAnimator = ValueAnimator.ofInt(tabLayoutHeight, 0);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 1, 0);
        }else{
            valueAnimator = ValueAnimator.ofInt(0, tabLayoutHeight);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 0, 1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height= (int) valueAnimator.getAnimatedValue();
                tabLayout.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator,alpha);
        animatorSet.start();
    }

}
