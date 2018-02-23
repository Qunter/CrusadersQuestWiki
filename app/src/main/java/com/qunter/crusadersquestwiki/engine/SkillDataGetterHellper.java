package com.qunter.crusadersquestwiki.engine;

import android.util.Log;

import com.qunter.crusadersquestwiki.entity.SkillData;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldk on 17-11-27.
 */

public class SkillDataGetterHellper implements DataGetter {
    List<SkillData> datas = new ArrayList<SkillData>();
    /**
     * 使用jsoup获取数据
     */
    private void getDataFromUrlAndSave(String endString){
        String url = "http://wiki.joyme.com/cq/"+endString+"特殊技能";
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
            Elements filterBase = doc.select("div[class=cq_frame]");
            Elements filterfinal = filterBase.select("tr");
            Elements filterfinall = filterfinal.select("td");
            /*
            String xml = filterfinal.get(1).toString();

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
            */
            SkillData data;
            for(int i=0;i<filterfinall.size()/4;i++){
                data = new SkillData();
                //Log.e("hehe", filterfinall.get(i*4).toString() );
                //Log.e("hehe", filterfinal.toString());
                data.setSkilllDetailUrl(filterfinall.get(i*4).select("a").get(0).absUrl("href"));
                //Log.e("heheda",filterfinall.get(i*4).select("a").get(0).absUrl("href"));
                data.setSkillName(filterfinall.get(i*4).select("a").get(0).attr("title"));
                //Log.e("element",filterfinall.get(i*4).select("a").get(0).attr("title"));
                data.setSkillImgUrl(filterfinall.get(i*4).select("img").get(0).absUrl("src"));
                //Log.e("url", filterfinall.get(i*4).select("img").get(0).absUrl("src"));
                data.setSkillType(filterfinall.get(i*4+2).text());
                //Log.e("url", filterfinall.get(i*4+2).text());
                data.setSkillDetail(filterfinall.get(i*4+3).text());
                //Log.e("url", filterfinall.get(i*4+3).text());
                datas.add(data);
            }
            //getHeroInfoAndSave(filterMostly.toString()+filterRate.toString());
            //Log.e("getDataFromUrl2", filterRate.toString());

        }
    }

    @Override
    public void getDataAndSendCallback(String endString, DataCallback callback) {
        getDataFromUrlAndSave(endString);
        callback.afterGetData(datas);
    }
}
