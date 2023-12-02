package com.fpoly.pro1121_da1.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InvoiceViewModel extends ViewModel {
    private MutableLiveData<String> invoiceData  = new MutableLiveData<>();
    public void setInvoiceData(String data){
        invoiceData.setValue(data);
    }
    public LiveData<String> getInvoiceData(){
        return invoiceData;
    }
}
