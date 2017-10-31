package com.qunter.crusadersquestwiki.engine;

import android.util.Log;

import com.qunter.crusadersquestwiki.entity.EquipmentData;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class EquipmentDataGetterHellper implements DataGetter {
    private List<EquipmentData> datas = new ArrayList<EquipmentData>();
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
            Elements filterBase = doc.select("div[id=mw-content-text]");
            Elements filterthen = filterBase.select("table");
            Elements filterfinal = filterthen.get(1).select("tr");
            Elements filterfinalal = filterfinal.select("td");
            String xml = filterfinalal.toString();
            Log.e("getDataFromUrl", "do");
            if(xml.length() > 4000) {
                for(int i=0;i<xml.length();i+=4000){
                    if(i+4000<xml.length())
                        Log.e("getDataFromUrl"+i,xml.substring(i, i+4000));
                    else
                        Log.e("getDataFromUrl"+i,xml.substring(i, xml.length()));
                }
            } else
                Log.e("getDataFromUrl",xml);


            //Log.e("hehe", filterPicUrl.toString() );

            EquipmentData data;
            for(int i=0;i<filterfinalal.size()/7;i++){
                data = new EquipmentData();
                data.setEquipmentPicUrl(filterfinalal.get(i*7).select("img").attr("src"));
                data.setEquipmentName(filterfinalal.get(i*7+1).text());
                //data.setEquipmentName(filterfinalal.get(i*7+2).attr("alt"));
                data.setEquipmentRate(filterfinalal.get(i*7+3).text());
                data.setEquipmentAttack(filterfinalal.get(i*7+4).text());
                data.setEquipmentASPD(filterfinalal.get(i*7+5).text());
                data.setEquipmentForWho(filterfinalal.get(i*7+6).text());
                data.setEquipmentDetailUrl(filterfinalal.get(i*7+1).select("a").get(0).absUrl("href"));
                Log.e("setEquipmentForWho", filterfinalal.get(i*7+1).absUrl("href")+"");
                datas.add(data);
            }

            //getHeroInfoAndSave(filterMostly.toString()+filterRate.toString());
            //Log.e("getDataFromUrl2", filterRate.toString());
        }
    }

    @Override
    public void getDataAndSendCallback(String heroType, DataCallback callback) {
        getDataFromUrlAndSave(heroType);
        callback.afterGetData(datas);
    }
}
