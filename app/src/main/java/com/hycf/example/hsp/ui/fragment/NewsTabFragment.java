package com.hycf.example.hsp.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.animation.ScaleInAnimation;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.hycf.example.hsp.R;
import com.hycf.example.hsp.adapter.NewsTabAdapter;
import com.hycf.example.hsp.bean.NewsSummary;
import com.hycf.example.hsp.constant.AppConstant;
import com.hycf.example.hsp.contract.NewsTabContract;
import com.hycf.example.hsp.model.NewsTabModel;
import com.hycf.example.hsp.presenter.NewsTabPresenter;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 新闻子Fragment
 */
public class NewsTabFragment extends BaseFragment<NewsTabPresenter, NewsTabModel> implements NewsTabContract.View, OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.rv_tab_news)
    IRecyclerView rvTabNews;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;

    private NewsTabAdapter mAdapter;
    private List<NewsSummary> datas = new ArrayList<>();
    private String mNewsId;
    private String mNewsType;
    private int mStartPage = 0;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    private boolean isVisible;


    /**
     * 返回对应布局文件
     *
     * @return
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news_tab;
    }

    /**
     * 返回对应presenter
     */
    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    /**
     * 初始化UI
     */
    @Override
    protected void initView() {
        if (getArguments() != null) {
            mNewsId = getArguments().getString(AppConstant.NEWS_ID);
            mNewsType = getArguments().getString(AppConstant.NEWS_TYPE);
        }
        rvTabNews.setLayoutManager(new LinearLayoutManager(getContext()));
        datas.clear();
        mAdapter = new NewsTabAdapter(getContext(), datas);
        mAdapter.openLoadAnimation(new ScaleInAnimation());
        rvTabNews.setAdapter(mAdapter);
        rvTabNews.setOnRefreshListener(this);
        rvTabNews.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if (mAdapter.getSize() <= 0) {
            mStartPage = 0;
            mPresenter.getNewsListDataRequest(mNewsType, mNewsId, mStartPage);
        }
    }

    /**
     * 返回新闻数据集合
     *
     * @param newsSummaries
     */
    @Override
    public void returnNewsListData(List<NewsSummary> newsSummaries) {
        if (newsSummaries != null) {
            mStartPage += 20;
            if (mAdapter.getPageBean().isRefresh()) {
                rvTabNews.setRefreshing(false);
                mAdapter.replaceAll(newsSummaries);
            } else {
                if (newsSummaries.size() > 0) {
                    rvTabNews.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    mAdapter.addAll(newsSummaries);
                } else {
                    rvTabNews.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    /**
     * 置顶
     */
    @Override
    public void scrolltoTop() {
        rvTabNews.smoothScrollToPosition(0);
    }

    /**
     * 加载时
     *
     * @param title
     */
    @Override
    public void showLoading(String title) {
        if (mAdapter.getPageBean().isRefresh()) {
            if (mAdapter.getSize() <= 0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }
    }

    /**
     * 加载完成
     */
    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    /**
     * 加载失败
     *
     * @param msg
     */
    @Override
    public void showErrorTip(String msg) {
        if (mAdapter.getPageBean().isRefresh()) {
            if (mAdapter.getSize() <= 0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadedTip.setTips(msg);
            }
            rvTabNews.setRefreshing(false);
        } else {
            rvTabNews.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        rvTabNews.setRefreshing(true);
        mPresenter.getNewsListDataRequest(mNewsType, mNewsId, mStartPage);
    }

    /**
     * 上拉加载更多
     *
     * @param loadMoreView
     */
    @Override
    public void onLoadMore(View loadMoreView) {
        mAdapter.getPageBean().setRefresh(false);
        //发起请求
        rvTabNews.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getNewsListDataRequest(mNewsType, mNewsId, mStartPage);
    }
}
