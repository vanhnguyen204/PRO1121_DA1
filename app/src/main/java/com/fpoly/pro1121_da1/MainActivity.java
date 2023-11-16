package com.fpoly.pro1121_da1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.fpoly.pro1121_da1.Fragment.FragmentHome;
import com.fpoly.pro1121_da1.builder.DrinkBuilder;
import com.fpoly.pro1121_da1.model.Drink;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Drink drink = new DrinkBuilder()
                .setDrinkId(1)
                .build();
        FragmentHome fragmentHome = new FragmentHome();
       getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, fragmentHome).commit();
    }
}