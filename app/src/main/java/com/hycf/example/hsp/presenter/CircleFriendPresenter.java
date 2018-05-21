package com.hycf.example.hsp.presenter;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.aspsine.irecyclerview.bean.PageBean;
import com.hycf.example.hsp.R;
import com.hycf.example.hsp.api.DatasUtil;
import com.hycf.example.hsp.bean.CircleItem;
import com.hycf.example.hsp.bean.CommentConfig;
import com.hycf.example.hsp.bean.CommentItem;
import com.hycf.example.hsp.bean.FavortItem;
import com.hycf.example.hsp.bean.Result;
import com.hycf.example.hsp.constant.AppConstant;
import com.hycf.example.hsp.contract.CircleFriendContrat;
import com.hycf.example.hsp.ui.weight.GoodView;
import com.jaydenxiao.common.baseapp.AppCache;
import com.jaydenxiao.common.commonutils.JsonUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.MDAlertDialog;

import java.util.List;
import java.util.Random;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * 朋友圈Presenter
 */
public class CircleFriendPresenter extends CircleFriendContrat.Presenter {

    //点赞效果
    private GoodView goodView;

    /**
     * 重新onstart()
     * 监听说说更新
     */
    @Override
    public void onStart() {
        super.onStart();
        mRxManage.on(AppConstant.ZONE_PUBLISH_ADD, new Action1<CircleItem>() {
            @Override
            public void call(CircleItem circleItem) {
                if (circleItem!=null){
                    mView.setOnePublishData(circleItem);
                }
            }
        });
    }

    /**
     * 未读消息
     */
    @Override
    public void getNotReadNewsCount() {
        mRxManage.add(mModel.getZoneNotReadNews().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(String icon) {
               mView.updateNotReadNewsCount(10,icon);
            }
        }));
    }

    /**
     * 请求数据
     * @param type
     * @param userId
     * @param page
     * @param rows
     * 获取列表
     */
    @Override
    public void getListData(String type, String userId, int page, int rows) {
        //加载更多不显示加载条
        if (page <= 1)
            mView.showLoading("加载中...");
        mRxManage.add(mModel.getListDatas(type, userId, page, rows).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                mView.stopLoading();
            }
            @Override
            public void onError(Throwable e) {
                mView.showErrorTip("" + e.getMessage());
            }
            @Override
            public void onNext(Result result) {
                if (result != null) {
                    try {
                        List<CircleItem> circleItems = JSON.parseArray(JsonUtils.getValue(result.getMsg(), "list"), CircleItem.class);
                        for (int i = 0; i < circleItems.size(); i++) {
                            circleItems.get(i).setPictures(DatasUtil.getRandomPhotoUrlString(new Random().nextInt(9)));
                        }
                        PageBean pageBean = JSON.parseObject(JsonUtils.getValue(result.getMsg(), "page"), PageBean.class);
                        mView.setListData(circleItems, pageBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
    }


    /**
     * 删除说说
     * @param circleId
     * @param position
     */
    private MDAlertDialog mdialog;
    @Override
    public void deleteCircle(final String circleId, final int position) {
        mdialog=new MDAlertDialog.Builder(mContext)
                .setHeight(0.25f)  //屏幕高度*0.3
                .setWidth(0.7f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("温馨提示")
                .setTitleTextColor(R.color.black_light)
                .setContentText("确定删除该条说说吗？")
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText("取消")
                .setLeftButtonTextColor(R.color.black_light)
                .setRightButtonText("确认")
                .setRightButtonTextColor(R.color.black_light)
                .setTitleTextSize(16)
                .setContentTextSize(14)
                .setButtonTextSize(14)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                         mdialog.dismiss();
                    }
                    @Override
                    public void clickRightButton(View view) {
                        mdialog.dismiss();
                        mView.startProgressDialog();
                        mRxManage.add(mModel.deleteCircle(circleId,position).subscribe(new Subscriber<Result>() {
                            @Override
                            public void onCompleted() {
                                mView.stopProgressDialog();
                            }
                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                            @Override
                            public void onNext(Result result) {
                                mView.updateDeleteCircle(circleId,position);
                            }
                        }));
                    }
                }).build();
         mdialog.show();
    }

    /**
     * 点赞
     * @param publishId
     * @param publishUserId
     * @param circlePosition
     * @param view
     */
    @Override
    public void addFavort(final String publishId, String publishUserId, final int circlePosition, final View view) {
            mView.startProgressDialog();
            mRxManage.add(mModel.addFavort(publishId,publishUserId).subscribe(new Subscriber<Result>() {
                @Override
                public void onCompleted() {
                    mView.stopProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
                }

                @Override
                public void onNext(Result result) {
                     if (result!=null){
                         if (goodView==null){
                             goodView=new GoodView(mContext);
                         }
                         goodView.setImage(R.drawable.dianzan);
                         goodView.show(view);
                         FavortItem item=new FavortItem(publishId, AppCache.getInstance().getUserId(),"MateralDesign");
                         mView.updateAddFavorite(circlePosition,item);
                     }
                }
            }));
    }

    /**
     * 取消点赞
     * @param publishId
     * @param publishUserId
     * @param circlePosition
     */
    @Override
    public void deleteFavort(String publishId, String publishUserId, final int circlePosition) {
               mView.startProgressDialog();
               mRxManage.add(mModel.deleteFavort(publishId,publishUserId).subscribe(new Subscriber<Result>() {
                   @Override
                   public void onCompleted() {
                       mView.stopProgressDialog();
                   }
                   @Override
                   public void onError(Throwable e) {
                       ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
                   }
                   @Override
                   public void onNext(Result result) {
                        if (result!=null){
                            mView.updateDeleteFavort(circlePosition,AppCache.getInstance().getUserId());

                        }
                   }
               }));
    }

    /**
     * 评论
     * @param content
     * @param config
     */
    @Override
    public void addComment(final String content, final CommentConfig config) {
          if (config==null){
              return;
          }
          mView.startProgressDialog();
          mRxManage.add(mModel.addComment(config.getPublishUserId(),new CommentItem(config.getName(),
                  config.getId(),content,config.getPublishId(),AppCache.getInstance().getUserId(),"MateralDesign"))
                  .subscribe(new Subscriber<Result>() {
              @Override
              public void onCompleted() {
                   mView.stopProgressDialog();
              }

              @Override
              public void onError(Throwable e) {
                  mView.stopProgressDialog();
                  ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
              }

              @Override
              public void onNext(Result result) {
                  if (result != null) {
                      mView.updateAddComment(config.circlePosition, new CommentItem(config.getName(), config.getId(), content, config.getPublishId(), AppCache.getInstance().getUserId(), "MateralDesign"));
                  }
              }
          }));
    }

    /**
     * 删除评论
     * @param circlePosition
     * @param commentId
     * @param commentPosition
     */
    @Override
    public void deleteComment(final int circlePosition, final String commentId, final int commentPosition) {
              mView.startProgressDialog();
              mRxManage.add(mModel.deleteComment(commentId).subscribe(new Subscriber<Result>() {
                  @Override
                  public void onCompleted() {
                      mView.stopProgressDialog();
                  }

                  @Override
                  public void onError(Throwable e) {
                      mView.stopProgressDialog();
                      ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
                  }

                  @Override
                  public void onNext(Result result) {
                       mView.updateDeleteComment(circlePosition,commentId,commentPosition);
                  }
              }));
    }

    /**
     *  显示评论输入框
     * @param commentConfig
     */
    @Override
    public void showEditTextBody(CommentConfig commentConfig) {
        mView.updateEditTextBodyVisible(View.VISIBLE,commentConfig);
    }
}
