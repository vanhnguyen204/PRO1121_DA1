package com.fpoly.pro1121_da1.spinner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fpoly.pro1121_da1.R;

public class SpinerCalender extends BaseAdapter {
    String shift[];
    Activity activity;

    public SpinerCalender(String[] shift, Activity activity) {
        this.shift = shift;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return shift.length;
    }

    @Override
    public Object getItem(int position) {
        return shift[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_spiner_calender,parent,false);


        return convertView;
    }
}
