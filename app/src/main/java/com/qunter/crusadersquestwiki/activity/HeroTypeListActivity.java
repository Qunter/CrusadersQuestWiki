package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.adapter.HeroListActRecAdapter;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.entity.HeroListActRecItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/23.
 */

public class HeroTypeListActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ImageView heroTypeListBackBtn;
    private TextView heroTypeListTitle;
    private List<HeroListActRecItemData> datas = new ArrayList<HeroListActRecItemData>();
    private int[] itemPicDatas = {R.drawable.ic_warrior,R.drawable.ic_paladin,R.drawable.ic_archer,R.drawable.ic_hunter,R.drawable.ic_wizard,R.drawable.ic_priest};
    private int[] itemContentDatas = {R.string.warrior,R.string.paladin,R.string.archer,R.string.hunter,R.string.wizard,R.string.priest};
    private final int HERO=0,EQUIPMENT=1;
    private int from = 0;
    private String title = null;

    @Override
    protected void initVariablesAndService() {
        from = getIntent().getIntExtra("listType",0);
        title = getIntent().getStringExtra("listTitle");
        for(int i=0;i<itemPicDatas.length;i++){
            HeroListActRecItemData data = new HeroListActRecItemData();
            data.setIv_imgResource(itemPicDatas[i]);
            data.setTv_content(getString(itemContentDatas[i]));
            datas.add(data);
        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_herotypelist);

        heroTypeListBackBtn = (ImageView) findViewById(R.id.heroList_backBtn);
        heroTypeListBackBtn.setOnClickListener(this);

        heroTypeListTitle = (TextView) findViewById(R.id.heroTypeList_title_tv);
        heroTypeListTitle.setText(title);

        recyclerView = (RecyclerView) findViewById(R.id.heroType_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        HeroListActRecAdapter adapter = new HeroListActRecAdapter(datas);
        adapter.setOnItemClickListener(new HeroListActRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (from){
                    case HERO:
                        startActivity(new Intent(getApplicationContext(),HeroListActivity.class)
                                .putExtra("heroType",getString(itemContentDatas[position])));
                        break;
                    case EQUIPMENT:
                        startActivity(new Intent(getApplicationContext(),EquipmentListActivity.class)
                                .putExtra("heroType",getString(itemContentDatas[position])));
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.heroList_backBtn:
                finish();
                break;
        }
    }


}
