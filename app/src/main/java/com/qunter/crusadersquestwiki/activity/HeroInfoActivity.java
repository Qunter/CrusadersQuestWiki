package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.engine.HeroDataCallback;
import com.qunter.crusadersquestwiki.engine.HeroDataGetterHellper;
import com.qunter.crusadersquestwiki.entity.HeroData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/8.
 */

public class HeroInfoActivity extends BaseActivity implements HeroDataCallback{
    private RecyclerView recyclerView;
    private HeroDataGetterHellper heroDataGetterHellper;
    private List<HeroData> datas = new ArrayList<HeroData>();
    @Override
    protected void initVariablesAndService() {
        heroDataGetterHellper.getData(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_heroinfo);
        recyclerView = (RecyclerView) findViewById(R.id.heroinfo_rec);

    }

    @Override
    public void getHeroData(List<HeroData> datas) {
        Log.e("getHeroData", datas.size()+"" );
    }
}
