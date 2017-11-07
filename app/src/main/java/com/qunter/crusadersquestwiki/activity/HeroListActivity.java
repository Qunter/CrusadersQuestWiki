package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.adapter.HeroInfoActRecAdapter;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.engine.DataCallback;
import com.qunter.crusadersquestwiki.engine.HeroDataGetterHellper;
import com.qunter.crusadersquestwiki.entity.HeroData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/8.
 */

public class HeroListActivity extends BaseActivity implements DataCallback<HeroData> {
    private RecyclerView recyclerView;
    private ImageView heroInfoBackBtn;
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
                    adapter.setOnItemClickListener(new HeroInfoActRecAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(getApplicationContext(),WebDetailActivity.class)
                                    .putExtra("url",datas.get(position).getHeroDetailUrl())
                                    .putExtra("title",datas.get(position).getHeroName()));
                        }
                    });
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
        heroInfoBackBtn = (ImageView) findViewById(R.id.heroType_backBtn);
        heroInfoBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * jsoup爬取勇士数据
     */
    private void getHeroData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                heroDataGetterHellper.getDataAndSendCallback(heroType,HeroListActivity.this);
            }
        }).start();
    }

    /**
     * 重写接口中定义的方法
     * 获取勇士数据后应该进行的操作
     */
    @Override
    public void afterGetData(List<HeroData> datas) {
        this.datas = datas;
        handler.sendEmptyMessage(PUSHDATAINTORECYCLERVIEW);
        Log.e("afterGetData", datas.size()+"" );
    }
}
