package com.fpoly.pro1121_da1.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.IngredientAdapterAddDrink;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.spinner.SpinnerAddIngredientToDrink;
import com.fpoly.pro1121_da1.spinner.SpinnerImageDrink;
import com.fpoly.pro1121_da1.spinner.SpinnerTypeOfDrink;

import java.util.ArrayList;

public class FragmentUpdateDrink extends Fragment {
    EditText edtNameDrink, edtPriceDrink, edtQuantityDrink, edtVoucher, edtDateAdd, edtDateExpiry;
    String getName, getPrice, getQuantity, getVoucher, getDateAdd, getDateExpiry, getTypeOfDrink = "Pha chế";
    Spinner spinner;
    Button btnConfirmUpdateDrink;
    DrinkDAO drinkDAO;
    ImageView imgShowIngredient;
    StringBuilder s;
    RecyclerView recyclerView;
    IngredientAdapterAddDrink ingredientAdapterUpdateDrink;
    Spinner spinnerImageDrink;
    String getIngredientID = "";
    int getImageDrink;

    public void setAdapterRecyclerView(ArrayList<Ingredient> list) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientAdapterUpdateDrink = new IngredientAdapterAddDrink(list, getActivity());
        recyclerView.setAdapter(ingredientAdapterUpdateDrink);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_drink, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drinkDAO = new DrinkDAO(getActivity(), new Dbhelper(getActivity()));
        spinnerImageDrink = view.findViewById(R.id.spinner_image_drink_fragmentUpdateDrink);
        spinner = view.findViewById(R.id.spinner_typeOfDrink_updateDrink);
        edtNameDrink = view.findViewById(R.id.edt_nameDrink_updateDrink);
        edtPriceDrink = view.findViewById(R.id.edt_priceDrink_updateDrink);
        edtQuantityDrink = view.findViewById(R.id.edt_quantity_updateDrink);
        edtVoucher = view.findViewById(R.id.edt_voucherDrink_updateDrink);
        edtDateAdd = view.findViewById(R.id.edt_dateAdd_updateDrink);
        edtDateExpiry = view.findViewById(R.id.edt_dateExpiry_updateDrink);
        imgShowIngredient = view.findViewById(R.id.img_addIngredient_fragmentUpdateDrink);
        recyclerView = view.findViewById(R.id.recyclerView_ingredient_fragmentUpdateDrink);
        btnConfirmUpdateDrink = view.findViewById(R.id.btnConfirm_updateDrink_fragmentUpdateDrink);
        int[] arrImageDrink = new int[]{
                R.mipmap.juice_watermelon,
                R.mipmap.peach_drink,
                R.mipmap.pineapple_drink,
                R.mipmap.coffee_cup,
                R.mipmap.bubble_tea,
                R.mipmap.lemon_juice,
                R.mipmap.liquor,
                R.mipmap.cola,
                R.mipmap.soju,
                R.mipmap.beer
        };
        SpinnerImageDrink spinnerDrinkImage = new SpinnerImageDrink(arrImageDrink, getActivity());

        spinnerImageDrink.setAdapter(spinnerDrinkImage);

