package com.hycf.example.hsp.contract;

import com.hycf.example.hsp.bean.PhotoGirl;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * 图片列表Contract
 * 契约类
 */
public interface BelleContract {

    interface  Model extends BaseModel{
        //请求获取图片
        Observable<List<PhotoGirl>> getPhotoListData(int size, int page);
    }

    interface View extends BaseView{
        //返回获取到的图片
        void returnPhotoListData(List<PhotoGirl> photoGirls);
    }


    abstract static class Presenter extends BasePresenter<View,Model>{
        //发起获取图片请求
        public  abstract void getPhotoListDataRequest(int size,int page);
    }

}
