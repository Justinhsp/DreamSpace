package com.hycf.example.hsp.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hycf.example.hsp.R;
import com.hycf.example.hsp.bean.VideoData;
import com.hycf.example.hsp.constant.AppConstant;
import com.hycf.example.hsp.contract.VideoContract;
import com.hycf.example.hsp.model.VideoModel;
import com.hycf.example.hsp.presenter.VideoPresenter;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonwidget.LoadingTip;

import java.util.List;

import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 视频子fragment
 */
public class VideoTabFragment extends BaseFragment<VideoPresenter,VideoModel> implements VideoContract.View, OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.rv_video)
    IRecyclerView rvVideo;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;

    private CommonRecycleViewAdapter<VideoData> mAdapter;

    //视频类型
    private String mVideoType;
    //初始页数
    private int mStartPage=0;

    /**
     * 返回对应布局文件
     * @return
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_video_tabs;
    }

    /**
     * 返回对应Prsenter
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
        if (getArguments() != null) {
            mVideoType = getArguments().getString(AppConstant.VIDEO_TYPE);
        }
        rvVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter =new CommonRecycleViewAdapter<VideoData>(getContext(),R.layout.item_video_list) {
            @Override
            public void convert(ViewHolderHelper helper, VideoData videoData) {
                helper.setImageRoundUrl(R.id.iv_logo,videoData.getTopicImg());
                helper.setText(R.id.tv_from,videoData.getTopicName());
                helper.setText(R.id.tv_play_time,String.format(getResources().getString(R.string.video_play_times), String.valueOf(videoData.getPlayCount())));
                JCVideoPlayerStandard jcVideoPlayerStandard=helper.getView(R.id.videoplayer);
                boolean setUp = jcVideoPlayerStandard.setUp(
                        videoData.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        TextUtils.isEmpty(videoData.getDescription())?videoData.getTitle()+"":videoData.getDescription());
                if (setUp) {
                    Glide.with(mContext).load(videoData.getCover())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                            .crossFade().into(jcVideoPlayerStandard.thumbImageView);
                }
            }
        };
        rvVideo.setAdapter(mAdapter);
        rvVideo.setOnRefreshListener(this);
        rvVideo.setOnLoadMoreListener(this);
        //视频监听
        rvVideo.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }
            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (JCVideoPlayerManager.listener() != null) {
                    JCVideoPlayer videoPlayer = (JCVideoPlayer) JCVideoPlayerManager.listener();
                    if (((ViewGroup) view).indexOfChild(videoPlayer) != -1 && videoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                        JCVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });
        //数据为空才重新发起请求
        if(mAdapter.getSize()<=0) {
            //发起请求
            mStartPage=0;
            mPresenter.getVideosListDataRequest(mVideoType, mStartPage);
        }
    }

    /**
     * 重写onPause
     *  默认暂停播放视频
     */
    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }



    /**
     * 视频 数据 集合
     * @param videoDatas
     */
    @Override
    public void returnVideosListData(List<VideoData> videoDatas) {
        if (videoDatas != null) {
            mStartPage += 1;
            if (mAdapter.getPageBean().isRefresh()) {
                rvVideo.setRefreshing(false);
                mAdapter.replaceAll(videoDatas);
            } else {
                if (videoDatas.size() > 0) {
                    rvVideo.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    mAdapter.addAll(videoDatas);
                } else {
                    rvVideo.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    /**
     * 置顶
     */
    @Override
    public void scrolltoTop() {
         rvVideo.smoothScrollToPosition(0);
    }

    /**
     * 加载时
     * @param title
     */
    @Override
    public void showLoading(String title) {
        if( mAdapter.getPageBean().isRefresh()) {
            if(mAdapter.getSize()<=0) {
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
     * @param msg
     */
    @Override
    public void showErrorTip(String msg) {
        if( mAdapter.getPageBean().isRefresh()) {
            if(mAdapter.getSize()<=0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadedTip.setTips(msg);
                rvVideo.setRefreshing(false);
            }
        }else{
            rvVideo.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
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
        rvVideo.setRefreshing(true);
        mPresenter.getVideosListDataRequest(mVideoType, mStartPage);
    }

    /**
     * 上拉加载
     * @param loadMoreView
     */
    @Override
    public void onLoadMore(View loadMoreView) {
        mAdapter.getPageBean().setRefresh(false);
        //发起请求
        rvVideo.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getVideosListDataRequest(mVideoType, mStartPage);
    }
}
