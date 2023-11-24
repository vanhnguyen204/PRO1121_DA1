package com.fpoly.pro1121_da1.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Fragment.FragmentOrderDrink;
import com.fpoly.pro1121_da1.Fragment.FragmentTable;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Table;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.Viewholder>{
    ArrayList<Table> list;
    Activity activity;

    public TableAdapter(ArrayList<Table> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_table, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Table table = list.get(position);
        holder.tvTableID.setText("Mã bàn: "+table.getTableID());
        if (table.getStatus() == 1){
            holder.imgStatus.setImageResource(R.mipmap.record);
        }else {
            holder.imgStatus.setImageResource(R.mipmap.rec);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)activity).reloadFragment(new FragmentOrderDrink());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        ImageView imgTable, imgStatus;
        TextView tvTableID;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgTable = itemView.findViewById(R.id.image_item_recyclerviewTable);
            imgStatus = itemView.findViewById(R.id.img_status_table);
            tvTableID = itemView.findViewById(R.id.tv_tableID_recyclerviewTable);
        }
    }
}
