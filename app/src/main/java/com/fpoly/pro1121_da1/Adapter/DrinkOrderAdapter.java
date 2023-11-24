package com.fpoly.pro1121_da1.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Fragment.FragmentDrinkDetail;
import com.fpoly.pro1121_da1.Interface.MyOnItemClickListener;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Drink;

import java.util.ArrayList;

public class DrinkOrderAdapter extends RecyclerView.Adapter<DrinkOrderAdapter.Viewholder> {
    ArrayList<Drink> list;
    Activity activity;
    private MyOnItemClickListener myOnItemClickListener;
    public void setMyOnItemClick(MyOnItemClickListener myOnItemClickListener){
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public DrinkOrderAdapter(ArrayList<Drink> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_orderdrink, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Drink drink = list.get(position);
        holder.imgDrink.setImageResource(drink.getImage());
        holder.tvNameDrink.setText(drink.getName());
        holder.chkOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    myOnItemClickListener.onClick(drink);
                }else {
                    Toast.makeText(activity, "UnClicked", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgDrink;
        TextView tvNameDrink;
        CheckBox chkOrder;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgDrink = itemView.findViewById(R.id.img_item_orderDrink);
            tvNameDrink = itemView.findViewById(R.id.tv_nameDrink_item_orderDrink);
            chkOrder = itemView.findViewById(R.id.chk_selectToOrder);
        }
    }
}
