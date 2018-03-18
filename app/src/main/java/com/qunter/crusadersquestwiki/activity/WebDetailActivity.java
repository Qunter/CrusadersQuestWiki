package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Administrator on 2017/10/11.
 */
public class WebDetailActivity extends BaseActivity {
    private TextView webDetaiTitleTv;
    private ImageView webDetailBackBtn;
    private WebView webDetaiWebView;
    private View webdetailInterruptView;
    private ProgressBar webDetailProgressBar;
    private String url, title ,endString ,selectorString;
    public enum DetailType {HERO,EQUIPMENT,SKILL}
    DetailType detailType;
    private String htmlContent;
    private final int GETHTMLCONTENTSUCCESS=0x00;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==GETHTMLCONTENTSUCCESS){
                webDetaiWebView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8",null);
                //Log.e("handleMessage: ", "do");
            }
            return false;
        }
    });
    @Override
    protected void initVariablesAndService() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        detailType = (DetailType) getIntent().getSerializableExtra("detailType");
        endString = getIntent().getStringExtra("endString");
        switch (detailType){
            case HERO:
                selectorString = getString(R.string.heroHtmlContentSelectorString);
                getHtmlContent();
                break;
            case EQUIPMENT:
                selectorString = getString(R.string.equimentHtmlContentSelectorString);
                getHtmlContent();
                break;
            case SKILL:
                selectorString = getString(R.string.skillHtmlContentSelectorString);
                getHtmlContent();
                break;
        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webdetail);
        webDetaiTitleTv = (TextView) findViewById(R.id.webDetail_title_tv);
        webDetaiTitleTv.setText(title);
        webDetailBackBtn = (ImageView) findViewById(R.id.webDetail_backBtn_iv);
        webDetailBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webDetailProgressBar = (ProgressBar) findViewById(R.id.webDetail_pgb_progressBar);
        webdetailInterruptView = findViewById(R.id.webDetail_interrupt_view);
        webDetaiWebView = (WebView) findViewById(R.id.webDetail_content_webview);
        //设置 缓存模式
        webDetaiWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webDetaiWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        Log.e("WB", webDetaiWebView.isHardwareAccelerated()+"");
        webDetaiWebView.getSettings().setDomStorageEnabled(true);
        webDetaiWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                if(newProgress==100){
                    webDetailProgressBar.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    webDetailProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    webDetailProgressBar.setProgress(newProgress);//设置进度值
                }

            }
        });
        webDetaiWebView.getSettings().setJavaScriptEnabled(true);
        //Log.e("dadahe", "do" );
        webDetaiWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                switch (detailType){
                    case HERO:
                        break;
                    case EQUIPMENT:
                        /*
                        String fun = "javascript:function getClass(parent,sClass){var aEle=parent.getElementsByTagName('div');var aResult=[];var i=0;for(i<0; i<aEle.length; i++){if(aEle[i].className==sClass){aResult.push(aEle[i]);}};return aResult;}";
                        String fun2 = "javascript:function hideOther() {getClass(document,'firstHeading2 page-header nry-h1 fn-clear')[0].style.display='none';getClass(document,'col-md-4')[0].style.display='none';getClass(document,'col-md-8')[0].style.display='none';getClass(document,'hero-info-block hero-info-block-frame hero-info-skill')[0].style.display='none';getClass(document,'hero-info-block hero-info-block-frame hero-info-spskill')[0].style.display='none';getClass(document,'hero-info-block hero-info-block-frame')[3].style.display='none';getClass(document,'hero-info-block hero-info-block-frame')[4].style.display='none';getClass(document,'date-tab clearfix')[0].style.display='none';getClass(document,'hero-info-block hero-info-block-frame')[0].style.display='none';}";
                        String fun3 = "javascript:hideOther();";
                        */
                        /*
                        String funfinal = "function getClass(parent,sClass){var aEle=parent.getElementsByTagName('div');var aResult=[];var i=0;for(i<0; i<aEle.length; i++){if(aEle[i].className==sClass){aResult.push(aEle[i]);}};return aResult;}function hideOther() {getClass(document,'firstHeading2 page-header nry-h1 fn-clear')[0].style.display='none';getClass(document,'col-md-4')[0].style.display='none';getClass(document,'hero-info-block hero-info-stat')[0].style.display='none';getClass(document,'hero-info-block hero-info-block-frame hero-info-skill')[0].style.display='none';getClass(document,'hero-info-block hero-info-block-frame hero-info-spskill')[0].style.display='none';getClass(document,'date-tab clearfix')[0].style.display='none';}();";
                        webDetaiWebView.evaluateJavascript(funfinal, new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                Log.e("dadahe", "do"+value );
                            }
                        });
                        */
                        //webDetaiWebView.loadUrl(fun);
                        //webDetaiWebView.loadUrl(fun2);
                        //webDetaiWebView.loadUrl(fun3);
                        break;
                    case SKILL:
                        break;
                }
                super.onPageFinished(view, url);
                webdetailInterruptView.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //true代表事件已被消费
                return true;
            }
        });
        //webDetaiWebView.loadUrl(url);
    }
    private void getHtmlContentWithSelector(String endString,String selectorString){
        Connection conn = Jsoup.connect("http://wiki.joyme.com/cq/"+endString);
        //Log.e("dadahe",  "dodo: "+url );
        conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc==null){
            Log.e("getHtmlContent", "有问题");
        }else{
            String htmlEquipmentContent = doc.select(selectorString).first().toString();
            //Log.e("dadahe", htmlEquipmentContent+"");
            doc.select("div").remove();
            doc.select("body").first().append(htmlEquipmentContent);
            htmlContent = doc.toString();
            /*
            if(htmlContent.length() > 4000) {
                for(int i=0;i<htmlContent.length();i+=4000){
                    if(i+4000<htmlContent.length())
                        Log.e("dadahe"+i,htmlContent.substring(i, i+4000));
                    else
                        Log.e("dadahe"+i,htmlContent.substring(i, htmlContent.length()));
                }
            } else
                Log.i("dadahe","fuck"+doc.toString());
            */
            handler.sendEmptyMessage(GETHTMLCONTENTSUCCESS);
        }

    }

    private void getHtmlContent(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getHtmlContentWithSelector(endString,selectorString);
            }
        }).start();
    }
}
