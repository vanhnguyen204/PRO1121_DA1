package com.fpoly.pro1121_da1.builder;

import com.fpoly.pro1121_da1.model.Drink;

public interface Builder {
    Builder setDrinkId(int drinkId);
    Drink build();
}
