package com.fpoly.pro1121_da1.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Interface.SenDataClick;
import com.fpoly.pro1121_da1.Interface.SetDelete;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CalenderShowDetailDAO;
import com.fpoly.pro1121_da1.model.CalendarWorkForStaff;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;
import java.util.Set;
import java.util.zip.Inflater;

public class ShowdetailCalenderAdapter extends RecyclerView.Adapter<ShowdetailCalenderAdapter.Viewhoder> {


    Activity activity;
    ArrayList<User> list;
    public SetDelete setDelete;
    public int calendarID;

    public void onDeleteUser(SetDelete setDelete) {
        this.setDelete = setDelete;
    }


    public ShowdetailCalenderAdapter(Activity activity, ArrayList<User> list, int calendarID) {
        this.activity = activity;
        this.list = list;
        this.calendarID = calendarID;
    }

    @NonNull
    @Override
    public Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_showdetail_calenderforork, parent, false);
        return new Viewhoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhoder holder, @SuppressLint("RecyclerView") int position) {
        User user = list.get(position);
        holder.tv_nameStaff.setText(list.get(position).getFullName());
        holder.tv_StaffID.setText(list.get(position).getUserID());
        CalenderShowDetailDAO calenderShowDetailDAO = new CalenderShowDetailDAO(activity);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Xoá user");
                builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (calenderShowDetailDAO.deleteCalendarWorkForStaff(user.getUserID(), calendarID)) {
                          list.remove(position);
                            Toast.makeText(activity, "Xoá thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(activity, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewhoder extends RecyclerView.ViewHolder {
        TextView tv_nameStaff, tv_StaffID;

        public Viewhoder(@NonNull View itemView) {
            super(itemView);

            tv_StaffID = itemView.findViewById(R.id.tv_staffID_item_showDetailCalendarWork);
            tv_nameStaff = itemView.findViewById(R.id.tv_name_staff);
        }
    }
}
