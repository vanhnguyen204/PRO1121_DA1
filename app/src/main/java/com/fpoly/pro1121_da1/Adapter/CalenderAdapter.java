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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Fragment.FragmentCalendar;
import com.fpoly.pro1121_da1.Fragment.FragmentShowDetailCalendar;
import com.fpoly.pro1121_da1.Interface.SenDataCalenderWorkClick;
import com.fpoly.pro1121_da1.Interface.SenDataClick;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CalenderDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.model.CalenderWork;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder_Calender> {

    Activity activity;
    ArrayList<CalenderWork> listCalender;


    public FragmentManager fragmentManager;


    public CalenderAdapter(Activity activity, ArrayList<CalenderWork> listCalender, FragmentManager fragmentManager) {
        this.activity = activity;
        this.listCalender = listCalender;
        this.fragmentManager = fragmentManager;

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
        CalenderWork calenderWork = listCalender.get(holder.getAdapterPosition());
        holder.tv_dayofWork.setText("Ngày Làm Việc:" + calenderWork.getDayofWork());

        if (calenderWork.getShiftWork() == 1){
            holder.tv_shiftWork.setText("Ca sáng: 7:00 - 11:30");
        } else if (calenderWork.getShiftWork()   == 2) {
            holder.tv_shiftWork.setText("Ca chiều: 12:00 - 16:30");
        }else {
            holder.tv_shiftWork.setText("Ca tối: 17:00 - 22:00");
        }
        User user = ((MainActivity)activity).user;
        if (!user.getRole().equals("admin")){
            holder.tv_showdetail_calender.setVisibility(View.INVISIBLE);
        }else {
            holder.tv_showdetail_calender.setVisibility(View.VISIBLE);
        }
        holder.tv_showdetail_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("KEY_DAY_WORK", calenderWork.getDayofWork());
                bundle.putInt("KEY_SHIFT_WORK", calenderWork.getShiftWork());
                bundle.putInt("KEY_CALENDAR_ID", calenderWork.getId_calender());
                FragmentShowDetailCalendar frm = new FragmentShowDetailCalendar();
                frm.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.container_layout, frm).commit();

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

}
