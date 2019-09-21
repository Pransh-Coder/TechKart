package com.example.kenthomeopathicpharmacy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText useremail,password;
    Button signin;
    Button register;
    Button skiplogin;

    RequestQueue queue;

    String useremail1;
    String password1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        skiplogin = findViewById(R.id.skiplgn);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setTitle("Kent Tech");
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
        }

        useremail=(EditText)findViewById(R.id.writeemail);
        password=(EditText)findViewById(R.id.writepassword);

        queue= Volley.newRequestQueue(this);

        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        signin=(Button)findViewById(R.id.button1);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //these are for passing 2nd argument in hash funct
                useremail1 = useremail.getText().toString();
                password1=password.getText().toString();

                if(useremail.getText().toString().length()==0)
                {
                    useremail.setError("Username is not entered!");
                }
                else if(password.getText().toString().length()==0)
                {
                    password.setError("Password is not entered!");
                }
                else
                {
                    login();

                    /*Intent intent = new Intent(getApplicationContext(),Drawerlayout.class);
                    startActivity(intent);*/
                }

            }
        });
        skiplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Drawerlayout.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        StringRequest request = new StringRequest(Request.Method.POST, "http://sakardeal.com/android/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
               // System.out.println(response);

                String str = response.toString();
                System.out.println(""+ str);

                SharedPreferences sharedpreferences = getSharedPreferences("username",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("u_id",str);    // we are sending this u_id in items_cart activity
                editor.commit();    //commit()-Commit your preferences changes back from this Editor to the SharedPreferences object it is editing. This atomically performs the requested modifications, replacing whatever is currently in the SharedPreferences.

                if(response.equalsIgnoreCase("invalide"))
                {
                    Toast.makeText(Login.this, "Either email or password is incorrect!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),Drawerlayout.class);
                    intent.putExtra("id",str);
                    startActivity(intent);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {             // we are sending this data so that at the database we can check whether we have the same data or not i.e for matching
                HashMap<String,String> map=new HashMap<>();
                map.put("email",useremail1);
                map.put("pass",password1);
                return map;
            }
        };
        queue.add(request);
    }
}
