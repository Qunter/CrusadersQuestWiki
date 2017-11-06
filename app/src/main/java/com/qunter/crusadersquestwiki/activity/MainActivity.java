package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.adapter.MainActRecAdapter;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.entity.MainActRecItemData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<MainActRecItemData> datas = new ArrayList<MainActRecItemData>();
    private int[] itemPicDatas = {R.drawable.ic_hero,R.drawable.ic_equipment,R.drawable.ic_skill};
    private int[] itemContentDatas = {R.string.hero_list_title,R.string.equipment_list_title,R.string.skill_list_title};
    @Override
    protected void initVariablesAndService() {
        for(int i=0;i<itemPicDatas.length;i++){
            MainActRecItemData data = new MainActRecItemData();
            data.setIv_imgResource(itemPicDatas[i]);
            data.setTv_content(getString(itemContentDatas[i]));
            datas.add(data);
        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.main_rec);
        //设置并列2行的layoutManager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        MainActRecAdapter adapter=new MainActRecAdapter(datas);
        adapter.setOnItemClickListener(new MainActRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                enterActivity(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * 进入对应activity
     */
    private void enterActivity(int position){
        switch (position){
            case 0:
                startActivityWithTwoExtra("listType",position,"listTitle",getString(itemContentDatas[position]),HeroTypeListActivity.class);
                break;
            case 1:
                startActivityWithTwoExtra("listType",position,"listTitle",getString(itemContentDatas[position]),HeroTypeListActivity.class);
                break;
            case 2:
                startActivity(SkillListActivity.class);
                break;
        }
    }


}
