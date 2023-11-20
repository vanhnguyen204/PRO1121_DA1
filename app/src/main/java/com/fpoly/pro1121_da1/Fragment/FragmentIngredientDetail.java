package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.model.Ingredient;

public class FragmentIngredientDetail extends Fragment {
    private String getID;
    private IngredientDAO ingredientDAO;
    private ImageView img;
    private TextView tvID, tvName, tvDateAdd, tvDateExpiry, tvPrice, tvQuantity;
    private Button btnDelete, btnUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img = view.findViewById(R.id.img_ingredient_fragmentIngredient_Detail);
        tvID = view.findViewById(R.id.tv_ingredientID_fragmentIngredient_detail);
        tvName = view.findViewById(R.id.tv_nameIngredient_Detail);
        tvDateAdd = view.findViewById(R.id.tv_dateAddIngredient_Detail);
        tvDateExpiry = view.findViewById(R.id.tv_dateExpiryIngredient_Detail);
        tvPrice = view.findViewById(R.id.tv_priceIngredient_Detail);
        tvQuantity = view.findViewById(R.id.tv_quantityIngredient_Detail);
        btnDelete = view.findViewById(R.id.btn_delete_ingredient);
        btnUpdate = view.findViewById(R.id.btnUpdateIngredient_fragmentIngredientDetail);

        ingredientDAO = new IngredientDAO(getActivity(), new Dbhelper(getContext()));
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                getID = result.getString("KEY_ING_ID");

                Ingredient ingredient = ingredientDAO.getIngredientByID(getID);
                tvID.setText(String.format("Mã nguyên liệu: %s", ingredient.getIngredientID()));
                tvName.setText(String.format("Tên: %s", ingredient.getName()));

            }
        });
    }
}