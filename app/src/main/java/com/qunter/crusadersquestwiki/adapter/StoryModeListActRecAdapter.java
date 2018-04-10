package com.qunter.crusadersquestwiki.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.entity.HeroListActRecItemData;
import com.qunter.crusadersquestwiki.entity.StoryModeListActRecItemData;

import java.util.List;

/**
 * Created by Administrator on 2017/9/23.
 */

public class StoryModeListActRecAdapter extends RecyclerView.Adapter<StoryModeListActRecAdapter.ViewHolder>{
    private List<StoryModeListActRecItemData> datas;
    private OnItemClickListener onItemClickListener;
    //构造方法传入参数
    public StoryModeListActRecAdapter(List<StoryModeListActRecItemData> datas){
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storymodelist,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item_iv.setImageResource(datas.get(position).getStoryModeItemPicSource());
        holder.item_tv.setText(datas.get(position).getStoryModeItemTitle());
        if(onItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    //ViewHolder绑定控件
    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView item_iv;
        private TextView item_tv;
        public ViewHolder(View itemView) {
            super(itemView);
            item_iv = (ImageView) itemView.findViewById(R.id.item_storyModeList_img_iv);
            item_tv = (TextView) itemView.findViewById(R.id.item_storyModeList_name_tv);
        }
    }
    /**
     * 点击事件接口
     */
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    /**
     * 设置点击事件方法
     */
    public void setOnItemClickListener(StoryModeListActRecAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
