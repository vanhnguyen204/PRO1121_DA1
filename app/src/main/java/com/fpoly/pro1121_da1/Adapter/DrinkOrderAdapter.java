package com.fpoly.pro1121_da1.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.fpoly.pro1121_da1.Fragment.FragmentOrderDrink;
import com.fpoly.pro1121_da1.Interface.MyOnItemClickListener;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Ingredient;

import java.util.ArrayList;
import java.util.Map;

public class DrinkOrderAdapter extends RecyclerView.Adapter<DrinkOrderAdapter.Viewholder> {
    ArrayList<Drink> list;
    Activity activity;

    private MyOnItemClickListener myOnItemClickListener;

    public void setMyOnItemClick(MyOnItemClickListener myOnItemClickListener) {
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
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        Drink drink = list.get(position);
        int[] quantity = {1};
        DrinkDAO drinkDAO = new DrinkDAO(activity, new Dbhelper(activity));
        holder.imgDrink.setImageResource(drink.getImage());
        holder.tvNameDrink.setText(drink.getName());
        ArrayList<String> listDrink = FragmentOrderDrink.listDrinkID_checkbox;
        ArrayList<Integer> listQuantity2 = FragmentOrderDrink.listQuantityBack;
        Map<Integer, Integer> map = FragmentOrderDrink.map;

        if (listDrink != null) {
            for (int i = 0; i < listDrink.size(); i++) {
                if (listDrink.get(i).equalsIgnoreCase(String.valueOf(drink.getDrinkID()))) {
                    holder.chkOrder.setChecked(true);
                    quantity[0] = listQuantity2.get(i);
                    holder.tvQuantity.setText("Số lượng: " + quantity[0]);
                }
            }

            holder.imgMinus.setVisibility(View.VISIBLE);
            holder.imgPlus.setVisibility(View.VISIBLE);
        }
        holder.imgMinus.setVisibility(View.INVISIBLE);
        holder.imgPlus.setVisibility(View.INVISIBLE);

        holder.chkOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    myOnItemClickListener.onClick(drink, position, quantity[0]);
                    holder.imgMinus.setVisibility(View.VISIBLE);
                    holder.imgPlus.setVisibility(View.VISIBLE);
                } else {
                    quantity[0] = 1;
                    holder.tvQuantity.setText("Số lượng: " + quantity[0]);
                    holder.imgMinus.setVisibility(View.INVISIBLE);
                    holder.imgPlus.setVisibility(View.INVISIBLE);
                    myOnItemClickListener.setDeleteDrink(drink, position, quantity[0]);
                }

            }
        });

        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity[0]++;
                if (drink.getTypeOfDrink().equalsIgnoreCase("Pha chế")) {
                    ArrayList<Ingredient> ingredient = drinkDAO.getIngredientFromDrinkID(drink.getDrinkID());

                } else {
                  if (drink.getQuantity() < quantity[0]){
                      Toast.makeText(activity, "Đồ uống này đã hết, không thể tăng số lượng", Toast.LENGTH_SHORT).show();
                  }else {

                      map.put(drink.getDrinkID(), quantity[0]);
                      holder.tvQuantity.setText("Số lượng: " + quantity[0]);
                  }
                }
            }
        });

        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity[0]--;

                if (quantity[0] <= 0) {
                    quantity[0] = 1;
                    holder.tvQuantity.setText("Số lượng: " + quantity[0]);
                    Toast.makeText(activity, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                } else {
                    holder.tvQuantity.setText("Số lượng: " + quantity[0]);
                    map.put(drink.getDrinkID(), quantity[0]);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgDrink, imgPlus, imgMinus;
        TextView tvNameDrink, tvQuantity;
        CheckBox chkOrder;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgDrink = itemView.findViewById(R.id.img_item_orderDrink);
            tvNameDrink = itemView.findViewById(R.id.tv_nameDrink_item_orderDrink);
            chkOrder = itemView.findViewById(R.id.chk_selectToOrder);
            imgPlus = itemView.findViewById(R.id.img_increQuantity);
            tvQuantity = itemView.findViewById(R.id.tv_quantityDrinkFragmentOrder);
            imgMinus = itemView.findViewById(R.id.img_minus);
        }
    }
}
