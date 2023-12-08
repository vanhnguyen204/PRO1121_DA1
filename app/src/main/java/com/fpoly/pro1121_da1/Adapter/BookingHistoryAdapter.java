package com.fpoly.pro1121_da1.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Booking;

import java.util.ArrayList;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.BookingHistoryViewHolder> {
    ArrayList<Booking> list;
    Activity activity;

    public BookingHistoryAdapter(ArrayList<Booking> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public BookingHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_bookinghistory, parent, false);
        return new BookingHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHistoryViewHolder holder, int position) {
        Booking booking = list.get(position);
        holder.tvID.setText("Mã bàn: "+booking.getTableID());
        holder.tvDay.setText("Ngày đặt: "+booking.getDayBooking() +" lúc: "+booking.getHourBooking());
        holder.tvNameCustomer.setText("Tên khách hàng: "+booking.getNameCustomer());
        holder.tvPhoneNumber.setText("Số điện thoại: "+booking.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BookingHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvID, tvNameCustomer, tvDay, tvPhoneNumber;

        public BookingHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_idBooking_history);
            tvNameCustomer = itemView.findViewById(R.id.tv_nameCustomerBooking_history);
            tvDay = itemView.findViewById(R.id.tv_dayBooking_history);
            tvPhoneNumber = itemView.findViewById(R.id.tv_phoneNumber_historyBooking);
        }
    }
}
