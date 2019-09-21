package com.example.kenthomeopathicpharmacy;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
        }


        textView =findViewById(R.id.text);

        textView.setText("Welcome to KHP, your number one source for all Electronic gadgets & gizmos. We're dedicated to providing you the very best of Smartphones, with an emphasis on Timeliness, Mobile Friendly, Security features.\n" +
                "\n" +
                "\n" +
                "Founded in 1900 by Pratap Shankar Rastogi, KHP has come a long way from its beginnings in Lucknow, UP. When Pratap Shankar Rastogi first started out, his passion for latest gadgets drove them to start their own business.\n" +
                "\n" +
                "\n" +
                "We hope you enjoy our products as much as we enjoy offering them to you. If you have any questions or comments, please don't hesitate to contact us.\n");
    }
}
