package com.fpoly.pro1121_da1.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.ShowdetailCalenderAdapter;
import com.fpoly.pro1121_da1.Adapter.UserToCalendarAdapter;
import com.fpoly.pro1121_da1.Interface.MyChecked;
import com.fpoly.pro1121_da1.Interface.SetDelete;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CalenderDAO;
import com.fpoly.pro1121_da1.database.CalenderShowDetailDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.CalendarWorkForStaff;
import com.fpoly.pro1121_da1.model.CalenderWork;
import com.fpoly.pro1121_da1.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class FragmentShowDetailCalendar extends Fragment {

    TextView tv_shiftNow, tvDayWork;
    RecyclerView rcv_calendarShowDetail;
    Button btn_deleteStaff_showDetailCalendar;
    ImageView imgBackFragmentCalenDarWork;
    FloatingActionButton btn_addStaff_showDetailCalendar;

    ArrayList<CalenderWork> listCalendarWord;
    int receiveShiffWork = 0, receiveCalendarID = 0;
    String reciveDateWork = "";
    ShowdetailCalenderAdapter calenderUserAdapter;
    ArrayList<User> userArrayList;
    CalenderDAO calenderDAO;
    CalenderShowDetailDAO calenderShowDetailDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__show_detail__calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDayWork = view.findViewById(R.id.tv_day_work);
        imgBackFragmentCalenDarWork = view.findViewById(R.id.img_back_frameCalenDarWork);
        tv_shiftNow = view.findViewById(R.id.tv_shift_work);

        rcv_calendarShowDetail = view.findViewById(R.id.rcv_showDetailCalenderWorkForShiff);
        btn_addStaff_showDetailCalendar = view.findViewById(R.id.btn_add_staffForShowDetail_CalendarWork);

        calenderDAO = new CalenderDAO(getActivity(), new Dbhelper(getActivity()));
        calenderShowDetailDAO = new CalenderShowDetailDAO(getActivity());


        Bundle bundle = this.getArguments();
        if (bundle != null) {

            receiveShiffWork = bundle.getInt("KEY_SHIFT_WORK");
            reciveDateWork = bundle.getString("KEY_DAY_WORK");
            receiveCalendarID = bundle.getInt("KEY_CALENDAR_ID");
            userArrayList = calenderShowDetailDAO.getUser(receiveCalendarID);

            if (receiveShiffWork != 0 && !reciveDateWork.equalsIgnoreCase("")) {
                tv_shiftNow.setText("Ca làm: " + receiveShiffWork);
                tvDayWork.setText("Ngày: " + reciveDateWork);
            }

            if (receiveShiffWork == 1) {
                tv_shiftNow.setText("Ca làm việc: Ca Sáng");
            } else if (receiveShiffWork == 2) {
                tv_shiftNow.setText("Ca làm việc: Ca Chiều");
            } else {
                tv_shiftNow.setText("Ca làm việc: Ca Tối");
            }

        } else {
            Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
        }

        calenderUserAdapter = new ShowdetailCalenderAdapter(getActivity(), userArrayList, receiveCalendarID);
        rcv_calendarShowDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_calendarShowDetail.setAdapter(calenderUserAdapter);


        imgBackFragmentCalenDarWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentCalendar());
            }
        });
        btn_addStaff_showDetailCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogAddUser(userArrayList);
            }
        });


    }

    RecyclerView recyclerView;
    Button btnConfirmAdd;

    public void showAlertDialogAddUser(ArrayList<User> userArrayList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_adduser_tocalendar, null, false);
        builder.setView(view);
        UserDAO userDAO = new UserDAO(getContext(), new Dbhelper(getContext()));
        // array all user
        ArrayList<User> arrayListUser = userDAO.getAllUser();


        for (int i = 0; i < arrayListUser.size(); i++) {
            if (arrayListUser.get(i).getRole().equalsIgnoreCase("admin")) {
                arrayListUser.remove(i);
            }
        }

        CalenderWork calenderWork = calenderDAO.getIDCalendarByShiftWorkAndDateWork(receiveShiffWork, reciveDateWork);
        if (receiveShiffWork != 0 && !reciveDateWork.equalsIgnoreCase("")) {

        }
        AlertDialog alertDialog = builder.create();
        btnConfirmAdd = view.findViewById(R.id.btnConfirmAdd_alert);
        recyclerView = view.findViewById(R.id.recyclerView_addUserFromCalendar);


        UserToCalendarAdapter userToCalendarAdapter = new UserToCalendarAdapter(getActivity(), arrayListUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(userToCalendarAdapter);
        ArrayList<User> listUser = new ArrayList<>();
        userToCalendarAdapter.setCheckBoxChecked(new MyChecked() {
            @Override
            public void setChecked(User user, int position) {
                listUser.add(user);
            }

            @Override
            public void setUnChecked(User user, int position) {
                listUser.remove(position);
            }
        });
        btnConfirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (listUser.size() != 0) {

                        for (int i = 0; i < listUser.size(); i++) {
                            CalendarWorkForStaff calendarWorkForStaff = new CalendarWorkForStaff(listUser.get(i).getUserID(), receiveCalendarID);
                            calenderShowDetailDAO.insertCalendarWorkForStaff(calendarWorkForStaff, "", "");

                        }
                       getParentFragmentManager().beginTransaction().replace(R.id.container_layout, new FragmentCalendar()).commit();
                    }
                    alertDialog.dismiss();
                } catch (Exception e) {

                }

            }
        });

        alertDialog.show();
    }
}