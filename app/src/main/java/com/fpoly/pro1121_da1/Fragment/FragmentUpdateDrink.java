package com.fpoly.pro1121_da1.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
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
import com.fpoly.pro1121_da1.database.InvoiceDAO;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FragmentUpdateDrink extends Fragment {
    Map<String, Double> mapIngredient = new HashMap<>();
    EditText edtNameDrink, edtPriceDrink, edtQuantityDrink, edtVoucher, edtDateAdd, edtDateExpiry;
    String getName, getPrice, getQuantity, getDateAdd, getDateExpiry, getTypeOfDrink = "Pha chế";
    Spinner spinner;
    TextView tvAddIngredient;
    ArrayList<Double> listQuantity;
    Spinner spinnerIngredient;
    IngredientDAO ingredientDAO;
    SpinnerAddIngredientToDrink spinnerAddIngredientToDrink;
    ArrayList<Ingredient> listIngredient;
    ArrayList<Ingredient> listAddRecyclerView;
    Button btnConfirmUpdateDrink;
    DrinkDAO drinkDAO;
    ImageView imgShowIngredient;
    StringBuilder s;
    RecyclerView recyclerView;
    IngredientAdapterAddDrink ingredientAdapterAddDrink;
    Spinner spinnerImageDrink, spinnerVoucher;
    String getIngredientID = "";
    int getImageDrink, getVoucher = 2023;
    IngredientForDrinkDAO ingredientForDrinkDAO;
    ImageView imgback;
    int getDrinkIdUpdate;
    IngredientForDrink ingredientForDrink;

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
                listQuantity.remove(position);
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
        Bundle bundle = this.getArguments();
        ingredientForDrinkDAO = new IngredientForDrinkDAO(getContext());
        listQuantity = new ArrayList<>();


        if (bundle != null) {
            getDrinkIdUpdate = bundle.getInt("KEY_DRINK_ID_UPDATE");
        }

        spinnerVoucher = view.findViewById(R.id.spn_voucherDrink_updateDrink);
        imgback = view.findViewById(R.id.img_back_fragmentUpdateDrink);
        spinnerImageDrink = view.findViewById(R.id.spinner_image_drink_fragmentUpdateDrink);
        tvAddIngredient = view.findViewById(R.id.tv_ingredientID_addDrink);
        edtNameDrink = view.findViewById(R.id.edt_nameDrink_updateDrink);
        edtPriceDrink = view.findViewById(R.id.edt_priceDrink_updateDrink);
        edtQuantityDrink = view.findViewById(R.id.edt_quantity_updateDrink);
        edtDateAdd = view.findViewById(R.id.edt_dateAdd_updateDrink);
        edtDateExpiry = view.findViewById(R.id.edt_dateExpiry_updateDrink);
        imgShowIngredient = view.findViewById(R.id.img_addIngredient_fragmentUpdateDrink);
        recyclerView = view.findViewById(R.id.recyclerView_ingredient_fragmentUpdateDrink);
        btnConfirmUpdateDrink = view.findViewById(R.id.btnConfirm_updateDrink_fragmentUpdateDrink);

        ingredientDAO = new IngredientDAO(getContext(), new Dbhelper(getContext()));
        listIngredient = ingredientDAO.getAllIngredient();
        listIngredient.add(0, new Ingredient());
        Drink drinkUpdate = drinkDAO.getDrinkByID(String.valueOf(getDrinkIdUpdate));


        listAddRecyclerView = drinkDAO.getIngredientFromDrinkID(drinkUpdate.getDrinkID());

        for (int i = 0; i < listAddRecyclerView.size(); i++) {
            ingredientForDrink = ingredientForDrinkDAO.getModelIngreForDrink(getDrinkIdUpdate, listAddRecyclerView.get(i).getIngredientID());
            listQuantity.add(ingredientForDrink.getQuantity());
            mapIngredient.put(listAddRecyclerView.get(i).getIngredientID(), ingredientForDrink.getQuantity());
        }
        Toast.makeText(getContext(), "" + mapIngredient.size(), Toast.LENGTH_SHORT).show();

        setAdapterRecyclerView(listAddRecyclerView);

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


        getTypeOfDrink = drinkUpdate.getTypeOfDrink();

        if (getTypeOfDrink.equalsIgnoreCase("Pha chế")) {
            edtDateAdd.setEnabled(false);
            edtDateAdd.setText("Bỏ qua");
            edtDateExpiry.setEnabled(false);
            edtDateExpiry.setText("Bỏ qua");
            imgShowIngredient.setVisibility(View.VISIBLE);
            tvAddIngredient.setVisibility(View.VISIBLE);
            edtQuantityDrink.setText("Không pha sẵn lên không có số lượng !");
            edtQuantityDrink.setEnabled(false);
        } else {
            edtDateAdd.setText(drinkUpdate.getDateAdd());
            edtDateExpiry.setText(drinkUpdate.getDateExpiry());
            edtDateAdd.setEnabled(false);
            edtDateExpiry.setEnabled(true);
            edtQuantityDrink.setEnabled(true);
            edtQuantityDrink.setText(String.valueOf(drinkUpdate.getQuantity()));
            imgShowIngredient.setVisibility(View.INVISIBLE);
            tvAddIngredient.setVisibility(View.INVISIBLE);
        }

        imgShowIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogIngredient();
            }
        });
        // set spinner voucher
        VoucherDAO voucherDAO = new VoucherDAO(getContext(), new Dbhelper(getContext()));
        ArrayList<Voucher> listVoucher = voucherDAO.getAllVoucher();
        listVoucher.add(0, new Voucher(1976, 100, "2099-12-12"));
        SpinnerVoucher spnVoucher = new SpinnerVoucher(listVoucher, getActivity());
        spinnerVoucher.setAdapter(spnVoucher);
        setDefaultSpinnerVoucher(listVoucher, drinkUpdate.getVoucherID());
        spinnerVoucher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    getVoucher = listVoucher.get(i).getVoucherID();
                } else {
                    getVoucher = 2023;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getVoucher = 2023;
            }
        });

        edtNameDrink.setText(drinkUpdate.getName());
        edtPriceDrink.setText(String.valueOf(drinkUpdate.getPrice()));


        setDefaultImageSpinner(arrImageDrink, drinkUpdate.getImage());
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("DRINK_ID", getDrinkIdUpdate);
                FragmentDrinkDetail detail = new FragmentDrinkDetail();
                detail.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.container_layout, detail).commit();

            }
        });

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String timeNow = formatter.format(date);
        IngredientForDrinkDAO ingredientForDrinkDAO = new IngredientForDrinkDAO(getContext());
        btnConfirmUpdateDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getName = edtNameDrink.getText().toString().trim();
                getPrice = edtPriceDrink.getText().toString().trim();
                getQuantity = edtQuantityDrink.getText().toString().trim();

                getDateAdd = edtDateAdd.getText().toString().trim();
                getDateExpiry = edtDateExpiry.getText().toString().trim();

                if (getName.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống tên !", Toast.LENGTH_SHORT).show();
                } else if (isNumber(edtPriceDrink, "Không được để trống giá", "Giá phải là số")) {

                } else {
                    if (getTypeOfDrink.equalsIgnoreCase("Pha chế")) {
                        Drink drink = new Drink(getDrinkIdUpdate, getVoucher, getName, getTypeOfDrink, getDateExpiry, getDateAdd, Integer.parseInt(getPrice), 0, getImageDrink, "lit", 0);
                        if (ingredientForDrinkDAO.deleteIngredientForDrink(getDrinkIdUpdate) && drinkDAO.updateDrink(drink) && listAddRecyclerView.size() == listQuantity.size()) {
                            for (int i = 0; i < listAddRecyclerView.size(); i++) {
                                ingredientForDrinkDAO.insertValues(new IngredientForDrink(getDrinkIdUpdate, listAddRecyclerView.get(i).getIngredientID(), listQuantity.get(i)));
                            }
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("DRINK_ID", drink.getDrinkID());
                            FragmentDrinkDetail fragmentDrinkDetail = new FragmentDrinkDetail();
                            fragmentDrinkDetail.setArguments(bundle1);
                            getParentFragmentManager().beginTransaction().replace(R.id.container_layout, fragmentDrinkDetail).commit();
                        }
                    } else {
                        if (isNumber(edtQuantityDrink, "Không được để trống số lượng", "Số lượng phải là số")) {

                        } else {
                            try {
                                if (!checkExpiry(getDateExpiry, timeNow)) {
                                    Toast.makeText(getContext(), "Ngày hết hạn phải lớn hơn ngày hiện tại !", Toast.LENGTH_SHORT).show();
                                } else {
                                    Drink drink = new Drink(getDrinkIdUpdate, getVoucher, getName, getTypeOfDrink, getDateExpiry, getDateAdd, Integer.parseInt(getPrice), Integer.parseInt(getQuantity), getImageDrink, "lit", 0);
                                    drinkDAO.updateDrink(drink);
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putInt("DRINK_ID", drink.getDrinkID());
                                    FragmentDrinkDetail fragmentDrinkDetail = new FragmentDrinkDetail();
                                    fragmentDrinkDetail.setArguments(bundle1);
                                    getParentFragmentManager().beginTransaction().replace(R.id.container_layout, fragmentDrinkDetail).commit();
                                }
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }
                }
            }
        });
    }

    EditText edtGetQuan;
    Button btnConfirm;
    Ingredient ingredient;

    double getQuantityIngredientAddForDrink = 0;

    // show dialog to add ingredient
    public void showDialogIngredient() {
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

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNumber(edtGetQuan, "Số lượng phải lớn hơn 0", "Số lượng phải là số")) {
                    alertDialog.setCancelable(false);
                } else {
                    try {
                        getQuantityIngredientAddForDrink = Double.parseDouble(edtGetQuan.getText().toString().trim());
                        mapIngredient.put(ingredient.getIngredientID(), getQuantityIngredientAddForDrink);
                        listQuantity.clear();
                        listAddRecyclerView.clear();
                        for (Map.Entry<String, Double> entry : mapIngredient.entrySet()) {
                            listAddRecyclerView.add(ingredientDAO.getIngredientByID(entry.getKey()));
                            listQuantity.add(entry.getValue());
                        }
                        ingredientAdapterAddDrink.notifyDataSetChanged();
                        alertDialog.dismiss();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
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

    public void setDefaultImageSpinner(int arrImage[], int img) {
        for (int i = 0; i < arrImage.length; i++) {
            if (arrImage[i] == img) {
                spinnerImageDrink.setSelection(i);
            }
        }
    }

    public void setDefaultSpinnerVoucher(ArrayList<Voucher> list, int getVoucher) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getVoucherID() == getVoucher) {
                spinnerVoucher.setSelection(i);
            }
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

    public boolean checkFormatDate(String input) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFormat.parse(input);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

}