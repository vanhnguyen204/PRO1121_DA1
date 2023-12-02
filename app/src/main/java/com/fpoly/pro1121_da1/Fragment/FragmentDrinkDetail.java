package com.fpoly.pro1121_da1.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.database.IngredientForDrinkDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.model.IngredientForDrink;
import com.fpoly.pro1121_da1.model.User;
import com.fpoly.pro1121_da1.spinner.SpinnerImageDrink;
import com.fpoly.pro1121_da1.spinner.SpinnerTypeOfDrink;

import java.util.ArrayList;


public class FragmentDrinkDetail extends Fragment {
    int getDrinkID;
    DrinkDAO drinkDAO;

    ImageView imgBack, imgDrink;
    Drink drink;
    IngredientDAO ingredientDAO;
    TextView tvDrinkID, tvIngredientID, tvVoucherID, tvNameDrink, tvTypeOfDrink, tvDateAdd, tvDateExpiry, tvPrice, tvQuantity;
    Button btnDelete, btnUpdate;
    int receiveDrinkID;

    public void findID(View view) {

    }

    int getID;

    public Integer geIdDrinkUD(int getID) {

        return this.getID = getID;
    }

    Animation animation;
    RelativeLayout layout;
    Drink drinkSaveInstance;


    public void setAnimationText() {
        int fromXDelta = -500;

        Animation animation1 = new TranslateAnimation(fromXDelta, 0, 0, 0);
        animation1.setDuration(1000);
        animation1.setRepeatMode(Animation.RELATIVE_TO_SELF);
        animation1.setRepeatCount(0);

        Animation animation2 = new TranslateAnimation(fromXDelta, 0, 0, 0);
        animation2.setDuration(1200);
        animation2.setRepeatMode(Animation.RELATIVE_TO_SELF);
        animation2.setRepeatCount(0);

        Animation animation3 = new TranslateAnimation(fromXDelta, 0, 0, 0);
        animation3.setDuration(1300);
        animation3.setRepeatMode(Animation.RELATIVE_TO_SELF);
        animation3.setRepeatCount(0);

        Animation animation4 = new TranslateAnimation(fromXDelta, 0, 0, 0);
        animation4.setDuration(1400);
        animation4.setRepeatMode(Animation.RELATIVE_TO_SELF);
        animation4.setRepeatCount(0);

        Animation animation5 = new TranslateAnimation(fromXDelta, 0, 0, 0);
        animation5.setDuration(1500);
        animation5.setRepeatMode(Animation.RELATIVE_TO_SELF);
        animation5.setRepeatCount(0);

        Animation animation6 = new TranslateAnimation(fromXDelta, 0, 0, 0);
        animation6.setDuration(1600);
        animation6.setRepeatMode(Animation.RELATIVE_TO_SELF);
        animation6.setRepeatCount(0);

        Animation animation7 = new TranslateAnimation(fromXDelta, 0, 0, 0);
        animation7.setDuration(1700);
        animation7.setRepeatMode(Animation.RELATIVE_TO_SELF);
        animation7.setRepeatCount(0);

        Animation animation8 = new TranslateAnimation(fromXDelta, 0, 0, 0);
        animation8.setDuration(1800);
        animation8.setRepeatMode(Animation.RELATIVE_TO_SELF);
        animation8.setRepeatCount(0);

        Animation animation9 = new TranslateAnimation(fromXDelta, 0, 0, 0);
        animation9.setDuration(1900);
        animation9.setRepeatMode(Animation.RELATIVE_TO_SELF);
        animation9.setRepeatCount(0);


        tvDrinkID.setAnimation(animation1);
        tvIngredientID.setAnimation(animation2);
        tvVoucherID.setAnimation(animation3);
        tvNameDrink.setAnimation(animation4);
        tvTypeOfDrink.setAnimation(animation5);
        tvDateAdd.setAnimation(animation6);
        tvDateExpiry.setAnimation(animation7);
        tvPrice.setAnimation(animation8);
        tvQuantity.setAnimation(animation9);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_drink_detail, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        imgDrink = view.findViewById(R.id.img_drinkDetail);
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
        IngredientForDrinkDAO ingredientForDrinkDAO = new IngredientForDrinkDAO(getContext());
        ingredientDAO = new IngredientDAO(getContext(), new Dbhelper(getContext()));
        ArrayList<Ingredient> ingredientArrayList;
        User user = ((MainActivity) requireActivity()).user;
        Animation animation = new TranslateAnimation(
                0, 0, 0, 50);
        animation.setDuration(2200);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        imgDrink.setAnimation(animation);
        setAnimationText();


        if (!user.getRole().equals("admin")) {
            btnDelete.setVisibility(View.INVISIBLE);
            btnUpdate.setVisibility(View.INVISIBLE);
        }


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            receiveDrinkID = bundle.getInt("DRINK_ID");

            drink = drinkDAO.getDrinkByID(String.valueOf(receiveDrinkID));
            tvDrinkID.setText("Mã đồ uống: " + drink.getDrinkID());
            imgDrink.setImageResource(drink.getImage());
            tvVoucherID.setText("Mã giảm giá:" + drink.getVoucherID());
            tvNameDrink.setText("Tên đồ uống: " + drink.getName());
            tvTypeOfDrink.setText("Loại đồ uống: " + drink.getTypeOfDrink());

            tvDateAdd.setText("Ngày thêm: " + drink.getDateAdd());

            tvPrice.setText("Giá: " + drink.getPrice());



            if (drink.getTypeOfDrink().equals("Pha chế")) {
                ingredientArrayList = drinkDAO.getIngredientFromDrinkID(drink.getDrinkID());
                StringBuilder stringBuilder = new StringBuilder();
                IngredientForDrink ingredientForDrink ;
                for (int i = 0; i < ingredientArrayList.size(); i++) {
                    Ingredient ingredient = ingredientArrayList.get(i);
                    ingredientForDrink = ingredientForDrinkDAO.getModelIngreForDrink(drink.getDrinkID(), ingredient.getIngredientID());
                    stringBuilder.append(ingredient.getName()).append(" ").append(ingredientForDrink.getQuantity()).append(" ").append(ingredient.getUnit()).append("\n");
                }
                tvDateExpiry.setText("Ngày hết hạn: 24h sau ngày mua");
                tvIngredientID.setText("Nguyên liệu: " + stringBuilder.toString());
                tvQuantity.setText("Số lượng: không pha sẵn" );
            }else {
                tvDateExpiry.setText("Ngày hết hạn: " + drink.getDateExpiry());
                tvIngredientID.setText("Nguyên liệu: không cần nguyên liệu pha chế");
                tvQuantity.setText("Số lượng: " + drink.getQuantity());
            }


        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("KEY_DRINK_ID_UPDATE", drink.getDrinkID());
                FragmentUpdateDrink frm = new FragmentUpdateDrink();
                frm.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.container_layout, frm).commit();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) requireActivity()).reloadFragment(new FragmentDrink());
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setMessage("Bạn có muốn xoá đồ uống này không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        drinkDAO.deleteDrink(drink);
                        ((MainActivity) requireActivity()).reloadFragment(new FragmentDrink());
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


    }

}