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
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.HistoryInvoiceAdapter;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.InvoiceDAO;
import com.fpoly.pro1121_da1.model.Invoice;

import java.util.ArrayList;

public class FragmentHistoryInvoice extends Fragment {
    RecyclerView recyclerView;
    HistoryInvoiceAdapter adapter;
    ArrayList<Invoice> list;
    InvoiceDAO invoiceDAO;
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
        list = invoiceDAO.getAllInvoice();
        adapter = new HistoryInvoiceAdapter(getActivity(), list);
        recyclerView = view.findViewById(R.id.recyclerview_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        ArrayList<Invoice> listClone = invoiceDAO.getAllInvoice();
        edtSearch = view.findViewById(R.id.edt_searchHistoryInvoice);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}