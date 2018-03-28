package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.engine.permission.PermissionManager;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

/**
 * Created by ldk on 3/9/18.
 */

public class SettingListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView settingListBackBtn;
    private TextView settingListTitle;
    private LinearLayout settingListUpgradeBtn,settingListAboutBtn;
    private BmobUpdateAgent bmobUpdateAgent;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settinglist);

        settingListBackBtn = (ImageView) findViewById(R.id.settinglist_back_iv);
        settingListBackBtn.setOnClickListener(this);

        settingListTitle = (TextView) findViewById(R.id.settinglist_title_tv);
        settingListTitle.setText(R.string.setting_list_title);

        settingListUpgradeBtn = (LinearLayout) findViewById(R.id.settinglist_upgrade_Btn);
        settingListUpgradeBtn.setOnClickListener(this);

        settingListAboutBtn = (LinearLayout) findViewById(R.id.settinglist_about_Btn);
        settingListAboutBtn.setOnClickListener(this);

        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                // TODO Auto-generated method stub
                //根据updateStatus来判断更新是否成功
                Log.e("bmob","成功："+updateStatus);
                switch (updateStatus){
                    case 0:
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(),"当前版本已是最新版本",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settinglist_back_iv:
                finish();
                break;
            case R.id.settinglist_upgrade_Btn:
                //调试时使用该代码一键生成版本表  无实际功能
                //BmobUpdateAgent.initAppVersion();
                //检查更新
                PermissionManager.with(this).permissions("android.permission.READ_PHONE_STATE").addRequestCode(1).request();
                bmobUpdateAgent.forceUpdate(getApplicationContext());
                break;
            case R.id.settinglist_about_Btn:
                Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
                intent.putExtra("title",getString(R.string.SettingListAboutTitle));
                startActivity(intent);
                break;
        }
    }

}
