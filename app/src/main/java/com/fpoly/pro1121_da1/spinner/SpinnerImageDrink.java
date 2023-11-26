package com.fpoly.pro1121_da1.spinner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fpoly.pro1121_da1.R;

public class SpinnerImageDrink extends BaseAdapter {
    int[] arrImg;
    Activity activity;

    public SpinnerImageDrink(int[] arrImg, Activity activity) {
        this.arrImg = arrImg;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return arrImg.length;
    }

    @Override
    public Object getItem(int i) {
        return arrImg[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.item_spinner_image_drink, viewGroup, false);
        ImageView imageView = view.findViewById(R.id.img_spinnerADD_drink);
        imageView.setImageResource(arrImg[i]);
        return view;
    }
}
