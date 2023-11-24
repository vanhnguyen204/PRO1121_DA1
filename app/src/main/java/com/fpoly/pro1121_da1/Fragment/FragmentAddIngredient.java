package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.spinner.SpinnerImageIngredient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class FragmentAddIngredient extends Fragment {
    ImageView imgBack;
    EditText edtAddName, edtDateExpiry, edtPrice, edtQuantity;
    Button btnConfirmAdd;
    String getName, getDateADD, getDateExpiry, getPrice, getQuantity;
    IngredientDAO ingredientDAO;
    Spinner spinner;
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getImage = arrImage[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

                if (ingredientDAO.insertIngredient(new Ingredient(addIngredientID, getName, getDateADD, getDateExpiry, Integer.parseInt(getPrice), Double.parseDouble(getQuantity), getImage), "Thêm nguyên liệu thành công", "Thêm nguyên liệu thất bại")){
                    clearEditText();
                }


            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).reloadFragment(new FragmentIngredient());
            }
        });
    }

    public void clearEditText() {
        edtAddName.setText("");
        edtDateExpiry.setText("");
        edtQuantity.setText("");
        edtPrice.setText("");
    }
}