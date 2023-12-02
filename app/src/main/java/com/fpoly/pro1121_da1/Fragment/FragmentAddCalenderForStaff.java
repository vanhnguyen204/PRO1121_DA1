package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.fpoly.pro1121_da1.Adapter.AddCalendarWorkForStaffAdapter;
import com.fpoly.pro1121_da1.Adapter.UserAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CalenderDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.CalenderWork;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;

public class FragmentAddCalenderForStaff extends Fragment {
    AddCalendarWorkForStaffAdapter addCalendarWorkForStaffAdapter;
    RecyclerView rec_staff;
    Spinner spn_shiftWork, spn_dateWork;
    Button btn_confirm;
    ArrayList<User> list;
    UserDAO userDAO;
    private ImageView img_backFragmentSetting;
    ArrayList<CalenderWork> calenderWorkArrayList;
    CalenderDAO calenderDAO;
    String getDaySpn;
    Integer getShiftWorkSpn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_calender_for_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        img_backFragmentSetting = view.findViewById(R.id.img_back_fragmentSetting);
        rec_staff = view.findViewById(R.id.rcv_staff_fragmentAddCalendarWorkForStaff);
        spn_shiftWork = view.findViewById(R.id.spn_shiftWork_fragmentAddCalenderWorkForStaff);
        spn_dateWork = view.findViewById(R.id.spn_dateWork);
        btn_confirm = view.findViewById(R.id.btn_confirm_addCalendarWorkForStaft);


        userDAO = new UserDAO(getContext(),new Dbhelper(getContext()));
        list = userDAO.getAllUser();
        addCalendarWorkForStaffAdapter = new AddCalendarWorkForStaffAdapter(getActivity(),list);
        rec_staff.setAdapter(addCalendarWorkForStaffAdapter);

        calenderDAO = new CalenderDAO(getContext(),new Dbhelper(getContext()));
        calenderWorkArrayList = calenderDAO.getAllCalendar();

        String[] getDay = new String[calenderWorkArrayList.size()];
        Integer[] getShiftWork = new Integer[calenderWorkArrayList.size()];

        for (int i = 0; i < calenderWorkArrayList.size(); i++) {
           getDay[i] = calenderWorkArrayList.get(i).getDayofWork();
           getShiftWork[i] = calenderWorkArrayList.get(i).getShiftWork();
        }

        ArrayAdapter adapterGetShiftWork = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,getShiftWork);
        ArrayAdapter adapterGetDay = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,getDay);
        spn_dateWork.setAdapter(adapterGetDay);
        spn_shiftWork.setAdapter(adapterGetShiftWork);

        spn_shiftWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               getDaySpn = getDay[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_dateWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getShiftWorkSpn = getShiftWork[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        calenderWorkArrayList = calenderDAO.getIDCalendarByShiftWorkAndDateWork(getShiftWorkSpn,getDaySpn);

        img_backFragmentSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).reloadFragment(new FragmentSettings());
            }
        });


    }
}