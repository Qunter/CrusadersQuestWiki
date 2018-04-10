package com.qunter.crusadersquestwiki.entity;

/**
 * Created by ldk on 4/10/18.
 */

public class StoryModeListActRecItemData {
    private int storyModeItemTitle;
    private int storyModeItemPicSource;
    private int storyModeItemEndString;
    private int storyModeItemSelectorString;

    public StoryModeListActRecItemData(int storyModeItemTitle, int storyModeItemPicSource, int storyModeItemEndString, int storyModeItemSelectorString){
        this.storyModeItemTitle = storyModeItemTitle;
        this.storyModeItemPicSource = storyModeItemPicSource;
        this.storyModeItemEndString = storyModeItemEndString;
        this.storyModeItemSelectorString = storyModeItemSelectorString;

    }

    public int getStoryModeItemTitle() {
        return storyModeItemTitle;
    }

    public void setStoryModeItemTitle(int storyModeItemTitle) {
        this.storyModeItemTitle = storyModeItemTitle;
    }

    public int getStoryModeItemPicSource() {
        return storyModeItemPicSource;
    }

    public void setStoryModeItemPicSource(int storyModeItemPicSource) {
        this.storyModeItemPicSource = storyModeItemPicSource;
    }

    public int getStoryModeItemEndString() {
        return storyModeItemEndString;
    }

    public void setStoryModeItemEndString(int storyModeItemEndString) {
        this.storyModeItemEndString = storyModeItemEndString;
    }

    public int getStoryModeItemSelectorString() {
        return storyModeItemSelectorString;
    }

    public void setStoryModeItemSelectorString(int storyModeItemSelectorString) {
        this.storyModeItemSelectorString = storyModeItemSelectorString;
    }
}
