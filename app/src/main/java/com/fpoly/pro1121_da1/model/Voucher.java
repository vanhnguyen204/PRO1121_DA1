package com.fpoly.pro1121_da1.model;

public class Voucher {
    private int voucherID;
    private int priceReduce;
    private String dateExpiry;
    private int conditionReduce;

    public Voucher(int voucherID, int priceReduce, String dateExpiry, int conditionReduce) {
        this.voucherID = voucherID;
        this.priceReduce = priceReduce;
        this.dateExpiry = dateExpiry;
        this.conditionReduce = conditionReduce;
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

    public int getConditionReduce() {
        return conditionReduce;
    }

    public void setConditionReduce(int conditionReduce) {
        this.conditionReduce = conditionReduce;
    }
}
