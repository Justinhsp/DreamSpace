package com.hycf.example.hsp.contract;

import com.aspsine.irecyclerview.bean.PageBean;
import com.hycf.example.hsp.bean.CircleItem;
import com.hycf.example.hsp.bean.CommentConfig;
import com.hycf.example.hsp.bean.CommentItem;
import com.hycf.example.hsp.bean.FavortItem;
import com.hycf.example.hsp.bean.Result;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * 朋友圈Contract
 * 契约类
 */
public interface CircleFriendContrat {

    interface Model extends BaseModel {
        //未读总数
        Observable<String> getZoneNotReadNews();
        //获取数据
        Observable<Result> getListDatas(String type, String userId, final int page, int rows);
        //删除
        Observable<Result> deleteCircle(String circleId, int position);
        //点赞
        Observable<Result> addFavort(String publishId, String publishUserId);
        //取消点赞
        Observable<Result> deleteFavort(String publishId, String publishUserId);
        //增加评论
        Observable<Result> addComment(String publishUserId, CommentItem circleItem);
        //删除评论
        Observable<Result> deleteComment(String commentId);
    }


    interface View extends BaseView {
        //获取未读总数
        void updateNotReadNewsCount(int count, String icon);
        //获取数据
        void setListData(List<CircleItem> circleItems, PageBean pageBean);
        //Save单条数据
        void setOnePublishData(CircleItem circleItem);
        //删除说说更新
        void updateDeleteCircle(String circleId, int position);
        //点赞更新
        void updateAddFavorite(int circlePosition, FavortItem addItem);
       //取消点赞更新
        void updateDeleteFavort(int circlePosition, String UserId);
        //添加评论更新
        void updateAddComment(int circlePosition, CommentItem addItem);
         //删除评论更新
        void updateDeleteComment(int circlePosition, String commentId, int commentPosition);
        //控制评论输入框
        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
        //显示加载框
        void startProgressDialog();
        //停止加载框
        void stopProgressDialog();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        //获取未读总数
        public abstract void getNotReadNewsCount();
        //获取数据
        public abstract void getListData(String type, String userId, int page, int rows);
        //删除
        public abstract void deleteCircle(final String circleId, int position);
        //点赞
        public abstract void addFavort(final String publishId, final String publishUserId, final int circlePosition, final android.view.View view);
        //取消点赞
        public abstract void deleteFavort(final String publishId, final String publishUserId, final int circlePosition);
        //增加评论
        public abstract void addComment(final String content, final CommentConfig config);
        //删除评论
        public abstract void deleteComment(final int circlePosition, final String commentId, int commentPosition);
        //显示评论输入框
        public abstract void showEditTextBody(CommentConfig commentConfig);

    }
}
