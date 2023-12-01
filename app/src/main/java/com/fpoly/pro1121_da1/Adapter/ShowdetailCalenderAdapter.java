package com.fpoly.pro1121_da1.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.CalendarWorkForStaff;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ShowdetailCalenderAdapter extends RecyclerView.Adapter<ShowdetailCalenderAdapter.ViewHoder> {

    Activity activity;
    ArrayList<CalendarWorkForStaff> list;

    public ShowdetailCalenderAdapter(Activity activity, ArrayList<CalendarWorkForStaff> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_showdetail_calenderforork,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder{
        TextView tv_nameStaff, tv_StaffID;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            tv_StaffID = itemView.findViewById(R.id.tv_staffID_item_showDetailCalendarWork);
            tv_nameStaff = itemView.findViewById(R.id.tv_name_staff);
        }
    }
}
