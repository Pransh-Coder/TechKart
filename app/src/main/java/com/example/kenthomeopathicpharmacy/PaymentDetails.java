package com.example.kenthomeopathicpharmacy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PaymentDetails extends AppCompatActivity {

    String nameofperson, city, address, state;


    TextView textView;
    String p, no, s;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView delAvail, textView1;
    TextView NoOfItems;
    TextView total_Amnt,TotalPay;

    Button TotalAmount;
    Button Contin_btn;

    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
        }

        value = "1";

        NoOfItems = findViewById(R.id.NoItems);
        TotalAmount = findViewById(R.id.Rs);
        total_Amnt = findViewById(R.id.total_Amnt);
        Contin_btn = findViewById(R.id.conti);
        TotalPay = findViewById(R.id.total_pay);

        textView = findViewById(R.id.show_address);
        textView1 = findViewById(R.id.del_time);

        radioGroup = findViewById(R.id.radiogrp);
        delAvail = findViewById(R.id.del_Avail);

        SharedPreferences sharedPreferences1 = getApplication().getSharedPreferences("user_details", MODE_PRIVATE);

        p = sharedPreferences1.getString("p_pincode", "");
        no = sharedPreferences1.getString("p_pno", "");
        nameofperson = sharedPreferences1.getString("p_name", "");
        System.out.println(nameofperson);
        city = sharedPreferences1.getString("p_city", "");
        address = sharedPreferences1.getString("p_address", "");
        state = sharedPreferences1.getString("p_state", "");

        textView.setText(nameofperson + " " + address + " " + city + " " + state + " " + p);

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("values", MODE_PRIVATE);
        String size = sharedPreferences.getString("size", "");

        String T_amt = sharedPreferences.getString("totalPrice", "");

        NoOfItems.setText("Price  " + "(" + size + " items" + ")");
        TotalAmount.setText("Rs. " + T_amt);
        total_Amnt.setText("Total Amount: Rs. " + T_amt);
        TotalPay.setText("Total Payable:" + T_amt);

        Contin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value.equalsIgnoreCase("1")) {
                    Toast.makeText(getApplicationContext(), "Please Select the Delivery Mode !", Toast.LENGTH_SHORT).show();
                } else if (value.equalsIgnoreCase("cod")){
                    Intent intent = new Intent(PaymentDetails.this, ConfirmOrder.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(getApplicationContext(), "" + radioButton.getText(), Toast.LENGTH_SHORT).show();
        s = String.valueOf(radioButton.getText());
        System.out.println(s);
        if (s.equalsIgnoreCase("COD - Cash on Delivery")) {
            textView.setText("Delivery Not Available at selected Pincode!");
            if (p.equalsIgnoreCase("226003")) {
                textView.setText("Delivery charge: 0");
                delAvail.setText("Delivery Available Press Continue");
                textView1.setText("COD Delivery time: 1-2 days");
                value="cod";
            }

        } else {
            textView.setText(nameofperson + " " + address + " " + city + " " + state + " " + p);
            delAvail.setText("");
            textView1.setText("");
        }
    }
}
