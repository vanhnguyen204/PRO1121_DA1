package com.fpoly.pro1121_da1.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.TableAdapter;
import com.fpoly.pro1121_da1.Interface.TableOnClickListener;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.InvoiceDAO;
import com.fpoly.pro1121_da1.database.TableDAO;
import com.fpoly.pro1121_da1.model.Invoice;
import com.fpoly.pro1121_da1.model.InvoiceViewModel;
import com.fpoly.pro1121_da1.model.Table;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class FragmentTable extends Fragment {
    EditText edtSearchTable;
    RecyclerView recyclerView;
    private InvoiceViewModel viewModel;
    ArrayList<Table> list;
    TableDAO tableDAO;
    FloatingActionButton fab;
    TableAdapter tableAdapter;
    int sendInvoiceID = 0;
public void sendInvoiceIDFromFragmentt(int s){
    viewModel.setInvoiceData(String.valueOf(s));
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    private void getInvoiceData() {
        viewModel.getInvoiceData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // xử lý mã hoá đơn
                sendInvoiceID = Integer.parseInt(s);
                Toast.makeText(getContext(), "Đã lấy được mã bên hoá đơn: "+sendInvoiceID, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(InvoiceViewModel.class);

        getInvoiceData();


        edtSearchTable = view.findViewById(R.id.edt_search_table);
        recyclerView = view.findViewById(R.id.recyclerView_fragmentTable);
        tableDAO = new TableDAO(getContext(), new Dbhelper(getContext()));
        fab = view.findViewById(R.id.fab_addTable);
        list = tableDAO.getAllTable();
        tableAdapter = new TableAdapter(list, getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(tableAdapter);
        InvoiceDAO invoiceDAO = new InvoiceDAO(getContext(), new Dbhelper(getContext()));
        tableAdapter.setOnTableClick(new TableOnClickListener() {
            @Override
            public void setItemTableClick(Table table, int position) {
               if (table.getStatus() != 0) {
                    Invoice invoice = invoiceDAO.getInvoiceByTableID(table.getTableID());
                    Bundle bundle = new Bundle();
                   if (sendInvoiceID != 0){
                       sendInvoiceIDFromFragmentt(sendInvoiceID);
                       bundle.putInt("KEY_STATUS_TABLE", table.getStatus());
                       Toast.makeText(getContext(), "Mã hoá đơn: "+sendInvoiceID, Toast.LENGTH_SHORT).show();
                   }else {
                       bundle.putInt("KEY_INVOICE", invoice.getInvoiceID());
                       bundle.putInt("KEY_STATUS_TABLE", table.getStatus());
                       Toast.makeText(getContext(), "Mã hoá đơn: " + invoice.getInvoiceID(), Toast.LENGTH_SHORT).show();
                   }
                    bundle.putString("KEY_TABLE_ID_EXPORT", table.getTableID());

                    FragmentExportInvoice fragmentExportInvoice = new FragmentExportInvoice();
                    fragmentExportInvoice.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.container_layout, fragmentExportInvoice).commit();

                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("KEY_TABLE_ID", table.getTableID());
                    FragmentOrderDrink frm = new FragmentOrderDrink();
                    frm.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.container_layout, frm).commit();
                }

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có chắc muốn thêm bàn mới không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int idRandom = new Random().nextInt(1000);
                        String getTableID = "TB" + idRandom;
                        Table table = new Table(getTableID, 0);
                        if (tableDAO.insertTable(table)) {
                            list.add(table);
                            tableAdapter.notifyItemInserted(list.size());

                        }
                    }
                });

                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

}