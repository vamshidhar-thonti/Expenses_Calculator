package com.project.expensescalculatorv2;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Read extends Fragment implements DatePickerDialog.OnDateSetListener {

    TextView textView;
    TextView textDate;
    Button button;
    Double finalExpensesData = 0.0;

    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbref;

    public Read() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_read, container, false);
        textView = v.findViewById(R.id.textRead);
        button = v.findViewById(R.id.readData);
        textDate = v.findViewById(R.id.textDate);

        button.setEnabled(false); //Button doesn't work unless date is set
        button.setVisibility(View.GONE); //Button will be disappeared

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(Read.this, 1);
                datePicker.show(getParentFragmentManager(), "date picker");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalExpensesData = 0.0;
                String[] str = textDate.getText().toString().split("~");
                Log.d("Split string values: ", str[0]+" "+str[1]);
                dbref = db.getReference().child(str[0]).child(str[1]);
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for(DataSnapshot child : dataSnapshot.getChildren()) {
                                ExpensesData ED = child.getValue(ExpensesData.class);
                                finalExpensesData += ED.getAmount();
                            }
                            String end = "Total expenses are " + decimalFormat.format(finalExpensesData);
                            textView.setText(end);
                        } else {
                            Toast.makeText(getActivity(), "You didn't spend even a paisa on that day", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        int sysDay = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("Day ", String.valueOf(sysDay));
        int sysMonth = calendar.get(Calendar.MONTH);
        Log.d("Month ", String.valueOf(sysMonth));
        int sysYear = calendar.get(Calendar.YEAR);
        Log.d("Year ", String.valueOf(sysYear));

        String mMonth = new DateFormatSymbols().getMonths()[month];
        String month_year = mMonth + "-" + year;
        String date = (day < 10 ? "0" : "") + day;
        String s = month_year + "~" + date;

        if (month < 5 && year <= 2020) {
            Toast.makeText(getActivity(), "You didn't create this app then :(", Toast.LENGTH_LONG).show();
        } else if (day > sysDay && month >= sysMonth && year >= sysYear) {
            Toast.makeText(getActivity(), "Bro! Please use your brain!", Toast.LENGTH_LONG).show();
        } else {
            textDate.setText(s);
            button.setVisibility(View.VISIBLE); //Button will be disappeared
            button.setEnabled(true); //Button doesn't work unless date is set
        }

    }

}