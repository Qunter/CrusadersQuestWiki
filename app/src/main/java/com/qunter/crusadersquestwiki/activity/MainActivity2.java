package com.qunter.crusadersquestwiki.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.IndicatorViewPager.IndicatorFragmentPagerAdapter;
import com.shizhefei.view.indicator.slidebar.LayoutBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;

public class MainActivity2 extends BaseActivity {
    private BottomNavigationBar bottomBar;
    private IndicatorViewPager indicatorViewPager;
    private FixedIndicatorView indicator;
    private SViewPager viewPager;
    private LayoutInflater inflate;
    @Override
    protected void initVariablesAndService() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main2);
        indicator = (FixedIndicatorView) findViewById(R.id.main_indicator);
        viewPager = (SViewPager) findViewById(R.id.main_viewpager);
        inflate = LayoutInflater.from(getApplicationContext());
        initViewPager();
        bottomBar = (BottomNavigationBar) findViewById(R.id.bottomBar);
        initBottomBar(bottomBar);
    }
    /**
     * 加载ViewPager
     */
    private void initViewPager(){
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.RED, Color.GRAY));

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        //        // 禁止viewpager的滑动事件
        //        //viewPager.setCanScroll(false);
        //        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(4);

        indicator.setScrollBar(new LayoutBar(getApplicationContext(), R.layout.layout_slidebar, ScrollBar.Gravity.CENTENT_BACKGROUND));

        float unSelectSize = 10;
        float selectSize = unSelectSize * 1.2f;

        int selectColor = getResources().getColor(R.color.tab_top_text_2);
        int unSelectColor = getResources().getColor(R.color.tab_top_text_1);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
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
//    private class MyAdapter extends IndicatorFragmentPagerAdapter {
//        private String[] tabNames = {"勇士资料", "阵容一览", "游戏资源", "阵容搭配"};
//        private int[] tabIcons = {R.drawable.ic_about_48dp, R.drawable.ic_about_48dp, R.drawable.ic_about_48dp,
//                R.drawable.ic_about_48dp};
//        private LayoutInflater inflater;
//
//        public MyAdapter(FragmentManager fragmentManager) {
//            super(fragmentManager);
//            inflater = LayoutInflater.from(getApplicationContext());
//        }
//
//        @Override
//        public int getCount() {
//            return tabNames.length;
//        }
//
//        @Override
//        public View getViewForTab(int position, View convertView, ViewGroup container) {
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.tab_main, container, false);
//            }
//            TextView textView = (TextView) convertView;
//            textView.setText(tabNames[position]);
//            textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
//            return textView;
//        }
//
//        @Override
//        public Fragment getFragmentForPage(int position) {
//            FirstLayerFragment mainFragment = new FirstLayerFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString(FirstLayerFragment.INTENT_STRING_TABNAME, tabNames[position]);
//            bundle.putInt(FirstLayerFragment.INTENT_INT_INDEX, position);
//            mainFragment.setArguments(bundle);
//            return mainFragment;
//        }
//    }

    private class MyAdapter extends IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"勇士资料", "阵容一览", "游戏资源", "阵容搭配"};
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            Fragment mainFragment = new Fragment();
            return mainFragment;
        }
    }
}
