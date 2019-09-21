package com.example.kenthomeopathicpharmacy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EnterDetailsofAddress extends AppCompatActivity {

    Spinner spinner;

    String states[] = {"Select State","Andra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir",
            "Jharkhand","Karnataka","Kerala","Madya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Orissa","Punjab","Rajasthan","Sikkim","Tamil Nadu",
            "Telagana","Tripura","Uttaranchal","Uttar Pradesh","West Bengal"};

    Button add_details;

    EditText city,address,pincode,name,pno;

    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_detailsof_address);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        add_details = findViewById(R.id.adddetails);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, states);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getApplicationContext(),states[position] , Toast.LENGTH_LONG).show();

                value = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        city=findViewById(R.id.city);
        address=findViewById(R.id.e_address);
        pincode=findViewById(R.id.pincode);
        name=findViewById(R.id.e_name);
        pno=findViewById(R.id.p_no);

       add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String p_name = name.getText().toString();
                String p_city= city.getText().toString();
                String p_address= address.getText().toString();
                String p_pincode = pincode.getText().toString();
                String p_pno= pno.getText().toString();

                //Using SharedPreferences to send  (or) pass data to ShowAdress activity and item_cart activity
                SharedPreferences sharedPreferences = getSharedPreferences("user_details",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("p_name",p_name);   // used for storing value
                editor.putString("p_city",p_city);
                editor.putString("p_address",p_address);
                //value from spinner
                editor.putString("p_state", value);

                editor.putString("p_pincode",p_pincode);
                editor.putString("p_pno",p_pno);

                editor.commit();

                Intent intent = new Intent(EnterDetailsofAddress.this,ShowAdress.class);
                startActivity(intent);

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this,ShowAdress.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
