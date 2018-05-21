package com.hycf.example.hsp.ui.fragment;

import android.view.View;
import android.widget.ProgressBar;

import com.hycf.example.hsp.R;
import com.hycf.example.hsp.constant.AppConstant;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.baserx.RxSchedulers;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图文新闻详情
 * Fragment
 */
public class BelleDetailFragment  extends BaseFragment{

    @Bind(R.id.photo_view)
    PhotoView photoView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    private String mImgSrc;

    /**
     * 返回对应布局文件
     * @return
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_new_detail_photo;
    }

    /**
     * 初始化Presenter
     */
    @Override
    public void initPresenter() {

    }

    /**
     * 初始化View
     */
    @Override
    protected void initView() {
        if (getArguments() != null) {
            mImgSrc = getArguments().getString(AppConstant.PHOTO_DETAIL_IMGSRC);
        }
        initPhotoView();
        setPhotoViewClickEvent();
    }


    /**
     * 初始化图片查看器
     */
    private void initPhotoView() {
        mRxManager.add(Observable.timer(100, TimeUnit.MILLISECONDS) // 直接使用glide加载的话，activity切换动画时背景短暂为默认背景色
                .compose(RxSchedulers.<Long>io_main())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        ImageLoaderUtils.displayBigPhoto(getContext(),photoView,mImgSrc);
                    }
                }));
    }

    /**
     * 点击图片
     */
    private void setPhotoViewClickEvent() {
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                mRxManager.post(AppConstant.PHOTO_TAB_CLICK,"");
            }
        });
    }
}
