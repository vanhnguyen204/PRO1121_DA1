package com.fpoly.pro1121_da1.Fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.IngredientAdapterAddDrink;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.database.IngredientForDrinkDAO;
import com.fpoly.pro1121_da1.database.VoucherDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.model.IngredientForDrink;
import com.fpoly.pro1121_da1.model.Voucher;
import com.fpoly.pro1121_da1.spinner.SpinnerAddIngredientToDrink;
import com.fpoly.pro1121_da1.spinner.SpinnerImageDrink;
import com.fpoly.pro1121_da1.spinner.SpinnerTypeOfDrink;
import com.fpoly.pro1121_da1.spinner.SpinnerVoucher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class FragmentAddDrink extends Fragment {

    EditText edtNameDrink, edtPriceDrink, edtQuantityDrink, edtDateAdd, edtDateExpiry;
    String getName, getPrice, getQuantity, getDateAdd, getDateExpiry, getTypeOfDrink = "Pha chế", getIngredientID;
    Spinner spinner, spinnerVoucher;
    Button btnConfirmAddDrink;
    ArrayList<Ingredient> listAddRecyclerView;
    DrinkDAO drinkDAO;
    ImageView imgShowIngredient, imgBack;
    RecyclerView recyclerView;
    IngredientAdapterAddDrink ingredientAdapterAddDrink;
    Spinner spinnerImageDrink;
    TextView tvTitleAddIngredient;
    int getImageDrink;
    int getVoucher, getDrinkID, getIngredientForDrink;


    public void setAdapterRecyclerView(ArrayList<Ingredient> list) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientAdapterAddDrink = new IngredientAdapterAddDrink(list, getActivity());
        recyclerView.setAdapter(ingredientAdapterAddDrink);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                listAddRecyclerView.remove(position);
                ingredientAdapterAddDrink.notifyItemRemoved(position);

            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_drink, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);
        drinkDAO = new DrinkDAO(getActivity(), new Dbhelper(getActivity()));
        spinnerImageDrink = view1.findViewById(R.id.spinner_image_drink_fragmentAddDrink);
        edtNameDrink = view1.findViewById(R.id.edt_nameDrink_addDrink);
        edtPriceDrink = view1.findViewById(R.id.edt_priceDrink_addDrink);
        edtQuantityDrink = view1.findViewById(R.id.edt_quantity_addDrink);
        spinnerVoucher = view1.findViewById(R.id.spinner_voucherDrink_addDrink);
        imgBack = view1.findViewById(R.id.img_back_fragmentAddDrink);
        edtDateExpiry = view1.findViewById(R.id.edt_dateExpiry_addDrink);
        tvTitleAddIngredient = view1.findViewById(R.id.tv_ingredientID_addDrink);
        imgShowIngredient = view1.findViewById(R.id.img_addIngredient_fragmentAddDrink);
        recyclerView = view1.findViewById(R.id.recyclerView_ingredient_fragmentAddDrink);
        ingredientDAO = new IngredientDAO(getActivity(), new Dbhelper(getActivity()));
        listIngredient = ingredientDAO.getAllIngredient();


        listIngredient.add(0, new Ingredient());
        IngredientForDrinkDAO ingredientForDrinkDAO = new IngredientForDrinkDAO(getContext());
        getDrinkID = new Random().nextInt(1000);
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
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentDrink());
            }
        });
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

        VoucherDAO voucherDAO = new VoucherDAO(getContext(), new Dbhelper(getContext()));

        ArrayList<Voucher> listVoucher = voucherDAO.getAllVoucher();
        listVoucher.add(0, new Voucher(1976, 100, "2099-12-12"));
        SpinnerVoucher spnVoucher = new SpinnerVoucher(listVoucher, getActivity());
        spinnerVoucher.setAdapter(spnVoucher);

        spinnerVoucher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    getVoucher = listVoucher.get(i).getVoucherID();
                } else {
                    getVoucher = 0;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnConfirmAddDrink = view1.findViewById(R.id.btnConfirm_addDrink);
        spinner = view1.findViewById(R.id.spinner_typeOfDrink_addDrink);
        String type[] = new String[]{"Pha chế", "Đóng chai"};
        SpinnerTypeOfDrink spinnerTypeOfDrink = new SpinnerTypeOfDrink(type, getActivity());
        spinner.setAdapter(spinnerTypeOfDrink);
        listAddRecyclerView = new ArrayList<>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getTypeOfDrink = type[i];

                if (getTypeOfDrink.equalsIgnoreCase("Pha chế")) {
                    imgShowIngredient.setVisibility(View.VISIBLE);
                    tvTitleAddIngredient.setVisibility(View.VISIBLE);
                    edtDateExpiry.setEnabled(false);
                    edtDateExpiry.setHint("Bỏ qua");
                    edtQuantityDrink.setEnabled(false);
                    edtQuantityDrink.setHint("Bỏ qua");
                } else {
                    imgShowIngredient.setVisibility(View.INVISIBLE);
                    tvTitleAddIngredient.setVisibility(View.INVISIBLE);
                    edtDateExpiry.setEnabled(true);
                    edtDateExpiry.setHint("Ngày hết hạn");
                    edtQuantityDrink.setEnabled(true);
                    edtQuantityDrink.setHint("Nhập số lượng");

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

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        getDateAdd = dateFormat.format(cal.getTime());
        btnConfirmAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getName = edtNameDrink.getText().toString().trim();
                getPrice = edtPriceDrink.getText().toString().trim();
                getQuantity = edtQuantityDrink.getText().toString().trim();

                getDateExpiry = edtDateExpiry.getText().toString().trim();
                if (getName.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống tên đồ uống.", Toast.LENGTH_SHORT).show();
                } else if (isNumber(edtPriceDrink, "Giá phải lớn hơn 0", "Giá phải là số")) {

                } else if (getVoucher == 0) {
                    Toast.makeText(getContext(), "Vui lòng chọn phiếu giảm giá !", Toast.LENGTH_SHORT).show();
                } else {
                    if (getTypeOfDrink.equalsIgnoreCase("Pha chế")) {

                        Drink drink = new Drink(getDrinkID, getVoucher, getName, getTypeOfDrink, getDateAdd, getDateAdd, Integer.parseInt(getPrice), 0, getImageDrink, "lit");
                        getIngredientForDrink = new Random().nextInt(1000);
                        if (drinkDAO.insertDrink(drink)) {
                            for (int i = 0; i < listAddRecyclerView.size(); i++) {
                                IngredientForDrink ingredient = new IngredientForDrink(getDrinkID, listAddRecyclerView.get(i).getIngredientID());
                                ingredientForDrinkDAO.insertValues(ingredient);
                                ((MainActivity) getActivity()).reloadFragment(new FragmentDrink());

                            }
                        }
                    } else {
                        if (isNumber(edtQuantityDrink, "Số lượng phải lớn hơn 0", "Số lượng phải là số.")) {

                        } else if (getDateExpiry.length() == 0) {
                            Toast.makeText(getContext(), "Không được để trống ngày hết hạn", Toast.LENGTH_SHORT).show();
                        } else {
                            Drink drink = new Drink(getDrinkID, getVoucher, getName, getTypeOfDrink, getDateExpiry, getDateAdd, Integer.parseInt(getPrice), Integer.parseInt(getQuantity), getImageDrink, "long");
                            if (drinkDAO.insertDrink(drink)) {
                                ((MainActivity) getActivity()).reloadFragment(new FragmentDrink());
                            }
                        }

                    }
                }
            }
        });

    }

    public boolean isNumber(EditText editText, String mess, String messErr) {
        try {
            int number = Integer.parseInt(editText.getText().toString().trim());
            if (number < 0) {
                Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), messErr, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    Spinner spinnerIngredient;
    IngredientDAO ingredientDAO;
    SpinnerAddIngredientToDrink spinnerAddIngredientToDrink;
    ArrayList<Ingredient> listIngredient;


    // show dialog to add ingredient
    public void showDialogIngredient() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Style_AlertDialog_Corner);
        LayoutInflater inflater = getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.alertdialog_addingredient_fragmentadddrink, null);
        builder.setView(viewDialog);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        spinnerIngredient = viewDialog.findViewById(R.id.spinner_addIngredient_fragmentAddDrink);

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

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                for (int i = 0; i < listAddRecyclerView.size(); i++) {
                    for (int j = i + 1; j < listAddRecyclerView.size(); j++) {
                        if (listAddRecyclerView.get(i).getIngredientID().equalsIgnoreCase(listAddRecyclerView.get(j).getIngredientID())) {
                            listAddRecyclerView.remove(j);
                            Toast.makeText(getContext(), "Đã có nguyên liệu này rồi", Toast.LENGTH_SHORT).show();

                        }

                    }
                }
                setAdapterRecyclerView(listAddRecyclerView);
            }
        });
    }

}