package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;

/**
 * Created by Administrator on 2018/3/18.
 */

public class Season2ListActivity extends BaseActivity implements View.OnClickListener {
    private TextView season2ListTitle;
    private ImageView season2ListBackBtn;
    private CardView season2ListStoryBtn,season2ListChallengeBtn;
    private Intent intent;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_season2list);

        season2ListBackBtn = (ImageView) findViewById(R.id.season2List_backBtn_iv);
        season2ListBackBtn.setOnClickListener(this);

        season2ListTitle = (TextView) findViewById(R.id.season2List_title_tv);
        season2ListTitle.setText(getString(R.string.season2_list_title));

        season2ListStoryBtn = (CardView) findViewById(R.id.item_season2List_storyBtn);
        season2ListStoryBtn.setOnClickListener(this);

        season2ListChallengeBtn = (CardView) findViewById(R.id.item_season2List_challengeBtn);
        season2ListChallengeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.season2List_backBtn_iv:
                finish();
                break;
            case R.id.item_season2List_storyBtn:
                intent = new Intent(getApplicationContext(),WebDetailActivity.class);
                intent.putExtra("title",getString(R.string.season2StoryModeName)).putExtra("endString",getString(R.string.season2StoryModeName)).putExtra("selectorString",getString(R.string.season2StoryModeHtmlContentSelectorString));
                startActivity(intent);
                break;
            case R.id.item_season2List_challengeBtn:
                intent = new Intent(getApplicationContext(),WebDetailActivity.class);
                intent.putExtra("title",getString(R.string.season2ChallengeModeName)).putExtra("endString",getString(R.string.season2ChallengeModeName)).putExtra("selectorString",getString(R.string.season2ChallengeModeHtmlContentSelectorString));
                startActivity(intent);
                break;
        }
    }
}
