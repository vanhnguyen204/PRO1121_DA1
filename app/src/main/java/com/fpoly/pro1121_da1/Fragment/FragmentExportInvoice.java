package com.fpoly.pro1121_da1.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CustomerDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.IngredientDAO;
import com.fpoly.pro1121_da1.database.IngredientForDrinkDAO;
import com.fpoly.pro1121_da1.database.InvoiceDAO;
import com.fpoly.pro1121_da1.database.InvoiceDetailDao;
import com.fpoly.pro1121_da1.database.TableDAO;
import com.fpoly.pro1121_da1.model.Customer;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.model.IngredientForDrink;
import com.fpoly.pro1121_da1.model.Invoice;
import com.fpoly.pro1121_da1.model.InvoiceDetail;
import com.fpoly.pro1121_da1.model.InvoiceViewModel;
import com.fpoly.pro1121_da1.model.Table;
import com.fpoly.pro1121_da1.model.User;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FragmentExportInvoice extends Fragment {
    private InvoiceViewModel viewModel;
    ArrayList<String> listDrinkID;
    ArrayList<Integer> listQuantityOfDink;
    TextView tvInvoiceID, tvNameStaff, tvNameDrink, tvTableID, tvTotalBill, tvDateCreate, tvServe;
    ImageView imgAddCustomer, imgBack;

    ArrayList<Ingredient> arrayListIngredient = new ArrayList<>();
    String getTableID;
    Button btnConfirm;
    String getServe = "";

    int sumPrice = 0;
    int getInvoiceID = 0, getGetInvoiceIdFromFragmentTable = 0;
    int getStatusTable = 0;
    int getInvoiceIDRandom;
    ArrayList<Drink> arrayListDrink = new ArrayList<>();


    private void getGetInvoiceIDFrom() {
        viewModel.getInvoiceData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                getGetInvoiceIdFromFragmentTable = Integer.parseInt(s);
                Toast.makeText(getContext(), "" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveInvoiceData(String data) {
        viewModel.setInvoiceData(data);
    }

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
        viewModel = new ViewModelProvider(requireActivity()).get(InvoiceViewModel.class);
        getGetInvoiceIDFrom();
        getInvoiceIDRandom = new Random().nextInt(1000000);
        User user = ((MainActivity) getActivity()).user;
        tvInvoiceID = view.findViewById(R.id.tv_invoiceID_fragmentExport);
        tvNameStaff = view.findViewById(R.id.tv_nameStaff_fragmentExport);
        tvNameDrink = view.findViewById(R.id.tv_drink_fragmentExport);
        tvTableID = view.findViewById(R.id.tv_table_fragmentExport);
        tvTotalBill = view.findViewById(R.id.tv_totalBill_fragmentExport);
        tvDateCreate = view.findViewById(R.id.tv_dateCreate_fragmentExport);
        tvServe = view.findViewById(R.id.tv_serve_exportInvoice);
        imgAddCustomer = view.findViewById(R.id.img_addCustomer_fragmentExport);
        imgBack = view.findViewById(R.id.img_back_fragmentExport);
        btnConfirm = view.findViewById(R.id.btnConfirmExportInvoice);
        TableDAO tableDAO = new TableDAO(getContext(), new Dbhelper(getContext()));
        InvoiceDAO invoiceDAO = new InvoiceDAO(getContext(), new Dbhelper(getContext()));
        int getIdCustomer = new Random().nextInt(100000);
        DrinkDAO drinkDAO = new DrinkDAO(getContext(), new Dbhelper(getContext()));

        IngredientForDrinkDAO ingredientForDrinkDAO = new IngredientForDrinkDAO(getContext());
        IngredientDAO ingredientDAO = new IngredientDAO(getContext(), new Dbhelper(getContext()));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String getDateCreate = dateFormat.format(cal.getTime());


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            listDrinkID = (ArrayList<String>) bundle.getSerializable("KEY_ARRAY_DRINK_ID");
            getTableID = bundle.getString("KEY_TABLE_ID_EXPORT");
            getInvoiceID = bundle.getInt("KEY_INVOICE");
            getStatusTable = bundle.getInt("KEY_STATUS_TABLE");
            listQuantityOfDink = (ArrayList<Integer>) bundle.getSerializable("KEY_ARRAY_QUANTITY_DRINK");
        }

        Table table = tableDAO.getTableByID(getTableID);

        if (getStatusTable == 0) {
            getServe = "Mang về";

        } else {
            getServe = "Tại quán";
        }
        // lấy đồ xuống sau khi chọn bên fragment order sau khi bắn list drink id từ fragment order qua
        if (listDrinkID.size() != 0) {
            for (int i = 0; i < listDrinkID.size(); i++) {
                Drink drink = drinkDAO.getDrinkByID(listDrinkID.get(i));
                arrayListDrink.add(drink);
            }
        }

// đã có list đồ uống sau khi chọn
        StringBuilder strBuilderNameDrink = new StringBuilder();
        for (int i = 0; i < arrayListDrink.size(); i++) {
            strBuilderNameDrink.append(arrayListDrink.get(i).getName()).append(String.valueOf(listQuantityOfDink.get(i))).append(" - ");
            sumPrice += arrayListDrink.get(i).getPrice();
        }

        tvInvoiceID.setText("Mã hoá đơn: " + getInvoiceIDRandom);
        tvNameStaff.setText("Nhân viên: " + user.getFullName());
        tvNameDrink.setText("Đồ uống: " + strBuilderNameDrink.toString());
        tvTableID.setText("Bàn: " + getTableID);
        tvTotalBill.setText("Tổng tiền: " + sumPrice);
        tvDateCreate.setText("Ngày tạo: " + getDateCreate);
        tvServe.setText("Phục vụ: " + getServe);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBackOrder();
            }
        });

        imgAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertCreateNewCustomer(getIdCustomer);
            }
        });
        IngredientForDrinkDAO forDrinkDAO = new IngredientForDrinkDAO(getActivity());


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int index = 0; index < arrayListDrink.size(); index++) {
                    Drink drink = arrayListDrink.get(index);
                    if (drink.getTypeOfDrink().equalsIgnoreCase("Pha chế")) {
                        for (int i = 0; i < arrayListDrink.size(); i++) {
                            if (arrayListDrink.get(i).getTypeOfDrink().equalsIgnoreCase("Pha chế")) {
                                arrayListIngredient = drinkDAO.getIngredientFromDrinkID(arrayListDrink.get(i).getDrinkID());
                                for (int j = 0; j < arrayListIngredient.size(); j++) {
                                    IngredientForDrink ingredientForDrink = forDrinkDAO.getModelIngreForDrink(arrayListDrink.get(i).getDrinkID(), arrayListIngredient.get(i).getIngredientID());
                                    double quantity = ingredientForDrink.getQuantity() *  listQuantityOfDink.get(index);
                                    ingredientDAO.updateQuantityIngredient(arrayListIngredient.get(i).getIngredientID(), arrayListIngredient.get(i).getQuantity() - quantity);
                                }
                            }
                        }
                    } else {
                        int quantityOLd = drink.getQuantity() - listQuantityOfDink.get(index);
                        drinkDAO.updateQuantityDrink(arrayListDrink.get(index).getDrinkID(), quantityOLd);
                        ((MainActivity) requireActivity()).reloadFragment(new FragmentDrink());
                    }
                }


