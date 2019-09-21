package com.example.kenthomeopathicpharmacy;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class items_cart extends AppCompatActivity {

    String p,no;
    String id;
    //for storing in cart
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    List<CartItems> cartItemsList = new ArrayList<>();        //Made list of cart items to fill data that we retrive from json

    ArrayList<Integer> sum = new ArrayList();
    ArrayList<Integer> products = new ArrayList<>();

    TextView filladdressdetails;

    RequestQueue queue2;  // for filling items in cart i.e filltocart()  & for storing in cart i.e ShowCartItems() func

    Button button;
    Button  rupees;
    Button continue_btn;

    TextView textView,showname;
    TextView totalPrice,totalPayable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_cart);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        totalPrice = findViewById(R.id.t_amount);
        totalPayable=findViewById(R.id.t_payable);
        textView=findViewById(R.id.cost);
        continue_btn = findViewById(R.id.continu);


        queue2=Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);


        SharedPreferences sharedPreferences =getApplication().getSharedPreferences("username",MODE_PRIVATE);
        final String a= sharedPreferences.getString("u_id","");   // we are receving this u_id from Login Activity this u_id is response
        System.out.println(a);   //id=39

        final Intent intent = getIntent();           // Receiving data from RecyclerAdapterTopseller (id) in items_cart activity
        final String ids = intent.getStringExtra("id");

        //filltocart(ids,a);
        filltocart(ids,a);
        //storetocart();
        ShowCartItems(a);


        rupees = findViewById(R.id.Rs);

        filladdressdetails = findViewById(R.id.filladdress);
        showname = findViewById(R.id.showname);

        //Getting address from EnterDetailsofAdress activity using sharedPrefrence
        SharedPreferences sharedPreferences1 = getApplication().getSharedPreferences("user_details",MODE_PRIVATE);

        p = sharedPreferences1.getString("p_pincode","");
        //System.out.println(p);
        no = sharedPreferences1.getString("p_pno","");
        //System.out.println(no);
        String nameofperson=sharedPreferences1.getString("p_name","");
        System.out.println(nameofperson);
        String city = sharedPreferences1.getString("p_city","");
        String address = sharedPreferences1.getString("p_address","");
        String state = sharedPreferences1.getString("p_state","");

        showname.setText(""+nameofperson);
        filladdressdetails.setText(city + "\n" + address + " ," + state+"\n" + p +"\n"+ no);

        button=findViewById(R.id.chgAdress);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(items_cart.this,ShowAdress.class);
                startActivity(intent1);
            }
        });
        if(a.isEmpty())
        {
            continue_btn.setVisibility(View.GONE);
        }
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent1 = new Intent(items_cart.this,PaymentDetails.class);
                    startActivity(intent1);
            }
        });
    }
    // filltocart- this func is for filling cart
    private void filltocart(final  String id,final String name) {
        StringRequest request = new StringRequest(Request.Method.POST, "http://sakardeal.com/android/add_to_cart.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                System.out.println(response);


                adapter = new RecyclerAdapterCartItems(items_cart.this,cartItemsList);          //constructor of RecyclerAdapterCartItems
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("product_id",id);
                map.put("user_id",name);
                return map;
            }
        };
        queue2.add(request);
    }
    private void ShowCartItems(final String name){
        StringRequest request = new StringRequest(Request.Method.POST, "http://sakardeal.com/android/cart_detail.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                System.out.println(response);

                if(response.equalsIgnoreCase("[]"))
                {
                    continue_btn.setVisibility(View.GONE);
                }

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        final CartItems cartItems = new CartItems();           //  cartItems - obj of class CartItems

                        cartItems.setP_id(jsonObject.getString("add_to_cart_id"));
                        cartItems.setP_name(jsonObject.getString("product_name"));
                        cartItems.setP_price(jsonObject.getString("product_price"));
                        cartItems.setImg(jsonObject.getString("feature_url"));

                        cartItemsList.add(cartItems);     //adding obj to list of cartitemsList to fill the data in adapter

                        //for adding (sum) values in rupees textview
                        String summ = jsonObject.getString("product_price");

                        sum.add(Integer.valueOf(summ));
                        int sumddata = 0;    //counter
                        for (int ii : sum)
                            sumddata += ii;
                        String t = String.valueOf(sumddata);
                        int b=sum.size();                      // for getting length (size) of arraylist
                        String c =String.valueOf(b);
                        // Toast.makeText(getApplicationContext(),""+c,Toast.LENGTH_SHORT).show();

                        rupees.setText("Rs." + t);
                        totalPrice.setText("Total Amount: Rs. "+t);
                        textView.setText("Price  "+ "("+c+" items"+ ")");
                        totalPayable.setText("Total Payable: "+t );

                        SharedPreferences sharedPreferences = getSharedPreferences("values",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("size",c);
                        editor.putString("totalPrice",t);
                        editor.commit();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new RecyclerAdapterCartItems(items_cart.this,cartItemsList);          //constructor of RecyclerAdapterCartItems
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("user_id",name);
                return map;
            }
        };
        queue2.add(request);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                /*Intent intent = new Intent(this,ShowData.class);
                startActivity(intent);*/
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
