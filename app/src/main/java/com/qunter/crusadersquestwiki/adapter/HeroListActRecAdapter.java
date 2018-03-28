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
import com.qunter.crusadersquestwiki.entity.HeroData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public class HeroListActRecAdapter extends RecyclerView.Adapter<HeroListActRecAdapter.ViewHolder>{
    private List<HeroData> datas = new ArrayList<HeroData>();
    private Context context;
    private OnItemClickListener onItemClickListener;
    private int[] rankImageResource = new int[]{R.drawable.ic_rank_star_1,R.drawable.ic_rank_star_2,R.drawable.ic_rank_star_3,R.drawable.ic_rank_star_4,R.drawable.ic_rank_star_5};

    public HeroListActRecAdapter(Context context, List<HeroData> datas){
        this.context = context;
        this.datas = datas;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_herolist,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Glide.with(context).load(datas.get(position).getHeroPicUrl()).into(holder.hero_img);
        //Log.e("url", datas.get(position).getHeroPicUrl());
        holder.hero_name.setText(datas.get(position).getHeroName());
        holder.hero_rank_1.setImageResource(getRankImageResource(datas.get(position).getHeroRate()[0]));
        holder.hero_rank_2.setImageResource(getRankImageResource(datas.get(position).getHeroRate()[1]));
        holder.hero_rank_3.setImageResource(getRankImageResource(datas.get(position).getHeroRate()[2]));
        holder.hero_rank_4.setImageResource(getRankImageResource(datas.get(position).getHeroRate()[3]));
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

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView hero_img;
        private TextView hero_name;
        private ImageView hero_rank_1,hero_rank_2,hero_rank_3,hero_rank_4;
        public ViewHolder(View itemView) {
            super(itemView);
            hero_img = (ImageView) itemView.findViewById(R.id.item_herolist_img_iv);
            hero_name = (TextView) itemView.findViewById(R.id.item_herolist_name_tv);
            hero_rank_1 = (ImageView) itemView.findViewById(R.id.item_herolist_rank_1_iv);
            hero_rank_2 = (ImageView) itemView.findViewById(R.id.item_herolist_rank_2_iv);
            hero_rank_3 = (ImageView) itemView.findViewById(R.id.item_herolist_rank_3_iv);
            hero_rank_4 = (ImageView) itemView.findViewById(R.id.item_herolist_rank_4_iv);
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
    public void setOnItemClickListener(HeroListActRecAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    private int getRankImageResource(int index){
        if (index!=0)
            return rankImageResource[index-1];
        else
            return 0;
    }
}
