package com.fpoly.pro1121_da1.spinner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fpoly.pro1121_da1.R;

public class SpinnerRole extends BaseAdapter {
    String role[];
    Activity activity;

    public SpinnerRole(String[] role, Activity activity) {
        this.role = role;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return role.length;
    }

    @Override
    public Object getItem(int i) {
        return role[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.item_spinner_role, viewGroup, false);
        TextView tvRole = view.findViewById(R.id.tv_role_spinner);
        tvRole.setText(role[i]);
        return view;
    }
}
