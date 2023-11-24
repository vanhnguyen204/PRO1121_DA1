
package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.DrinkOrderAdapter;
import com.fpoly.pro1121_da1.Interface.MyOnItemClickListener;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.model.Drink;

import java.util.ArrayList;

public class FragmentOrderDrink extends Fragment {

    RecyclerView recyclerView;
    DrinkOrderAdapter drinkOrderAdapter;
    ArrayList<Drink> list;
    DrinkDAO drinkDAO;
    ArrayList<Drink> listDrinkOrder;
    Button btnConfirmOrder;
    ImageView imgBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_drink, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView_orderDrink);
        imgBack = view.findViewById(R.id.img_back_fragmentOrderDrink);
        drinkDAO = new DrinkDAO(getActivity(), new Dbhelper(getActivity()));
        list = drinkDAO.getAllDrink();
        drinkOrderAdapter = new DrinkOrderAdapter(list, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(drinkOrderAdapter);
        btnConfirmOrder = view.findViewById(R.id.btnConfirmSelectOrder);
        listDrinkOrder = new ArrayList<>();
        drinkOrderAdapter.setMyOnItemClick(new MyOnItemClickListener() {
            @Override
            public void onClick(Drink drink) {
                listDrinkOrder.add(drink);
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("KEY_ARRAY_DRINK", listDrinkOrder);
                FragmentExportInvoice fragmentExportInvoice = new FragmentExportInvoice();
                fragmentExportInvoice.setArguments(bundle);
                ((MainActivity)getActivity()).reloadFragment(new FragmentExportInvoice());
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).reloadFragment(new FragmentTable());
            }
        });
    }

}