package com.qunter.crusadersquestwiki.base;


import android.content.Context;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    private static BaseFragment baseFragment;
    private Context context = getContext().getApplicationContext();
    public static BaseFragment getInstance() {
        if (baseFragment == null){
            baseFragment = new BaseFragment();
        }
        return baseFragment;
    }
}
