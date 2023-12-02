package com.fpoly.pro1121_da1.Interface;

import android.widget.TextView;

import com.fpoly.pro1121_da1.model.Ingredient;

public interface SetTextRecyclerView {
    void onSetText(TextView textView, Ingredient ingredient, int position);
}
