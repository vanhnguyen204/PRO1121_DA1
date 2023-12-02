package com.fpoly.pro1121_da1.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.VoucherAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.NotificationDAO;
import com.fpoly.pro1121_da1.database.VoucherDAO;
import com.fpoly.pro1121_da1.model.Notification;
import com.fpoly.pro1121_da1.model.User;
import com.fpoly.pro1121_da1.model.Voucher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;


public class FragmentSales extends Fragment {

    ImageView img_Comeback_setting;
    RecyclerView recyclerView;
    Button btn_addVoucher;
    ArrayList<Voucher> listVoucher;
    VoucherDAO voucherDAO;
    VoucherAdapter voucherAdapter;
    User user;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = ((MainActivity) getActivity()).user;
        img_Comeback_setting = view.findViewById(R.id.img_back_fragmentSale);
        recyclerView = view.findViewById(R.id.rcv_sales);
        btn_addVoucher = view.findViewById(R.id.btn_add_voucher);

        voucherDAO = new VoucherDAO(getContext(), new Dbhelper(getContext()));
        listVoucher = voucherDAO.getAllVoucher();
        for (int i = 0; i < listVoucher.size(); i++) {
            if (listVoucher.get(i).getPriceReduce() == 0) {
                listVoucher.remove(i);
            }
        }
        voucherAdapter = new VoucherAdapter(getActivity(), listVoucher);

        recyclerView.setAdapter(voucherAdapter);

        NotificationDAO notificationDAO = new NotificationDAO(getContext());
        img_Comeback_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentSettings());
            }
        });


        btn_addVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View view1 = inflater.inflate(R.layout.dialog_add_voucher, null, false);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();

                EditText edt_Price_Reduce = view1.findViewById(R.id.edt_price_reduce_dialogAddVoucher);
                EditText edt_date_Expiry = view1.findViewById(R.id.edt_date_expiry_dialogAddVoucher);

                Button btnAdd = view1.findViewById(R.id.btn_add_voucher_dialog);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String get_Price_Reduce = edt_Price_Reduce.getText().toString().trim();
                        String get_date_Expiry = edt_date_Expiry.getText().toString().trim();


                        if (get_Price_Reduce.equals("")) {
                            Toast.makeText(getContext(), "Số phần trăm bạn muốn giảm giá không được để chống!", Toast.LENGTH_SHORT).show();
                        } else if (get_date_Expiry.equals("")) {
                            Toast.makeText(getContext(), "Số ngày hết hạn giảm giá không được để chống!", Toast.LENGTH_SHORT).show();
                        } else {

                            Voucher voucher = new Voucher(Integer.valueOf(get_Price_Reduce), get_date_Expiry);


                            if (voucherDAO.insertVoucher(voucher)) {
                                listVoucher.add(voucher);
                                voucherAdapter.notifyItemInserted(listVoucher.size());
                                notificationDAO.insertNotifi(new Notification(user.getFullName() + "\nĐã thêm phiếu giảm giá mới","now"));
                            }
                        }
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getLayoutPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Cảnh báo !");
                builder.setMessage("Bạn có muốn xóa Voucher này không ?");

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        voucherAdapter.notifyItemChanged(position);
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listVoucher.remove(position);
                        voucherDAO.deleteVoucher(listVoucher.get(position).getVoucherID(), "Xoa Voucher thành công", "Xóa voucher không thành công");
                        voucherAdapter.notifyItemRemoved(position);
                    }
                });

                builder.show();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

}