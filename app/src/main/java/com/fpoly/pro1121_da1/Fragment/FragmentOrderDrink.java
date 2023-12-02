
package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.DrinkOrderAdapter;
import com.fpoly.pro1121_da1.Interface.MyOnItemClickListener;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.TableDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Table;

import java.util.ArrayList;

public class FragmentOrderDrink extends Fragment {

    RecyclerView recyclerView;
    public static ArrayList<String> listDrinkID;
    DrinkOrderAdapter drinkOrderAdapter;
    private Bundle bundle = null;
    ArrayList<Drink> list;
    DrinkDAO drinkDAO;
    ArrayList<Drink> listDrinkOrder;
    Button btnConfirmOrder;
    ImageView imgBack;
    Drink drinkClone = null;
    String getTableID;
    String saveTableID;
    Spinner spinner;
    int getStatus;
    int getInvoiceIdExport = 0;
    public static ArrayList<String> listDrinkID_checkbox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_order_drink, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = view.findViewById(R.id.spinner_fragmentOrder);
        recyclerView = view.findViewById(R.id.recyclerView_orderDrink);
        imgBack = view.findViewById(R.id.img_back_fragmentOrderDrink);
        drinkDAO = new DrinkDAO(getActivity(), new Dbhelper(getActivity()));
        list = drinkDAO.getAllDrink();
        drinkOrderAdapter = new DrinkOrderAdapter(list, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(drinkOrderAdapter);
        btnConfirmOrder = view.findViewById(R.id.btnConfirmSelectOrder);
        listDrinkOrder = new ArrayList<>();
        listDrinkID_checkbox = new ArrayList<>();

        listDrinkID = new ArrayList<>();
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            getTableID = bundle.getString("KEY_TABLE_ID");
            listDrinkID_checkbox = (ArrayList<String>) bundle.getSerializable("KEY_CHECKBOX");
            saveTableID = bundle.getString("KEY_SAVE_TABLE_ID");
            getInvoiceIdExport = bundle.getInt("KEY_INVOICE_ID_EXPORT");
            if (saveTableID != null) {
                getTableID = saveTableID;
            }
            if (listDrinkID_checkbox != null) {
                listDrinkID = listDrinkID_checkbox;
            }
        }

        String[] arrSpinner = new String[]{"Mang về", "Tại quán"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                arrSpinner
        );

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getStatus = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getStatus = 0;
            }
        });

        drinkOrderAdapter.setMyOnItemClick(new MyOnItemClickListener() {
            @Override
            public void onClick(Drink drink, int position) {

                listDrinkID.add(String.valueOf(drink.getDrinkID()));

            }

            @Override
            public void setDeleteDrink(Drink drink, int position) {
                for (int i = 0; i < listDrinkID.size(); i++) {
                    if (listDrinkID.get(i).equals(String.valueOf(drink.getDrinkID()))) {
                        listDrinkID.remove(i);
                    }
                }


            }
        });
        TableDAO tableDAO = new TableDAO(getContext(), new Dbhelper(getContext()));

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt("KEY_STATUS_TABLE", getStatus);
                bundle.putString("KEY_TABLE_ID_EXPORT", getTableID);
                bundle.putSerializable("KEY_ARRAY_DRINK_ID", listDrinkID); // Put anything what you want

                FragmentExportInvoice manager = new FragmentExportInvoice();
                manager.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.container_layout, manager).commit();

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentTable());
            }
        });
    }

}