package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;

/**
 * Created by ldk on 3/9/18.
 */

public class SettingListActivity extends BaseActivity {
    private ImageView settingListBackBtn;
    private TextView settingListTitle;
    private RecyclerView recyclerView;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settinglist);

        settingListBackBtn = (ImageView) findViewById(R.id.settinglist_back_iv);
        settingListBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settingListTitle = (TextView) findViewById(R.id.settinglist_title_tv);
        settingListTitle.setText(R.string.setting_list_title);

        recyclerView = (RecyclerView) findViewById(R.id.settinglist_list_rec);

    }
}
