package com.qunter.crusadersquestwiki.engine;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 2017/10/25.
 */

public class EquipmentDataGetterHellper implements DataGetter {
    /**
     * 使用jsoup获取数据
     * http://wiki.joyme.com/cq/%E5%89%91%E5%A3%AB%E7%B2%BE%E6%B7%AC%E6%AD%A6%E5%99%A8%E5%88%97%E8%A1%A8
     * http://wiki.joyme.com/cq/剑士精淬武器列表
     */
    private void getDataFromUrlAndSave(String heroType){
        String url = "http://wiki.joyme.com/cq/"+heroType+"精淬武器列表";
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

            String xml = doc.toString();

            if(xml.length() > 4000) {
                for(int i=0;i<xml.length();i+=4000){
                    if(i+4000<xml.length())
                        Log.i("getDataFromUrl"+i,xml.substring(i, i+4000));
                    else
                        Log.i("getDataFromUrl"+i,xml.substring(i, xml.length()));
                }
            } else
                Log.i("getDataFromUrl",xml);

            //Log.e("hehe", filterPicUrl.toString() );
            /*
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
            */
            //getHeroInfoAndSave(filterMostly.toString()+filterRate.toString());
            //Log.e("getDataFromUrl2", filterRate.toString());
        }
    }

    @Override
    public void getDataAndSendCallback(String heroType, DataCallback callback) {
        getDataFromUrlAndSave(heroType);
    }
}
