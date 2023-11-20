package com.fpoly.pro1121_da1.model;

public class Voucher {
    private int voucherID;
    private int priceReduce;
    private String dateExpiry;

    public Voucher(int voucherID, int priceReduce, String dateExpiry) {
        this.voucherID = voucherID;
        this.priceReduce = priceReduce;
        this.dateExpiry = dateExpiry;
    }

    public int getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(int voucherID) {
        this.voucherID = voucherID;
    }

    public int getPriceReduce() {
        return priceReduce;
    }

    public void setPriceReduce(int priceReduce) {
        this.priceReduce = priceReduce;
    }

    public String getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(String dateExpiry) {
        this.dateExpiry = dateExpiry;
    }
}
