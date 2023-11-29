package com.fpoly.pro1121_da1.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.TopDrinkAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.InvoiceDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Invoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentHome extends Fragment {
    LinearLayout manageIngredient, manegeUser, manageDrink;
    String sub = "";
    ImageView imgAvatar;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_fragmentHome);
        // Inflate the layout for this fragment
        InvoiceDAO invoiceDAO = new InvoiceDAO(getContext(), new Dbhelper(getContext()));
        ArrayList<Invoice> invoiceArrayList = invoiceDAO.getAllInvoice();
        ArrayList<String> listDrinkID = new ArrayList<>();
        for (int i = 0; i < invoiceArrayList.size(); i++) {
            listDrinkID.add(invoiceArrayList.get(i).getDrinkID());
        }

        for (int i = 0; i < listDrinkID.size(); i++) {
            sub += listDrinkID.get(i) + " ";
        }
        String[] listDrinkIDInvoice = sub.split(" ");

        ArrayList<Integer> listIntegerDrinkID = new ArrayList<>();

        for (int i = 0; i < listDrinkIDInvoice.length; i++) {
            if (listDrinkIDInvoice[i].equals("")) {
                continue;
            } else {
                listIntegerDrinkID.add(Integer.parseInt(listDrinkIDInvoice[i]));
            }
        }

        int[] arrIntDrinkIDEnd = new int[listIntegerDrinkID.size()];
        for (int i = 0; i < arrIntDrinkIDEnd.length; i++) {
            arrIntDrinkIDEnd[i] = listIntegerDrinkID.get(i);
        }

//        // Sử dụng Map để đếm số lần xuất hiện của mỗi phần tử

        Map<Integer, Integer> countMap = new HashMap<>();
        for (int i = 0; i < arrIntDrinkIDEnd.length; i++) {

            int countChild = countMap.getOrDefault(arrIntDrinkIDEnd[i], 0);

            countMap.put(arrIntDrinkIDEnd[i], ++countChild);
        }

//        // Sắp xếp mảng theo số lần xuất hiện giảm dần

        List<Integer> list = new ArrayList<>();
        for (int integer : arrIntDrinkIDEnd) {
            list.add(integer);
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) == list.get(j)) {
                    list.remove(i);
                }
            }
        }

        Collections.sort(list, Comparator.comparingInt((Integer num) -> countMap.get(num)).reversed());
        ArrayList<Drink> drinkArrayList = new ArrayList<>();
        DrinkDAO drinkDAO = new DrinkDAO(getContext(), new Dbhelper(getContext()));
        for (int i = 0; i < list.size(); i++) {
            drinkArrayList.add(drinkDAO.getDrinkByID(String.valueOf(list.get(i))));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!drinkArrayList.isEmpty()) {
                    TopDrinkAdapter drinkAdapter = new TopDrinkAdapter(getActivity(), drinkArrayList);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(drinkAdapter);
                }
                Animation animation1 = new TranslateAnimation(1000, 0, 0, 0);
                animation1.setDuration(2000);
                animation1.setRepeatMode(Animation.RELATIVE_TO_SELF);
                animation1.setRepeatCount(0);
                recyclerView.setAnimation(animation1);
            }
        },400);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manageIngredient = view.findViewById(R.id.manage_ingredient);
        manegeUser = view.findViewById(R.id.manage_member);
        manageDrink = view.findViewById(R.id.manage_drink);
        imgAvatar = view.findViewById(R.id.avatar_staff);
        manageIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentIngredient());
            }
        });
        manegeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentUser());
            }
        });
        manageDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentDrink());
            }
        });


    }

}