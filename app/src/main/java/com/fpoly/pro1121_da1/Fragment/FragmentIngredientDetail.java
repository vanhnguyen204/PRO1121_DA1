package com.fpoly.pro1121_da1.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
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
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.model.User;
import com.fpoly.pro1121_da1.spinner.SpinnerImageIngredient;

import java.util.ArrayList;

public class FragmentIngredientDetail extends Fragment {
    String getID;
    private IngredientDAO ingredientDAO;
    private ImageView img, imgBack;
    private TextView tvID, tvName, tvDateAdd, tvDateExpiry, tvPrice, tvQuantity;
    private Button btnDelete, btnUpdate;

    SpinnerImageIngredient spinnerImageIngredient;
    int getImage;
    String userName;

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

        getParentFragmentManager().setFragmentResultListener("KEY_INGREDIENT", this, new FragmentResultListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                getID = result.getString("KEY_ING_ID");
                Ingredient ingredient = ingredientDAO.getIngredientByID(getID);
                img.setImageResource(ingredient.getImage());
                tvID.setText(String.format("Mã nguyên liệu: %s", ingredient.getIngredientID()));
                tvName.setText(String.format("Tên: %s", ingredient.getName()));
                tvDateAdd.setText(String.format("Ngày thêm: %s \n", ingredient.getDateAdd()));
                tvDateExpiry.setText(String.format("Ngày hết hạn: %s", ingredient.getDateExpiry()));
                tvPrice.setText(String.format("Giá: %d VND", ingredient.getPrice()));
                tvQuantity.setText(String.format("Số lượng: %s", ingredient.getQuantity()));

                UserDAO userDAO = new UserDAO(getContext(), new Dbhelper(getContext()));
                User user = ((MainActivity) getActivity()).user;


                if (!user.getRole().equals("admin")) {
                    btnDelete.setVisibility(View.INVISIBLE);
                    btnUpdate.setVisibility(View.INVISIBLE);
                }


                Animation animation = new TranslateAnimation(0, 0, 0, 50);
                animation.setDuration(2200);
                animation.setRepeatMode(Animation.REVERSE);
                animation.setRepeatCount(Animation.INFINITE);
                img.setAnimation(animation);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showDialogUpdateIngredient(ingredient);

                    }
                });
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentIngredient());
            }
        });


    }

    Spinner spinner;
    EditText edtName, edtDateExpiry, edtPrice, edtQuantity;
    Button btnConfirmUpdate;
    String getName, getDateExpiry, getPrice, getQuantity;

    public void showDialogUpdateIngredient(Ingredient ingredient1) {
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
        spinner = view1.findViewById(R.id.spinner_image_fragmentIngredientDetail_update);
        btnConfirmUpdate.setText("Xác nhận");
        edtName.setText(ingredient1.getName());
        edtDateExpiry.setText(ingredient1.getDateExpiry());
        edtPrice.setText(String.valueOf(ingredient1.getPrice()));
        edtQuantity.setText(String.valueOf(ingredient1.getQuantity()));
        int arrImage[] = new int[]{
                R.mipmap.sugar,
                R.mipmap.milk,
                R.mipmap.matcha,
                R.mipmap.coffee_beans,
                R.mipmap.peach,
                R.mipmap.pineapple,
                R.mipmap.apple,
                R.mipmap.honey,
                R.mipmap.dragon_fruit,
                R.mipmap.orange,
                R.mipmap.lemons
        };

        spinnerImageIngredient = new SpinnerImageIngredient(arrImage, getActivity());
        spinner.setAdapter(spinnerImageIngredient);
        setSpinnerSelectDefaultImage(arrImage, ingredient1.getImage());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getImage = arrImage[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnConfirmUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getName = edtName.getText().toString().trim();
                getDateExpiry = edtDateExpiry.getText().toString().trim();
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
                    Ingredient ingredient2 = new Ingredient(ingredient1.getIngredientID(), getName, ingredient1.getDateAdd(), getDateExpiry, Integer.parseInt(getPrice), Double.parseDouble(getQuantity), getImage);
                    if (ingredientDAO.updateIngredient(ingredient2, "Update nguyên liệu thành công", "Update nguyên liệu thất bại")) {
                        img.setImageResource(getImage);

                        tvName.setText(String.format("Tên: %s", getName));

                        tvDateExpiry.setText(String.format("Ngày hết hạn: %s", getDateExpiry));
                        tvPrice.setText("Giá: " + getPrice);
                        tvQuantity.setText(String.format("Số lượng: %s", getQuantity));

                        alertDialog.dismiss();

                    }
                }
            }
        });
    }

    public void setSpinnerSelectDefaultImage(int[] listImage, int img) {
        for (int i = 0; i < listImage.length; i++) {
            if (listImage[i] == img) {
                spinner.setSelection(i);
            }
        }
    }
}