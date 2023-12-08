package com.fpoly.pro1121_da1.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.BookingAdapter;
import com.fpoly.pro1121_da1.Interface.BookingInterface;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.BookingDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.TableDAO;
import com.fpoly.pro1121_da1.model.Booking;
import com.fpoly.pro1121_da1.model.Table;
import com.fpoly.pro1121_da1.model.User;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FragmentBooking extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Table> tableArrayList;
    TableDAO tableDAO;
    BookingAdapter bookingAdapter;
    Button btnConfirm;

    BookingDAO bookingDAO;
    Map<String, Integer> map = new HashMap<>();
    int check = 0;
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String timeNow = formatter.format(date);

    Button btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // status = 6886 is booking
        btnCancel = view.findViewById(R.id.btnCancelBooking);
        bookingDAO = new BookingDAO(getContext());
        tableDAO = new TableDAO(getContext(), new Dbhelper(getContext()));
        recyclerView = view.findViewById(R.id.recyclerView_tableIsEmpty);
        btnConfirm = view.findViewById(R.id.btnConfirmBooking);
        tableArrayList = tableDAO.getTableNotBooking();
        if (tableArrayList.size() == 0){
            Toast.makeText(getContext(), "Không có bàn trống", Toast.LENGTH_SHORT).show();
        }
        bookingAdapter = new BookingAdapter(getActivity(), tableArrayList);
        recyclerView.setAdapter(bookingAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ((MainActivity) requireActivity()).chipNavigationBar.setVisibility(View.INVISIBLE);
        bookingAdapter.setOnTableBookingListener(new BookingInterface() {
            @Override
            public void onBooking(Table table, int position) {
                map.put(table.getTableID(), 6886);
            }

            @Override
            public void onCancelBooking(Table table, int position) {
                map.put(table.getTableID(), 0);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogConfirm();
            }
        });
        User user = ((MainActivity) requireActivity()).user;
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getRole().equals("admin")) {
                    ((MainActivity) requireActivity()).reloadFragment(new FragmentSettings());
                    ((MainActivity)requireActivity()).chipNavigationBar.setVisibility(View.VISIBLE);
                } else {
                    ((MainActivity) requireActivity()).reloadFragment(new FragmentSettingsStaff());
                    ((MainActivity)requireActivity()).chipNavigationBar.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    Button btnConfirmAlert;
    EditText edtNameCustomer, edtPhoneNumber, edtDayBook, edtHoursBook, edtPassUser;

    public void showAlertDialogConfirm() {
        final Dialog view1 = new Dialog(requireActivity());
        view1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view1.setContentView(R.layout.alertdialog_confirmbooking);
        edtNameCustomer = view1.findViewById(R.id.edt_nameCustomer_booking);
        edtPhoneNumber = view1.findViewById(R.id.edt_phoneNumber__booking);
        edtDayBook = view1.findViewById(R.id.edt_date_booking);
        edtHoursBook = view1.findViewById(R.id.edt_hours_booking);
        edtPassUser = view1.findViewById(R.id.edt_passWordStaff_booking);
        btnConfirmAlert = view1.findViewById(R.id.btnConfirmBooking_part2);
        User user = ((MainActivity) requireActivity()).user;
        btnConfirmAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNameCustomer = edtNameCustomer.getText().toString().trim();
                String getPhoneNumber = edtPhoneNumber.getText().toString().trim();
                String getDay = edtDayBook.getText().toString().trim();
                String getHours = edtHoursBook.getText().toString().trim();
                String getPassUser = edtPassUser.getText().toString().trim();
                if (getNameCustomer.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống tên khách hàng !", Toast.LENGTH_SHORT).show();
                } else if (checkPhoneNumber(edtPhoneNumber)) {

                } else if (getDay.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống ngày đặt !", Toast.LENGTH_SHORT).show();

                } else if (getHours.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống giờ đặt !", Toast.LENGTH_SHORT).show();

                } else if (!checkHours(edtHoursBook)) {
                    Toast.makeText(getContext(), "Giờ đặt phải sau giờ hiện tại", Toast.LENGTH_SHORT).show();

                } else if (getPassUser.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống mật khẩu !", Toast.LENGTH_SHORT).show();

                } else if (!user.getPassWord().equals(getPassUser)) {
                    Toast.makeText(getContext(), "Vui lòng nhập đúng mật khẩu để đặt bàn !", Toast.LENGTH_SHORT).show();

                } else {
                    String subHour = getHours.substring(0, 2);
                    String subMinute = getHours.substring(2);
                    String printHours = subHour + ":" + subMinute;
                    if (checkHour(getDay, printHours)) {


                    } else {
                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
                            if (entry.getValue() == 6886) {
                                Booking booking = new Booking(entry.getKey(), user.getUserID(), getDay, getNameCustomer, getPhoneNumber, printHours);

                                if (bookingDAO.insertBooking(booking)) {
                                    tableDAO.updateStatusTable(entry.getKey(), entry.getValue());
                                    Toast.makeText(getContext(), "Đặt bàn thành công !", Toast.LENGTH_SHORT).show();
                                    ((MainActivity) requireActivity()).reloadFragment(new FragmentTable());
                                    ((MainActivity) requireActivity()).chipNavigationBar.setItemSelected(R.id.table, true);
                                    view1.dismiss();
                                }

                            }
                        }
                    }

                }
            }
        });
