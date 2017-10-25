package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.engine.DataCallback;
import com.qunter.crusadersquestwiki.engine.EquipmentDataGetterHellper;
import com.qunter.crusadersquestwiki.entity.HeroData;

import java.util.List;

/**
 * Created by Administrator on 2017/9/23.
 */

public class EquipmentListActivity extends BaseActivity implements DataCallback<HeroData> {
    private ImageView backBtn;
    private RecyclerView recyclerView;
    private EquipmentDataGetterHellper equipmentDataGetterHellper = new EquipmentDataGetterHellper();
    @Override
    protected void initVariablesAndService() {
        getHeroData();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_equipmentlist);
        backBtn = (ImageView) findViewById(R.id.equipment_list_backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.equipment_list_rec);

    }

    /**
     * jsoup爬取勇士数据
     */
    private void getHeroData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                equipmentDataGetterHellper.getDataAndSendCallback("剑士",EquipmentListActivity.this);
            }
        }).start();
    }

    @Override
    public void afterGetData(List<HeroData> datas) {

    }
}