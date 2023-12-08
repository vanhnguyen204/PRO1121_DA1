package com.fpoly.pro1121_da1.Interface;

import com.fpoly.pro1121_da1.model.Table;

public interface BookingInterface {
    void onBooking(Table table, int position);
    void onCancelBooking(Table table, int position);
}
