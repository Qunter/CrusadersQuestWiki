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
import com.qunter.crusadersquestwiki.entity.EquipmentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class EquipmentListRecAdapter extends RecyclerView.Adapter<EquipmentListRecAdapter.ViewHolder>{
    private List<EquipmentData> datas = new ArrayList<EquipmentData>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public EquipmentListRecAdapter(Context context, List<EquipmentData> datas){
        this.context = context;
        this.datas = datas;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equipmentlist,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Glide.with(context).load(datas.get(position).getEquipmentPicUrl()).into(holder.equipment_img);
        //Log.e("url", datas.get(position).getHeroPicUrl());
        holder.equipment_name.setText(datas.get(position).getEquipmentName());
        holder.equipment_rate.setText(datas.get(position).getEquipmentRate());
        holder.equipment_attack.setText(datas.get(position).getEquipmentAttack());
        holder.equipment_aspd.setText(datas.get(position).getEquipmentASPD());
        holder.equipment_for.setText(datas.get(position).getEquipmentForWho());
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
        private ImageView equipment_img;
        private TextView equipment_name,equipment_rate,equipment_attack,equipment_aspd,equipment_for;
        public ViewHolder(View itemView) {
            super(itemView);
            equipment_img = (ImageView) itemView.findViewById(R.id.item_equipmentList_img_iv);
            equipment_name = (TextView) itemView.findViewById(R.id.item_equipmentList_name_tv);
            equipment_rate = (TextView) itemView.findViewById(R.id.item_equipmentList_rate_tv);
            equipment_attack = (TextView) itemView.findViewById(R.id.item_equipmentList_attack_tv);
            equipment_aspd = (TextView) itemView.findViewById(R.id.item_equipmentList_aspd_tv);
            equipment_for = (TextView) itemView.findViewById(R.id.item_equipmentList_for_tv);
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
    public void setOnItemClickListener(EquipmentListRecAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
