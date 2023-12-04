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
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.TopDrink;

import java.util.ArrayList;
import java.util.List;

public class TopDrinkAdapter extends RecyclerView.Adapter<TopDrinkAdapter.Viewholder> {
    Activity activity;
    List<TopDrink> list;

    public TopDrinkAdapter(Activity activity, List<TopDrink> list) {
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
        TopDrink topDrink = list.get(position);
        DrinkDAO drinkDAO = new DrinkDAO(activity, new Dbhelper(activity));
        Drink drink = drinkDAO.getDrinkByID(String.valueOf(topDrink.getDrinkId()));
        holder.imgDrink.setImageResource(drink.getImage());
        holder.tvNameDrink.setText(drink.getName());
        holder.tvQuantity.setText("Đã bán: " + topDrink.getQuantity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        ImageView imgDrink;
        TextView tvNameDrink, tvQuantity;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgDrink = itemView.findViewById(R.id.img_drink_top5);
            tvNameDrink = itemView.findViewById(R.id.tv_nameDrink_top5);
            tvQuantity = itemView.findViewById(R.id.tv_quantityTopDrink);
        }
    }
}
