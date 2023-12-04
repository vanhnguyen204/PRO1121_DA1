package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.DrinkAdapter;
import com.fpoly.pro1121_da1.Interface.SenDataClick;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.NotificationDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Notification;
import com.fpoly.pro1121_da1.model.User;
import com.fpoly.pro1121_da1.spinner.SpinnerTypeOfDrink;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentDrink extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Drink> list;
    DrinkDAO drinkDAO;
    EditText edtSearch;
    DrinkAdapter adapter;
    FloatingActionButton btnAddDrink;
    View view;
    ImageView imgBack;

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
        imgBack = view.findViewById(R.id.img_back_fragmentDrink);
        NotificationDAO notificationDAO = new NotificationDAO(getContext());
        list = drinkDAO.getDrinkNow();
        if (list.size() == 0) {
            Toast.makeText(getContext(), "Chưa có dữ liệu đồ uống", Toast.LENGTH_SHORT).show();
        }
        recyclerView = view.findViewById(R.id.rcv_drink);
        ((MainActivity) requireActivity()).chipNavigationBar.setVisibility(View.INVISIBLE);
        adapter = new DrinkAdapter(list, getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).reloadFragment(new FragmentHome());
                ((MainActivity) requireActivity()).chipNavigationBar.setVisibility(View.VISIBLE);
                ((MainActivity) requireActivity()).chipNavigationBar.setItemSelected(R.id.home, true);
            }
        });

        User user = ((MainActivity) requireActivity()).user;

        if (user.getRole().equalsIgnoreCase("staff")) {
            btnAddDrink.setVisibility(View.INVISIBLE);
            btnAddDrink.setVisibility(View.GONE);
        }

        btnAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).reloadFragment(new FragmentAddDrink());
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
                    for (int index = 0; index < listClone.size(); index++) {
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
                getParentFragmentManager().beginTransaction().replace(R.id.container_layout, detail).commit();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    btnAddDrink.setVisibility(View.INVISIBLE);
                } else {
                    btnAddDrink.setVisibility(View.VISIBLE);
                }
            }
        });


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