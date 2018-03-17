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

import java.util.ArrayList;
import java.util.List;

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
        handler.sendEmptyMessage(PUSHDATAINTORECYCLERVIEW);
        //Log.e("afterGetData", datas.size()+"" );
    }
}