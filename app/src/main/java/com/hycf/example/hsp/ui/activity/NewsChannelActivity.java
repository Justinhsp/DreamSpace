package com.hycf.example.hsp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.hycf.example.hsp.R;
import com.hycf.example.hsp.adapter.ChannelAdapter;
import com.hycf.example.hsp.bean.NewsChannelTable;
import com.hycf.example.hsp.contract.NewsChannelContract;
import com.hycf.example.hsp.model.NewsChannelModel;
import com.hycf.example.hsp.presenter.NewsChannelPresenter;
import com.hycf.example.hsp.ui.weight.ItemDragHelperCallback;
import com.jaydenxiao.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 选择关注频道
 * Activity
 */
public class NewsChannelActivity extends BaseActivity<NewsChannelPresenter, NewsChannelModel> implements NewsChannelContract.View {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_mychannel)
    RecyclerView rvMychannel;
    @Bind(R.id.rv_more_channel)
    RecyclerView rvMoreChannel;

    private ChannelAdapter channelAdapterMine;
    private ChannelAdapter channelAdapterMore;


    /**
     * 返回对应布局文件
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_news_channel;
    }

    /**
     * 初始化Presenter
     */
    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    /**
     * 初始化UI
     */
    @Override
    public void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });
        mPresenter.lodeChannelsRequest();
    }


    /**
     * 我的频道
     *
     * @param newsChannelsMine
     */
    @Override
    public void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine) {
        channelAdapterMine = new ChannelAdapter(mContext,R.layout.item_news_channel);
        rvMychannel.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        rvMychannel.setItemAnimator(new DefaultItemAnimator());
        rvMychannel.setAdapter(channelAdapterMine);
        channelAdapterMine.replaceAll(newsChannelsMine);
        channelAdapterMine.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsChannelTable newsChannel = channelAdapterMine.get(position);
                channelAdapterMore.add(newsChannel);
                channelAdapterMine.removeAt(position);
                mPresenter.onItemAddOrRemove((ArrayList<NewsChannelTable>) channelAdapterMine.getAll(), (ArrayList<NewsChannelTable>)channelAdapterMore.getAll());

            }
        });
        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(channelAdapterMine);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(rvMychannel);
        channelAdapterMine.setItemDragHelperCallback(itemDragHelperCallback);
    }

    /**
     * 更多频道
     *
     * @param newsChannelsMore
     */
    @Override
    public void returnMoreNewsChannels(List<NewsChannelTable> newsChannelsMore) {
        channelAdapterMore = new ChannelAdapter(mContext,R.layout.item_news_channel);
        rvMoreChannel.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        rvMoreChannel.setItemAnimator(new DefaultItemAnimator());
        rvMoreChannel.setAdapter(channelAdapterMore);
        channelAdapterMore.replaceAll(newsChannelsMore);
        channelAdapterMore.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsChannelTable newsChannel = channelAdapterMore.get(position);
                channelAdapterMine.add(newsChannel);
                channelAdapterMore.removeAt(position);
                mPresenter.onItemAddOrRemove((ArrayList<NewsChannelTable>) channelAdapterMine.getAll(), (ArrayList<NewsChannelTable>)channelAdapterMore.getAll());
            }
        });
    }

    /**
     * 加载时
     *
     * @param title
     */
    @Override
    public void showLoading(String title) {

    }

    /**
     * 加载完成
     */
    @Override
    public void stopLoading() {

    }

    /**
     * 加载失败
     *
     * @param msg
     */
    @Override
    public void showErrorTip(String msg) {

    }

    /**
     * Activity入口
     *
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, NewsChannelActivity.class);
        context.startActivity(intent);
    }

}
