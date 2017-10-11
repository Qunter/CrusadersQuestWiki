package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;

/**
 * Created by Administrator on 2017/10/11.
 */
public class HeroDetailActivity extends BaseActivity {
    private WebView webView;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_herodetail);
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("http://wiki.joyme.com/cq/%E5%85%89%E6%98%8E%E5%89%91%E5%A3%AB%E9%87%8C%E6%98%82");
    }
}
