package com.qunter.crusadersquestwiki.entity;

/**
 * Created by ldk on 17-11-27.
 */

public class SkillData {
    private String SkillName;
    private String SkillImgUrl;
    private String SkillType;
    private String SkillDetail;
    private String EquipmentDetailUrl;

    public String getSkillName() {
        return SkillName;
    }

    public void setSkillName(String skillName) {
        SkillName = skillName;
    }

    public String getSkillImgUrl() {
        return SkillImgUrl;
    }

    public void setSkillImgUrl(String skillImgUrl) {
        SkillImgUrl = skillImgUrl;
    }

    public String getSkillType() {
        return SkillType;
    }

    public void setSkillType(String skillType) {
        SkillType = skillType;
    }

    public String getSkillDetail() {
        return SkillDetail;
    }

    public void setSkillDetail(String skillDetail) {
        SkillDetail = skillDetail;
    }

    public String getEquipmentDetailUrl() {
        return EquipmentDetailUrl;
    }

    public void setEquipmentDetailUrl(String equipmentDetailUrl) {
        EquipmentDetailUrl = equipmentDetailUrl;
    }
}
