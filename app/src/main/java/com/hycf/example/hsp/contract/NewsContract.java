package com.hycf.example.hsp.contract;

import com.hycf.example.hsp.bean.NewsChannelTable;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * 新闻列表Contract
 * 契约类
 */
public interface NewsContract {


    interface Model extends BaseModel {
        Observable<List<NewsChannelTable>> lodeMineNewsChannels();
    }


    interface View extends BaseView {
        void returnMineNewsChannels(List<NewsChannelTable> newsChannelMine);
    }


    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeMineChannelsRequest();
    }


}
