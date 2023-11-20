package com.fpoly.pro1121_da1.spinner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fpoly.pro1121_da1.R;

public class SpinnerImageIngredient extends BaseAdapter {
    int arrayImage[];
    Activity activity;

    public SpinnerImageIngredient(int[] arrayImage, Activity activity) {
        this.arrayImage = arrayImage;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return arrayImage.length;
    }

    @Override
    public Object getItem(int i) {
        return arrayImage[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view =inflater.inflate(R.layout.spinner_image_ingredient, viewGroup, false);
        ImageView img = view.findViewById(R.id.img_spinner_ingredient);
        img.setImageResource(arrayImage[i]);
        return view;
    }
}
