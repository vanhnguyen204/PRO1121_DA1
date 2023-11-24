package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Invoice;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentExportInvoice extends Fragment {

    ArrayList<Drink> list;
    TextView tvInvoiceID, tvNameStaff, tvNameDrink, tvTableID, tvTotalBill, tvDateCreate;
    ImageView imgAddCustomer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_export_invoice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            tvInvoiceID = view.findViewById(R.id.tv_invoiceID_fragmentExport);
            tvNameStaff = view.findViewById(R.id.tv_nameStaff_fragmentExport);
            tvNameDrink = view.findViewById(R.id.tv_drink_fragmentExport);
            tvTableID = view.findViewById(R.id.tv_table_fragmentExport);
            tvTotalBill = view.findViewById(R.id.tv_totalBill_fragmentExport);
            tvDateCreate = view.findViewById(R.id.tv_dateCreate_fragmentExport);
            imgAddCustomer = view.findViewById(R.id.img_addCustomer_fragmentExport);
                      
        getParentFragmentManager().setFragmentResultListener("KEY_ARR", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                list = (ArrayList<Drink>) result.getSerializable("KEY_ARRAY_DRINK");
                 Toast.makeText(getContext(), ""+list.size(), Toast.LENGTH_SHORT).show();
                 String getTableID = result.getString("KEY_TABLE_ID");
                Toast.makeText(getContext(), ""+getTableID, Toast.LENGTH_SHORT).show();
            }
        });

    }
}