package com.fpoly.pro1121_da1.spinner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Ingredient;

import java.util.ArrayList;

public class SpinnerAddIngredientToDrink extends BaseAdapter {
    ArrayList<Ingredient> list;
    Activity activity;

    public SpinnerAddIngredientToDrink(ArrayList<Ingredient> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.item_spinner_addngredient_drink, viewGroup, false);
        ImageView imgIngredient = view.findViewById(R.id.img_spinnerAddIngredient_drink);
        TextView tvName = view.findViewById(R.id.tv_nameIngredient_spinnerAddIngredient_drink);
        TextView tvPreview = view.findViewById(R.id.tv_item_first);
        if (i == 0){
            imgIngredient.setVisibility(View.INVISIBLE);
            tvName.setVisibility(View.INVISIBLE);
            tvPreview.setVisibility(View.VISIBLE);
            tvPreview.setText("--- Chọn nguyên liệu ---");
        }else {
            tvPreview.setVisibility(View.INVISIBLE);
            imgIngredient.setVisibility(View.VISIBLE);
            tvName.setVisibility(View.VISIBLE);
            imgIngredient.setImageResource(list.get(i).getImage());
            tvName.setText(list.get(i).getName());
        }

        return view;
    }
}
