package com.skypan.easytochewroot;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private int[] ints;
    private int positionIndex = -1;

    public ItemAdapter(int[] ints) {
        this.ints = ints;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item1, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                positionIndex = position;
                Toast.makeText(view.getContext(), ints[position] + "元", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_item.setText(ints[position] + "元");
        if (position == positionIndex) {//选中
            holder.tv_item.setTextColor(Color.WHITE);
            holder.tv_item.setBackgroundColor(Color.DKGRAY);
        } else {//未选中
            holder.tv_item.setTextColor(Color.BLACK);
            holder.tv_item.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return ints.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tv_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            tv_item = (TextView) view.findViewById(R.id.tv_item);
        }
    }

    //返回选中的id
    public int getPositionIndex() {
        return positionIndex;
    }
}

