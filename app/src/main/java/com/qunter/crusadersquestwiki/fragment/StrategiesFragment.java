package com.qunter.crusadersquestwiki.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseFragment;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.LayoutBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;

public class StrategiesFragment extends BaseFragment{
    private ViewPager viewPager;
    private FixedIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;
    private StrategiesFragment strategiesFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_strategies,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (FixedIndicatorView) view.findViewById(R.id.indicator);
        indicator.setScrollBar(new LayoutBar(getContext(), R.layout.item_storymodelist, ScrollBar.Gravity.CENTENT));//滚动条也可以是一个Layout
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
    }

    public StrategiesFragment getInstance() {
        if (strategiesFragment == null){
            strategiesFragment = new StrategiesFragment();
        }
        return strategiesFragment;
    }

    private class MyPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"勇士资料", "阵容一览", "游戏资源", "阵容搭配"};

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }


        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.tab_top, container, false);
            }
            //TextView view = (TextView) convertView.findViewById(R.id.tv_tab);
            //view.setText(tabNames[position]);//设置Title的文字
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            return null;
        }
    }
}
