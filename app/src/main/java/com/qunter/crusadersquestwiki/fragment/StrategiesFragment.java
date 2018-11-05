package com.qunter.crusadersquestwiki.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseFragment;

public class StrategiesFragment extends BaseFragment implements View.OnClickListener {
    private StrategiesFragment strategiesFragment;
    private TextView tv1,tv2,tv3,tv4;
    private LinearLayout iv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strategies,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv1.setOnClickListener(this);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tv2.setOnClickListener(this);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        tv3.setOnClickListener(this);
        tv4 = (TextView) view.findViewById(R.id.tv4);
        tv4.setOnClickListener(this);
        iv = (LinearLayout) view.findViewById(R.id.iv);
    }

    public StrategiesFragment getInstance() {
        if (strategiesFragment == null){
            strategiesFragment = new StrategiesFragment();
        }
        return strategiesFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv1:
                break;
            case R.id.tv2:
                moveIndex(1);
                break;
            case R.id.tv3:
                break;
            case R.id.tv4:
                break;
        }
    }

    private void moveIndex(int position){
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int screen_x = metric.widthPixels;     // 屏幕宽度（像素）
//        int screen_y = metric.heightPixels;   // 屏幕高度（像素）
        Animation translateAnimation = new TranslateAnimation(0,metric.widthPixels/4,0,0);//平移动画  从0,0,平移到100,100
        translateAnimation.setDuration(500);//动画持续的时间为0.5s
        translateAnimation.setFillEnabled(true);//使其可以填充效果从而不回到原地
        translateAnimation.setFillAfter(true);//不回到起始位置
        //如果不添加setFillEnabled和setFillAfter则动画执行结束后会自动回到远点
//        iv.setAnimation(translateAnimation);//给imageView添加的动画效果
        iv.startAnimation(translateAnimation);
//        translateAnimation.startNow();//动画开始执行 放在最后即可
    }
}
