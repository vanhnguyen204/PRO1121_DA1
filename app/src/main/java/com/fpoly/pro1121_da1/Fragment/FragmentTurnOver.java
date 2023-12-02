package com.fpoly.pro1121_da1.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.InvoiceDAO;

public class FragmentTurnOver extends Fragment {


    TextView tv_starDay, tv_endDay, tv_turnover;
    Button btn_starDay,btn_endDay, btn_confirm;
    ImageView img_backHome;
    InvoiceDAO invoiceDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_turn_over, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_starDay = view.findViewById(R.id.tv_dayStart);
        tv_endDay = view.findViewById(R.id.tv_dateEnd);
        tv_turnover = view.findViewById(R.id.tv_turnover);

        btn_starDay = view.findViewById(R.id.btn_dateStart);
        btn_endDay = view.findViewById(R.id.btn_dateEnd);
        btn_confirm = view.findViewById(R.id.btn_confirm_Statistical);

        img_backHome = view.findViewById(R.id.img_back_home);

        invoiceDAO = new InvoiceDAO(getContext(),new Dbhelper(getContext()));

        btn_starDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_starDay.setText((year)+"-"+(month+1)+"-"+(dayOfMonth));
                    }
                },2024,01,02);
                datePickerDialog.show();
            }
        });

        btn_endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_endDay.setText((year)+"-"+(month+1)+"-"+(dayOfMonth));
                    }
                },2024,01,02);
                datePickerDialog.show();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            String getStartDay = tv_starDay.getText().toString().trim();
            String getEndDay = tv_endDay.getText().toString().trim();
            @Override
            public void onClick(View v) {
                tv_turnover.setText(""+invoiceDAO.getTotalBill(getStartDay,getEndDay));
            }
        });

        img_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).reloadFragment(new FragmentHome());
            }
        });

    }
}