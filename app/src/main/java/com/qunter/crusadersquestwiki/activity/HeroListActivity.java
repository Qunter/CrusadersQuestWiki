package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.graphics.Color;
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
    private TextView heroListTitle,heroListResetSortBtn,heroListOverallBtn,heroListPlotBtn,heroListArenaBtn,heroListChallengeBtn;
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
                    loadNewDataToRec(datas);
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

        heroListResetSortBtn = (TextView) findViewById(R.id.herolist_resetSortBtn);
        heroListResetSortBtn.setOnClickListener(this);

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
            case R.id.herolist_resetSortBtn:
                loadNewDataToRec(datas);
                resetAllTv();
                break;
            case R.id.herolist_overallBtn:
                loadNewDataToRec(sortDatas(SORTTYPE.OVERALL,datas));
                resetAllTv();
                setOneTv(heroListOverallBtn,sortState[SORTTYPE.OVERALL.getTypeIndex()]);
                break;
            case R.id.herolist_plotBtn:
                loadNewDataToRec(sortDatas(SORTTYPE.PLOT,datas));
                resetAllTv();
                setOneTv(heroListPlotBtn,sortState[SORTTYPE.PLOT.getTypeIndex()]);
                break;
            case R.id.herolist_arenaBtn:
                loadNewDataToRec(sortDatas(SORTTYPE.ARENA,datas));
                resetAllTv();
                setOneTv(heroListArenaBtn,sortState[SORTTYPE.ARENA.getTypeIndex()]);
                break;
            case R.id.herolist_challengeBtn:
                loadNewDataToRec(sortDatas(SORTTYPE.CHALLENGE,datas));
                resetAllTv();
                setOneTv(heroListChallengeBtn,sortState[SORTTYPE.CHALLENGE.getTypeIndex()]);
                break;
        }
    }

    /**
     * 对勇士数据进行排序
     */
    private List<HeroData> sortDatas(SORTTYPE sorttype,List<HeroData> datas){
        List<HeroData> newDatas = new ArrayList<>(datas);
        final int index = sorttype.getTypeIndex();
        Collections.sort(newDatas, new Comparator<HeroData>() {
            @Override
            public int compare(HeroData o1, HeroData o2) {
                if (!sortState[index]){
                    return o1.getHeroRate()[index] == o2.getHeroRate()[index] ? 0 : (o1.getHeroRate()[index] > o2.getHeroRate()[index] ? -1 : 1);
                }else{
                    return o1.getHeroRate()[index] == o2.getHeroRate()[index] ? 0 : (o1.getHeroRate()[index] > o2.getHeroRate()[index] ? 1 : -1);
                }
            }

        });
        for (int i=0;i<sortState.length;i++){
            if (i==index)
                sortState[index] = !sortState[index];
            else
                sortState[i] = false;
        }
        return newDatas;
    }
    /**
     * 让recycleview加载新数据
     */
    private void loadNewDataToRec(final List<HeroData> datas){
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
    }
    /**
     * 恢复其他Tv特效
     */
    private void resetAllTv(){
        heroListOverallBtn.setTextColor(Color.BLACK);
        heroListPlotBtn.setTextColor(Color.BLACK);
        heroListArenaBtn.setTextColor(Color.BLACK);
        heroListChallengeBtn.setTextColor(Color.BLACK);
    }
    /**
     * 设置单个Tv特效
     */
    private void setOneTv(TextView tv,boolean ifUpSort){
        if (ifUpSort)
            tv.setTextColor(Color.RED);
        else
            tv.setTextColor(Color.GREEN);
    }
}
