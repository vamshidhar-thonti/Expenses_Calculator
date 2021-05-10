package com.project.expensescalculatorv2;

import java.text.SimpleDateFormat;

public class ExpensesData {

    long sysdate = System.currentTimeMillis();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    String currentDate = dateFormat.format(sysdate);
    Double amount;

    public ExpensesData() {
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
