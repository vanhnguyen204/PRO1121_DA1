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
import android.os.Message;
import android.util.Log;
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
import com.fpoly.pro1121_da1.model.TopDrink;
import com.fpoly.pro1121_da1.model.User;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

    public boolean checkExpiry(String day1, String day2) {
        try {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = spf.parse(day1);
            Date date2 = spf.parse(day2);
            int compare = date1.compareTo(date2);
            if (compare > 0) {
                return true;
            } else if (compare < 0) {
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    Date date = new Date();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    String getTimeNow = simpleDateFormat.format(date);

    public void setSnowFlake(View view) {
        ImageView snow1, snow2, snow3, snow4, snow5, snow6, snow7, snow8, snow9;
        snow1 = view.findViewById(R.id.snow_flake1);
        snow2 = view.findViewById(R.id.snow_flake2);
        snow3 = view.findViewById(R.id.snow_flake3);
        snow4 = view.findViewById(R.id.snow_flake4);
        snow5 = view.findViewById(R.id.snow_flake5);
        snow6 = view.findViewById(R.id.snow_flake6);
        snow7 = view.findViewById(R.id.snow_flake7);
        snow8 = view.findViewById(R.id.snow_flake8);
        snow9 = view.findViewById(R.id.snow_flake9);


        Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.snow_flake);
        anim1.setDuration(10000);
        Animation anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.snow_flake);
        anim2.setDuration(8000);
        Animation anim3 = AnimationUtils.loadAnimation(getContext(), R.anim.snow_flake);
        anim3.setDuration(9000);
        Animation anim4 = AnimationUtils.loadAnimation(getContext(), R.anim.snow_flake);
        anim4.setDuration(7000);
        Animation anim5 = AnimationUtils.loadAnimation(getContext(), R.anim.snow_flake);
        anim5.setDuration(8000);

        snow1.startAnimation(anim1);
        snow2.startAnimation(anim2);
        snow3.startAnimation(anim3);
        snow4.startAnimation(anim4);
        snow5.startAnimation(anim5);
        snow6.startAnimation(anim1);
        snow7.startAnimation(anim2);
        snow8.startAnimation(anim3);
        snow9.startAnimation(anim4);
    }

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
        setSnowFlake(view);
        DrinkDAO drinkDAO = new DrinkDAO(getContext(), new Dbhelper(getContext()));
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogNotification();
            }
        });
        List<TopDrink> list = drinkDAO.getTopSellingDrinksWithId();
        if (list.size() != 0) {
            ArrayList<Drink> drinkArrayList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Log.d("index: " + i, "value: " + list.get(i).getDrinkId());
                drinkArrayList.add(drinkDAO.getDrinkByID(String.valueOf(list.get(i).getDrinkId())));
            }
            TopDrinkAdapter topDrinkAdapter = new TopDrinkAdapter(getActivity(), list);
            recyclerView.setAdapter(topDrinkAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false));
        }
        NotificationDAO notificationDAO = new NotificationDAO(getContext());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String timeNow = formatter.format(date);

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

        tvNameStaff.setText("Hello! " + user.getFullName());
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
                ((MainActivity) requireActivity()).chipNavigationBar.setItemSelected(R.id.table, true);
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
                ((MainActivity) requireActivity()).chipNavigationBar.setItemSelected(R.id.drink, true);
                ((MainActivity) requireActivity()).reloadFragment(new FragmentDrink());
            }
        });

        InvoiceDAO invoiceDAO = new InvoiceDAO(getContext(), new Dbhelper(getContext()));
        if (user.getRole().equals("admin")) {
            try {

                int revenueInt = invoiceDAO.getRevenue();
                tvRevenue.setText(String.format("%dVNĐ", revenueInt));
                int count = invoiceDAO.countInvoiceExported();
                tvExportInvoice.setText(count + " đơn.");
            } catch (Exception e) {

            }
            manegeStatistical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((MainActivity) getContext()).reloadFragment(new FragmentTurnOver());
                }
            });
        } else {
            tvRevenue.setText("****************");
            int count = invoiceDAO.countInvoiceExported();
            tvExportInvoice.setText(count + " đơn.");
            manegeStatistical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Nhân viên không được sử dụng chức năng này !", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    RecyclerView recyclerViewNotify;
    Button btnDeleteNotifi;

    void showDialogNotification() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Style_AlertDialog_Corner_ver2);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_notification, null, false);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        recyclerViewNotify = view.findViewById(R.id.recyclerview_notification);
        btnDeleteNotifi = view.findViewById(R.id.btnDeleteNotification);
        DrinkDAO drinkDAO = new DrinkDAO(getContext(), new Dbhelper(getContext()));
        ArrayList<Drink> listDrinkExpired = drinkDAO.listDrinkExpired();

        NotificationDAO notificationDAO = new NotificationDAO(getContext());
        listNotify = notificationDAO.getAllNotification();
        for (int i = 0; i < listDrinkExpired.size(); i++) {
            listNotify.add(new Notification(listDrinkExpired.get(i).getName() + " đã hết hạn sử đụng", getTimeNow));
        }
        NotificationAdapter notificationAdapter = new NotificationAdapter(listNotify, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNotify.setLayoutManager(layoutManager);
        recyclerViewNotify.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();

        btnDeleteNotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationDAO.deleteAll();
                alertDialog.dismiss();
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