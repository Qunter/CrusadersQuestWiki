package com.qunter.crusadersquestwiki.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunter.crusadersquestwiki.R;
import com.qunter.crusadersquestwiki.entity.SkillData;

import java.util.List;

/**
 * Created by ldk on 17-11-27.
 */

public class SkillListRecAdapter extends RecyclerView.Adapter<SkillListRecAdapter.ViewHolder>{
    private Context context;
    private List<SkillData> datas;
    private OnItemClickListener onItemClickListener;

    public SkillListRecAdapter(Context context, List<SkillData> datas){
        this.context = context;
        this.datas = datas;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skilllist,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Glide.with(context).load(datas.get(position).getSkillImgUrl()).into(holder.skill_icon);
        //Log.e("url", datas.get(position).getHeroPicUrl());
        holder.skill_name.setText(datas.get(position).getSkillName());
        holder.skill_tpye.setText(datas.get(position).getSkillType());
        holder.skill_detail.setText(datas.get(position).getSkillDetail());
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView skill_icon;
        private TextView skill_name,skill_tpye,skill_detail;
        public ViewHolder(View itemView) {
            super(itemView);
            skill_icon = (ImageView) itemView.findViewById(R.id.item_skilllist_icon_iv);
            skill_name = (TextView) itemView.findViewById(R.id.item_skilllist_name_tv);
            skill_tpye = (TextView) itemView.findViewById(R.id.item_skilllist_tpye_tv);
            skill_detail = (TextView) itemView.findViewById(R.id.item_skilllist_detail_tv);
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
    public void setOnItemClickListener(SkillListRecAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
