package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.InvoiceDAO;
import com.fpoly.pro1121_da1.database.InvoiceDetailDao;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Invoice;
import com.fpoly.pro1121_da1.model.InvoiceDetail;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;

public class FragmentInvoiceDetail extends Fragment {
    TextView tvInvoiceID, tvNameStaff, tvNameCustomer, tvTableID, tvServe, tvInforDrink, tvTotalBill, tvDateCreate, tvStatus;
    TextView tvQuantity, tvPrice, tvExpiry;
    int getInvoiceID;

    //tv_quantityDrink
//tv_priceDrink
//tv_dateExpiry_drink
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invoice_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvInvoiceID = view.findViewById(R.id.tv_idInvoice_invoiceDetail);
        tvNameStaff = view.findViewById(R.id.tv_nameStaff_invoiceDetail);
        tvNameCustomer = view.findViewById(R.id.tv_nameCustomer__invoiceDetail);
        tvTableID = view.findViewById(R.id.tv_tableID_invoiceDetail);
        tvServe = view.findViewById(R.id.tv_serve_invoiceDetail);
        tvInforDrink = view.findViewById(R.id.tv_inforDrink_invoiceDetail);
        tvTotalBill = view.findViewById(R.id.tv_totalBill_invoiceDetail);
        tvDateCreate = view.findViewById(R.id.tv_dateCreate_invoiceDetail);
        tvStatus = view.findViewById(R.id.tv_status_invoiceDetail);
        tvQuantity = view.findViewById(R.id.tv_quantityDrink);
        tvPrice = view.findViewById(R.id.tv_priceDrink);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            getInvoiceID = bundle.getInt("KEY_INVOICE_ID");
        }

        UserDAO userDAO = new UserDAO(getActivity(), new Dbhelper(getActivity()));

        InvoiceDAO invoiceDAO = new InvoiceDAO(getActivity(), new Dbhelper(getActivity()));
        Invoice invoice = invoiceDAO.getInvoiceByID(getInvoiceID);

        InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDao(getActivity());

        DrinkDAO drinkDAO = new DrinkDAO(getContext(), new Dbhelper(getContext()));
        ArrayList<Drink> listDrink = new ArrayList<>();
       ArrayList<InvoiceDetail> invoiceDetailArrayList = invoiceDetailDao.getInvoiceDetail("SELECT * FROM InvoiceDetail");

        for (int i = 0; i < invoiceDetailArrayList.size(); i++) {
            if (invoiceDetailArrayList.get(i).getInvoiceID() != getInvoiceID){
                invoiceDetailArrayList.remove(i);
                i--;
            }
        }
        Toast.makeText(getContext(), ""+invoiceDetailArrayList.size(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < invoiceDetailArrayList.size(); i++) {
            listDrink.add(drinkDAO.getDrinkByID(String.valueOf(invoiceDetailArrayList.get(i).getDrinkID())));
        }
        Toast.makeText(getContext(), ""+listDrink.size(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < listDrink.size(); i++) {
            for (int j = i + 1; j < listDrink.size(); j++) {
                if (listDrink.get(i) == listDrink.get(j)){
                    listDrink.remove(j);
                }
            }
        }
        StringBuilder builderNameDrink = new StringBuilder();
        StringBuilder builderQuantity = new StringBuilder();
        StringBuilder builderPrice = new StringBuilder();
        StringBuilder builderExpiry = new StringBuilder();
        for (int i = 0; i < listDrink.size(); i++) {
            Drink drink = listDrink.get(i);
            builderNameDrink.append("\n").append(drink.getName());
            builderQuantity.append("\n").append(invoiceDetailArrayList.get(i).getQuantityDrink());
            builderPrice.append("\n").append(drink.getPrice()).append(" VNĐ");
            builderExpiry.append(drink.getDateExpiry()).append("\n");

        }

        User user = userDAO.getUserByID(invoice.getUserID());
        tvInvoiceID.setText("Mã hoá đơn: " + invoice.getInvoiceID());
        tvNameStaff.setText("Tên nhân viên: " + user.getFullName());
        tvNameCustomer.setText("Tên khách hàng: ");
        tvTableID.setText("Mã bàn: "+invoice.getTableID());
        tvServe.setText("Phục vụ: "+ invoice.getServe());
        tvInforDrink.setText("Tên đồ uống: " + builderNameDrink.toString());
        tvTotalBill.setText("Tổng tiền: "+ invoice.getTotalBill());
        tvDateCreate.setText("Ngày xuất hoá đơn: " +invoice.getDateCreate());
        tvStatus.setText("Trạng thái: " +invoice.getStatus());
        tvQuantity.setText("Số lượng: "+builderQuantity.toString());
        tvPrice.setText("Giá: "+builderPrice.toString());


    }
}