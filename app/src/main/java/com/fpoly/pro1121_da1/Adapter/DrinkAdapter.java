package com.fpoly.pro1121_da1.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.fpoly.pro1121_da1.Interface.SenDataClick;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.database.NotificationDAO;
import com.fpoly.pro1121_da1.database.VoucherDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.model.Notification;
import com.fpoly.pro1121_da1.model.Voucher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.Viewholder> {

    DrinkDAO drinkDAO;
    ArrayList<Drink> list;
    IngredientDAO ingredientDAO;
    Activity atv;
    public SenDataClick senDataClick;

    public void sentData(SenDataClick senDataClick) {
        this.senDataClick = senDataClick;
    }

    public DrinkAdapter(ArrayList<Drink> list, Activity atv) {
        this.list = list;
        this.atv = atv;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_drink, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        ingredientDAO = new IngredientDAO(atv, new Dbhelper(atv));
        drinkDAO = new DrinkDAO(atv, new Dbhelper(atv));

        Drink drink = list.get(position);

        holder.tvNameDrink.setText(drink.getName());

        holder.tvDetail.setTextColor(Color.RED);
        holder.tvPrice.setText(String.valueOf(drink.getPrice()));
        holder.imgDrink.setImageResource(drink.getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(atv, "" + drink.getStatus(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senDataClick.sendData(drink);
            }
        });
        VoucherDAO voucherDAO = new VoucherDAO(atv, new Dbhelper(atv));
        Voucher voucher = voucherDAO.getVoucherByID(String.valueOf(drink.getVoucherID()));
        if (voucher.getPriceReduce() == 0) {
            holder.tvPriceReduce.setVisibility(View.INVISIBLE);
            holder.imgSale.setVisibility(View.INVISIBLE);

        } else {
            holder.tvPriceReduce.setVisibility(View.VISIBLE);
            holder.tvPriceReduce.setText("" + (drink.getPrice() - (drink.getPrice() * voucher.getPriceReduce() / 100)));
            holder.imgSale.setVisibility(View.VISIBLE);
            holder.tvPrice.setPaintFlags(holder.tvPriceReduce.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvPrice.setTextColor(Color.BLUE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgDrink, imgSale;
        TextView tvNameDrink, tvPrice, tvDetail, tvPriceReduce;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvNameDrink = itemView.findViewById(R.id.tv_name_drink);
            tvPrice = itemView.findViewById(R.id.tv_price_drink);
            tvDetail = itemView.findViewById(R.id.tv_seeDetail_drink);
            imgDrink = itemView.findViewById(R.id.img_drink);
            tvPriceReduce = itemView.findViewById(R.id.tv_priceReduce_itemRCVDrink);
            imgSale = itemView.findViewById(R.id.img_sale_drink);
        }
    }

    public boolean checkExpiry(String day1, String day2) {
        try {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = spf.parse(day1);
            Date date2 = spf.parse(day2);
            int compare = date1.compareTo(date2);
            if (compare > 0) {
                return true;
            } else if (compare < 0) {
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
