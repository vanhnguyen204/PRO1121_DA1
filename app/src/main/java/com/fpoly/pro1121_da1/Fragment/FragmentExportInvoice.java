package com.fpoly.pro1121_da1.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CustomerDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.InvoiceDAO;
import com.fpoly.pro1121_da1.model.Customer;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Invoice;
import com.fpoly.pro1121_da1.model.User;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class FragmentExportInvoice extends Fragment {

    ArrayList<Drink> list;
    ArrayList<String> listDrinkID;
    TextView tvInvoiceID, tvNameStaff, tvNameDrink, tvTableID, tvTotalBill, tvDateCreate;
    ImageView imgAddCustomer, imgBack;
    String getTableID;
    Button btnConfirm;

String getDrinkID = "";
    int sumPrice = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_export_invoice, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvInvoiceID = view.findViewById(R.id.tv_invoiceID_fragmentExport);
        tvNameStaff = view.findViewById(R.id.tv_nameStaff_fragmentExport);
        tvNameDrink = view.findViewById(R.id.tv_drink_fragmentExport);
        tvTableID = view.findViewById(R.id.tv_table_fragmentExport);
        tvTotalBill = view.findViewById(R.id.tv_totalBill_fragmentExport);
        tvDateCreate = view.findViewById(R.id.tv_dateCreate_fragmentExport);
        imgAddCustomer = view.findViewById(R.id.img_addCustomer_fragmentExport);
        imgBack = view.findViewById(R.id.img_back_fragmentExport);
        btnConfirm = view.findViewById(R.id.btnConfirmExportInvoice);

        InvoiceDAO invoiceDAO = new InvoiceDAO(getContext(), new Dbhelper(getContext()));

        int invoiceID = new Random().nextInt(10000);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listDrinkID = (ArrayList<String>) bundle.getSerializable("KEY_ARRAY_DRINK_ID");
            getTableID = bundle.getString("KEY_TABLE_ID_EXPORT");
        }
        DrinkDAO drinkDAO = new DrinkDAO(getContext(), new Dbhelper(getContext()));
        String nameDrink = "";

        for (int i = 0; i < listDrinkID.size(); i++) {
            nameDrink += drinkDAO.getDrinkByID(listDrinkID.get(i)).getName() + ", ";
            sumPrice += drinkDAO.getDrinkByID(listDrinkID.get(i)).getPrice();
            getDrinkID += listDrinkID.get(i)+" ";
        }


        tvTotalBill.setText("Tổng tiền: " + sumPrice + " VNĐ");
        tvNameDrink.setText("Đồ uống đã chọn: " + nameDrink);
        User user = ((MainActivity) getActivity()).user;
        tvNameStaff.setText("Nhân viên: " + user.getFullName());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String getDateCreate = dateFormat.format(cal.getTime());
        tvDateCreate.setText("Ngày tạo: " + getDateCreate);
        tvTableID.setText("Mã bàn: " + getTableID);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn đang tạo hoá đơn, bạn có chắc muốn thoát không");
                builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("KEY_CHECKBOX", listDrinkID);
                        bundle1.putString("KEY_SAVE_TABLE_ID", getTableID);
                        FragmentOrderDrink frm = new FragmentOrderDrink();
                        frm.setArguments(bundle1);
                        getParentFragmentManager().beginTransaction().replace(R.id.container_layout, frm).commit();

                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });

        imgAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int getCustomerID = new Random().nextInt(10000);
                showAlertCreateNewCustomer(getCustomerID);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Invoice invoice = new Invoice(invoiceID, user.getUserID(), 1, getDrinkID, getTableID, sumPrice, getDateCreate);
                if (invoiceDAO.insertInvoice(invoice)){

                    getParentFragmentManager().beginTransaction().replace(R.id.container_layout, new FragmentTable()).commit();
                }
            }
        });
    }
    EditText edtNameCustomer, edtPhoneNumber;
    Button btnConfirmAddUser;
    public void showAlertCreateNewCustomer(int customerID){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Style_AlertDialog_Corner);
        LayoutInflater inflater =getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_addcustomer, null);
        builder.setView(view);
        AlertDialog alert = builder.create();
        alert.show();
        CustomerDAO customerDAO =new CustomerDAO(getContext(), new Dbhelper(getContext()));
        edtNameCustomer = view.findViewById(R.id.edtNameCustomer);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumberCustomer);
        btnConfirmAddUser = view.findViewById(R.id.btnConfirmAddCustomer);

        btnConfirmAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = edtNameCustomer.getText().toString().trim();
                String getPhone = edtPhoneNumber.getText().toString().trim();
                if (getName.length()==0){
                    Toast.makeText(getContext(), "Không được để trống tên ", Toast.LENGTH_SHORT).show();
                } else if (getPhone.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống phone ", Toast.LENGTH_SHORT).show();
                }else {
                    if (customerDAO.insertCustomer(new Customer(customerID, getName, getPhone), "Thêm khách hàng oke", "Chưa thêm được rùi")){
                        alert.dismiss();
                        imgAddCustomer.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });



    }

}