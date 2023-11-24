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
import com.fpoly.pro1121_da1.model.Ingredient;

import java.util.ArrayList;

public class IngredientAdapterAddDrink extends RecyclerView.Adapter<IngredientAdapterAddDrink.Viewholder> {
    ArrayList<Ingredient> list;
    Activity activity;

    public IngredientAdapterAddDrink(ArrayList<Ingredient> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_addingredient_drink, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Ingredient ingredient = list.get(position);
        holder.imgIngredient.setImageResource(ingredient.getImage());
        holder.tvNameIngredient.setText(ingredient.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgIngredient;
        TextView tvNameIngredient;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgIngredient = itemView.findViewById(R.id.img_recyclerViewIngredient_drink);
            tvNameIngredient = itemView.findViewById(R.id.tv_nameIngredient_recyclerView);
        }
    }
}
