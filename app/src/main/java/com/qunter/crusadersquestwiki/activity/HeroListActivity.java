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
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.adapter.HeroListActRecAdapter;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.engine.DataCallback;
import com.qunter.crusadersquestwiki.engine.HeroDataGetterHellper;
import com.qunter.crusadersquestwiki.entity.HeroData;
import com.qunter.crusadersquestwiki.entity.KeywordData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

/**
 * Created by Administrator on 2017/10/8.
 */

public class HeroListActivity extends BaseActivity implements DataCallback<HeroData>, View.OnClickListener {
    private RecyclerView recyclerView;
    private ImageView heroListBackBtn;
    private TextView heroListTitle,heroListOverallBtn,heroListPlotBtn,heroListArenaBtn,heroListChallengeBtn;
    private HeroDataGetterHellper heroDataGetterHellper = new HeroDataGetterHellper();
    private HeroListActRecAdapter adapter;
    private List<HeroData> datas = new ArrayList<HeroData>();
    private String heroType = "null";
    private enum SORTTYPE {
        OVERALL(0),PLOT(1),ARENA(2),CHALLENGE(3);
        int index;
        SORTTYPE(int index){
            this.index = index;
        }
        public int getTypeIndex() {
            return this.index;
        }

    }
    private boolean[] sortState = new boolean[4];
    private final int PUSHDATAINTORECYCLERVIEW = 0x00;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PUSHDATAINTORECYCLERVIEW:
                    adapter = new HeroListActRecAdapter(getApplicationContext(),datas);
                    adapter.setOnItemClickListener(new HeroListActRecAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(getApplicationContext(),WebDetailActivity.class)
                                    .putExtra("url",datas.get(position).getHeroDetailUrl())
                                    .putExtra("title",datas.get(position).getHeroName())
                                    .putExtra("detailType", WebDetailActivity.DetailType.HERO)
                                    .putExtra("endString",datas.get(position).getHeroName()));
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
        setContentView(R.layout.activity_herolist);
        recyclerView = (RecyclerView) findViewById(R.id.herolist_list_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        heroListTitle = (TextView) findViewById(R.id.herolist_title_tv);
        heroListTitle.setText(heroType+getString(R.string.hero_list_title));

        heroListBackBtn = (ImageView) findViewById(R.id.herolist_back_iv);
        heroListBackBtn.setOnClickListener(this);

        heroListOverallBtn = (TextView) findViewById(R.id.herolist_overallBtn);
        heroListOverallBtn.setOnClickListener(this);

        heroListPlotBtn = (TextView) findViewById(R.id.herolist_plotBtn);
        heroListPlotBtn.setOnClickListener(this);

        heroListArenaBtn = (TextView) findViewById(R.id.herolist_arenaBtn);
        heroListArenaBtn.setOnClickListener(this);

        heroListChallengeBtn = (TextView) findViewById(R.id.herolist_challengeBtn);
        heroListChallengeBtn.setOnClickListener(this);

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
        //addDataToBmob(this.datas);
        handler.sendEmptyMessage(PUSHDATAINTORECYCLERVIEW);
        Log.e("afterGetData", datas.size()+"" );
    }

    /**
     * 将关键词搜索需要的数据传输到bmob
     */
    private void addDataToBmob(List<HeroData> datas){
        List<BmobObject> list = new ArrayList<BmobObject>();
        for (HeroData data:datas){
            KeywordData l = new KeywordData();
            l.setKeyword(data.getHeroName());
            l.setTruekey(data.getHeroName());
            l.setEnterType("HERO");
            list.add(l);
        }
        new BmobBatch().insertBatch(list).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if(e==null){
                    for(int i=0;i<list.size();i++){
                        BatchResult result = list.get(i);
                        BmobException ex =result.getError();
                        if(ex==null){
                            Log.e("Batch","第"+i+"个数据批量添加成功："+result.getCreatedAt()+","+result.getObjectId()+","+result.getUpdatedAt());
                        }else{
                            Log.e("Batch","第"+i+"个数据批量添加失败："+ex.getMessage()+","+ex.getErrorCode());
                        }
                    }
                }else{
                    Log.i("Batch","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.herolist_back_iv:
                finish();
                break;
            case R.id.herolist_overallBtn:

                break;
            case R.id.herolist_plotBtn:
                break;
            case R.id.herolist_arenaBtn:
                break;
            case R.id.herolist_challengeBtn:
                break;
        }
    }

    private void sortDatas(SORTTYPE sorttype){
        switch (sorttype.getTypeIndex()){
            case 0:

                Collections.sort(datas, new Comparator<HeroData>() {
                    @Override
                    public int compare(HeroData o1, HeroData o2) {
                        return o2.getHeroRate()[0]-o1.getHeroRate()[0];
                    }
                });
                break;
        }
    }

}
