package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.DrinkAdapter;
import com.fpoly.pro1121_da1.Adapter.HistoryInvoiceAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.InvoiceDAO;
import com.fpoly.pro1121_da1.model.Invoice;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;

public class FragmentHistoryInvoice extends Fragment {
    RecyclerView recyclerView;
    HistoryInvoiceAdapter adapter;
    ArrayList<Invoice> list;
    InvoiceDAO invoiceDAO;
    ImageView imgBack;
    EditText edtSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_invoice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        invoiceDAO = new InvoiceDAO(getContext(), new Dbhelper(getContext()));
        recyclerView = view.findViewById(R.id.recyclerview_history);
        list = invoiceDAO.getAllInvoice();
        User user = ((MainActivity)requireActivity()).user;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (user.getRole().equals("admin")){
            adapter = new HistoryInvoiceAdapter(getActivity(), list, getParentFragmentManager());
            recyclerView.setAdapter(adapter);

        }else {
            ArrayList<Invoice> listInvoiceStaff = invoiceDAO.getInvoiceByIdUser(user.getUserID());
            adapter = new HistoryInvoiceAdapter(getActivity(), listInvoiceStaff, getParentFragmentManager());
            recyclerView.setAdapter(adapter);
        }

        imgBack = view.findViewById(R.id.img_back_fragmentHistoryInvoice);

        ArrayList<Invoice> listClone = invoiceDAO.getAllInvoice();
        edtSearch = view.findViewById(R.id.edt_searchHistoryInvoice);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getRole().equals("admin")){
                    ((MainActivity)requireActivity()).reloadFragment(new FragmentSettings());
                }else {
                    ((MainActivity)requireActivity()).reloadFragment(new FragmentSettingsStaff());
                }

            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();

                String getTextFromEdt = charSequence.toString().toLowerCase().trim();
                if (getTextFromEdt.length() == 0) {
                    recyclerView.setAdapter(new HistoryInvoiceAdapter(getActivity(), listClone, getParentFragmentManager()));
                } else {
                    for (int index = 0; index < listClone.size(); index++) {
                        if (listClone.get(index).getDateCreate().toLowerCase().contains(getTextFromEdt)) {
                            list.add(listClone.get(index));
                        }
                    }
                    recyclerView.setAdapter(new HistoryInvoiceAdapter(getActivity(), list, getParentFragmentManager()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}