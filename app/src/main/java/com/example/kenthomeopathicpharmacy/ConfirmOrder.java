package com.example.kenthomeopathicpharmacy;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ConfirmOrder extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    String generatedPassword;
    String e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
        }

        editText=findViewById(R.id.entercode);
        textView=findViewById(R.id.code);
        button =findViewById(R.id.confirm_btn);

        Random random = new Random();
        generatedPassword = String.format("%04d", random.nextInt(1000000));
        textView.setText(generatedPassword);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e = editText.getText().toString();
                Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
                if(e.isEmpty())
                {
                    editText.setError("Please enter captcha to continue!");
                }
                if(e.equalsIgnoreCase(generatedPassword))
                {
                    Intent intent = new Intent(getApplicationContext(),PopActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
