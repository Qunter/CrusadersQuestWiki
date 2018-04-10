package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.entity.KeywordData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by Administrator on 2018/3/23.
 */

public class SearchActivity extends BaseActivity {
    private List<KeywordData> datas = new ArrayList<>();
    private Toolbar searchSearchBar;
    private ListView searchList;
    private int ifSearchOver;
    private String partOfKeyword;
    private List<String> keys = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private final int SEARCHOVER=0x01,SEARCHNEXTTIME=0x02;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SEARCHOVER:
                    if (ifSearchOver==2){
                        keys.clear();
                        for(int i=0;i<datas.size();i++){
                            keys.add(datas.get(i).getKeyword());
                        }
                        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,keys);
                        searchList.setAdapter(adapter);
                        ifSearchOver=0;
                    }
                    break;
                case SEARCHNEXTTIME:
                    searchKeywordFromBmob(partOfKeyword,1);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        searchSearchBar = (Toolbar) findViewById(R.id.search_search_bar);
        setSupportActionBar(searchSearchBar);
        searchList = (ListView) findViewById(R.id.search_list);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,keys);
        searchList.setAdapter(adapter);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),WebDetailActivity.class);
                switch (datas.get(position).getEnterType()){
                    case "HERO":
                        intent.putExtra("selectorString",getString(R.string.heroHtmlContentSelectorString));
                        break;
                    case "EQUIPMENT":
                        intent.putExtra("selectorString",getString(R.string.equimentHtmlContentSelectorString));
                        break;
                    case "SKILL":
                        intent.putExtra("selectorString",getString(R.string.skillHtmlContentSelectorString));
                        break;
                }
                intent.putExtra("title",datas.get(position).getKeyword())
                        //.putExtra("detailType", WebDetailActivity.DetailType.fromTypeString())
                        .putExtra("endString",datas.get(position).getTruekey());
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_view,menu);
        //找到searchView MenuItem
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.menu_search_title));
        searchSearchBar.setTitle("");
        searchView.setSubmitButtonEnabled(true);//添加提交按钮
        searchView.setIconified(false);//设置searchView处于展开状态
        searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                partOfKeyword=query;
                searchKeywordFromBmob(query,0);
                //Toast.makeText(getApplicationContext(),"提交",Toast.LENGTH_SHORT).show();
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

    /**
     *
     */
    private void searchKeywordFromBmob(final String partOfKeyword, final int index){
        if(index==0)
            datas = new ArrayList<>();
        BmobQuery<KeywordData> query = new BmobQuery<KeywordData>();
        //查询playerName叫“比目”的数据
        //query.addWhereEqualTo("playerName", "比目");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(500);
        //执行查询方法
        query.setSkip(500*index);
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.setMaxCacheAge(TimeUnit.DAYS.toMillis(1));//此表示缓存一天
        query.findObjects(new FindListener<KeywordData>() {
            @Override
            public void done(List<KeywordData> object, BmobException e) {
                if(e==null){
                    Log.e("bmob","成功："+object.size());
                    for(KeywordData keywordData:object) {
                        if (keywordData.getKeyword().contains(partOfKeyword)){
                            datas.add(keywordData);
                            Log.e("bmob","成功："+keywordData.getKeyword()+" "+keywordData.getTruekey()+" "+keywordData.getEnterType());
                        }
                    }
                    ifSearchOver++;
                    if(object.size()<500){
                        handler.sendEmptyMessage(SEARCHOVER);
                    }else {
                        handler.sendEmptyMessage(SEARCHNEXTTIME);
                    }

                }else
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
            }
        });
    }

}
