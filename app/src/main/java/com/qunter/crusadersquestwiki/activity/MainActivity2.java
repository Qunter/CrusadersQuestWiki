package com.qunter.crusadersquestwiki.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.base.BaseFragment;
import com.qunter.crusadersquestwiki.fragment.StrategiesFragment;

public class MainActivity2 extends BaseActivity {
    private BottomNavigationBar bottomBar;
    private FrameLayout content;
    private StrategiesFragment strategiesFragment;
    @Override
    protected void initVariablesAndService() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main2);
        bottomBar = (BottomNavigationBar) findViewById(R.id.bottomBar);
        content = (FrameLayout) findViewById(R.id.main_content);
        initBottomBar(bottomBar);
    }

    /**
     * 加载底部导航栏
     */
    private void initBottomBar(BottomNavigationBar bottomBar){
        bottomBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomBar.addItem(new BottomNavigationItem(R.drawable.ic_main_bottomavigation_find_black,"发现")
                .setInactiveIconResource(R.drawable.ic_main_bottomavigation_find_gray).setActiveColor(R.color.colorBlack));
        bottomBar.addItem(new BottomNavigationItem(R.drawable.ic_main_bottomavigation_strategies_black,"攻略")
                .setInactiveIconResource(R.drawable.ic_main_bottomavigation_strategies_gray).setActiveColor(R.color.colorBlack));
        bottomBar.addItem(new BottomNavigationItem(R.drawable.ic_main_bottomavigation_datas_black,"数据")
                .setInactiveIconResource(R.drawable.ic_main_bottomavigation_datas_gray).setActiveColor(R.color.colorBlack));
        bottomBar.addItem(new BottomNavigationItem(R.drawable.ic_main_bottomavigation_about_black,"关于我们")
                .setInactiveIconResource(R.drawable.ic_main_bottomavigation_about_gray).setActiveColor(R.color.colorBlack));
        bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        bottomBar.initialise();
    }


    private void initFragment(){
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

//        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
//        if(f1 == null){
//            f1 = new MyFragment("消息");
//            transaction.add(R.id.main_frame_layout, f1);
//        }
//        //隐藏所有fragment
//        hideFragment(transaction);
//        //显示需要显示的fragment
//        transaction.show(f1);

        //第二种方式(replace)，初始化fragment
        if(strategiesFragment == null){
            strategiesFragment = (StrategiesFragment) StrategiesFragment.getInstance();
        }
        transaction.replace(R.id.main_content, strategiesFragment);

        //提交事务
        transaction.commit();
    }


}
