package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;

/**
 * Created by Administrator on 2018/3/17.
 */

public class AboutActivity extends BaseActivity {
    private TextView aboutTitle;
    private ImageView aboutBackBtn;
    private String title;
    @Override
    protected void initVariablesAndService() {
        title = getIntent().getStringExtra("title");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);

        aboutTitle = (TextView) findViewById(R.id.about_title_tv);
        aboutTitle.setText(title);

        aboutBackBtn = (ImageView) findViewById(R.id.about_backBtn_iv);
        aboutBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
