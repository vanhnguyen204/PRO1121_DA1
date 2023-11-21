package com.fpoly.pro1121_da1.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.spinner.SpinnerTypeOfDrink;


public class FragmentDrinkDetail extends Fragment {
    int getDrinkID;
    DrinkDAO drinkDAO;

    ImageView imgBack;
    Drink drink;
    TextView tvDrinkID, tvIngredientID, tvVoucherID, tvNameDrink, tvTypeOfDrink, tvDateAdd, tvDateExpiry, tvPrice, tvQuantity;
    Button btnDelete, btnUpdate;

    public void findID(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_drink_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDrinkID = view.findViewById(R.id.tv_drinkID_fragmentDrinkDeTail);
        tvIngredientID = view.findViewById(R.id.tv_ingredientID_fragmentDrinkDeTail);
        tvVoucherID = view.findViewById(R.id.tv_voucherID_fragmentDrinkDeTail);
        tvNameDrink = view.findViewById(R.id.tv_nameDrink_fragmentDrinkDeTail);
        tvTypeOfDrink = view.findViewById(R.id.tv_typeOfDrink_fragmentDrinkDeTail);
        tvDateAdd = view.findViewById(R.id.tv_dateAdd_fragmentDrinkDeTail);
        tvDateExpiry = view.findViewById(R.id.tv_dateExpiry_fragmentDrinkDeTail);
        tvPrice = view.findViewById(R.id.tv_price_fragmentDrinkDeTail);
        tvQuantity = view.findViewById(R.id.tv_quantity_fragmentDrinkDeTail);
        imgBack = view.findViewById(R.id.img_back_fragmentDrinkDetail);
        drinkDAO = new DrinkDAO(getContext(), new Dbhelper(getContext()));
        btnUpdate = view.findViewById(R.id.btnUpdateDrink_fragmentDrinkDetail);
        btnDelete = view.findViewById(R.id.btnDeleteDrink_fragmentDrinkDetail);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                getDrinkID = result.getInt("KEY_DRINKID");
                Toast.makeText(getContext(), "" + getDrinkID, Toast.LENGTH_SHORT).show();
                drink = drinkDAO.getDrinkByID(String.valueOf(getDrinkID));
                tvDrinkID.setText("Mã đồ uống: " + drink.getDrinkID());
                tvIngredientID.setText("Mã nguyên liệu: " + drink.getIngredientID());
                tvVoucherID.setText("Mã giảm giá:" + drink.getVoucherID());
                tvNameDrink.setText("Tên đồ uống: " + drink.getName());
                tvTypeOfDrink.setText("Loại đồ uống: " + drink.getTypeOfDrink());
                tvDateAdd.setText("Ngày thêm: " + drink.getDateAdd());
                tvDateExpiry.setText("Ngày hết hạn: " + drink.getDateExpiry());
                tvPrice.setText("Giá: " + drink.getPrice());
                tvQuantity.setText("Số lượng: " + drink.getQuantity());


            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogUpdateDrink(getDrinkID, drink);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentDrink());
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có muốn xoá đồ uống này không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        drinkDAO.deleteDrink(drink);
                        ((MainActivity)getActivity()).reloadFragment(new FragmentDrink());
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });

    }
    Spinner spinner;
    Button btnConfirmUpdate;
    EditText edtNameDrink, edtPriceDrink, edtQuantityDrink, edtVoucher, edtDateAdd, edtDateExpiry, edtIngredientID;
    String getName, getPrice, getQuantity, getVoucher, getDateAdd, getDateExpiry, getIngredientID, getTypeOfDrink = "Pha chế";
    public void showAlertDialogUpdateDrink(int drinkID, Drink drink) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Style_AlertDialog_Corner);
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.alertdialog_update_drink, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        edtNameDrink = view1.findViewById(R.id.edt_nameDrink_updateDrink);
        edtPriceDrink = view1.findViewById(R.id.edt_priceDrink_updateDrink);
        edtQuantityDrink = view1.findViewById(R.id.edt_quantity_updateDrink);
        edtVoucher = view1.findViewById(R.id.edt_voucherDrink_updateDrink);
        edtDateAdd = view1.findViewById(R.id.edt_dateAdd_updateDrink);
        edtDateExpiry = view1.findViewById(R.id.edt_dateExpiry_updateDrink);
        edtIngredientID = view1.findViewById(R.id.edt_ingredientID_updateDrink);
        edtNameDrink.setText(drink.getName());
                edtPriceDrink.setText(String.valueOf(drink.getPrice()));
        edtQuantityDrink.setText(String.valueOf(drink.getQuantity()));
                edtVoucher.setText(drink.getVoucherID()+"");
        edtDateAdd.setText(drink.getDateAdd());
                edtDateExpiry.setText(drink.getDateExpiry());
        edtIngredientID.setText(drink.getIngredientID()+"");

        btnConfirmUpdate = view1.findViewById(R.id.btnConfirm_updateDrink);
        spinner = view1.findViewById(R.id.spinner_typeOfDrink_updateDrink);
        String type[] = new String[]{"Pha chế", "Đóng chai"};
        SpinnerTypeOfDrink spinnerTypeOfDrink = new SpinnerTypeOfDrink(type, getActivity());
        spinner.setAdapter(spinnerTypeOfDrink);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getTypeOfDrink = type[i];
                Toast.makeText(getContext(), ""+getTypeOfDrink, Toast.LENGTH_SHORT).show();
                if (getTypeOfDrink.equalsIgnoreCase("Pha chế")){
                    edtDateAdd.setEnabled(false);
                    edtDateAdd.setHint("Bỏ qua");
                    edtDateExpiry.setEnabled(false);
                    edtDateExpiry.setHint("Bỏ qua");
                    edtIngredientID.setEnabled(true);
                    edtQuantityDrink.setEnabled(false);
                }else {
                    edtDateAdd.setEnabled(true);
                    edtDateAdd.setHint("Ngày nhập");
                    edtDateExpiry.setEnabled(true);
                    edtDateExpiry.setHint("Ngày hết hạn");
                    edtIngredientID.setEnabled(false);
                    edtIngredientID.setHint("Bỏ qua");
                    edtQuantityDrink.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnConfirmUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getName = edtNameDrink.getText().toString().trim();
                getPrice = edtPriceDrink.getText().toString().trim();
                getQuantity = edtQuantityDrink.getText().toString().trim();
                getVoucher = edtVoucher.getText().toString().trim();
                getDateAdd = edtDateAdd.getText().toString().trim();
                getDateExpiry = edtDateExpiry.getText().toString().trim();
                getIngredientID = edtIngredientID.getText().toString().trim();


                    if (drinkDAO.updateDrink(new Drink(drinkID,Integer.parseInt(getIngredientID), Integer.parseInt(getVoucher), getName, getTypeOfDrink, getDateExpiry, getDateAdd, Integer.parseInt(getPrice), Integer.parseInt(getQuantity)))) {
                       alertDialog.dismiss();
                        ((MainActivity)getActivity()).reloadFragment(new FragmentDrink());
                    }

            }
        });
    }
}