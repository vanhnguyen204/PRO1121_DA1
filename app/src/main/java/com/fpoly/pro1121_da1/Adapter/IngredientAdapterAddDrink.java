package com.fpoly.pro1121_da1.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Interface.SetTextRecyclerView;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.Ingredient;

import java.util.ArrayList;

public class IngredientAdapterAddDrink extends RecyclerView.Adapter<IngredientAdapterAddDrink.Viewholder> {
    ArrayList<Ingredient> list;
    Activity activity;
    SetTextRecyclerView setTextRecyclerView;

    public void setTextForTextView(SetTextRecyclerView setTextRecyclerView) {
        this.setTextRecyclerView = setTextRecyclerView;
    }

    public IngredientAdapterAddDrink(ArrayList<Ingredient> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_addingredient_drink, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Ingredient ingredient = list.get(position);
        holder.imgIngredient.setImageResource(ingredient.getImage());
        holder.tvNameIngredient.setText(ingredient.getName());
        if (ingredient.getUnit().equals("Kg")) {
            holder.tvQuantity.setText("Số lượng: " + ingredient.getUnit());
        }
        setTextRecyclerView.onSetText(holder.tvQuantity, ingredient, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "id: "+ ingredient.getIngredientID(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgIngredient;
        TextView tvNameIngredient, tvQuantity;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgIngredient = itemView.findViewById(R.id.img_recyclerViewIngredient_drink);
            tvNameIngredient = itemView.findViewById(R.id.tv_nameIngredient_recyclerView);
            tvQuantity = itemView.findViewById(R.id.tv_quantity_recyclerview);
        }
    }

    EditText edtQuantity;
    Button btnConfirm;

    public void showDialogUpdateQuantity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Style_AlertDialog_Corner);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view1 = inflater.inflate(R.layout.alertdialog_update_quantity_ingredientdrink, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btnConfirm = view1.findViewById(R.id.btnConfirmUpdate);
        edtQuantity = view1.findViewById(R.id.edt_quantity_updateIngredientDrink);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