        spinnerImageDrink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getImageDrink = arrImageDrink[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String type[] = new String[]{"Pha chế", "Đóng chai"};
        SpinnerTypeOfDrink spinnerTypeOfDrink = new SpinnerTypeOfDrink(type, getActivity());
        spinner.setAdapter(spinnerTypeOfDrink);
        listAddRecyclerView = new ArrayList<>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getTypeOfDrink = type[i];

                if (getTypeOfDrink.equalsIgnoreCase("Pha chế")) {
                    edtDateAdd.setEnabled(false);
                    edtDateAdd.setHint("Bỏ qua");
                    edtDateExpiry.setEnabled(false);
                    edtDateExpiry.setHint("Bỏ qua");

                } else {
                    edtDateAdd.setEnabled(true);
                    edtDateAdd.setHint("Ngày nhập");
                    edtDateExpiry.setEnabled(true);
                    edtDateExpiry.setHint("Ngày hết hạn");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imgShowIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogIngredient();
            }
        });

        getParentFragmentManager().setFragmentResultListener("KEY_UPDATE_DRINK", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
             int getDrinkID = result.getInt("KEY_DRINK_ID");
             Drink drinkUpdate = drinkDAO.getDrinkByID(String.valueOf(getDrinkID));

             btnConfirmUpdateDrink.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     getName = edtNameDrink.getText().toString().trim();
                     getPrice = edtPriceDrink.getText().toString().trim();
                     getQuantity = edtQuantityDrink.getText().toString().trim();
                     getVoucher = edtVoucher.getText().toString().trim();
                     getDateAdd = edtDateAdd.getText().toString().trim();
                     getDateExpiry = edtDateExpiry.getText().toString().trim();

                     if (getName.length() == 0) {

                     } else if (getPrice.length() == 0) {

                     } else if (getQuantity.length() == 0) {

                     } else if (getVoucher.length() == 0) {

                     } else if (getDateAdd.length() == 0) {

                     } else if (getDateExpiry.length() == 0) {

                     } else {

                     }

                     if (getTypeOfDrink.equalsIgnoreCase("Pha chế")) {
                         try {
                             if (drinkDAO.updateDrink(new Drink(
                                     drinkUpdate.getDrinkID(),
                                     s.toString(),
                                     Integer.parseInt(getVoucher),
                                     getName,
                                     getTypeOfDrink,
                                     getDateExpiry,
                                     getDateAdd,
                                     Integer.parseInt(getPrice),
                                     Integer.parseInt(getQuantity),
                                     getImageDrink))) {
                                 ((MainActivity) getActivity()).reloadFragment(new FragmentDrinkDetail());
                             }
                         } catch (Exception e) {
                             Toast.makeText(getContext(), "Nhầm loại đồ uống rồi", Toast.LENGTH_SHORT).show();
                         }

                     } else {
                         try {
                             if (drinkDAO.updateDrink(new Drink(
                                     drinkUpdate.getDrinkID(),
                                     " ",
                                     Integer.parseInt(getVoucher),
                                     getName,
                                     getTypeOfDrink,
                                     getDateExpiry,
                                     getDateAdd,
                                     Integer.parseInt(getPrice),
                                     Integer.parseInt(getQuantity),
                                     getImageDrink))) {
                                 ((MainActivity) getActivity()).reloadFragment(new FragmentDrinkDetail());
                             }
                         }catch (Exception e){

                         }

                     }
                 }
             });
            }
        });

    }
    Spinner spinnerIngredient;
    IngredientDAO ingredientDAO;
    SpinnerAddIngredientToDrink spinnerAddIngredientToDrink;
    ArrayList<Ingredient> listIngredient;
    ArrayList<Ingredient> listAddRecyclerView;

    public void showDialogIngredient() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Style_AlertDialog_Corner);
        LayoutInflater inflater = getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.alertdialog_addingredient_fragmentadddrink, null);
        builder.setView(viewDialog);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        spinnerIngredient = viewDialog.findViewById(R.id.spinner_addIngredient_fragmentAddDrink);
        ingredientDAO = new IngredientDAO(getActivity(), new Dbhelper(getActivity()));
        listIngredient = ingredientDAO.getAllIngredient();


        spinnerAddIngredientToDrink = new SpinnerAddIngredientToDrink(listIngredient, getActivity());
        spinnerIngredient.setAdapter(spinnerAddIngredientToDrink);
        spinnerIngredient.setSelection(0, false);
        spinnerIngredient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listAddRecyclerView.add(listIngredient.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s = new StringBuilder();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                for (int i = 0; i < listAddRecyclerView.size(); i++) {
                    s.append(listAddRecyclerView.get(i).getIngredientID() + " ");
                }
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                setAdapterRecyclerView(listAddRecyclerView);
            }
        });
    }
}