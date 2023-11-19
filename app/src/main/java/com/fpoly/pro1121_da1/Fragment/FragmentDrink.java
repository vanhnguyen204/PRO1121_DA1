package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.DrinkAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.spinner.SpinnerTypeOfDrink;

import java.util.ArrayList;


public class FragmentDrink extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Drink> list;
    DrinkDAO drinkDAO;
    EditText edtSearch;
    DrinkAdapter adapter;
    Button btnAddDrink;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__drink, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drinkDAO = new DrinkDAO(getContext(), new Dbhelper(getContext()));
        btnAddDrink = view.findViewById(R.id.btn_add_drink);
        edtSearch = view.findViewById(R.id.edt_search_fragmentDrink);
        list = drinkDAO.getAllDrink();
        recyclerView = view.findViewById(R.id.rcv_drink);

        adapter = new DrinkAdapter(list, getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        btnAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogAddDrink();
            }
        });
        ArrayList<Drink> listClone = drinkDAO.getAllDrink();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();

                String getTextFromEdt = charSequence.toString().trim();
                if (getTextFromEdt.length() == 0) {
                    recyclerView.setAdapter(new DrinkAdapter(listClone, getActivity()));
                } else {
                    for (int index = 0 ; index < listClone.size(); index++) {
                        if (listClone.get(index).getName().equalsIgnoreCase(getTextFromEdt)) {

                            list.add(listClone.get(index));
                        }
                    }
                    recyclerView.setAdapter(new DrinkAdapter(list, getActivity()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    EditText edtNameDrink, edtPriceDrink, edtQuantityDrink, edtVoucher, edtDateAdd, edtDateExpiry, edtIngredientID;
    String getName, getPrice, getQuantity, getVoucher, getDateAdd, getDateExpiry, getIngredientID, getTypeOfDrink = "Pha chế";
    Spinner spinner;
    Button btnConfirmAddDrink;


    public void showAlertDialogAddDrink() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Style_AlertDialog_Corner);
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.alertdialog_add_drink, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        edtNameDrink = view1.findViewById(R.id.edt_nameDrink_addDrink);
        edtPriceDrink = view1.findViewById(R.id.edt_priceDrink_addDrink);
        edtQuantityDrink = view1.findViewById(R.id.edt_quantity_addDrink);
        edtVoucher = view1.findViewById(R.id.edt_voucherDrink_addDrink);
        edtDateAdd = view1.findViewById(R.id.edt_dateAdd_addDrink);
        edtDateExpiry = view1.findViewById(R.id.edt_dateExpiry_addDrink);
        edtIngredientID = view1.findViewById(R.id.edt_ingredientID_addDrink);
        btnConfirmAddDrink = view1.findViewById(R.id.btnConfirm_addDrink);
        spinner = view1.findViewById(R.id.spinner_typeOfDrink_addDrink);
        String type[] = new String[]{"Pha chế", "Đóng chai"};
        SpinnerTypeOfDrink spinnerTypeOfDrink = new SpinnerTypeOfDrink(type, getActivity());
        spinner.setAdapter(spinnerTypeOfDrink);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getTypeOfDrink = type[i];

                if (getTypeOfDrink.equalsIgnoreCase("Pha chế")) {
                    edtDateAdd.setEnabled(false);
                    edtDateAdd.setHint("Bỏ qua");
                    edtDateExpiry.setEnabled(false);
                    edtDateExpiry.setHint("Bỏ qua");
                    edtIngredientID.setEnabled(true);
                } else {
                    edtDateAdd.setEnabled(true);
                    edtDateAdd.setHint("Ngày nhập");
                    edtDateExpiry.setEnabled(true);
                    edtDateExpiry.setHint("Ngày hết hạn");
                    edtIngredientID.setEnabled(false);
                    edtIngredientID.setHint("Bỏ qua");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnConfirmAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getName = edtNameDrink.getText().toString().trim();
                getPrice = edtPriceDrink.getText().toString().trim();
                getQuantity = edtQuantityDrink.getText().toString().trim();
                getVoucher = edtVoucher.getText().toString().trim();
                getDateAdd = edtDateAdd.getText().toString().trim();
                getDateExpiry = edtDateExpiry.getText().toString().trim();
                getIngredientID = edtIngredientID.getText().toString().trim();
                if (getName.length() == 0) {

                } else if (getPrice.length() == 0) {

                } else if (getQuantity.length() == 0) {

                } else if (getVoucher.length() == 0) {

                } else if (getDateAdd.length() == 0) {

                } else if (getDateExpiry.length() == 0) {

                } else if (getIngredientID.length() == 0) {

                } else {

                }

                if (drinkDAO.insertDrink(new Drink(Integer.parseInt(getIngredientID), Integer.parseInt(getVoucher), getName, "Đóng chai", "10/12/2022", "10/12/2020", Integer.parseInt(getPrice), Integer.parseInt(getQuantity)))) {
                    list.add(new Drink(Integer.parseInt(getIngredientID), Integer.parseInt(getVoucher), getName, "Đóng chai", "10/12/2022", "10/12/2020", Integer.parseInt(getPrice), Integer.parseInt(getQuantity)));
                    adapter.notifyItemInserted(list.size());
                    alertDialog.dismiss();
                    ((MainActivity)getActivity()).reloadFragment(new FragmentDrink());
                }
            }
        });
    }
}