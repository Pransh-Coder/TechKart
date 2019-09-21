package com.example.kenthomeopathicpharmacy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowAdress extends AppCompatActivity {

    TextView textView,textView1;
    ConstraintLayout constraintLayout;

    Button button ;
    String phone;
    String no;
    String p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_adress);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        button = findViewById(R.id.add_address);

        constraintLayout = findViewById(R.id.layout);

        textView=findViewById(R.id.filling_text);
        textView1=findViewById(R.id.filling_name);

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("user_details",MODE_PRIVATE);

        p = sharedPreferences.getString("p_pincode","");
        System.out.println(p);

        no = sharedPreferences.getString("p_pno","");
        System.out.println(no);


        String city = sharedPreferences.getString("p_city","");
        String address = sharedPreferences.getString("p_address","");
        String state = sharedPreferences.getString("p_state","");



        textView1.setText(sharedPreferences.getString("p_name",""));
        textView.setText(city + "\n" + address + " ," + state+"\n" + p +"\n"+ no );

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowAdress.this,items_cart.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowAdress.this,EnterDetailsofAddress.class);
                startActivity(intent);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this,items_cart.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
