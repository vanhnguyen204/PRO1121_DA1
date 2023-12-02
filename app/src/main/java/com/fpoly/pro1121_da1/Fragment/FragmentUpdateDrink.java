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

import java.util.ArrayList;
import java.util.Random;

public class FragmentUpdateDrink extends Fragment {
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
    int getImageDrink, getVoucher;
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
        ingredientDAO = new IngredientDAO(getContext(), new Dbhelper(getContext()));
        listIngredient = ingredientDAO.getAllIngredient();
        listIngredient.add(0, new Ingredient());
        Drink drinkUpdate = drinkDAO.getDrinkByID(String.valueOf(getDrinkIdUpdate));

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


        listAddRecyclerView = drinkDAO.getIngredientFromDrinkID(drinkUpdate.getDrinkID());
        for (int i = 0; i < listAddRecyclerView.size(); i++) {
            ingredientForDrink = ingredientForDrinkDAO.getModelIngreForDrink(getDrinkIdUpdate, listAddRecyclerView.get(i).getIngredientID());
            listQuantity.add(ingredientForDrink.getQuantity());
        }

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
            edtDateAdd.setHint("Bỏ qua");
            edtDateExpiry.setEnabled(false);
            edtDateExpiry.setHint("Bỏ qua");
            imgShowIngredient.setVisibility(View.VISIBLE);
            tvAddIngredient.setVisibility(View.VISIBLE);
        } else {
            edtDateAdd.setEnabled(true);
            edtDateAdd.setHint("Ngày nhập");
            edtDateExpiry.setEnabled(true);
            edtDateExpiry.setHint("Ngày hết hạn");
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
                    getVoucher = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        edtNameDrink.setText(drinkUpdate.getName());
        edtPriceDrink.setText(String.valueOf(drinkUpdate.getPrice()));
        edtQuantityDrink.setText(String.valueOf(drinkUpdate.getQuantity()));
        edtDateAdd.setText(drinkUpdate.getDateAdd());
        edtDateExpiry.setText(drinkUpdate.getDateExpiry());
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

        IngredientForDrinkDAO ingredientForDrinkDAO = new IngredientForDrinkDAO(getContext());
        btnConfirmUpdateDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getName = edtNameDrink.getText().toString().trim();
                getPrice = edtPriceDrink.getText().toString().trim();
                getQuantity = edtQuantityDrink.getText().toString().trim();

                getDateAdd = edtDateAdd.getText().toString().trim();
                getDateExpiry = edtDateExpiry.getText().toString().trim();

                if (getVoucher == 0) {

                } else {
                    if (getTypeOfDrink.equalsIgnoreCase("Pha chế")) {
                        Drink drink = new Drink(getDrinkIdUpdate, getVoucher, getName, getTypeOfDrink, getDateExpiry, getDateAdd, Integer.parseInt(getPrice), Integer.parseInt(getQuantity), getImageDrink, "lit");
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


                    }
                }
            }
        });
    }

    EditText edtGetQuan;
    Button btnConfirm;
    Ingredient ingredient;

    double getQuantityIngredientAddForDrink=0;

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
                    getQuantityIngredientAddForDrink = Double.parseDouble(edtGetQuan.getText().toString().trim());
                    listAddRecyclerView.add(ingredient);
                   listQuantity.add(getQuantityIngredientAddForDrink);
                   ingredientAdapterAddDrink.notifyItemInserted(listAddRecyclerView.size());
                    alertDialog.dismiss();
                }
                Toast.makeText(getContext(), "List SL: " + listQuantity.size() + "--- List ingre:  " + listAddRecyclerView.size(), Toast.LENGTH_SHORT).show();
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

}