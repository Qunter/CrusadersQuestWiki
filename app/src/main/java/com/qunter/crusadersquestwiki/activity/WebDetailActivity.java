package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
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
        //设置 缓存模式
        webDetaiWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webDetaiWebView.getSettings().setDomStorageEnabled(true);
        webDetaiWebView.getSettings().setJavaScriptEnabled(true);
        Log.e("dadahe", "do" );

        webDetaiWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                String fun = "javascript:function getClass(parent,sClass){var aEle=parent.getElementsByTagName('div');var aResult=[];var i=0;for(i<0; i<aEle.length; i++){if(aEle[i].className==sClass){aResult.push(aEle[i]);}};return aResult;}";
                String fun2 = "javascript:function hideOther() {getClass(document,'firstHeading2 page-header nry-h1 fn-clear')[0].style.display='none';getClass(document,'col-md-4')[0].style.display='none';getClass(document,'hero-info-block hero-info-stat')[0].style.display='none';getClass(document,'hero-info-block hero-info-block-frame hero-info-skill')[0].style.display='none';getClass(document,'hero-info-block hero-info-block-frame hero-info-spskill')[0].style.display='none';getClass(document,'date-tab clearfix')[0].style.display='none';}";
                String fun3 = "javascript:hideOther();";
                /*
                String funfinal = "function getClass(parent,sClass){var aEle=parent.getElementsByTagName('div');var aResult=[];var i=0;for(i<0; i<aEle.length; i++){if(aEle[i].className==sClass){aResult.push(aEle[i]);}};return aResult;}function hideOther() {getClass(document,'firstHeading2 page-header nry-h1 fn-clear')[0].style.display='none';getClass(document,'col-md-4')[0].style.display='none';getClass(document,'hero-info-block hero-info-stat')[0].style.display='none';getClass(document,'hero-info-block hero-info-block-frame hero-info-skill')[0].style.display='none';getClass(document,'hero-info-block hero-info-block-frame hero-info-spskill')[0].style.display='none';getClass(document,'date-tab clearfix')[0].style.display='none';}();";
                webDetaiWebView.evaluateJavascript(funfinal, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.e("dadahe", "do"+value );
                    }
                });
                */
                webDetaiWebView.loadUrl(fun);
                webDetaiWebView.loadUrl(fun2);
                webDetaiWebView.loadUrl(fun3);
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //true代表事件已被消费
                return true;
            }
        });
        webDetaiWebView.loadUrl(url);
    }
}
