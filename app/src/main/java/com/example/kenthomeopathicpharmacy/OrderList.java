package com.example.kenthomeopathicpharmacy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

public class OrderList extends AppCompatActivity {

    List<Order> orderList = new ArrayList<>();

    RequestQueue queue;

    Button cnclorder;

    RecyclerView recyclerView;
    RecyclerAdapterOrderList adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        queue = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.recyclerVieww);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences =getApplication().getSharedPreferences("username",MODE_PRIVATE);
        String a= sharedPreferences.getString("u_id","");
        System.out.println(a);

        showOrders(a);
        addOrders(a);
    }
    private void showOrders(final String user_id){
        StringRequest request = new StringRequest(Request.Method.POST, "http://sakardeal.com/android/my_order.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                System.out.println(response);

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        final Order order = new Order();
                        order.setOrder_id(jsonObject.getString("order_id"));
                        order.setName(jsonObject.getString("product_name"));
                        order.setPrice(jsonObject.getString("product_price"));
                        order.setDelivery(jsonObject.getString("delivery"));
                        order.setStatus(jsonObject.getString("product_status"));
                        order.setImg(jsonObject.getString("feature_url"));

                        orderList.add(order);
                    }
                    adapter = new RecyclerAdapterOrderList(OrderList.this,orderList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("user_id",user_id);
                return map;
            }
        };
        queue.add(request);
    }

    private void addOrders(final String user_id){
        StringRequest request = new StringRequest(Request.Method.POST, "http://sakardeal.com/android/checkout.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                System.out.println(response);

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        final Order order = new Order();
                        order.setOrder_id(jsonObject.getString("order_id"));
                        order.setName(jsonObject.getString("product_name"));
                        order.setPrice(jsonObject.getString("product_price"));
                        order.setDelivery(jsonObject.getString("delivery"));
                        order.setStatus(jsonObject.getString("product_status"));
                        order.setImg(jsonObject.getString("feature_url"));

                        orderList.add(order);
                    }
                    adapter = new RecyclerAdapterOrderList(OrderList.this,orderList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("user_id",user_id);
                return map;
            }
        };
        queue.add(request);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this,Drawerlayout.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
