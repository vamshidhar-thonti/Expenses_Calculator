package com.project.expensescalculatorv2;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
public class Push {

    long c = System.currentTimeMillis();
    SimpleDateFormat monthAndYearFormat = new SimpleDateFormat("MMMM-YYYY");

    String month_year = monthAndYearFormat.format(c);
    String date_time = null;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = db.getReference(month_year);

    public void Update(Object finalData) {
        dbRef.child(date_time).setValue(finalData);
    }

    public void setDate(String date) {
        this.date_time = date;
    }

}
