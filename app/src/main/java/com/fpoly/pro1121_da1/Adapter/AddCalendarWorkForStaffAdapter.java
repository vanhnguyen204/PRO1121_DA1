package com.fpoly.pro1121_da1.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CalenderDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.model.CalenderWork;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;

public class AddCalendarWorkForStaffAdapter extends RecyclerView.Adapter<AddCalendarWorkForStaffAdapter.ViewHolder> {

    Activity activity;
    ArrayList<User> list;
    ArrayList<CalenderWork> calenderWorkArrayList;
    CalenderDAO calenderDAO;

    public AddCalendarWorkForStaffAdapter(Activity activity, ArrayList<User> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view   = inflater.inflate(R.layout.item_recyclerview_add_calendar_work_for_staff,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_nameStaff.setText("Họ và tên:"+list.get(position).getFullName());
        holder.tv_phoneNumber.setText("SDY: "+list.get(position).getPhoneNumber());

        calenderDAO = new CalenderDAO(activity,new Dbhelper(activity));




    }

    @Override

    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nameStaff,tv_phoneNumber;
        CheckBox chk_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameStaff = itemView.findViewById(R.id.tv_nameStaff_itemAddCalendarWorkForStaff);
            tv_phoneNumber = itemView.findViewById(R.id.tv_phoneNumberStaff_itemAddCalendarWorkForStaff);
            chk_status = itemView.findViewById(R.id.chk_status_itemAddCalendarWorkForStaff);

        }
    }
}
