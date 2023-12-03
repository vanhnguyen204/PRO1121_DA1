package com.fpoly.pro1121_da1.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.NotificationAdapter;
import com.fpoly.pro1121_da1.Adapter.TopDrinkAdapter;
import com.fpoly.pro1121_da1.Interface.SetTextRecyclerviewNotify;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.DrinkDAO;
import com.fpoly.pro1121_da1.database.InvoiceDAO;
import com.fpoly.pro1121_da1.database.NotificationDAO;
import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Invoice;
import com.fpoly.pro1121_da1.model.Notification;
import com.fpoly.pro1121_da1.model.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FragmentHome extends Fragment {
    LinearLayout manageIngredient, manegeUser, manageDrink, manageTable, manageExportInvoice, manegeStatistical;
    String sub = "";
    ImageView imgAvatar, imgNotification;
    RecyclerView recyclerView;
    ScrollView scrollView;
    TextView tvRevenue, tvExportInvoice, tvCountNotify, tvNameStaff;
    ArrayList<Notification> listNotify;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_fragmentHome);
        tvRevenue = view.findViewById(R.id.tv_revenue_fragmentHome);
        tvExportInvoice = view.findViewById(R.id.tv_countInvoice_atWeek);
        imgNotification = view.findViewById(R.id.img_notification);
        tvCountNotify = view.findViewById(R.id.tv_numberNotification);
        tvNameStaff = view.findViewById(R.id.tv_nameOfStaff);


        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogNotification();
            }
        });

        NotificationDAO notificationDAO = new NotificationDAO(getContext());
        listNotify = notificationDAO.getAllNotification();
        if (listNotify.size() != 0) {
            tvCountNotify.setText(String.valueOf(listNotify.size()));
            for (int i = 0; i < listNotify.size(); i++) {
                for (int j = i + 1; j < listNotify.size(); j++) {
                    if (listNotify.get(i).getMessage().equalsIgnoreCase(listNotify.get(j).getMessage())){
                       listNotify.remove(j);
                       tvCountNotify.setText(""+listNotify.size());
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        InvoiceDAO invoiceDAO = new InvoiceDAO(getContext(), new Dbhelper(getContext()));
        ArrayList<Invoice> invoiceArrayList = invoiceDAO.getAllInvoice();
        ArrayList<String> listDrinkID = new ArrayList<>();
        scrollView = view.findViewById(R.id.scrollView);
        for (int i = 0; i < invoiceArrayList.size(); i++) {
            listDrinkID.add(invoiceArrayList.get(i).getDrinkID());
        }

        for (int i = 0; i < listDrinkID.size(); i++) {
            sub += listDrinkID.get(i) + " ";
        }
        String[] listDrinkIDInvoice = sub.split(" ");

        ArrayList<Integer> listIntegerDrinkID = new ArrayList<>();

        for (int i = 0; i < listDrinkIDInvoice.length; i++) {
            if (listDrinkIDInvoice[i].equals("")) {
                continue;
            } else {
                listIntegerDrinkID.add(Integer.parseInt(listDrinkIDInvoice[i]));
            }
        }

        int[] arrIntDrinkIDEnd = new int[listIntegerDrinkID.size()];
        for (int i = 0; i < arrIntDrinkIDEnd.length; i++) {
            arrIntDrinkIDEnd[i] = listIntegerDrinkID.get(i);
        }

//        // Sử dụng Map để đếm số lần xuất hiện của mỗi phần tử

        Map<Integer, Integer> countMap = new HashMap<>();
        for (int i = 0; i < arrIntDrinkIDEnd.length; i++) {

            int countChild = countMap.getOrDefault(arrIntDrinkIDEnd[i], 0);

            countMap.put(arrIntDrinkIDEnd[i], ++countChild);
        }

//        // Sắp xếp mảng theo số lần xuất hiện giảm dần
        List<Integer> list = new ArrayList<>();
        for (int integer : arrIntDrinkIDEnd) {
            list.add(integer);
        }
        Collections.sort(list, Comparator.comparingInt((Integer num) -> countMap.get(num)).reversed());

        ArrayList<Drink> drinkArrayList = new ArrayList<>();
        DrinkDAO drinkDAO = new DrinkDAO(getContext(), new Dbhelper(getContext()));
        for (int i = 0; i < list.size(); i++) {
            drinkArrayList.add(drinkDAO.getDrinkByID(String.valueOf(list.get(i))));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), ""+drinkArrayList.size(), Toast.LENGTH_SHORT).show();
                if (!drinkArrayList.isEmpty()) {
                    for (int i = 0; i < drinkArrayList.size(); i++) {
                        for (int j = i + 1; j < drinkArrayList.size(); j++) {
                             if (drinkArrayList.get(i).getDrinkID() == drinkArrayList.get(j).getDrinkID()) {
                                drinkArrayList.remove(drinkArrayList.get(i));
                            }
                        }
                    }
                    Toast.makeText(getContext(), ""+drinkArrayList.size(), Toast.LENGTH_SHORT).show();
                    TopDrinkAdapter drinkAdapter = new TopDrinkAdapter(getActivity(), drinkArrayList);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(drinkAdapter);
                }
                Animation animation1 = new TranslateAnimation(1000, 0, 0, 0);
                animation1.setDuration(2000);
                animation1.setRepeatMode(Animation.RELATIVE_TO_SELF);
                animation1.setRepeatCount(0);
                recyclerView.setAnimation(animation1);
            }
        }, 400);

        return view;
    }

    User user;

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manageIngredient = view.findViewById(R.id.manage_ingredient);
        manegeUser = view.findViewById(R.id.manage_member);
        manageDrink = view.findViewById(R.id.manage_drink);
        imgAvatar = view.findViewById(R.id.avatar_staff);
        manageTable = view.findViewById(R.id.manage_table);
        manageExportInvoice = view.findViewById(R.id.manage_exportInvoice);
        manegeStatistical = view.findViewById(R.id.manage_statistical);
        user = ((MainActivity) requireActivity()).user;

        tvNameStaff.setText("Hello! "+user.getFullName());
        manageExportInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).reloadFragment(new FragmentHistoryInvoice());
            }
        });
        manageTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).reloadFragment(new FragmentTable());
            }
        });
        manageIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).reloadFragment(new FragmentIngredient());
            }
        });
        manegeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).reloadFragment(new FragmentUser());
            }
        });
        manageDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).reloadFragment(new FragmentDrink());
            }
        });

        try {
            InvoiceDAO invoiceDAO = new InvoiceDAO(getContext(), new Dbhelper(getContext()));
            int revenueInt = invoiceDAO.getRevenue();
            tvRevenue.setText(String.format("%dVNĐ", revenueInt));
            int count = invoiceDAO.countInvoiceExported();
            tvExportInvoice.setText(count + " đơn.");
        } catch (Exception e) {

        }
        manegeStatistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).reloadFragment(new FragmentTurnOver());
            }
        });

    }

    RecyclerView recyclerViewNotify;
    Button btnDeleteNotifi;

    void showDialogNotification() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Style_AlertDialog_Corner_ver2);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_notification, null, false);
        builder.setView(view);
        recyclerViewNotify = view.findViewById(R.id.recyclerview_notification);
        btnDeleteNotifi = view.findViewById(R.id.btnDeleteNotification);

        if (listNotify.size() == 0) {
            Toast.makeText(getContext(), "Không có thông báo !", Toast.LENGTH_SHORT).show();
        } else {
            NotificationAdapter notificationAdapter = new NotificationAdapter(listNotify, getActivity());
            recyclerViewNotify.setAdapter(notificationAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerViewNotify.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                   layoutManager.getOrientation());
            recyclerViewNotify.addItemDecoration(dividerItemDecoration);


            btnDeleteNotifi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listNotify.clear();
                    notificationAdapter.notifyDataSetChanged();
                    tvCountNotify.setText("0");
                }
            });


            notificationAdapter.setTextNotify(new SetTextRecyclerviewNotify() {
                @SuppressLint("SetTextI18n")
                @Override
                public void setText(TextView textView, Notification notification, int position) {
                    textView.setText(notification.getMessage());

                }
            });

            notificationAdapter.setTextNotify2(new SetTextRecyclerviewNotify() {
                @Override
                public void setText(TextView textView, Notification notification, int position) {

                    textView.setText(notification.getTime());

                }
            });
        }
        AlertDialog alertDialog = builder.create();
        int[] iconLocation = new int[2];
        imgNotification.getLocationOnScreen(iconLocation);
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.gravity = Gravity.TOP;

        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(alertDialog.getWindow().getAttributes());
        layoutParams.y = iconLocation[1] + imgNotification.getHeight() - 30;
        layoutParams.x = -50;
        alertDialog.show();
        alertDialog.getWindow().setAttributes(layoutParams);

        alertDialog.getWindow().setLayout(1150, 750);

    }

}