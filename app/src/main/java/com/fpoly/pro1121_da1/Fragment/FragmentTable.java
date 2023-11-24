package com.fpoly.pro1121_da1.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fpoly.pro1121_da1.Adapter.TableAdapter;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.TableDAO;
import com.fpoly.pro1121_da1.model.Table;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class FragmentTable extends Fragment {
    EditText edtSearchTable;
    RecyclerView recyclerView;
    ArrayList<Table> list;
    TableDAO tableDAO;
    FloatingActionButton fab;
    TableAdapter tableAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtSearchTable = view.findViewById(R.id.edt_search_table);
        recyclerView = view.findViewById(R.id.recyclerView_fragmentTable);
        tableDAO = new TableDAO(getContext(), new Dbhelper(getContext()));
        fab = view.findViewById(R.id.fab_addTable);
        list = tableDAO.getAllTable();
        tableAdapter = new TableAdapter(list, getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(tableAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có chắc muốn thêm bàn mới không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int idRandom = new Random().nextInt(1000);
                        String getTableID = "TB"+idRandom;
                        Table table = new Table(getTableID, 0);
                        if (tableDAO.insertTable(table)){
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