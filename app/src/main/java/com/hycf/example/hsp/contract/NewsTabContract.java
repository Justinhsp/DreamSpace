package com.hycf.example.hsp.contract;

import com.hycf.example.hsp.bean.NewsSummary;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * 新闻列表Contract
 *  契约类
  */
public interface NewsTabContract {

     interface  Model extends BaseModel{
         //请求新闻数据
         Observable<List<NewsSummary>> getNewsListData(String type,final String id,int startPage);
     }

     interface View extends BaseView{
         //返回获取的新闻
         void returnNewsListData(List<NewsSummary> newsSummaries);
         //返回顶部
         void scrolltoTop();
     }

    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取新闻请求
        public abstract void getNewsListDataRequest(String type, final String id, int startPage);
    }
}
