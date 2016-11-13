package com.jhs.taolibao.code.news.widget;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jhs.taolibao.R;
import com.jhs.taolibao.view.TitleBar;

public class AdActivity extends AppCompatActivity {
    private TitleBar titlebar;
    private WebView webview;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        url = getIntent().getStringExtra("url");
        initView();
        initData();
    }


    private void initView() {
        titlebar = (TitleBar) findViewById(R.id.titlebar);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webview = (WebView) findViewById(R.id.webview);
        initSetting();

    }


    private void initData() {
        webview.loadUrl(url);
    }


    private void initSetting() {
        webview.getSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩放。大视图模式
        webview.getSettings().setLoadWithOverviewMode(true);// 和setUseWideViewPort(true)一起解决网页自适应问题
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setSaveFormData(true);
        webview.setBackgroundColor(getResources().getColor(R.color.Background));
        webview.setWebViewClient(new MyWebViewClient());
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url); // 加载新的url
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }
    }
}
