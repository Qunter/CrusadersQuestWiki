package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.base.BaseActivity;

/**
 * Created by Administrator on 2017/9/15.
 */

public class SplashActivityBefore extends BaseActivity {
    private ImageView trueWikiIcon;
    private LinearLayout title;
    private LinearLayout wikiIcon;
    private boolean isFirst = true;
    private AnimationSet animSet_trueWikiIcon,animSet_title,animSet_wikiIcon;
    private Handler mHandler = new Handler();
    @Override
    protected void initVariablesAndService() {
        super.setIfTranslucent(true);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash_before);
        wikiIcon = (LinearLayout) findViewById(R.id.splash_wikiIcon);
        title = (LinearLayout) findViewById(R.id.splash_trueTitle);
        trueWikiIcon = (ImageView) findViewById(R.id.splash_trueWikiIcon);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus&&isFirst){
            isFirst = false;
            initAnim(title.getWidth());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    startAnim();
                }
            });
        }
    }
    private void initAnim(int title_width){
        //创建一个AnimationSet对象，参数为Boolean型，
        //true表示使用Animation的interpolator，false则是使用自己的
        animSet_trueWikiIcon = new AnimationSet(true);
        //创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        //设置动画执行的时间
        alphaAnimation.setDuration(600);
        //将alphaAnimation对象添加到AnimationSet当中
        animSet_trueWikiIcon.addAnimation(alphaAnimation);

        animSet_title = new AnimationSet(true);
        //创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(0, 1);
        //设置动画执行的时间
        alphaAnimation2.setStartOffset(700);
        alphaAnimation2.setDuration(1);
        //将alphaAnimation对象添加到AnimationSet当中
        animSet_title.addAnimation(alphaAnimation2);

        animSet_wikiIcon = new AnimationSet(true);
        //参数1～2：x轴的开始位置
        //参数3～4：x轴的结束位置
        //参数5～6：y轴的开始位置
        //参数7～8：y轴的结束位置
        TranslateAnimation translateAnimation =
                new TranslateAnimation(
                        Animation.ABSOLUTE,0,
                        Animation.ABSOLUTE,-(title_width+9),
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,0f);
        translateAnimation.setDuration(2000);
        translateAnimation.setStartOffset(800);
        animSet_wikiIcon.addAnimation(translateAnimation);
        animSet_wikiIcon.setFillAfter(true);
        animSet_wikiIcon.setFillBefore(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                finish();
            }
        },3500);
    }

    private void startAnim(){
        trueWikiIcon.startAnimation(animSet_trueWikiIcon);
        title.startAnimation(animSet_title);
        wikiIcon.startAnimation(animSet_wikiIcon);
    }

}
