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
public class HeroDetailActivity extends BaseActivity {
    private TextView heroNameTv;
    private ImageView heroDetailBackBtn;
    private WebView webView;
    private String url,heroName;
    @Override
    protected void initVariablesAndService() {
        url = getIntent().getStringExtra("url");
        heroName = getIntent().getStringExtra("heroName");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_herodetail);
        heroNameTv = (TextView) findViewById(R.id.herodetail_tv);
        heroNameTv.setText(heroName);
        heroDetailBackBtn = (ImageView) findViewById(R.id.herodetail_backBtn);
        heroDetailBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView = (WebView) findViewById(R.id.herodetail_webview);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //true代表事件已被消费
                return true;
            }
        });
        //设置 缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
    }
}
