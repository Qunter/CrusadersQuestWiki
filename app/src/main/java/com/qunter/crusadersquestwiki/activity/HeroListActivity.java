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
import com.qunter.crusadersquestwiki.adapter.HeroListActRecAdapter;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.entity.HeroData;
import com.qunter.crusadersquestwiki.entity.HeroListActRecItemData;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/23.
 */

public class HeroListActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ImageView BackBtn;
    private List<HeroListActRecItemData> datas = new ArrayList<HeroListActRecItemData>();
    private List<HeroData> dataas = new ArrayList<HeroData>();
    private int[] itemPicDatas = {R.drawable.ic_warrior,R.drawable.ic_paladin,R.drawable.ic_archer,R.drawable.ic_hunter,R.drawable.ic_wizard,R.drawable.ic_priest};
    private int[] itemContentDatas = {R.string.warrior,R.string.paladin,R.string.archer,R.string.hunter,R.string.wizard,R.string.priest};


    @Override
    protected void initVariablesAndService() {
        for(int i=0;i<itemPicDatas.length;i++){
            HeroListActRecItemData data = new HeroListActRecItemData();
            data.setIv_imgResource(itemPicDatas[i]);
            data.setTv_content(getString(itemContentDatas[i]));
            datas.add(data);
        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_herolist);

        BackBtn = (ImageView) findViewById(R.id.herolist_backBtn);
        BackBtn.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.herolist_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        HeroListActRecAdapter adapter = new HeroListActRecAdapter(datas);
        adapter.setOnItemClickListener(new HeroListActRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(),HeroInfoActivity.class);
                intent.putExtra("heroType",getString(itemContentDatas[position]));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.herolist_backBtn:
                finish();
                break;
        }
    }


}
