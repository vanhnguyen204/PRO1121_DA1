package com.fpoly.pro1121_da1.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Fragment.FragmentInvoiceDetail;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Invoice;

import java.util.ArrayList;

public class HistoryInvoiceAdapter extends RecyclerView.Adapter<HistoryInvoiceAdapter.Viewholder> {
    Activity activity;
    ArrayList<Invoice> list;
    FragmentManager fragmentManager;

    public HistoryInvoiceAdapter(Activity activity, ArrayList<Invoice> list, FragmentManager fragmentManager) {
        this.activity = activity;
        this.list = list;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_historyinvoice, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Invoice invoice = list.get(position);
        holder.tvInvoiceID.setText("Mã hoá đơn: " + invoice.getInvoiceID());
        holder.tvTotalBill.setText("Tổng tiền: " + invoice.getTotalBill());
        holder.tvTime.setText("Thời gian: " + invoice.getDateCreate());
        holder.tvShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("KEY_INVOICE_ID", invoice.getInvoiceID());
                FragmentInvoiceDetail frm = new FragmentInvoiceDetail();
                frm.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.container_layout, frm).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView tvInvoiceID, tvTotalBill, tvTime, tvShowDetail;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvInvoiceID = itemView.findViewById(R.id.tv_invoiceID);
            tvTotalBill = itemView.findViewById(R.id.tv_totalbill);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvShowDetail = itemView.findViewById(R.id.tv_showInvoiceDetail);
        }
    }
}
