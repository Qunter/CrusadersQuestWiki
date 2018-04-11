package com.qunter.crusadersquestwiki.engine;

/**
 * Created by Administrator on 2017/10/8.
 */

public interface DataGetter {
    public void getDataAndSendCallback(String wikiUrlHead,String endString, DataCallback callback);
}
