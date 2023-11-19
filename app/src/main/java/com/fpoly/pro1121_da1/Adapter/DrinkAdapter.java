package com.fpoly.pro1121_da1.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Fragment.FragmentDrinkDetail;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.model.Drink;

import java.util.ArrayList;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.Viewholder> {

    DrinkDAO drinkDAO;
    ArrayList<Drink> list;
    Activity atv;

    public DrinkAdapter(ArrayList<Drink> list, Activity atv) {
        this.list = list;
        this.atv = atv;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycelview_drink, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        drinkDAO = new DrinkDAO(atv, new Dbhelper(atv));
        Drink drink = list.get(position);
        holder.tvNameDrink.setText(drink.getName());
        holder.imgDrink.setImageResource(R.mipmap.drink);
        holder.tvPrice.setText(String.valueOf(drink.getPrice()));
        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("KEY_DRINKID", list.get(position).getDrinkID()); // Put anything what you want
                FragmentManager manager = ((AppCompatActivity)atv).getSupportFragmentManager();
                manager.setFragmentResult("key", bundle);

                ((MainActivity)atv).reloadFragment(new FragmentDrinkDetail());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgDrink;
        TextView tvNameDrink, tvPrice, tvDetail;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvNameDrink = itemView.findViewById(R.id.tv_name_drink);
            tvPrice = itemView.findViewById(R.id.tv_price_drink);
            tvDetail = itemView.findViewById(R.id.tv_seeDetail_drink);
            imgDrink = itemView.findViewById(R.id.img_drink);
        }
    }
}
