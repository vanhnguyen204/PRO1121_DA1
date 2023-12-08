package com.fpoly.pro1121_da1.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Interface.BookingInterface;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.TableDAO;
import com.fpoly.pro1121_da1.model.Table;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    Activity activity;
    ArrayList<Table> listTable;
    TableDAO tableDAO;
    public BookingInterface bookingInterface;
    public void setOnTableBookingListener(BookingInterface bookingInterface){
        this.bookingInterface = bookingInterface;
    }

    public BookingAdapter(Activity activity, ArrayList<Table> listTable) {
        this.activity = activity;
        this.listTable = listTable;
        tableDAO = new TableDAO(activity, new Dbhelper(activity));
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Table table = listTable.get(position);
        holder.tvTableId.setText("Mã bàn: "+ table.getTableID());
        holder.chkBooking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
             if (b){
                 bookingInterface.onBooking(table, position);
             }else {
                 bookingInterface.onCancelBooking(table, position);
             }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTable.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView tvTableId, tvCountDown;
        ImageView imgTable;
        CheckBox chkBooking;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTableId = itemView.findViewById(R.id.tv_tableID_recyclerBooking);
            tvCountDown = itemView.findViewById(R.id.countDown_booking);
            imgTable = itemView.findViewById(R.id.img_booking);
            chkBooking = itemView.findViewById(R.id.chk_checkedBooking);
        }
    }
}
