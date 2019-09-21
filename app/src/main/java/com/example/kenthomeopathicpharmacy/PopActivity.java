package com.example.kenthomeopathicpharmacy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class PopActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        DisplayMetrics displayMetrics =new DisplayMetrics();

        button=findViewById(R.id.keepshopping);


        //getWindowManager()-Retrieve the window manager for showing custom windows.
        //getDefaultDisplay() -Returns the Display upon which this WindowManager instance will create new windows.
        //getMetrics()-Gets display metrics that describe the size and density of this display

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width =displayMetrics.widthPixels;                  //widthPixels-The absolute width of the available display size in pixels
        int height =displayMetrics.heightPixels;                //heightPixels-The absolute height of the available display size in pixels

        getWindow().setLayout((int)(width*.9),(int)(height*.4));       //getWindow()-Retrieve the current Window for the activity.

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrderList.class);
                startActivity(intent);
            }
        });
    }


}



