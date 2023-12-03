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
import com.fpoly.pro1121_da1.Interface.SetTextRecyclerView;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.database.IngredientForDrinkDAO;
import com.fpoly.pro1121_da1.database.NotificationDAO;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class FragmentAddDrink extends Fragment {

    EditText edtNameDrink, edtPriceDrink, edtQuantityDrink, edtDateAdd, edtDateExpiry, edtGetQuan;
    String getName, getPrice, getQuantity, getDateAdd, getDateExpiry, getTypeOfDrink = "Pha chế", getIngredientID;
    Spinner spinner, spinnerVoucher;
    Button btnConfirmAddDrink, btnConfirm;

    Spinner spinnerIngredient, spinnerImageDrink;
    IngredientDAO ingredientDAO;
    SpinnerAddIngredientToDrink spinnerAddIngredientToDrink;


    Ingredient ingredient;
    ArrayList<Ingredient> listIngredientRecyclerView, listIngredient;
    DrinkDAO drinkDAO;
    ImageView imgShowIngredient, imgBack;
    RecyclerView recyclerView;
    IngredientAdapterAddDrink ingredientAdapterAddDrink;
    ArrayList<Double> listQuantity;
    TextView tvTitleAddIngredient;
    int getVoucher, getDrinkID, getIngredientForDrink, getImageDrink;
    double getQuantityIngredientAddForDrink = 0;


    public void setIngredientAdapterRecyclerView(ArrayList<Ingredient> list) {

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
                listIngredientRecyclerView.remove(position);
                ingredientAdapterAddDrink.notifyItemRemoved(position);

            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        ingredientAdapterAddDrink.setTextForTextView(new SetTextRecyclerView() {
            @Override
            public void onSetText(TextView textView, Ingredient ingredient, int position) {
                textView.setText("Số lượng: " + listQuantity.get(position) + " " + ingredient.getUnit());
            }
        });
    }

    public void findViewById(View view1) {
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
        btnConfirmAddDrink = view1.findViewById(R.id.btnConfirm_addDrink);
        spinner = view1.findViewById(R.id.spinner_typeOfDrink_addDrink);
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
        findViewById(view1);
        drinkDAO = new DrinkDAO(getActivity(), new Dbhelper(getActivity()));
        ingredientDAO = new IngredientDAO(getActivity(), new Dbhelper(getActivity()));
        VoucherDAO voucherDAO = new VoucherDAO(getContext(), new Dbhelper(getContext()));
        NotificationDAO notificationDAO = new NotificationDAO(getContext());
        listIngredient = ingredientDAO.getAllIngredient();
        if (listIngredient.size() == 0) {
            Toast.makeText(getContext(), "Nếu thêm đồ uống pha chế, hãy thêm nguyên liệu trước", Toast.LENGTH_SHORT).show();
        }
        listQuantity = new ArrayList<>();
        listIngredient.add(0, new Ingredient());
        listIngredientRecyclerView = new ArrayList<>();

        ArrayList<Voucher> listVoucher = voucherDAO.getAllVoucher();
        listVoucher.add(0, new Voucher(1979, 100, "2099-12-12"));
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
                ((MainActivity) requireActivity()).reloadFragment(new FragmentDrink());
            }
        });
        // set spinner cho hình ảnh đồ uống
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

        SpinnerVoucher spnVoucher = new SpinnerVoucher(listVoucher, getActivity());
        spinnerVoucher.setAdapter(spnVoucher);
        spinnerVoucher.setSelection(0, false);

        spinnerVoucher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    getVoucher = listVoucher.get(i).getVoucherID();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getVoucher = 1976;
            }
        });


        String type[] = new String[]{"Pha chế", "Đóng chai"};
        SpinnerTypeOfDrink spinnerTypeOfDrink = new SpinnerTypeOfDrink(type, getActivity());
        spinner.setAdapter(spinnerTypeOfDrink);

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
                showDialogAddIngredient();
            }
        });

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        getDateAdd = dateFormat.format(cal.getTime());

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String timeNow = formatter.format(date);
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

                        Drink drink = new Drink(getDrinkID,
                                getVoucher,
                                getName,
                                getTypeOfDrink,
                                getDateAdd,
                                getDateAdd,
                                Integer.parseInt(getPrice),
                                0,
                                getImageDrink,
                                "lit",
                                0);
                        getIngredientForDrink = new Random().nextInt(1000);
                        if (drinkDAO.insertDrink(drink)) {
                            for (int i = 0; i < listIngredientRecyclerView.size(); i++) {
                                IngredientForDrink ingredient = new IngredientForDrink(getDrinkID, listIngredientRecyclerView.get(i).getIngredientID(), listQuantity.get(i));
                               ingredientForDrinkDAO.insertValues(ingredient);
                            }
                            ((MainActivity) requireActivity()).reloadFragment(new FragmentDrink());

                        }
                    } else {
                        try {
                            if (isNumber(edtQuantityDrink, "Số lượng phải lớn hơn 0", "Số lượng phải là số.")) {

                            } else if (getDateExpiry.length() == 0) {
                                Toast.makeText(getContext(), "Không được để trống ngày hết hạn", Toast.LENGTH_SHORT).show();
                            } else if (!checkExpiry(getDateExpiry, timeNow)) {
                                Toast.makeText(getContext(), "Ngày hết hạn phải lớn hơn ngày hiện tại", Toast.LENGTH_SHORT).show();
                            } else {
                                Drink drink = new Drink(getDrinkID, getVoucher, getName, getTypeOfDrink, getDateExpiry, getDateAdd, Integer.parseInt(getPrice), Integer.parseInt(getQuantity), getImageDrink, "long", 0);
                                if (drinkDAO.insertDrink(drink)) {
                                    ((MainActivity) requireActivity()).reloadFragment(new FragmentDrink());
                                }
                            }
                        } catch (Exception e) {

                        }


                    }
                }
            }
        });

    }

    public boolean isNumber(EditText editText, String mess, String messErr) {
        try {
            double number = Double.parseDouble(editText.getText().toString().trim());
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


    // show dialog to add ingredient
    public void showDialogAddIngredient() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Style_AlertDialog_Corner);
        LayoutInflater inflater = getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.alertdialog_addingredient_fragmentadddrink, null);
        builder.setView(viewDialog);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        spinnerIngredient = viewDialog.findViewById(R.id.spinner_addIngredient_fragmentAddDrink);
        edtGetQuan = viewDialog.findViewById(R.id.edt_numIngreAddDrink);

        btnConfirm = viewDialog.findViewById(R.id.btnConfirmAddIngredientForDrink);
        spinnerAddIngredientToDrink = new SpinnerAddIngredientToDrink(listIngredient, getActivity());
        spinnerIngredient.setAdapter(spinnerAddIngredientToDrink);
        spinnerIngredient.setSelection(0, false);

        spinnerIngredient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ingredient = listIngredient.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ingredient = listIngredient.get(0);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNumber(edtGetQuan, "Số lượng phải lớn hơn 0", "Số lượng phải là số")) {
                    alertDialog.setCancelable(false);
                } else if (checkQuantity(Double.parseDouble(edtGetQuan.getText().toString().trim()))) {

                } else {
                    getQuantityIngredientAddForDrink = Double.parseDouble(edtGetQuan.getText().toString().trim());
                    listIngredientRecyclerView.add(ingredient);
                    listQuantity.add(getQuantityIngredientAddForDrink);
                    setIngredientAdapterRecyclerView(listIngredientRecyclerView);
                    alertDialog.dismiss();
                }
            }
        });

    }

    public boolean checkQuantity(double sl) {
        if (sl > ingredient.getQuantity()) {
            Toast.makeText(getContext(), "Số lượng còn lại không !", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    public boolean checkExpiry(String day1, String day2) throws ParseException {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = spf.parse(day1);
        Date date2 = spf.parse(day2);
        int compare = date1.compareTo(date2);
        if (compare > 0) {
            return true;
        } else if (compare < 0) {
            return false;
        } else {
            return false;
        }


    }
}