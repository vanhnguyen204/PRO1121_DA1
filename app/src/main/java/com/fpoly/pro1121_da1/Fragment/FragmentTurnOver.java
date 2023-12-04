package com.fpoly.pro1121_da1.Fragment;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.InvoiceDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        btn_starDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month < 9) {
                            tv_starDay.setText(year + "-" + "0" + (month + 1) + "-" + dayOfMonth);
                        } else if (dayOfMonth < 10) {

                            tv_starDay.setText(year + "-" + (month + 1) + "-0" + dayOfMonth);
                        } else {
                            tv_starDay.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        }
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btn_endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month < 9) {
                            tv_endDay.setText(year + "-" + "0" + (month + 1) + "-" + dayOfMonth);
                        } else if (dayOfMonth < 10) {

                            tv_endDay.setText(year + "-" + (month + 1) + "-0" + dayOfMonth);
                        } else {
                            tv_endDay.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        }
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                 String getStartDay = tv_starDay.getText().toString().trim();
                 String getEndDay = tv_endDay.getText().toString().trim();

                try {
                    if (checkDate2(getStartDay, getEndDay)){
                        Toast.makeText(getContext(), "Ngày bắt đầu phải nhỏ hơn ngày kết thúc.", Toast.LENGTH_SHORT).show();
                    }else {
                        int turnOver = invoiceDAO.getTotalBill(getStartDay,getEndDay);
                        tv_turnover.setText("Doanh thu: " + turnOver + "VNĐ");
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        img_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)requireActivity()).reloadFragment(new FragmentHome());
            }
        });

    }

    public boolean checkDate2(String day1, String day2) throws ParseException {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-mm-dd");
        Date date1 = spf.parse(day1);
        Date date2 = spf.parse(day2);
        int compare = date1.compareTo(date2);
        if (compare > 0) {
            return true;
        }else if (compare < 0){
            return false;
        }else  {
            return false;
        }
    }
}