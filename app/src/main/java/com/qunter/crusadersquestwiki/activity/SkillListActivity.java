package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.adapter.SkillListRecAdapter;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.engine.DataCallback;
import com.qunter.crusadersquestwiki.engine.SkillDataGetterHellper;
import com.qunter.crusadersquestwiki.entity.SkillData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/23.
 */

public class SkillListActivity extends BaseActivity implements DataCallback<SkillData> {
    private ImageView skillListBackBtn;
    private TextView skillListTitle;
    private RecyclerView recyclerView;
    private List<SkillData> datas = new ArrayList<SkillData>();
    private SkillDataGetterHellper skillDataGetterHellper = new SkillDataGetterHellper();
    private SkillListRecAdapter adapter;
    private String heroType = "null";
    private final int PUSHDATAINTORECYCLERVIEW = 0x00;
    /*
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PUSHDATAINTORECYCLERVIEW:
                    adapter = new SkillListRecAdapter(getApplicationContext(),datas);
                    adapter.setOnItemClickListener(new SkillListRecAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(getApplicationContext(),WebDetailActivity.class)
                                    .putExtra("url",datas.get(position).getEquipmentDetailUrl())
                                    .putExtra("title",datas.get(position).getEquipmentName())
                                    .putExtra("detailType",WebDetailActivity.DetailType.EQUIPMENT));
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };
    */
    @Override
    protected void initVariablesAndService() {
        //heroType = getIntent().getExtras().getString("heroType");
        getHeroData();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_skilllist);
    }

    /**
     * jsoup爬取勇士数据
     */
    private void getHeroData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                skillDataGetterHellper.getDataAndSendCallback("剑士特殊技能",SkillListActivity.this);
            }
        }).start();
    }

    @Override
    public void afterGetData(List<SkillData> datas) {

    }
}
