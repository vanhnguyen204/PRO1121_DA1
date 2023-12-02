package com.fpoly.pro1121_da1.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Fragment.FragmentShowDetailCalendar;
import com.fpoly.pro1121_da1.Interface.SenDataCalenderWorkClick;
import com.fpoly.pro1121_da1.Interface.SenDataClick;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CalenderDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.model.CalenderWork;

import java.util.ArrayList;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder_Calender> {

    Activity activity;
    ArrayList<CalenderWork> listCalender;

    CalenderDAO calenderDAO;
    Spinner spinner;
    int getShiftWork;
    CalenderWork calenderWork;
    public SenDataCalenderWorkClick senDataCalenderWorkClick;

    public void setOnCalendaClick(SenDataCalenderWorkClick senDataCalenderWorkClick) {
        this.senDataCalenderWorkClick = senDataCalenderWorkClick;
    }

    public CalenderAdapter(Activity activity, ArrayList<CalenderWork> listCalender) {
        this.activity = activity;
        this.listCalender = listCalender;
    }

    @NonNull
    @Override
    public ViewHolder_Calender onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recyclerview_workcalender, parent, false);
        return new ViewHolder_Calender(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_Calender holder, @SuppressLint("RecyclerView") int position) {
        calenderWork = listCalender.get(holder.getAdapterPosition());
        holder.tv_dayofWork.setText("Ngày Làm Việc:" + calenderWork.getDayofWork());
        holder.tv_shiftWork.setText("Ca làm việc:" + calenderWork.getShiftWork());



        holder.tv_showdetail_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               senDataCalenderWorkClick.senData(calenderWork, position, listCalender.get(holder.getAdapterPosition()).getShiftWork() ,listCalender.get(holder.getAdapterPosition()).getId_calender() );
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCalender.size();
    }

    public static class ViewHolder_Calender extends RecyclerView.ViewHolder {
        TextView tv_dayofWork, tv_shiftWork, tv_showdetail_calender;

        public ViewHolder_Calender(@NonNull View itemView) {
            super(itemView);
            tv_dayofWork = itemView.findViewById(R.id.tv_dayWork_item);
            tv_shiftWork = itemView.findViewById(R.id.tv_shiftWork_item);
            tv_showdetail_calender = itemView.findViewById(R.id.tv_showdetail_calendarwork_item);

        }
    }

    public void setDefauselected(Integer arrShiftWork[], int ShiftWork) {

        for (int i = 0; i < arrShiftWork.length; i++) {
            if (ShiftWork == arrShiftWork[i]) {
                spinner.setSelection(i);
            }
        }

    }
}