//                Invoice invoiceGetOut = new Invoice(getInvoiceIDRandom, user.getUserID(), getIdCustomer, getTableID, sumPrice, getDateCreate, getServe, "Đã xuất hoá đơn");
//                if (invoiceDAO.insertInvoice(invoiceGetOut)) {
//
//                    IngredientForDrink ingredientForDrink;
//                    // lấy đồ uống pha chế và đồ uống pha chế ở bảng trung gian
//                    for (int i = 0; i < listDrinkID.size(); i++) {
//                        Drink drink = drinkDAO.getDrinkByID(listDrinkID.get(i));
//                        if (drink.getTypeOfDrink().equalsIgnoreCase("Pha chế")) {
//                            drinkArrayList1.add(drink);
//                            ingredientForDrink = ingredientForDrinkDAO.getIngredientByDrinkID(Integer.parseInt(listDrinkID.get(i)));
//                            ingredientForDrinkArrayList.add(ingredientForDrink);
//                        }
//                    }
//
//                    for (int i = 0; i < drinkArrayList1.size(); i++) {
//                        ingredientArrayList = drinkDAO.getIngredientFromDrinkID(drinkArrayList1.get(i).getDrinkID());
//                        for (int j = 0; j < ingredientArrayList.size(); j++) {
//                            ingredientDAO.updateQuantityIngredient(ingredientArrayList.get(j).getIngredientID(), ingredientArrayList.get(j).getQuantity() - ingredientForDrinkArrayList.get(j).getQuantity());
//                        }
//                    }
//

//                    getParentFragmentManager().beginTransaction().replace(R.id.container_layout, new FragmentTable()).commit();
//                }
            }
        });
    }

    private void showDialogBackOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn đang tạo hoá đơn, bạn có chắc muốn thoát không");
        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("KEY_CHECKBOX", listDrinkID);
                bundle1.putString("KEY_SAVE_TABLE_ID", getTableID);
                bundle1.putInt("KEY_INVOICE_ID_EXPORT", getInvoiceIDRandom);
                bundle1.putSerializable("KEY_QUANTITY_BACK", listQuantityOfDink);

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


    EditText edtNameCustomer, edtPhoneNumber;
    Button btnConfirmAddUser;

    public void showAlertCreateNewCustomer(int customerID) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Style_AlertDialog_Corner);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_addcustomer, null);
        builder.setView(view);
        AlertDialog alert = builder.create();
        alert.show();
        CustomerDAO customerDAO = new CustomerDAO(getContext(), new Dbhelper(getContext()));
        edtNameCustomer = view.findViewById(R.id.edtNameCustomer);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumberCustomer);
        btnConfirmAddUser = view.findViewById(R.id.btnConfirmAddCustomer);

        btnConfirmAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = edtNameCustomer.getText().toString().trim();
                String getPhone = edtPhoneNumber.getText().toString().trim();
                if (getName.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống tên ", Toast.LENGTH_SHORT).show();
                } else if (getPhone.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống phone ", Toast.LENGTH_SHORT).show();
                } else {
                    if (customerDAO.insertCustomer(new Customer(customerID, getName, getPhone), "Thêm khách hàng oke", "Chưa thêm được rùi")) {
                        alert.dismiss();
                        imgAddCustomer.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });


    }

    public void countDown(int second) {
        while (second > 0) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            second--;
        }
    }


}