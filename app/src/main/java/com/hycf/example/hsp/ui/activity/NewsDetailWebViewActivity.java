package com.hycf.example.hsp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hycf.example.hsp.R;
import com.hycf.example.hsp.constant.AppConstant;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.Bind;

/**
 * 新闻详情页
 * 使用WebView打开
 */
public class NewsDetailWebViewActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.web_view)
    WebView webView;

    /**
     * 返回对应布局文件
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_news_dtail_webview;
    }

    /**
     * 返回对应Presenter
     */
    @Override
    public void initPresenter() {

    }

    /**
     * 初始化UI
     */
    @Override
    public void initView() {
        setWebViewSettings();
        setWebView();

    }

    /**
     * WwbView属性
     */
    private void setWebViewSettings() {
        WebSettings webSettings = webView.getSettings();
        // 打开页面时， 自适应屏幕
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        // 便页面支持缩放
        webSettings.setJavaScriptEnabled(true); //支持js
        webSettings.setSupportZoom(true); //支持缩放
        //webSettings.setBuiltInZoomControls(true); // 放大和缩小的按钮，容易引发异常 http://blog.csdn.net/dreamer0924/article/details/34082687
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }



    /**
     * Start WebView
     */
    private void setWebView() {
        webView.loadUrl(getIntent().getStringExtra(AppConstant.NEWS_LINK));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null) view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });
    }

    /**
     * Activity入口
     *
     * @param context
     * @param link
     * @param title
     */
    public static void startAction(Context context, String link, String title) {
        Intent intent = new Intent(context, NewsDetailWebViewActivity.class);
        intent.putExtra(AppConstant.NEWS_LINK, link);
        intent.putExtra(AppConstant.NEWS_TITLE, title);
        context.startActivity(intent);
    }


    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        if(webView!=null) {
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
    }

}
