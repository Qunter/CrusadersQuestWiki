package com.qunter.crusadersquestwiki.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.fragment.AboutFrgament;
import com.qunter.crusadersquestwiki.fragment.DatasFragment;
import com.qunter.crusadersquestwiki.fragment.FindFragment;
import com.qunter.crusadersquestwiki.fragment.StrategiesFragment;

public class MainActivity2 extends BaseActivity {
    private BottomNavigationBar bottomBar;
    private FrameLayout content;
    private FindFragment findFragment;
    private StrategiesFragment strategiesFragment;
    private DatasFragment datasFragment;
    private AboutFrgament aboutFrgament;
    enum WikiFragment{FindFragment,StrategiesFragment,DatasFragment,AboutFrgament}
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
                switch (position){
                    case 0:
                        changeFragment(WikiFragment.FindFragment);
                        break;
                    case 1:
                        changeFragment(WikiFragment.StrategiesFragment);
                        break;
                    case 2:
                        changeFragment(WikiFragment.DatasFragment);
                        break;
                    case 3:
                        changeFragment(WikiFragment.AboutFrgament);
                        break;
                }

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


    private void changeFragment(WikiFragment fragmentType){
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
        switch (fragmentType){
            case FindFragment:
                if (findFragment == null){
                    //findFragment = (FindFragment) FindFragment.getInstance();
                }
                //transaction.replace(R.id.main_content,findFragment);
                break;
            case StrategiesFragment:
                if (strategiesFragment == null){
                    strategiesFragment = new StrategiesFragment();
                }
                transaction.replace(R.id.main_content, strategiesFragment);
                break;
            case DatasFragment:
                if (datasFragment == null){
                    //datasFragment = (DatasFragmentI) DatasFragmentI.getInstance();
                }
                //transaction.replace(R.id.main_content,datasFragment);
                break;
            case AboutFrgament:
                if (aboutFrgament == null){
                    //aboutFrgament = (AboutFrgament) AboutFrgament.getInstance();
                }
                //transaction.replace(R.id.main_content,aboutFrgament);
                break;
        }
        //提交事务
        transaction.commit();
    }


}
