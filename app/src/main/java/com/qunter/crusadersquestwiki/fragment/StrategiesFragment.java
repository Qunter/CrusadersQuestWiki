package com.qunter.crusadersquestwiki.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseFragment;

public class StrategiesFragment extends BaseFragment{
    private ViewPager viewPager;
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
        viewPager.setAdapter(new MyPagerAdapter());
    }

    private class MyPagerAdapter extends PagerAdapter {
        private String[] tabNames = {"勇士资料", "阵容一览", "游戏资源", "阵容搭配"};

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabNames[position];
        }
    }
}
