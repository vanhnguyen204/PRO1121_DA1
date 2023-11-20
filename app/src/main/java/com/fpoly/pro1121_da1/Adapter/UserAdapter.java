package com.fpoly.pro1121_da1.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Viewhoder> {

    Activity activity;
    ArrayList<User> list;

    public UserAdapter(Activity activity, ArrayList<User> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_uer_detail,parent,false);
        return  new Viewhoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhoder holder, int position) {

        holder.TVName.setText("Họ tên: "+list.get(position).getUserName());
        holder.TVBirthDay.setText("Ngày sinh: "+list.get(position).getDateOfBirth());
        holder.TVAddress.setText("Địa chỉ: "+list.get(position).getAddress());

        holder.TVshowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewhoder extends RecyclerView.ViewHolder{
        TextView TVName, TVBirthDay,TVAddress ,TVshowDetail;
        public Viewhoder(@NonNull View itemView) {
            super(itemView);

             TVName = itemView.findViewById(R.id.tv_nameUer_itemDetailUser);
            TVBirthDay = itemView.findViewById(R.id.tv_birthDay_itemDetailUser);
            TVAddress  = itemView.findViewById(R.id.tv_addRess_itemDetailUser);
            TVshowDetail  = itemView.findViewById(R.id.tv_showDetail);


        }
    }
}
