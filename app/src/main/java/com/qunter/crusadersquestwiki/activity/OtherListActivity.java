package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;

/**
 * Created by Administrator on 2018/4/2.
 */

public class OtherListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView otherListBackBtn;
    private TextView otherListTitle;
    private LinearLayout otherListRuneAndWeaponImprintBtn;
    private Intent intent;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_otherlist);

        otherListBackBtn = (ImageView) findViewById(R.id.otherlist_back_iv);
        otherListBackBtn.setOnClickListener(this);

        otherListTitle = (TextView) findViewById(R.id.otherlist_title_tv);
        otherListTitle.setText(R.string.other_list_title);

        otherListRuneAndWeaponImprintBtn = (LinearLayout) findViewById(R.id.otherlist_runeAndWeaponImprint_Btn);
        otherListRuneAndWeaponImprintBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.otherlist_back_iv:
                finish();
                break;
            case R.id.otherlist_runeAndWeaponImprint_Btn:
                intent = new Intent(getApplicationContext(),WebDetailActivity.class);
                intent.putExtra("title",getString(R.string.otherListRuneAndWeaponImprintTitle)).putExtra("detailType",WebDetailActivity.DetailType.SEASON2_CHALLENGE).putExtra("endString",getString(R.string.otherListRuneAndWeaponImprintTitle));
                startActivity(intent);
                break;
        }
    }
}
