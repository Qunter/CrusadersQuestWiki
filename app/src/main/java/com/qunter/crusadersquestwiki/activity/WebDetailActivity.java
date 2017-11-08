package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;

/**
 * Created by Administrator on 2017/10/11.
 */
public class WebDetailActivity extends BaseActivity {
    private TextView webDetaiTitleTv;
    private ImageView webDetailBackBtn;
    private WebView webDetaiWebView;
    private String url, title;
    @Override
    protected void initVariablesAndService() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webdetail);
        webDetaiTitleTv = (TextView) findViewById(R.id.webdetail_title_tv);
        webDetaiTitleTv.setText(title);
        webDetailBackBtn = (ImageView) findViewById(R.id.webdetail_backBtn);
        webDetailBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webDetaiWebView = (WebView) findViewById(R.id.webdetail_webview);
        webDetaiWebView.loadUrl(url);
        webDetaiWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //true代表事件已被消费
                return true;
            }
        });
        //设置 缓存模式
        webDetaiWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webDetaiWebView.getSettings().setDomStorageEnabled(true);
    }
}
