package com.qunter.crusadersquestwiki.engine;

import com.qunter.crusadersquestwiki.entity.HeroData;

import java.util.List;

/**
 * Created by Administrator on 2017/10/8.
 */

public interface HeroDataCallback {
    public void getHeroData(List<HeroData> datas);
}
