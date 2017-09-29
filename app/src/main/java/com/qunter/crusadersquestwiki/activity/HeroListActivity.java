package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.adapter.HeroListActRecAdapter;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.entity.HeroDate;
import com.qunter.crusadersquestwiki.entity.HeroListActRecItemData;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/23.
 */

public class HeroListActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ImageView BackBtn;
    private List<HeroListActRecItemData> datas = new ArrayList<HeroListActRecItemData>();
    private List<HeroDate> dataas = new ArrayList<HeroDate>();
    private int[] itemPicDatas = {R.drawable.ic_warrior,R.drawable.ic_paladin,R.drawable.ic_archer,R.drawable.ic_hunter,R.drawable.ic_wizard,R.drawable.ic_priest};
    private int[] itemContentDatas = {R.string.warrior,R.string.paladin,R.string.archer,R.string.hunter,R.string.wizard,R.string.priest};
    private final int GETDATAFROMURL=0x00;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GETDATAFROMURL:
                    new Thread(runnable).start();
                    break;
            }
        }
    };
    @Override
    protected void initVariablesAndService() {
        for(int i=0;i<itemPicDatas.length;i++){
            HeroListActRecItemData data = new HeroListActRecItemData();
            data.setIv_imgResource(itemPicDatas[i]);
            data.setTv_content(getString(itemContentDatas[i]));
            datas.add(data);
        }
        handler.sendEmptyMessage(GETDATAFROMURL);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_herolist);

        BackBtn = (ImageView) findViewById(R.id.herolist_backBtn);
        BackBtn.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.herolist_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        HeroListActRecAdapter adapter = new HeroListActRecAdapter(datas);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.herolist_backBtn:
                finish();
                break;
        }
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getDataFromUrl();
        }
    };
    /**
     * 使用jsoup获取首页数据
     * http://wiki.joyme.com/cq/%E5%89%91%E5%A3%AB
     * http://wiki.joyme.com/cq/%E5%85%89%E6%98%8E%E5%89%91%E5%A3%AB%E9%87%8C%E6%98%82
     * http://wiki.joyme.com/cq/剑士
     */
    private void getDataFromUrl(){

        String url = "http://wiki.joyme.com/cq/剑士";
        Connection conn = Jsoup.connect(url);
        // 修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(doc==null){
            Log.e("getDataFromUrl", "null");
        }else{
            Elements filterBase = doc.select("div[id=mw-content-text]");
            //获取到勇士名称及头像url及详情Url
            Elements filterMostly = filterBase.select("a[title~=^★6]");
            //获取到勇士评级
            Elements filterRate = filterBase.select("img[data-file-height=30]");
            /*log答应出获取到的全部内容
            String xml = filterBase.toString();
            if(xml.length() > 4000) {
                for(int i=0;i<xml.length();i+=4000){
                    if(i+4000<xml.length())
                        Log.i("getDataFromUrl"+i,xml.substring(i, i+4000));
                    else
                        Log.i("getDataFromUrl"+i,xml.substring(i, xml.length()));
                }
            } else
                Log.i("getDataFromUrl",xml);
            */
            HeroDate data;
            for(Element element:filterMostly){
                data = new HeroDate();
                data.setHeroDetailUrl(element.absUrl("href"));
                data.setHeroName(element.attr("title").substring(3));
                //Log.e("element",element.attr("title").substring(3));
                data.setHeroPicUrl(element.absUrl("src"));
                dataas.add(data);
            }
            for(int i=0;i<filterRate.size()/5;i++){
                for(int j=0;j<5;j++){
                    dataas.get(i).setHeroRate(j,Integer.parseInt(filterRate.get(i*5+j).attr("alt").substring(10,11)));
                    //Log.e("element",filterRate.get(i*5+j).attr("alt").substring(10,11));
                }

            }
            //getHeroInfoAndSave(filterMostly.toString()+filterRate.toString());
            //Log.e("getDataFromUrl2", filterRate.toString());
        }
    }
    /**
     * 获取勇士信息并且储存
     */
    private void getHeroInfoAndSave(String informationAfterFilter){
        int sum = getStringOccurrenceNumber("title=\"★6",informationAfterFilter);
        int HeroNameSum=0;
        //StringBuffer HeroNamebuffer
        for(int i=0;i<sum;i++){
            HeroNameSum = informationAfterFilter.indexOf('a');
            informationAfterFilter.substring(informationAfterFilter.indexOf('a'),informationAfterFilter.indexOf('a')+10);

        }
    }
    /**
     * 获取源字符串中目标子串出现的次数
     */
    private int getStringOccurrenceNumber(String findString,String data){
        int originLength = data.length();
        return data.replaceAll(findString,findString+"*").length() - originLength;
    }
}
