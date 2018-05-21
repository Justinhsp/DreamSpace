package com.hycf.example.hsp.presenter;

import com.hycf.example.hsp.bean.NewsChannelTable;
import com.hycf.example.hsp.constant.AppConstant;
import com.hycf.example.hsp.contract.NewsContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

import rx.functions.Action1;

/**
 * 新闻presenter
 */
public class NewsPresenter extends NewsContract.Presenter {

    /**
     * 重新onStart方法
     * 监听新闻频道变化刷新
     */
    @Override
    public void onStart() {
        super.onStart();
        mRxManage.on(AppConstant.NEWS_CHANNEL_CHANGED, new Action1<List<NewsChannelTable>>() {
            @Override
            public void call(List<NewsChannelTable> newsChannelTables) {
                if (newsChannelTables != null) {
                    mView.returnMineNewsChannels(newsChannelTables);
                }
            }
        });
    }

    /**
     * News数据
     * 新闻种类
     */
    @Override
    public void lodeMineChannelsRequest() {
        mRxManage.add(mModel.lodeMineNewsChannels().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext, false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                mView.returnMineNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
