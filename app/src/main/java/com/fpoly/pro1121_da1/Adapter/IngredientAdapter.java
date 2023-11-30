package com.fpoly.pro1121_da1.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Fragment.FragmentIngredientDetail;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.model.Voucher;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.Viewholder> {
    Activity activity;
    ArrayList<Ingredient> list;

    public IngredientAdapter(Activity activity, ArrayList<Ingredient> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_ingredient, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        Ingredient ingredient = list.get(position);
        holder.tvName.setText(String.format("Tên nguyên liệu: %s", ingredient.getName()));
        holder.tvQuantity.setText(String.format("Số lượng: %s", ingredient.getQuantity() +" "+ ingredient.getUnit()));
        holder.tvSeeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient ingredient1 = list.get(position);
                Bundle bundle = new Bundle();
                // Put anything what you want
                bundle.putString("KEY_ING_ID", ingredient1.getIngredientID());

                FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
                manager.setFragmentResult("KEY_INGREDIENT", bundle);

                ((MainActivity) activity).reloadFragment(new FragmentIngredientDetail());
            }
        });
        holder.imgIngredient.setImageResource(ingredient.getImage());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgIngredient;
        TextView tvName, tvQuantity, tvSeeDetail;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgIngredient = itemView.findViewById(R.id.img_ingredient);
            tvName = itemView.findViewById(R.id.tv_name_fragmentIngredient);
            tvQuantity = itemView.findViewById(R.id.tv_quantity_fragmentIngredient);
            tvSeeDetail = itemView.findViewById(R.id.tv_seeDetail_ingredient);

        }
    }
}
