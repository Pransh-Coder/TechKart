package com.example.kenthomeopathicpharmacy;

import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyAccount extends AppCompatActivity {

    Button logout;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        constraintLayout = findViewById(R.id.constrain);

        logout=findViewById(R.id.logout);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
        }

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences1 = getApplication().getSharedPreferences("username",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.clear(); //Mark in the editor to remove all values from the preferences. Once commit is called, the only remaining preferences will be any that you have defined in this editor.
                editor.commit();
                Toast.makeText(getApplicationContext(),"Sucessfully Logged out!",Toast.LENGTH_SHORT).show();
                finish();


            }
        });
    }
}
