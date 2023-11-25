package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.fpoly.pro1121_da1.Adapter.IngredientAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;

public class FragmentIngredient extends Fragment {
    EditText edtSearch;
    Button btnAdd;
    RecyclerView recyclerView;
    IngredientAdapter adapter;
    IngredientDAO ingredientDAO;
    ArrayList<Ingredient> list;
    ImageView imgBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgBack = view.findViewById(R.id.img_back_fragmentIngredient);
        ingredientDAO = new IngredientDAO(getContext(), new Dbhelper(getContext()));
        list = ingredientDAO.getAllIngredient();
        recyclerView = view.findViewById(R.id.recyclerView_ingredient);
        adapter = new IngredientAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        btnAdd = view.findViewById(R.id.btn_add_ingredient);
        User user = ((MainActivity)getActivity()).user;
        if (!user.getRole().equalsIgnoreCase("admin")){
            btnAdd.setVisibility(View.INVISIBLE);
        }else {
            btnAdd.setVisibility(View.VISIBLE);
        }
        ((MainActivity) getActivity()).chipNavigationBar.setVisibility(View.INVISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentHome());
                ((MainActivity) getActivity()).chipNavigationBar.setVisibility(View.VISIBLE);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentAddIngredient());
            }
        });
    }
}