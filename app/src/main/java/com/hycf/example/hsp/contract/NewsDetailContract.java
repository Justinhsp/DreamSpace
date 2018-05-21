package com.hycf.example.hsp.contract;

import com.hycf.example.hsp.bean.NewsDetail;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

/**
 * 新闻详情 Contract
 * <p>
 * 契约类
 */
public interface NewsDetailContract {
    interface Model extends BaseModel {
        //请求获取新闻
        Observable<NewsDetail> getOneNewsData(String postId);
    }

    interface View extends BaseView {
        //返回获取的新闻
        void returnOneNewsData(NewsDetail newsDetail);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取单条新闻请求
        public abstract void getOneNewsDataRequest(String postId);
    }
}
