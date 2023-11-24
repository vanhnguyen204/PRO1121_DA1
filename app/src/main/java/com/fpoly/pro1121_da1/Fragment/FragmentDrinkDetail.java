package com.fpoly.pro1121_da1.Fragment;

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
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Ingredient;
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

    public void findID(View view) {

    }
    Animation animation;
    RelativeLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_drink_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = view.findViewById(R.id.layoutAnim);

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
        User user = ((MainActivity) getActivity()).user;
        if (!user.getRole().equals("admin")) {
            btnDelete.setVisibility(View.INVISIBLE);
            btnUpdate.setVisibility(View.INVISIBLE);
        }
        ingredientDAO = new IngredientDAO(getContext(), new Dbhelper(getContext()));
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                getDrinkID = result.getInt("KEY_DRINKID");
                Toast.makeText(getContext(), "" + getDrinkID, Toast.LENGTH_SHORT).show();
                drink = drinkDAO.getDrinkByID(String.valueOf(getDrinkID));
                tvDrinkID.setText("Mã đồ uống: " + drink.getDrinkID());
imgDrink.setImageResource(drink.getImage());
                tvVoucherID.setText("Mã giảm giá:" + drink.getVoucherID());
                tvNameDrink.setText("Tên đồ uống: " + drink.getName());
                tvTypeOfDrink.setText("Loại đồ uống: " + drink.getTypeOfDrink());
                tvDateAdd.setText("Ngày thêm: " + drink.getDateAdd());
                tvDateExpiry.setText("Ngày hết hạn: " + drink.getDateExpiry());
                tvPrice.setText("Giá: " + drink.getPrice());
                tvQuantity.setText("Số lượng: " + drink.getQuantity());

                StringBuilder stringBuilder = new StringBuilder();
                if (drink.getTypeOfDrink().equalsIgnoreCase("Pha chế")) {
                    ArrayList<Ingredient> ingredients = checkIngredientID(drink.getIngredientID());
                    for (int i = 0; i < ingredients.size(); i++) {
                        stringBuilder.append(ingredients.get(i).getName());
                    }

                    tvIngredientID.setText("Nguyên liệu: " + stringBuilder.toString());
                } else {
                    tvIngredientID.setText("Nguyên liệu: không có");
                }


            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Bundle bundle = new Bundle();
               bundle.putInt("KEY_DRINK_ID",drink.getDrinkID());
                FragmentManager manager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
                manager.setFragmentResult("KEY_UPDATE_DRINK", bundle);

                ((MainActivity)getActivity()).reloadFragment(new FragmentUpdateDrink());
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
                        ((MainActivity) getActivity()).reloadFragment(new FragmentDrink());
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


    public ArrayList<Ingredient> checkIngredientID(String ingredientID) {
        String[] arrID = ingredientID.split(" ");
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        for (int i = 0; i < arrID.length; i++) {
            ingredientArrayList.add(ingredientDAO.getIngredientByID(arrID[i]));

        }
        return ingredientArrayList;
    }

}