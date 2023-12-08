package com.fpoly.pro1121_da1.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.pro1121_da1.Fragment.FragmentOrderDrink;
import com.fpoly.pro1121_da1.Fragment.FragmentTable;
import com.fpoly.pro1121_da1.Interface.TableOnClickListener;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.BookingDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.TableDAO;
import com.fpoly.pro1121_da1.model.Booking;
import com.fpoly.pro1121_da1.model.Table;
import com.fpoly.pro1121_da1.model.User;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.Viewholder> {
    ArrayList<Table> list;
    Activity activity;
    String getTimeBooking;
    BookingDAO bookingDAO;
    FragmentManager fragmentManager;
    TableDAO tableDAO;

    public TableOnClickListener tableOnClickListener;

    public void setOnTableClick(TableOnClickListener tableOnClickListener) {
        this.tableOnClickListener = tableOnClickListener;

    }

    public TableAdapter(ArrayList<Table> list, Activity activity, FragmentManager fragmentManager) {
        this.list = list;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_table, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        bookingDAO = new BookingDAO(activity);
        tableDAO = new TableDAO(activity, new Dbhelper(activity));
        Table table = list.get(position);
        holder.tvTableID.setText("Mã bàn: " + table.getTableID());
        LocalTime getTimeCurrent = LocalTime.now();
        // Định dạng giờ với định dạng HH:mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = getTimeCurrent.format(formatter);
        LocalTime specificHour1 = LocalTime.parse(formattedTime, formatter);


        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String getTimeNow = format.format(date);

        if (table.getStatus() == 6886) {
            holder.imgStatus.setImageResource(R.mipmap.circle);
            ArrayList<Booking> bookingArrayList = bookingDAO.getBookingByTableIDandDay(table.getTableID(), getTimeNow);
            for (int i = 0; i < bookingArrayList.size(); i++) {
                String getHour = bookingArrayList.get(i).getHourBooking();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime specificHour = LocalTime.parse(getHour, dateTimeFormatter);

                LocalTime beforeTime = specificHour.minusMinutes(15);
                LocalTime afterTime = specificHour.plusMinutes(15);
                if (specificHour1.isBefore(afterTime) && specificHour1.isAfter(beforeTime)) {
                    Duration duration = Duration.between(getTimeCurrent, specificHour);
                    holder.tvCountDownTime.setVisibility(View.VISIBLE);
                    new CountDownTimer(duration.toMillis(), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Chuyển đổi thời gian còn lại từ mili giây sang Duration
                            Duration remainingTime = Duration.ofMillis(millisUntilFinished);

                            // Hiển thị thời gian đếm ngược trên TextView
                            holder.tvCountDownTime.setText(String.format(" %d:%d",
                                    remainingTime.toMinutes(), remainingTime.getSeconds() % 60));

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    showAlertDialogConfirm(table.getTableID(), getTimeNow);

                                }
                            });
                        }

                        @Override
                        public void onFinish() {
                            tableDAO.updateStatusTable(table.getTableID(), 0);
                            holder.tvCountDownTime.setVisibility(View.INVISIBLE);
                            holder.imgStatus.setImageResource(R.mipmap.circle_green);
                        }
                    }.start();

                }
            }


        } else {
            holder.tvCountDownTime.setVisibility(View.INVISIBLE);
            holder.imgStatus.setImageResource(R.mipmap.circle_green);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tableOnClickListener.setItemTableClick(table, position);
                }
            });
        }

// delete table
        User user = ((MainActivity) activity).user;
        if (user.getRole().equals("admin")) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Bạn có có chắn muốn xoá bàn không, điều này sẽ xoá toàn bộ dữ liệu liên quan đến bàn này.");
                    builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tableDAO.updateStatusTable(table.getTableID(), 4444);
                            list.remove(position);
                            notifyItemRemoved(position);
                        }
                    });
                    builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgTable, imgStatus;
        TextView tvTableID, tvCountDownTime;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgTable = itemView.findViewById(R.id.image_item_recyclerviewTable);
            imgStatus = itemView.findViewById(R.id.img_status_table);
            tvTableID = itemView.findViewById(R.id.tv_tableID_recyclerviewTable);
            tvCountDownTime = itemView.findViewById(R.id.tv_countDownTime);
        }
    }

    Button btnConfirmAlert;
    EditText edtNameCustomer, edtPhoneNumber, edtDayBook, edtHoursBook, edtPassUser;

    public void showAlertDialogConfirm(String tableID, String dayBooking) {
        final Dialog view1 = new Dialog(activity);
        view1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view1.setContentView(R.layout.alertdialog_confirmbooking_part2);

        edtPhoneNumber = view1.findViewById(R.id.edt_phoneNumber__booking_part2);
        edtHoursBook = view1.findViewById(R.id.edt_hours_booking_part2);
        edtPassUser = view1.findViewById(R.id.edt_passWordStaff_booking_part2);
        btnConfirmAlert = view1.findViewById(R.id.btnConfirmBooking_partTwo);
        User user = ((MainActivity) activity).user;
        btnConfirmAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getPhoneNumber = edtPhoneNumber.getText().toString().trim();
                String getHours = edtHoursBook.getText().toString().trim();
                String getPassUser = edtPassUser.getText().toString().trim();
                if (checkPhoneNumber(edtPhoneNumber)) {

                } else if (getHours.length() == 0) {
                    Toast.makeText(activity, "Không được để trống giờ đặt !", Toast.LENGTH_SHORT).show();

                } else if (!checkHours(edtHoursBook)) {
                    Toast.makeText(activity, "Giờ đặt phải sau giờ hiện tại", Toast.LENGTH_SHORT).show();

                } else if (getPassUser.length() == 0) {
                    Toast.makeText(activity, "Không được để trống mật khẩu !", Toast.LENGTH_SHORT).show();

                } else if (!user.getPassWord().equals(getPassUser)) {
                    Toast.makeText(activity, "Vui lòng nhập đúng mật khẩu để đặt bàn !", Toast.LENGTH_SHORT).show();

                } else {
                    String subHour = getHours.substring(0, 2);
                    String subMinute = getHours.substring(2);
                    String printHours = subHour + ":" + subMinute;

                    if (bookingDAO.checkBooking(tableID, dayBooking, getPhoneNumber, printHours)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("KEY_TABLE_ID", tableID);
                        FragmentOrderDrink frm = new FragmentOrderDrink();
                        frm.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.container_layout, frm).commit();

                    } else {
                        Toast.makeText(activity, "Thông tin đặt bàn không đúng !", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        view1.show();
        view1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view1.getWindow().getAttributes().windowAnimations = R.style.Style_AlertDialog_Corner;
        view1.getWindow().setGravity(Gravity.BOTTOM);

    }

    public boolean checkPhoneNumber(EditText edtPhoneNumber) {
        String getPhone = edtPhoneNumber.getText().toString().trim();

        if (getPhone.length() == 0) {
            Toast.makeText(activity, "Không được để trống số điện thoại khách", Toast.LENGTH_SHORT).show();
            return true;
        } else if (getPhone.length() != 10) {
            Toast.makeText(activity, "Số điện thoại phải là 10 số", Toast.LENGTH_SHORT).show();
            return true;
        } else if (checkIsVietNamePhone(getPhone)) {
            Toast.makeText(activity, "SĐT không đúng định dạng SĐT Việt Nam !", Toast.LENGTH_SHORT).show();
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

}
