package com.qunter.crusadersquestwiki.engine;

import android.util.Log;

import com.qunter.crusadersquestwiki.entity.HeroData;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/8.
 */

public class HeroDataGetterHellper implements DataGetter{
    private List<HeroData> datas = new ArrayList<HeroData>();
    /**
     * 使用jsoup获取首页数据
     * http://wiki.joyme.com/cq/%E5%89%91%E5%A3%AB
     * http://wiki.joyme.com/cq/%E5%85%89%E6%98%8E%E5%89%91%E5%A3%AB%E9%87%8C%E6%98%82
     * http://wiki.joyme.com/cq/剑士
     */
    private void getDataAndSave(String heroType){
        String url = "http://wiki.joyme.com/cq/"+heroType;
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
            //获取到勇士头像url
            Elements filterPicUrl = filterMostly.select("img[data-file-height=72]");
            //获取到勇士评级
            Elements filterRate = filterBase.select("img[data-file-height=30]");
            /*
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
            Log.e("hehe", filterPicUrl.toString() );
            HeroData data;
            for(Element element:filterMostly){
                data = new HeroData();
                data.setHeroDetailUrl(element.absUrl("href"));
                data.setHeroName(element.attr("title").substring(3));
                //Log.e("element",element.attr("title").substring(3));
                data.setHeroPicUrl(element.attr("src"));

                //Log.e("url", "do");
                datas.add(data);
            }
            for(int i=0;i<filterRate.size()/5;i++){
                datas.get(i).setHeroPicUrl(filterPicUrl.get(i).attr("src"));
                Log.e("url", filterPicUrl.get(i).attr("src"));
                for(int j=0;j<5;j++){
                    datas.get(i).setHeroRate(j,Integer.parseInt(filterRate.get(i*5+j).attr("alt").substring(10,11)));
                    //Log.e("element",filterRate.get(i*5+j).attr("alt").substring(10,11));
                }

            }
            //getHeroInfoAndSave(filterMostly.toString()+filterRate.toString());
            //Log.e("getDataFromUrl2", filterRate.toString());
        }
    }

    @Override
    public void getData(String heroType,HeroDataCallback callback) {
        getDataAndSave(heroType);
        callback.afterGetHeroData(datas);
    }
}
