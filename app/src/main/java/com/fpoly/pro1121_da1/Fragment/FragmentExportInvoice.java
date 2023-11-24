package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Invoice;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentExportInvoice extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_export_invoice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Invoice> list= (ArrayList<Invoice>)getArguments().getSerializable("KEY_ARRAY_DRINK");
        Toast.makeText(getContext(), ""+list.size(), Toast.LENGTH_SHORT).show();
    }
}