package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.os.Handler;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;

/**
 * Created by Administrator on 2017/9/15.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void initVariablesAndService() {
        super.setIfTranslucent(true);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
                finish();
            }
        },2000);
    }

}
