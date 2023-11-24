package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.model.Ingredient;

public class FragmentIngredientDetail extends Fragment {
    private String getID;
    private IngredientDAO ingredientDAO;
    private ImageView img, imgBack;
    private TextView tvID, tvName, tvDateAdd, tvDateExpiry, tvPrice, tvQuantity;
    private Button btnDelete, btnUpdate;
    private Ingredient ingredient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgBack = view.findViewById(R.id.img_back_fragmentIngredient_Detail);
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

                ingredient = ingredientDAO.getIngredientByID(getID);
                img.setImageResource(ingredient.getImage());
                tvID.setText(String.format("Mã nguyên liệu: %s", ingredient.getIngredientID()));
                tvName.setText(String.format("Tên: %s", ingredient.getName()));
                tvDateAdd.setText("Ngày thêm: " + ingredient.getDateAdd());
                tvDateExpiry.setText("Ngày hết hạn: " + ingredient.getDateAdd());
                tvPrice.setText("Giá: " + ingredient.getDateAdd());
                tvQuantity.setText("Số lượng: " + ingredient.getDateAdd());
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentIngredient());
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdateIngredient(ingredient);
            }
        });

    }

    Spinner spinner;
    EditText edtName, edtDateExpiry, edtPrice, edtQuantity;
    Button btnConfirmUpdate;
    String getName, getDateExpiry, getPrice, getQuantity;

    public void showDialogUpdateIngredient(Ingredient ingredient) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Style_AlertDialog_Corner);
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.alertdialog_update_ingredient, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        edtName = view1.findViewById(R.id.edt_nameIngredient_update);
        edtDateExpiry = view1.findViewById(R.id.edt_dateExpiry_fragmentIngredient_update);
        edtPrice = view1.findViewById(R.id.edt_price_fragmentIngredient_update);
        edtQuantity = view1.findViewById(R.id.edt_quantity_fragmentIngredient_update);
        btnConfirmUpdate = view1.findViewById(R.id.btn_confirmUpdate_fragmentIngredient_update);

        btnConfirmUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getName = edtName.getText().toString().trim();
                getDateExpiry = edtPrice.getText().toString().trim();
                getPrice = edtPrice.getText().toString().trim();
                getQuantity = edtQuantity.getText().toString().trim();
                if (getName.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống tên nguyên liệu", Toast.LENGTH_SHORT).show();
                } else if (getDateExpiry.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống ngày hết hạn", Toast.LENGTH_SHORT).show();
                } else if (getPrice.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống giá", Toast.LENGTH_SHORT).show();
                } else if (getQuantity.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống số lượng", Toast.LENGTH_SHORT).show();
                } else {
                    Ingredient ingredient1 = new Ingredient(ingredient.getIngredientID(), getName, ingredient.getDateAdd(), getDateExpiry, Integer.parseInt(getPrice), Double.parseDouble(getQuantity), ingredient.getImage());
                    if (ingredientDAO.updateIngredient(ingredient1, "Update nguyên liệu thành công", "Update nguyên liệu thất bại")) {
                        ((MainActivity) getActivity()).reloadFragment(new FragmentIngredientDetail());
                    }
                }
            }
        });
    }

}