package com.fpoly.pro1121_da1.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Drink;

import java.util.ArrayList;

public class TopDrinkAdapter extends RecyclerView.Adapter<TopDrinkAdapter.Viewholder> {
    Activity activity;
    ArrayList<Drink> list;

    public TopDrinkAdapter(Activity activity, ArrayList<Drink> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_top5drinkhot, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Drink drink = list.get(holder.getAdapterPosition());
        holder.imgDrink.setImageResource(drink.getImage());
        holder.tvNameDrink.setText(drink.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        ImageView imgDrink;
        TextView tvNameDrink;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgDrink = itemView.findViewById(R.id.img_drink_top5);
            tvNameDrink = itemView.findViewById(R.id.tv_nameDrink_top5);
        }
    }
}
