package com.fpoly.pro1121_da1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.fpoly.pro1121_da1.Fragment.FragmentDrink;
import com.fpoly.pro1121_da1.Fragment.FragmentHome;
import com.fpoly.pro1121_da1.Fragment.FragmentSettings;
import com.fpoly.pro1121_da1.model.Drink;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    public ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentHome fragmentHome = new FragmentHome();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, fragmentHome).commit();
        chipNavigationBar = findViewById(R.id.buttonNavigation);


        chipNavigationBar.setItemSelected(R.id.home, true);

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                int home = R.id.home;
                switch (i) {

                }
                if (i == R.id.home) {
                    fragment = new FragmentHome();

                } else if (i == R.id.drink) {
                    fragment = new FragmentDrink();

                } else if (i == R.id.settings) {
                    fragment = new FragmentSettings();
                }


                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, fragment).commit();
                }
            }
        });
    }

    public void reloadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, fragment).commit();
    }
}