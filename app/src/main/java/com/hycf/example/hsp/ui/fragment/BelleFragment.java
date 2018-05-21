package com.hycf.example.hsp.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hycf.example.hsp.R;
import com.hycf.example.hsp.bean.PhotoGirl;
import com.hycf.example.hsp.contract.BelleContract;
import com.hycf.example.hsp.model.BelleModel;
import com.hycf.example.hsp.presenter.BellePresenter;
import com.hycf.example.hsp.ui.activity.BelleDetailActivity;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;

import java.util.List;

import butterknife.Bind;

/**
 * 美女Fragment
 */
public class BelleFragment extends BaseFragment<BellePresenter, BelleModel> implements BelleContract.View, OnLoadMoreListener, OnRefreshListener {
    @Bind(R.id.titlebar)
    NormalTitleBar titlebar;
    @Bind(R.id.rv_belle)
    IRecyclerView rvBelle;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    @Bind(R.id.fab_belle)
    FloatingActionButton fabBelle;

    private CommonRecycleViewAdapter<PhotoGirl> mAdapter;
    private static int SIZE = 30;
    private int mStartPage = 1;

    /**
     * 返回对应布局文件
     * @return
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_belle;
    }

    /**
     * 返回对应presenter
     */
    @Override
    public void initPresenter() {
           mPresenter.setVM(this,mModel);
    }

    /**
     * 初始化UI
     */
    @Override
    protected void initView() {
        titlebar.setTvLeftVisiable(false);
        titlebar.setTitleText(getString(R.string.girl_title));
        mAdapter=new CommonRecycleViewAdapter<PhotoGirl>(getContext(),R.layout.item_belle) {
            @Override
            public void convert(ViewHolderHelper helper, final PhotoGirl photoGirl) {
                ImageView imageView=helper.getView(R.id.iv_photo);
                Glide.with(mContext).load(photoGirl.getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading)
                        .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                        .centerCrop().override(1090, 1090*3/4)
                        .crossFade().into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BelleDetailActivity.startAction(mContext,photoGirl.getUrl());
                    }
                });
            }
        };
        rvBelle.setAdapter(mAdapter);
        rvBelle.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        //上拉加载
        rvBelle.setOnLoadMoreListener(this);
        //下拉刷新
        rvBelle.setOnRefreshListener(this);
        fabBelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvBelle.smoothScrollToPosition(0);
            }
        });
        mPresenter.getPhotoListDataRequest(SIZE, mStartPage);
    }

    /**
     * 返回图片集合
     * @param photoGirls
     */
    @Override
    public void returnPhotoListData(List<PhotoGirl> photoGirls) {
        if (photoGirls != null) {
            mStartPage +=1;
            if (mAdapter.getPageBean().isRefresh()) {
                rvBelle.setRefreshing(false);
                mAdapter.replaceAll(photoGirls);
            } else {
                if (photoGirls.size() > 0) {
                    rvBelle.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    mAdapter.addAll(photoGirls);
                } else {
                    rvBelle.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    /**
     * 加载时
     * @param title
     */
    @Override
    public void showLoading(String title) {
        if(mAdapter.getPageBean().isRefresh())
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
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
     * @param msg
     */
    @Override
    public void showErrorTip(String msg) {
        if( mAdapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
            rvBelle.setRefreshing(false);
        }else{
            rvBelle.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    /**
     * 上拉加载
     * @param loadMoreView
     */
    @Override
    public void onLoadMore(View loadMoreView) {
        mAdapter.getPageBean().setRefresh(false);
        //发起请求
        rvBelle.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getPhotoListDataRequest(SIZE, mStartPage);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        rvBelle.setRefreshing(true);
        mPresenter.getPhotoListDataRequest(SIZE, mStartPage);
    }
}
