package com.project.expensescalculatorv2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Write#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Write extends Fragment {

    EditText amount;
    Button update;
    TextView date;

    ExpensesData data;
    Push push;

    public Write() {
        // Required empty public constructor
    }

    public static Write newInstance() {
        Write fragment = new Write();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        push = new Push();
        data = new ExpensesData();

        View v = inflater.inflate(R.layout.fragment_write, container, false);
        amount = v.findViewById(R.id.expenseAmount);
        update = v.findViewById(R.id.saveData);
        date = v.findViewById(R.id.date);

        /*
        * This code is to update the clock every time in the UI which
        * uses System.currentTimeMillis(), SimpleDateFormat methods/classes.
        * */
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long sysdate = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                SimpleDateFormat sdf_db = new SimpleDateFormat("dd/HH:mm:ss");
                                String dateString = sdf.format(sysdate);
                                String dateStringDB = sdf_db.format(sysdate);
                                date.setText(dateString);
                                data.setCurrentDate(dateString);
                                push.setDate(dateStringDB);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Check if the editText field is empty
                if(amount.getText().toString().trim().equalsIgnoreCase("")){
                    amount.setError("Lol! Don't Play!");
                } else {
                    data.setAmount(Double.parseDouble(amount.getText().toString().trim())); //get amount form the field and set to the ExpensesData class
                    push.Update(data); //Call the method which updates/writes the data to the firebase
                    Toast.makeText(getActivity(), "Updated to DB", Toast.LENGTH_LONG).show(); //Toast message which notifies that the values are updated successfully.
                    amount.setText("");
                }
            }
        });

        return v;
    }
}