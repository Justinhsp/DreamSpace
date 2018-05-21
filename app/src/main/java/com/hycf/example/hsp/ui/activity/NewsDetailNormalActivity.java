package com.hycf.example.hsp.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hycf.example.hsp.R;
import com.hycf.example.hsp.bean.NewsDetail;
import com.hycf.example.hsp.constant.AppConstant;
import com.hycf.example.hsp.contract.NewsDetailContract;
import com.hycf.example.hsp.model.NewsDetailModel;
import com.hycf.example.hsp.presenter.NewsDetailPresenter;
import com.hycf.example.hsp.ui.weight.URLImageGetter;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.baserx.RxSchedulers;
import com.jaydenxiao.common.commonutils.TimeUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * 普通新闻详情页
 */
public class NewsDetailNormalActivity extends BaseActivity<NewsDetailPresenter, NewsDetailModel> implements NewsDetailContract.View {
    @Bind(R.id.news_detail_photo_iv)
    ImageView newsDetailPhotoIv;
    @Bind(R.id.mask_view)
    View maskView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.news_detail_from_tv)
    TextView newsDetailFromTv;
    @Bind(R.id.news_detail_body_tv)
    TextView newsDetailBodyTv;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.fab_news_detail)
    FloatingActionButton fabNewsDetail;

    private String postId;
    private URLImageGetter mUrlImageGetter;
    private String mNewsTitle;
    private String mShareLink;


    /**
     * 返回对应布局文件
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail_normal;
    }

    /**
     * 初始化Presenter
     */
    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    /**
     * 初始化UI
     */
    @Override
    public void initView() {
        SetTranslanteBar();
        postId = getIntent().getStringExtra(AppConstant.NEWS_POST_ID);
        mPresenter.getOneNewsDataRequest(postId);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });
        toolbar.inflateMenu(R.menu.news_detail);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_web_view:
                        NewsDetailWebViewActivity.startAction(NewsDetailNormalActivity.this, mShareLink, mNewsTitle);
                        break;
                    case R.id.action_browser:
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        if (canBrowse(intent)) {
                            Uri uri = Uri.parse(mShareLink);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                        break;
                }
                return true;
            }
        });
        //分享
        fabNewsDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareLink == null) {
                    mShareLink = "";
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_contents, mNewsTitle, mShareLink));
                startActivity(Intent.createChooser(intent, getTitle()));
            }
        });
    }

    /**
     * 浏览器打开
     * @param intent
     * @return
     */
    private boolean canBrowse(Intent intent) {
        return intent.resolveActivity(getPackageManager()) != null && mShareLink != null;
    }


    /**
     * 新闻详情数据
     * 对象
     *
     * @param newsDetail
     */
    @Override
    public void returnOneNewsData(NewsDetail newsDetail) {
        mShareLink = newsDetail.getShareLink();
        mNewsTitle = newsDetail.getTitle();
        String newsSource = newsDetail.getSource();
        String newsTime = TimeUtil.formatDate(newsDetail.getPtime());
        String newsBody = newsDetail.getBody();
        String NewsImgSrc = getImgSrcs(newsDetail);
        //toolbar标题
        setToolBarLayout(mNewsTitle);
        newsDetailFromTv.setText(getString(R.string.news_from, newsSource, newsTime));
        setNewsDetailPhotoIv(NewsImgSrc);
        setNewsDetailBodyTv(newsDetail, newsBody);
    }

    /**
     * 图片
     * @param newsDetail
     * @return
     */
    private String getImgSrcs(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        String imgSrc;
        if (imgSrcs != null && imgSrcs.size() > 0) {
            imgSrc = imgSrcs.get(0).getSrc();
        } else {
            imgSrc = getIntent().getStringExtra(AppConstant.NEWS_IMG_RES);
        }
        return imgSrc;
    }


    /**
     * toolbar标题
     * @param newsTitle
     */
    private void setToolBarLayout(String newsTitle) {
        toolbarLayout.setTitle(newsTitle);
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_white));
    }

    /**
     * 详情页图片
     * @param imgSrc
     */
    private void setNewsDetailPhotoIv(String imgSrc) {
        Glide.with(this).load(imgSrc)
                .fitCenter()
                .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                .crossFade().into(newsDetailPhotoIv);
    }

    private void setNewsDetailBodyTv(final NewsDetail newsDetail, final String newsBody) {
        mRxManager.add(Observable.timer(500, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.<Long>io_main())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        fabNewsDetail.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onNext(Long aLong) {
                        setBody(newsDetail, newsBody);
                    }
                }));
    }


    private void setBody(NewsDetail newsDetail, String newsBody) {
        int imgTotal = newsDetail.getImg().size();
        if (isShowBody(newsBody, imgTotal)) {
           //mNewsDetailBodyTv.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效,实测经常卡机崩溃
            mUrlImageGetter = new URLImageGetter(newsDetailBodyTv, newsBody, imgTotal);
            newsDetailBodyTv.setText(Html.fromHtml(newsBody, mUrlImageGetter, null));
        } else {
            newsDetailBodyTv.setText(Html.fromHtml(newsBody));
        }
    }

    private boolean isShowBody(String newsBody, int imgTotal) {
        return imgTotal >= 2 && newsBody != null;
    }


    /**
     * 加载时
     *
     * @param title
     */
    @Override
    public void showLoading(String title) {

    }

    /**
     * 加载完成
     */
    @Override
    public void stopLoading() {

    }

    /**
     * 加载失败
     *
     * @param msg
     */
    @Override
    public void showErrorTip(String msg) {

    }


    /**
     * Activity入口
     *
     * @param mContext
     * @param postId
     */
    public static void startAction(Context mContext, View view, String postId, String imgUrl) {
        Intent intent = new Intent(mContext, NewsDetailNormalActivity.class);
        intent.putExtra(AppConstant.NEWS_POST_ID, postId);
        intent.putExtra(AppConstant.NEWS_IMG_RES, imgUrl);
        //沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) mContext, view, AppConstant.TRANSITION_ANIMATION_NEWS_PHOTOS);
            mContext.startActivity(intent, options.toBundle());
        } else {
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
        }
    }

}
