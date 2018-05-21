package com.hycf.example.hsp.presenter;

import com.hycf.example.hsp.R;
import com.hycf.example.hsp.bean.NewsDetail;
import com.hycf.example.hsp.contract.NewsDetailContract;
import com.jaydenxiao.common.baserx.RxSubscriber;
import com.jaydenxiao.common.commonutils.ToastUitl;

/**
 * 新闻详情Presenter
 */
public class NewsDetailPresenter extends NewsDetailContract.Presenter {
    @Override
    public void getOneNewsDataRequest(String postId) {
        mRxManage.add(mModel.getOneNewsData(postId).subscribe(new RxSubscriber<NewsDetail>(mContext) {
            @Override
            protected void _onNext(NewsDetail newsDetail) {
                mView.returnOneNewsData(newsDetail);
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showToastWithImg(message, R.drawable.ic_wrong);
            }
        }));
    }
}
