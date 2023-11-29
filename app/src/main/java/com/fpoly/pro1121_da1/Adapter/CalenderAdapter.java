package com.fpoly.pro1121_da1.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
    String getShiftWork;

    public CalenderAdapter(Activity activity, ArrayList<CalenderWork> listCalender) {
        this.activity = activity;
        this.listCalender = listCalender;
    }

    @NonNull
    @Override
    public ViewHolder_Calender onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view   =  inflater.inflate(R.layout.item_recyclerview_workcalender,parent,false);
        return new ViewHolder_Calender(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_Calender holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_dayofWork.setText(listCalender.get(position).getDayofWork());
        holder.tv_shiftWork.setText(listCalender.get(position).getShiftWork());


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Style_AlertDialog_Corner);
                LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_update_calender,null,false);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();

                calenderDAO = new CalenderDAO(activity,new Dbhelper(activity));

                EditText edt_dayOfWork = view.findViewById(R.id.edt_dayOfwork_dialogUpdateCalender);
                spinner = view.findViewById(R.id.spn_shiftWork_dialog);
                Button btn_updateCalender = view.findViewById(R.id.btn_updateCalender);


                String[] shiftWork = new String[]{
                        "Ca sáng: 8h -> 13h",
                        "Ca chiều: 13h -> 18h",
                        "Ca tối: 18h -> 23h"
                };

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        activity,
                        android.R.layout.simple_spinner_dropdown_item,
                        shiftWork
                );

                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getShiftWork = shiftWork[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                edt_dayOfWork.setText(listCalender.get(position).getDayofWork());

                setDefauselected(shiftWork,listCalender.get(position).getShiftWork());
                btn_updateCalender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String getDayOfWork = edt_dayOfWork.getText().toString().trim();


                        CalenderWork calenderWork = new CalenderWork(listCalender.get(position).getId_calender(),getDayOfWork,getShiftWork);
                        listCalender.set(position,calenderWork);
                        calenderDAO.updateCalender(calenderWork,"Update lịch làm việc thành công","Update lịch làm việc không thành công");
                        notifyItemChanged(position);
                        notifyItemRangeChanged(position, listCalender.size());

                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCalender.size();
    }

    public static class ViewHolder_Calender extends RecyclerView.ViewHolder{
        TextView tv_dayofWork , tv_shiftWork;
        public ViewHolder_Calender(@NonNull View itemView) {
            super(itemView);
            tv_dayofWork = itemView.findViewById(R.id.tv_dayWork_item);
            tv_shiftWork = itemView.findViewById(R.id.tv_shiftWork_item);

        }
    }
    public void setDefauselected(String arrShiftWork[], String ShiftWork){

        for (int i = 0; i < arrShiftWork.length; i++) {
            if (ShiftWork.equals(arrShiftWork[i])){
               spinner.setSelection(i);
            }
        }

    }
}
