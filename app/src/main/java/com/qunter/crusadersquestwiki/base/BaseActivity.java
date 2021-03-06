package com.qunter.crusadersquestwiki.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by Administrator on 2017/9/15.
 */

public abstract class BaseActivity extends AppCompatActivity {
    //是否设置沉浸式
    private boolean IfTranslucent = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariablesAndService();
        initViews(savedInstanceState);
        if(IfTranslucent){
            initTranslucent();
        }
        //设置要跳转到的页面以及跳转时的动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    //初始化变量
    protected abstract void initVariablesAndService();
    //初始化控件
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 设置是否沉浸式  在子类中super调用
     */
    public void setIfTranslucent(boolean IfTranslucent){
        this.IfTranslucent = IfTranslucent;
    }
    /**
     * 动态设置状态栏  实现透明式状态栏
     */
    private void initTranslucent() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }
    /**
     * 重写方法以添加跳转动画
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //设置要跳转到的页面以及跳转时的动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
