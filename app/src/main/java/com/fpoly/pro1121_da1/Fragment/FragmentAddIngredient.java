package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.spinner.SpinnerImageIngredient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class FragmentAddIngredient extends Fragment {
    ImageView imgBack;
    EditText edtAddName, edtDateExpiry, edtPrice, edtQuantity;
    Button btnConfirmAdd;
    String getName, getDateADD, getDateExpiry, getPrice, getQuantity, getUnit = "Kg";
    IngredientDAO ingredientDAO;
    Spinner spinner, spinnerUnit;
    SpinnerImageIngredient spinnerImageIngredient;
    int getImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredientDAO = new IngredientDAO(getContext(), new Dbhelper(getContext()));
        imgBack = view.findViewById(R.id.img_back_fragmentAddIngredient);
        edtAddName = view.findViewById(R.id.edt_addNameIngredient);
        spinner = view.findViewById(R.id.spinner_image_fragmentAddIngredient);
        edtDateExpiry = view.findViewById(R.id.edt_addDateExpiry_fragmentIngredient);
        edtPrice = view.findViewById(R.id.edt_price_fragmentIngredient);
        edtQuantity = view.findViewById(R.id.edt_quantity_fragmentIngredient);
        btnConfirmAdd = view.findViewById(R.id.btn_add_fragmentIngredient);
        spinnerUnit = view.findViewById(R.id.spinner_unitIngredient);
        String[] unitArr = {"Kg", "Lít"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                unitArr
        );
        spinnerUnit.setAdapter(adapter);
        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getUnit = unitArr[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        int[] arrImage = new int[]{
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getImage = arrImage[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getImage = arrImage[0];
            }
        });
        btnConfirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addIngredientID = "ING" + new Random().nextInt(1000);

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();

                getName = edtAddName.getText().toString().trim();
                getDateADD = dateFormat.format(cal.getTime());
                getDateExpiry = edtDateExpiry.getText().toString().trim();
                getPrice = edtPrice.getText().toString().trim();
                getQuantity = edtQuantity.getText().toString().trim();
                if (getName.length() == 0){
                    Toast.makeText(getContext(), "Không được để trống tên nguyên liệu", Toast.LENGTH_SHORT).show();
                } else if (getDateExpiry.length() ==0) {
                    Toast.makeText(getContext(), "Không được để trống ngày hết hạn", Toast.LENGTH_SHORT).show();
                } else if (checkIsNumber(edtPrice,"Giá phải lớn hơn 0","Giá phải là số !")) {
                    
                } else if (checkIsNumber(edtQuantity, "Số lượng phải lớn hơn 0", "Số lượng phải là số !")) {
                    
                } else if (ingredientDAO.checkExistsIngredient(getName)) {
                    Toast.makeText(getContext(), "Đã có nguyên liệu này rồi, vui lòng quay lại để xem thêm thông tin !", Toast.LENGTH_SHORT).show();
                }else {
                    if (ingredientDAO.insertIngredient(new Ingredient(addIngredientID, getName, getDateADD, getDateExpiry, Integer.parseInt(getPrice), Double.parseDouble(getQuantity), getImage, getUnit), "Thêm nguyên liệu thành công", "Thêm nguyên liệu thất bại")) {
                        clearEditText();
                    }
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentIngredient());
            }
        });
    }

    public void clearEditText() {
        edtAddName.setText("");
        edtDateExpiry.setText("");
        edtQuantity.setText("");
        edtPrice.setText("");
    }
    public boolean checkIsNumber(EditText edt, String mess, String messError){
        try {
            int getNumber = Integer.parseInt(edt.getText().toString().trim());
            if (getNumber < 0){
                Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
                return true;
            }
        }catch (Exception e){
            Toast.makeText(getContext(), messError, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}