package com.qunter.crusadersquestwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.adapter.StoryModeListActRecAdapter;
import com.qunter.crusadersquestwiki.base.BaseActivity;
import com.qunter.crusadersquestwiki.entity.StoryModeListActRecItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldk on 4/10/18.
 */

public class StoryModeListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ImageView storyModeListBackBtn;
    private TextView storyModeListTitle;
    private List<StoryModeListActRecItemData> datas = new ArrayList<>();
    private int[] storyModeItemTitles = {R.string.StotyMode_chapterN1_title,R.string.StotyMode_chapterN2_title,R.string.StotyMode_chapterN3_title,R.string.StotyMode_chapterN4_title,R.string.StotyMode_chapterN5_title,R.string.StotyMode_chapterH5_title,R.string.StotyMode_chapterN6_title,R.string.StotyMode_chapterH6_title,R.string.StotyMode_chapterN7_title,R.string.StotyMode_chapterH7_title,R.string.StotyMode_chapterN8_title};
    private int[] storyModeItemPicSources = {R.drawable.pic_story_chapter_1,R.drawable.pic_story_chapter_1,R.drawable.pic_story_chapter_1,R.drawable.pic_story_chapter_1,R.drawable.pic_story_chapter_5,R.drawable.pic_story_chapter_5,R.drawable.pic_story_chapter_6,R.drawable.pic_story_chapter_6,R.drawable.pic_story_chapter_7,R.drawable.pic_story_chapter_7,R.drawable.pic_story_chapter_8,};
    private int[] storyModeItemEndStrings = {R.string.StotyModeChapterN1T4HtmlContentEndString,R.string.StotyModeChapterN1T4HtmlContentEndString,R.string.StotyModeChapterN1T4HtmlContentEndString,R.string.StotyModeChapterN1T4HtmlContentEndString,R.string.StotyModeChapterN5HtmlContentEndString,R.string.StotyModeChapterH5HtmlContentEndString,R.string.StotyModeChapterN6HtmlContentEndString,R.string.StotyModeChapterH6HtmlContentEndString,R.string.StotyModeChapterN7HtmlContentEndString,R.string.StotyModeChapterH7HtmlContentEndString,R.string.StotyModeChapterN8HtmlContentEndString};
    private int[] storyModeItemSelectorStrings = {R.string.StotyModeChapterN1HtmlContentSelectorString,R.string.StotyModeChapterN2HtmlContentSelectorString,R.string.StotyModeChapterN3HtmlContentSelectorString,R.string.StotyModeChapterN4HtmlContentSelectorString,R.string.StotyModeChapterN5HtmlContentSelectorString,R.string.StotyModeChapterH5HtmlContentSelectorString,R.string.StotyModeChapterN6HtmlContentSelectorString,R.string.StotyModeChapterH6HtmlContentSelectorString,R.string.StotyModeChapterN7HtmlContentSelectorString,R.string.StotyModeChapterH7HtmlContentSelectorString,R.string.StotyModeChapterN8HtmlContentSelectorString};
    private String title = null;
    @Override
    protected void initVariablesAndService() {
        title = getIntent().getStringExtra("listTitle");
        for (int i=0;i<storyModeItemTitles.length;i++){
            datas.add(new StoryModeListActRecItemData(storyModeItemTitles[i],storyModeItemPicSources[i],storyModeItemEndStrings[i],storyModeItemSelectorStrings[i]));
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_stotymodelist);
        storyModeListBackBtn = (ImageView) findViewById(R.id.storyModeList_back_iv);
        storyModeListBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        storyModeListTitle = (TextView) findViewById(R.id.storyModeList_title_tv);
        storyModeListTitle.setText(title);

        recyclerView = (RecyclerView) findViewById(R.id.storyModeList_list_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        StoryModeListActRecAdapter adapter = new StoryModeListActRecAdapter(datas);
        adapter.setOnItemClickListener(new StoryModeListActRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getApplicationContext(),WebDetailActivity.class)
                        .putExtra("title",getString(datas.get(position).getStoryModeItemTitle()))
                        .putExtra("endString",getString(datas.get(position).getStoryModeItemEndString()))
                        .putExtra("selectorString",getString(datas.get(position).getStoryModeItemSelectorString())));
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
