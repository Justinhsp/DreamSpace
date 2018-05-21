package com.hycf.example.hsp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.hycf.example.hsp.R;
import com.hycf.example.hsp.bean.NewsChannelTable;
import com.hycf.example.hsp.constant.AppConstant;
import com.hycf.example.hsp.contract.NewsContract;
import com.hycf.example.hsp.model.NewsModel;
import com.hycf.example.hsp.presenter.NewsPresenter;
import com.hycf.example.hsp.ui.activity.NewsChannelActivity;
import com.hycf.example.hsp.utils.MyUtils;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.base.BaseFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 新闻首页Fragment
 */
public class NewsFragment extends BaseFragment<NewsPresenter, NewsModel> implements NewsContract.View {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.news_tablayout)
    TabLayout newsTablayout;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.news_fab)
    FloatingActionButton newsFab;

    private BaseFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        mPresenter.lodeMineChannelsRequest();
    }


    /**
     * 获取新闻数据
     *
     * @param newsChannelMine
     */
    @Override
    public void returnMineNewsChannels(List<NewsChannelTable> newsChannelMine) {
        if (newsChannelMine != null) {
            List<String> channelNames = new ArrayList<>();
            List<Fragment> mNewsFragmentList = new ArrayList<>();
            for (int i = 0; i < newsChannelMine.size(); i++) {
                channelNames.add(newsChannelMine.get(i).getNewsChannelName());
                mNewsFragmentList.add(createListFragments(newsChannelMine.get(i)));
            }
            if (fragmentAdapter == null) {
                fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, channelNames);
            } else {
                //刷新fragment
                fragmentAdapter.setFragments(getChildFragmentManager(), mNewsFragmentList, channelNames);
            }
            viewPager.setAdapter(fragmentAdapter);
            newsTablayout.setupWithViewPager(viewPager);
            MyUtils.dynamicSetTabLayoutMode(newsTablayout);
            setPageChangeListener();
        }
    }


    /**
     * 子fragment
     *
     * @param newsChannel
     * @return
     */
    private NewsTabFragment createListFragments(NewsChannelTable newsChannel) {
        NewsTabFragment fragment = new NewsTabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.NEWS_ID, newsChannel.getNewsChannelId());
        bundle.putString(AppConstant.NEWS_TYPE, newsChannel.getNewsChannelType());
        bundle.putInt(AppConstant.CHANNEL_POSITION, newsChannel.getNewsChannelIndex());
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Viewpager滑动监听
     */
    private void setPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @OnClick({R.id.iv_add, R.id.news_fab})
    public void OnClcik(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                NewsChannelActivity.startAction(getContext());
                break;
            case R.id.news_fab:
                mRxManager.post(AppConstant.NEWS_LIST_TO_TOP, "");
                break;
            default:
                break;
        }
    }


    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }


}
