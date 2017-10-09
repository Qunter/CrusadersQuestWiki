package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.adapter.HeroInfoActRecAdapter;
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
    private HeroDataGetterHellper heroDataGetterHellper = new HeroDataGetterHellper();
    private HeroInfoActRecAdapter adapter;
    private List<HeroData> datas = new ArrayList<HeroData>();
    private String heroType = "null";
    private final int PUSHDATAINTORECYCLERVIEW = 0x00;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PUSHDATAINTORECYCLERVIEW:
                    adapter = new HeroInfoActRecAdapter(getApplicationContext(),datas);
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    protected void initVariablesAndService() {
        heroType = getIntent().getExtras().getString("heroType");
        //Log.e("heroType", heroType+"");
        getHeroData();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_heroinfo);
        recyclerView = (RecyclerView) findViewById(R.id.heroinfo_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    /**
     * jsoup爬取勇士数据
     */
    private void getHeroData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                heroDataGetterHellper.getData(heroType,HeroInfoActivity.this);
            }
        }).start();
    }

    /**
     * 重写接口中定义的方法
     * 获取勇士数据后应该进行的操作
     */
    @Override
    public void afterGetHeroData(List<HeroData> datas) {
        this.datas = datas;
        handler.sendEmptyMessage(PUSHDATAINTORECYCLERVIEW);
        Log.e("afterGetHeroData", datas.size()+"" );
    }
}
