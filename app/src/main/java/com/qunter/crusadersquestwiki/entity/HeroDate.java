package com.qunter.crusadersquestwiki.entity;

/**
 * Created by Administrator on 2017/9/25.
 */

public class HeroDate {
    private String HeroName;
    private int HeroLevel;
    //暂无法高效获取
    private int HeroType;
    private int[] HeroRate = new int[5];
    private String HeroDetailUrl;
    private String HeroPicUrl;

    public String getHeroName() {
        return HeroName;
    }

    public void setHeroName(String heroName) {
        HeroName = heroName;
    }

    public int getHeroLevel() {
        return HeroLevel;
    }

    public void setHeroLevel(int heroLevel) {
        HeroLevel = heroLevel;
    }

    public int getHeroType() {
        return HeroType;
    }

    public void setHeroType(int heroType) {
        HeroType = heroType;
    }

    public int[] getHeroRate() {
        return HeroRate;
    }

    public void setHeroRate(int heroRateType,int heroRate) {
        HeroRate[heroRateType] = heroRate;
    }

    public String getHeroDetailUrl() {
        return HeroDetailUrl;
    }

    public void setHeroDetailUrl(String heroDetailUrl) {
        HeroDetailUrl = heroDetailUrl;
    }

    public String getHeroPicUrl() {
        return HeroPicUrl;
    }

    public void setHeroPicUrl(String heroPicUrl) {
        HeroPicUrl = heroPicUrl;
    }

}
