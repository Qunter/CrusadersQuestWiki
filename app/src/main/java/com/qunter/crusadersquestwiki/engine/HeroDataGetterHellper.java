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
     * 使用jsoup获取数据
     * http://wiki.joyme.com/cq/%E5%89%91%E5%A3%AB
     * http://wiki.joyme.com/cq/%E5%85%89%E6%98%8E%E5%89%91%E5%A3%AB%E9%87%8C%E6%98%82
     * http://wiki.joyme.com/cq/剑士
     */
    private void getDataFromUrlAndSave(String wikiUrlHead, String endString){
        String url = wikiUrlHead+endString;
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
            Elements filterRate = filterBase.select("div[class=hero-list]").get(0).select("tbody");
            filterRate.select("td[class=mwiki-hide]").remove();
            filterRate.select("td[style=text-align:left]").remove();
            filterRate = filterRate.select("td");

            String file_paths = filterBase.toString();
            String fileee = filterRate.toString();
            /*
            if(fileee.length() > 4000) {
                for(int i=0;i<fileee.length();i+=4000){
                    if(i+4000<fileee.length())
                        Log.i("getDataFromUrl"+i,fileee.substring(i, i+4000));
                    else
                        Log.i("getDataFromUrl"+i,fileee.substring(i, fileee.length()));
                }
            } else
                Log.i("getDataFromUrl",fileee);
            */
            //Log.e("hehe", filterPicUrl.toString() );
            HeroData data;
            for(Element element:filterMostly){
                data = new HeroData();
                data.setHeroDetailUrl(element.absUrl("href"));
                //Log.e("hehe", element.toString() );
                data.setHeroName(element.attr("title").substring(3));
                //Log.e("element",element.attr("title").substring(3));
                data.setHeroPicUrl(element.attr("src"));

                //Log.e("url", "do");
                datas.add(data);
            }

            for(int i=0;i<filterPicUrl.size();i++){
                //Log.e("filterRate.size()", filterPicUrl.size()+"");
                datas.get(i).setHeroPicUrl(filterPicUrl.get(i).attr("src"));
                //Log.e("url", filterPicUrl.get(i).attr("src"));
                for(int j=0;j<4;j++){
                    if (filterRate.get(i*4+j).select("img").size()!=0){
                        datas.get(i).setHeroRate(j,Integer.parseInt(filterRate.get(i*4+j).select("img").get(0).attr("alt").substring(10,11)));
                        //Log.e("element",filterRate.get(i*4+j).select("img").get(0).attr("alt").substring(10,11)+"");
                    }
                    //Log.e("fuck",filterRate.get(i*4+j).text());
                }

            }
            //getHeroInfoAndSave(filterMostly.toString()+filterRate.toString());
            //Log.e("getDataFromUrl2", filterRate.toString());
        }
    }

    @Override
    public void getDataAndSendCallback(String wikiUrlHead,String endString, DataCallback callback) {
        getDataFromUrlAndSave(wikiUrlHead,endString);
        callback.afterGetData(datas);
    }
}
