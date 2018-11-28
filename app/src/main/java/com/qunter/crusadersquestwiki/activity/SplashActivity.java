package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;

import java.util.Random;

public class SplashActivity extends BaseActivity {
    private int picGoddess[] = {R.drawable.ic_goddess_desert,R.drawable.ic_goddess_forest,R.drawable.ic_goddess_ocean,R.drawable.ic_goddess_jokul,R.drawable.ic_goddess_volcano};
    private int picTitleGoddess[] = {R.drawable.ic_goddess_desert_title,R.drawable.ic_goddess_forest_title,R.drawable.ic_goddess_ocean_title,R.drawable.ic_goddess_jokul_title,R.drawable.ic_goddess_volcano_title};
    private ImageView splashGoddessIv,splashGoddessTitleIv;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        int index = new Random().nextInt(picGoddess.length);
        splashGoddessIv = (ImageView) findViewById(R.id.splash_goddessIv);
        splashGoddessIv.setImageResource(picGoddess[index]);
        splashGoddessTitleIv = (ImageView) findViewById(R.id.splash_goddessTitleIv);
        splashGoddessTitleIv.setImageResource(picTitleGoddess[index]);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                finish();
            }
        },2500);
    }
}
