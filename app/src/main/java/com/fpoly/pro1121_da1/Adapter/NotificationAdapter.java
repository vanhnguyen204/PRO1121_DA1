package com.fpoly.pro1121_da1.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Interface.SetTextRecyclerviewNotify;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Notification;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Viewholder> {
    ArrayList<Notification> list;
    Activity activity;
    SetTextRecyclerviewNotify setTextRecyclerviewNotify;
    SetTextRecyclerviewNotify setTextRecyclerviewNotify2;

    public void setTextNotify(SetTextRecyclerviewNotify setTextRecyclerviewNotify) {
        this.setTextRecyclerviewNotify = setTextRecyclerviewNotify;
    }
    public void setTextNotify2(SetTextRecyclerviewNotify setTextRecyclerviewNotify) {
        this.setTextRecyclerviewNotify2 = setTextRecyclerviewNotify;
    }

    public NotificationAdapter(ArrayList<Notification> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_notification, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Notification notification = list.get(position);
        setTextRecyclerviewNotify.setText(holder.tvMessage, notification, position);
        setTextRecyclerviewNotify2.setText(holder.tvTime, notification, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
