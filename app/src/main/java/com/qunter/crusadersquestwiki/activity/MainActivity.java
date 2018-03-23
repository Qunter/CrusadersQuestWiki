package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.adapter.MainActRecAdapter;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.entity.MainActRecItemData;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {
    private RecyclerView mainRecyclerView;
    private Toolbar mainSearchBar;
    private TextView mainTitle;
    private List<MainActRecItemData> datas = new ArrayList<MainActRecItemData>();
    private int[] itemPicDatas = {R.drawable.ic_hero,R.drawable.ic_equipment,R.drawable.ic_skill,R.drawable.ic_season2,R.drawable.ic_setting};
    private int[] itemContentDatas = {R.string.hero_list_title,R.string.equipment_list_title,R.string.skill_list_title,R.string.season2_list_title,R.string.setting_list_title};
    @Override
    protected void initVariablesAndService() {
        Bmob.initialize(this, "0b29944baa0b71a4a563ffedf4cc5b6b");
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
        mainRecyclerView = (RecyclerView) findViewById(R.id.main_list_rec);
        //设置并列2行的layoutManager
        mainRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        MainActRecAdapter adapter=new MainActRecAdapter(datas);
        adapter.setOnItemClickListener(new MainActRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                enterActivity(position);
            }
        });
        mainRecyclerView.setAdapter(adapter);

        mainTitle = (TextView) findViewById(R.id.main_title_tv);

        mainSearchBar = (Toolbar) findViewById(R.id.main_search_bar);
        setSupportActionBar(mainSearchBar);



    }

    /**
     * 进入对应activity
     */
    private void enterActivity(int position){
        switch (position){
            case 0:
            case 1:
            case 2:
                startActivity(new Intent(getApplicationContext(),HeroTypeListActivity.class)
                        .putExtra("listType",position)
                        .putExtra("listTitle",getString(itemContentDatas[position])));
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(),Season2ListActivity.class));
                break;
            case 4:
                startActivity(new Intent(getApplicationContext(),SettingListActivity.class));
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_view,menu);
        //找到searchView MenuItem
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.menu_search_title));
        mainSearchBar.setTitle("");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainTitle.setVisibility(View.GONE);

            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mainTitle.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(),"提交",Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //设置searchView展开时的最大宽度，大于父控件宽度时宽度为match_parent
        searchView.setMaxWidth(10000);
        return super.onCreateOptionsMenu(menu);
    }
}
