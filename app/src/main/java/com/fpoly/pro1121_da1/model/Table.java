package com.fpoly.pro1121_da1.model;

public class Table {
    private String tableID;
    private int status;

    public Table(String tableID, int status) {
        this.tableID = tableID;
        this.status = status;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
