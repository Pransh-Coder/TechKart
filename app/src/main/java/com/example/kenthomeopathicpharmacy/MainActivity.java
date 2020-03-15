package com.example.kenthomeopathicpharmacy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {
    //TextView name,email,password;
    EditText name,email,password;
    Button signup;
    Button sigin;
    Button skip;

    RequestQueue queue;

    String username;
    String useremail;
    String userpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setTitle("Kent Tech");
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
        }

        skip = findViewById(R.id.skip);

        name =(EditText)findViewById(R.id.entername);
        email =(EditText)findViewById(R.id.enteremail);
        password=(EditText)findViewById(R.id.enterpassword);

        queue=Volley.newRequestQueue(this);

        sigin=(Button)findViewById(R.id.signin);
        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });

        signup=(Button)findViewById(R.id.button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we have made this becoz hashmap takes 1 argument as string so we will use below values
                username = name.getText().toString();                                                        // we have copied value of name editText into string variable username  we can use this in above if condition as if(username.IsEmpty())..
                useremail =email.getText().toString();
                userpassword = password.getText().toString();

                if(name.getText().toString().length()==0 )
                {
                    name.setError("Name feild is empty!");
                }
                else if(email.getText().toString().length()==0)
                {
                    email.setError("Enter Email!");
                }
                else if(password.getText().toString().length()==0)
                {
                    password.setError("Password feild is empty!");

                }
                else
                {
                    jsonParse();
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Drawerlayout.class);
                startActivity(intent);
            }
        });
    }
    private void jsonParse() {
        StringRequest request = new StringRequest(Request.Method.POST, "http://sakardeal.com/android/reg.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                Log.i("My success",""+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("name",username);
                map.put("email",useremail);
                map.put("password",userpassword);
                return map;
            }
        };
        queue.add(request);
    }
}
