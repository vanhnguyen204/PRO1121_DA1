package com.fpoly.pro1121_da1.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class InvoiceViewModel extends ViewModel {
    private MutableLiveData<Map<Integer, Integer>> myMapLiveData;

    public MutableLiveData<Map<Integer, Integer>> getMyMapLiveData() {
        if (myMapLiveData == null) {
            myMapLiveData = new MutableLiveData<>();
            myMapLiveData.setValue(new HashMap<>());
        }
        return myMapLiveData;
    }

    public void updateMapValue(Integer key, Integer value) {
        // Lấy giá trị hiện tại của Map
        Map<Integer, Integer> currentMap = myMapLiveData.getValue();

        // Kiểm tra null để tránh lỗi NullPointerException
        if (currentMap != null) {
            // Cập nhật giá trị của Map
            currentMap.put(key, value);

            // Gửi giá trị mới về LiveData
            myMapLiveData.setValue(currentMap);
        }
    }
    public void setMapValue(Integer key, Integer value) {
        // Lấy giá trị hiện tại của Map
        Map<Integer, Integer> currentMap = new HashMap<>();
        currentMap.put(key, value);
        // Gửi giá trị mới về LiveData
        myMapLiveData.setValue(currentMap);
    }

}
