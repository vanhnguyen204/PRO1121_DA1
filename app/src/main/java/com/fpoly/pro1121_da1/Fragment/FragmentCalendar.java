package com.fpoly.pro1121_da1.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.CalenderAdapter;
import com.fpoly.pro1121_da1.Interface.SenDataCalenderWorkClick;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CalenderDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.model.CalenderWork;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class FragmentCalendar extends Fragment {

    CalenderAdapter calenderAdapter;
    public static ArrayList<CalenderWork> calendarArrayList;
    CalenderDAO calenderDAO;
    RecyclerView rcViewCalendar;
    ArrayList<CalenderWork> listClone;
    TextView tv_dayNow;
    EditText tv_daytoWork;
    ImageView img_calendar, img_back_fragmentSetting;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    public void setRecyclerView(String day) {
        calendarArrayList = calenderDAO.getAllCalendarByDay(day);

        if (calendarArrayList.size() != 0) {
            calenderAdapter = new CalenderAdapter(getActivity(), calendarArrayList, getParentFragmentManager());
            rcViewCalendar.setAdapter(calenderAdapter);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        img_calendar = view.findViewById(R.id.img_calendar);
        img_back_fragmentSetting = view.findViewById(R.id.img_back_fragmentSetting);
        tv_dayNow = view.findViewById(R.id.tv_dayNow_calender);
        tv_daytoWork = view.findViewById(R.id.tv_daytoWord_calender);
        fab = view.findViewById(R.id.btn_add_CalendarWord);
        rcViewCalendar = view.findViewById(R.id.rcv_calenderWork);
        calenderDAO = new CalenderDAO(getActivity(), new Dbhelper(getActivity()));
        ((MainActivity) requireActivity()).chipNavigationBar.setVisibility(View.INVISIBLE);
        setTimeNow(tv_dayNow);
        tv_daytoWork.setText(tv_dayNow.getText().toString());
        setRecyclerView(tv_dayNow.getText().toString());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String getDayBack = bundle.getString("KEY_BACK_DAY");
            setRecyclerView(getDayBack);
            tv_daytoWork.setText(getDayBack);

        }


        Animation animation = new TranslateAnimation(0, 0, 0, 50);
        animation.setDuration(2000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        img_calendar.setAnimation(animation);
        img_back_fragmentSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentSettings());
            }
        });

        img_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDatePicker(tv_daytoWork);
            }
        });
        listClone = new ArrayList<>();
        tv_daytoWork.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calendarArrayList.clear();
                try {
                    listClone = calenderDAO.getAllCalendarByDay(tv_daytoWork.getText().toString());
                } catch (Exception e) {

                }
                String str = charSequence.toString().trim();
                if (str.length() == 0) {
                    setRecyclerView(tv_dayNow.getText().toString());
                } else {
                    for (int index = 0; index < listClone.size(); index++) {
                        if (str.equals(listClone.get(index).getDayofWork())) {
                            calendarArrayList.add(listClone.get(index));
                        }
                    }
                    if (calendarArrayList.size() != 0) {
                        rcViewCalendar.setAdapter(new CalenderAdapter(getActivity(), calendarArrayList, getParentFragmentManager()));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.alertdialog_addcalendar, null, false);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();

                EditText edtDay = view.findViewById(R.id.edt_dayAddCalendar);
                Button btnConfirm = view.findViewById(R.id.btnConfirmAddCalendar);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String getDay = edtDay.getText().toString().trim();
                        CalenderWork calenderWork1 = new CalenderWork(getDay, 1);
                        CalenderWork calenderWork2 = new CalenderWork(getDay, 2);
                        CalenderWork calenderWork3 = new CalenderWork(getDay, 3);
                        if (calenderDAO.checkDayAdd(getDay)) {

                        } else {
                            calenderDAO.insertCalender(calenderWork1);
                            calenderDAO.insertCalender(calenderWork2);
                            calenderDAO.insertCalender(calenderWork3);
                            setRecyclerView(getDay);
                            alertDialog.dismiss();
                            Toast.makeText(getContext(), "" + calendarArrayList.size(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.show();
            }
        });


    }

    public void showDatePicker(EditText textView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month < 9) {
                            textView.setText(year + "-" + "0" + (month + 1) + "-0" + dayOfMonth);
                        } else if (dayOfMonth < 10) {
                            textView.setText(year + "-" + (month + 1) + "-0" + dayOfMonth);
                        } else {
                            textView.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        }
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void setTimeNow(TextView textView) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM" +
                "-dd");
        Calendar calendar1 = Calendar.getInstance();
        textView.setText(dateFormat.format(calendar1.getTime()));
    }


}