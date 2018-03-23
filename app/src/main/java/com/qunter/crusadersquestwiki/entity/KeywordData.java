package com.qunter.crusadersquestwiki.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by ldk on 3/23/18.
 */

public class KeywordData extends BmobObject{
    private String keyword;
    private String truekey;
    private String enterType;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTruekey() {
        return truekey;
    }

    public void setTruekey(String truekey) {
        this.truekey = truekey;
    }

    public String getEnterType() {
        return enterType;
    }

    public void setEnterType(String enterType) {
        this.enterType = enterType;
    }
}
