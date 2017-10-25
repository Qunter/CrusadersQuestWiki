package com.qunter.crusadersquestwiki.engine;

import com.qunter.crusadersquestwiki.entity.HeroData;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/10/8.
 */

public interface DataCallback<T> {
    public void afterGetData(List<T> datas);
}
