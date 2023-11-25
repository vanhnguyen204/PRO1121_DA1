package com.fpoly.pro1121_da1.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.VoucherDAO;
import com.fpoly.pro1121_da1.model.Voucher;

import java.util.ArrayList;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.ViewHolder> {

    Activity activity;
    ArrayList<Voucher> list;

    VoucherDAO voucherDAO;


    public VoucherAdapter(Activity activity, ArrayList<Voucher> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recyclerview_voucher,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Voucher voucher = list.get(position);
        holder.tv_priceReduce.setText("Giảm giá : "+ voucher.getPriceReduce()+" %");
        holder.tv_dateExpiry.setText("Ngày hết hạn: "+ voucher.getDateExpiry());

        voucherDAO = new VoucherDAO(activity,new Dbhelper(activity));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                LayoutInflater inflater = activity.getLayoutInflater();
                View view1 = inflater.inflate(R.layout.dialog_update_voucher,null,false);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();

                EditText edt_update_priceReduce = view1.findViewById(R.id.edt_update_price_reduce_dialogAddVoucher);
                EditText edt_update_dateExpiry = view1.findViewById(R.id.edt_update_date_expiry_dialogAddVoucher);
                Button btn_updateVoucher = view1.findViewById(R.id.btn_update_voucher_dialog);


                btn_updateVoucher.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String getPriceReduce = edt_update_priceReduce.getText().toString().trim();
                        String getDateExpiry = edt_update_dateExpiry.getText().toString().trim();

                        Voucher voucher = new Voucher(list.get(position).getVoucherID(),Integer.valueOf(getPriceReduce),getDateExpiry);
                        list.set(position,voucher);
                        voucherDAO.updateVoucher(voucher,"Update Voucher thành công", "Update Voucher không thành công");
                        notifyItemChanged(position);
                        notifyItemRangeChanged(position, list.size());
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
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_priceReduce, tv_dateExpiry;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_priceReduce = itemView.findViewById(R.id.tv_price_reduce_itemVoucher);
            tv_dateExpiry = itemView.findViewById(R.id.tv_date_expiry_itemVoucher);
        }
    }
}
