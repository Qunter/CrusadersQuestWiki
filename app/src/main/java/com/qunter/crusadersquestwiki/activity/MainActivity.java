package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.adapter.MainActRecAdapter;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.entity.MainActRecItemData;
import com.qunter.crusadersquestwiki.entity.WikiUrlType;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mainRecyclerView;
    private ImageView mainSearchBtn;
    private List<MainActRecItemData> datas = new ArrayList<MainActRecItemData>();
    private int[] itemPicDatas = {R.drawable.ic_hero,R.drawable.ic_equipment,R.drawable.ic_skill,R.drawable.ic_challenge_mode,R.drawable.ic_challenge_mode,R.drawable.ic_other,R.drawable.ic_setting};
    private int[] itemContentDatas = {R.string.hero_list_title,R.string.equipment_list_title,R.string.skill_list_title,R.string.StotyMode_list_title,R.string.ChallengeMode_list_title,R.string.other_list_title,R.string.setting_list_title};
    @Override
    protected void initVariablesAndService() {
        Bmob.initialize(this, "0b29944baa0b71a4a563ffedf4cc5b6b");
        BmobUpdateAgent.update(this);
        ifUserWikiTemporaryUrl();
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
        MainActRecAdapter adapter=new MainActRecAdapter(this,datas);
        adapter.setOnItemClickListener(new MainActRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                enterActivity(position);
            }
        });
        mainRecyclerView.setAdapter(adapter);

        mainSearchBtn = (ImageView) findViewById(R.id.main_search_iv);
        mainSearchBtn.setOnClickListener(this);
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
                startActivity(new Intent(getApplicationContext(),StoryModeListActivity.class).putExtra("listTitle",getString(R.string.StotyMode_list_title)));
                break;
            case 4:
                startActivity(new Intent(getApplicationContext(),WebDetailActivity.class).putExtra("title",getString(R.string.season2ChallengeModeName)).putExtra("endString",getString(R.string.season2ChallengeModeName)).putExtra("selectorString",getString(R.string.season2ChallengeModeHtmlContentSelectorString)));
                break;
            case 5:
                startActivity(new Intent(getApplicationContext(),OtherListActivity.class));
                break;
            case 6:
                startActivity(new Intent(getApplicationContext(),SettingListActivity.class));
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_search_iv:
                startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                break;
        }
    }
    /**
     * 查询是否使用wiki临时网页头部
     */
    private void ifUserWikiTemporaryUrl(){
        BmobQuery<WikiUrlType> query = new BmobQuery<WikiUrlType>();
        query.getObject("YDjM4447", new QueryListener<WikiUrlType>() {
            @Override
            public void done(WikiUrlType object, BmobException e) {
                if(e==null){
                    //获得playerName的信息
                    Log.e("done: ", ""+object.getIfTemporary());
                    SharedPreferences sharedPreferences = getSharedPreferences("IfTemporary", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("IfTemporary", object.getIfTemporary());
                    editor.apply();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
    }
}
