package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;

public class FragmentSettings extends Fragment {
    ImageView imgGotoAddUser, imgGotoAddSale;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgGotoAddUser = view.findViewById(R.id.arrow1_setting);
        imgGotoAddSale = view.findViewById(R.id.arrow3_setting);
        imgGotoAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).reloadFragment(new FragmentAddUser());
            }
        });
        imgGotoAddSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).reloadFragment(new FragmentSales());
            }
        });
    }
}