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
import com.qunter.crusadersquestwiki.adapter.EquipmentListRecAdapter;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.engine.DataCallback;
import com.qunter.crusadersquestwiki.engine.EquipmentDataGetterHellper;
import com.qunter.crusadersquestwiki.entity.EquipmentData;
import com.qunter.crusadersquestwiki.entity.HeroData;
import com.qunter.crusadersquestwiki.entity.KeywordData;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

/**
 * Created by Administrator on 2017/9/23.
 */

public class EquipmentListActivity extends BaseActivity implements DataCallback<EquipmentData> {
    private ImageView equipmentListBackBtn;
    private TextView equipmentListTitle;
    private RecyclerView recyclerView;
    private List<EquipmentData> datas = new ArrayList<EquipmentData>();
    private EquipmentDataGetterHellper equipmentDataGetterHellper = new EquipmentDataGetterHellper();
    private EquipmentListRecAdapter adapter;
    private String heroType = "null";
    private final int PUSHDATAINTORECYCLERVIEW = 0x00;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PUSHDATAINTORECYCLERVIEW:
                    adapter = new EquipmentListRecAdapter(getApplicationContext(),datas);
                    adapter.setOnItemClickListener(new EquipmentListRecAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(getApplicationContext(),WebDetailActivity.class)
                                    .putExtra("url",datas.get(position).getEquipmentDetailUrl())
                                    .putExtra("title",datas.get(position).getEquipmentName())
                                    .putExtra("detailType",WebDetailActivity.DetailType.EQUIPMENT)
                                    .putExtra("endString",datas.get(position).getEquipmentForWho()));
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
        getHeroData();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_equipmentlist);
        equipmentListBackBtn = (ImageView) findViewById(R.id.equipmentList_backBtn_iv);
        equipmentListBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        equipmentListTitle = (TextView) findViewById(R.id.equipmentList_title_tv);
        equipmentListTitle.setText(heroType+getString(R.string.equipment_list_title));

        recyclerView = (RecyclerView) findViewById(R.id.equipmentList_list_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    /**
     * jsoup爬取勇士数据
     */
    private void getHeroData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                equipmentDataGetterHellper.getDataAndSendCallback(heroType,EquipmentListActivity.this);
            }
        }).start();
    }

    @Override
    public void afterGetData(List<EquipmentData> datas) {
        this.datas = datas;
        //addDataToBmob(this.datas);
        handler.sendEmptyMessage(PUSHDATAINTORECYCLERVIEW);
        //Log.e("afterGetData", datas.size()+"" );
    }

    /**
     * 将关键词搜索需要的数据传输到bmob
     */
    private void addDataToBmob(List<EquipmentData> datas){
        List<BmobObject> list;
        int i=0;
        Log.i("Batch","共有数据"+datas.size()+"个");
        while (i!=datas.size()){
            list = new ArrayList<BmobObject>();
            for(int j=0;j<50&&i<datas.size();j++,i++){
                KeywordData l = new KeywordData();
                l.setKeyword(datas.get(i).getEquipmentName());
                l.setTruekey(datas.get(i).getEquipmentForWho());
                l.setEnterType("EQUIPMENT");
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

    }
}