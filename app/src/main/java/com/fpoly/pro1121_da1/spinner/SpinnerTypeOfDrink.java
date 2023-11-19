package com.fpoly.pro1121_da1.spinner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Drink;

import java.util.ArrayList;

public class SpinnerTypeOfDrink extends BaseAdapter {
    String typeDrink[];
    Activity activity;

    public SpinnerTypeOfDrink(String[] typeDrink, Activity activity) {
        this.typeDrink = typeDrink;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return typeDrink.length;
    }

    @Override
    public Object getItem(int i) {
        return typeDrink[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.item_spinner_typeofdrink, viewGroup, false);
        TextView tvType = view.findViewById(R.id.tv_typeOfDrink_spinner);
        tvType.setText(typeDrink[i]);
        return view;
    }
}
