package com.fpoly.pro1121_da1.spinner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Voucher;

import java.util.ArrayList;

public class SpinnerVoucher extends BaseAdapter {
    ArrayList<Voucher> list;
    Activity activity;

    public SpinnerVoucher(ArrayList<Voucher> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.spinner_voucher_adddrink, viewGroup, false);
        TextView tv = view.findViewById(R.id.tv_voucherPercent);
        if (i == 0){
            tv.setText("-- Chọn phiếu giảm giá --");
        }else {

            tv.setText(list.get(i).getPriceReduce()+" %");

        }
        return view;
    }
}
