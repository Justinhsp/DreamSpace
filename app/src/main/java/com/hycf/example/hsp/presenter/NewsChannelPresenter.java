package com.hycf.example.hsp.presenter;

import com.hycf.example.hsp.bean.NewsChannelTable;
import com.hycf.example.hsp.constant.AppConstant;
import com.hycf.example.hsp.contract.NewsChannelContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择频道Presenter
 */
public class NewsChannelPresenter extends NewsChannelContract.Presenter {
    /**
     * 全部频道
     */
    @Override
    public void lodeChannelsRequest() {
        mRxManage.add(mModel.lodeMineNewsChannels().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext, false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                mView.returnMineNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
        mRxManage.add(mModel.lodeMoreNewsChannels().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext, false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                mView.returnMoreNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    /**
     * 频道变换
     * @param newsChannelTableList
     * @param fromPosition
     * @param toPosition
     */
    @Override
    public void onItemSwap(final ArrayList<NewsChannelTable> newsChannelTableList, int fromPosition, int toPosition) {
        mRxManage.add(mModel.swapDb(newsChannelTableList, fromPosition, toPosition).subscribe(new RxSubscriber<String>(mContext, false) {
            @Override
            protected void _onNext(String s) {
                mRxManage.post(AppConstant.NEWS_CHANNEL_CHANGED, newsChannelTableList);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    /**
     * 移除并且添加频道
     * @param mineChannelTableList
     * @param moreChannelTableList
     */
    @Override
    public void onItemAddOrRemove(final ArrayList<NewsChannelTable> mineChannelTableList, ArrayList<NewsChannelTable> moreChannelTableList) {
        mRxManage.add(mModel.updateDb(mineChannelTableList, moreChannelTableList).subscribe(new RxSubscriber<String>(mContext, false) {
            @Override
            protected void _onNext(String s) {
                mRxManage.post(AppConstant.NEWS_CHANNEL_CHANGED, mineChannelTableList);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
