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
import com.fpoly.pro1121_da1.Interface.SenDataClick;
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
                ((MainActivity)getContext()).reloadFragment(new FragmentAddDrink());
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
        adapter.sentData(new SenDataClick() {
            @Override
            public void sendData(Drink drink) {
                Bundle bundle = new Bundle();
                bundle.putInt("DRINK_ID", drink.getDrinkID());
                FragmentDrinkDetail detail = new FragmentDrinkDetail();
                detail.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container_layout, detail).commit();
            }
        });
    }


}