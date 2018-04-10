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
        //url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        //detailType = (DetailType) getIntent().getSerializableExtra("detailType");
        endString = getIntent().getStringExtra("endString");
        selectorString = getIntent().getStringExtra("selectorString");
        getHtmlContent();
        /*
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
            case SEASON2_STORY:
                selectorString = getString(R.string.season2StoryModeHtmlContentSelectorString);
                getHtmlContent();
                break;
            case SEASON2_CHALLENGE:
                selectorString = getString(R.string.season2ChallengeModeHtmlContentSelectorString);
                getHtmlContent();
                break;
            case RUNEANDWEAPONIMPRINT:
                selectorString = getString(R.string.RuneAndWeaponImprintHtmlContentSelectorString);
                getHtmlContent();
                break;
        }
        */

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
        //Connection conn = Jsoup.connect("http://wiki.woxihuan.com/cq/"+endString);
        Connection conn = Jsoup.connect("http://wiki.woxihuan.com/cq/"+endString);
        //Connection conn = Jsoup.connect("http://wiki.woxihuan.com/cq/%E4%BD%BF%E5%BE%92%E7%9A%84%E5%A4%8D%E6%B4%BB");
        Log.e("dadahe",  "dodo: "+endString );
        //conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
        //conn.header("User-Agent", "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;360SE");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc==null){
            Log.e("getHtmlContent", "有问题");
        }else{
            Log.e("dadahe", doc+"");
            String htmlEquipmentContent = doc.select(selectorString).first().toString();
            doc.select("div").remove();
            doc.select("body").first().append(htmlEquipmentContent);
            if (detailType==DetailType.SEASON2_STORY||detailType==DetailType.SEASON2_CHALLENGE)
                doc.select("div").get(1).remove();
            doc.select("a").removeAttr("href");
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

    public enum DetailType {
        HERO("HERO"),EQUIPMENT("EQUIPMENT"),SKILL("SKILL"),SEASON2_STORY("SEASON2_STORY"),SEASON2_CHALLENGE("SEASON2_CHALLENGE"),RUNEANDWEAPONIMPRINT("RUNEANDWEAPONIMPRINT");
        private String typeString;
        DetailType(String typeString){
            this.typeString = typeString;
        }

        public static DetailType fromTypeString(String typeString) {
            for (DetailType type : DetailType.values()) {
                if (type.getTypeString().equals(typeString)) {
                    return type;
                }
            }
            return null;
        }

        public String getTypeString() {
            return this.typeString;
        }

    }
}
