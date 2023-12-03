package com.fpoly.pro1121_da1.Interface;

import android.widget.TextView;

import com.fpoly.pro1121_da1.model.Drink;

public interface MyOnItemClickListener {
    void onClick(Drink drink, int position, int quanity);
    void setDeleteDrink(Drink drink, int position, int quanttity);

}
