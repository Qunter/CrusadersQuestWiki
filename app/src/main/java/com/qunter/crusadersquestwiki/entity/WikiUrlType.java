package com.qunter.crusadersquestwiki.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2018/4/11.
 */

public class WikiUrlType extends BmobObject {
    private Boolean IfTemporary;

    public Boolean getIfTemporary() {
        return IfTemporary;
    }

    public void setIfTemporary(Boolean ifTemporary) {
        IfTemporary = ifTemporary;
    }
}