//if (!checkDay(getDay, timeNow)) {
//                    Toast.makeText(getContext(), "Ngày phải lớn hơn ngày hiện tại", Toast.LENGTH_SHORT).show();
//
//                } else
        view1.show();
        view1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view1.getWindow().getAttributes().windowAnimations = R.style.Style_AlertDialog_Corner;
        view1.getWindow().setGravity(Gravity.BOTTOM);

    }

    public boolean checkPhoneNumber(EditText edtPhoneNumber) {
        String getPhone = edtPhoneNumber.getText().toString().trim();

        if (getPhone.length() == 0) {
            Toast.makeText(getContext(), "Không được để trống số điện thoại khách", Toast.LENGTH_SHORT).show();
            return true;
        } else if (getPhone.length() != 10) {
            Toast.makeText(getContext(), "Số điện thoại phải là 10 số", Toast.LENGTH_SHORT).show();
            return true;
        } else if (checkIsVietNamePhone(getPhone)) {
            Toast.makeText(getContext(), "SĐT không đúng định dạng SĐT Việt Nam !", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean checkIsVietNamePhone(String phoneNumber) {
        String sub = phoneNumber.substring(0, 2);
        if (!sub.equals("03") && !sub.equals("09") && !sub.equals("08")) {
            return true;
        }
        return false;
    }

    public boolean checkDay(String day1, String day2) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
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

    public boolean checkHours(EditText editText) {
        String specificTime = editText.getText().toString().trim();
        String getHour = specificTime.substring(0, 2);
        String getMinute = specificTime.substring(2);

        LocalTime currentTime = LocalTime.now();
        LocalTime timeEqual = LocalTime.of(Integer.parseInt(getHour), Integer.parseInt(getMinute));
        if (currentTime.isBefore(timeEqual)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkHour(String dayBooking, String hourAdd) {
        ArrayList<Booking> listCheck = bookingDAO.getBooking("SELECT * FROM Booking WHERE day_booking = ? ", dayBooking);

        for (int i = 0; i < listCheck.size(); i++) {
            String getHour = listCheck.get(i).getHourBooking();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime specificHour = LocalTime.parse(getHour, dateTimeFormatter);

            LocalTime beforeTime = specificHour.minusMinutes(15);

            LocalTime localEqual = LocalTime.parse(hourAdd, dateTimeFormatter);

            if (localEqual.isAfter(beforeTime) && localEqual.isBefore(specificHour)) {
                Toast.makeText(getContext(), "Không được đặt trong thời gian này !", Toast.LENGTH_SHORT).show();
                return true;
            }
        }


        return false;
    }
}