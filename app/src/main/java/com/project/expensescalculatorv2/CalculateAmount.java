package com.project.expensescalculatorv2;

import android.util.Log;

import java.util.List;

public class CalculateAmount {

    Double finalExpensesData = 0.0;

    public Double calculate (List<String> list) {
        for (String amountData : list) {
            finalExpensesData += Double.parseDouble(amountData);
        }
        Log.d("Amount is ", finalExpensesData.toString());
        return finalExpensesData;
    }

}
