package com.example.kenthomeopathicpharmacy;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductByCategory extends AppCompatActivity {
    RequestQueue queue;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private StaggeredGridLayoutManager gridLayoutManager;

    List<ProdByCategory> prodByCategoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
        }

        queue = Volley.newRequestQueue(this);

        final Intent intent = getIntent();           // Receiving data from RecyclerAdapter (id) in ProductByCategory activity
        final String ids = intent.getStringExtra("id");    // the name key must be same as in RecyclerAdapter "id" is used so here in getStringExtra(same key is used i.e "id")
        System.out.println(ids);

        recyclerView = findViewById(R.id.recy);
        gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);;

        showProducts(ids);
    }

    private void showProducts(final String ids) {
        StringRequest request = new StringRequest(Request.Method.POST,"http://sakardeal.com/android/cat_wise_data.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                System.out.println(response);
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i =0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        final ProdByCategory prodByCategory = new ProdByCategory();

                        prodByCategory.setId(jsonObject.getString("select_cat"));
                        prodByCategory.setProd_id(jsonObject.getString("add_product_id"));
                        prodByCategory.setName(jsonObject.getString("product_name"));
                        prodByCategory.setPrice(jsonObject.getString("product_price"));
                        prodByCategory.setImgs(jsonObject.getString("feature_url"));

                        prodByCategoryList.add(prodByCategory);

                    }
                    adapter = new RecyclerAdapterProductByCategory(ProductByCategory.this,prodByCategoryList);         //constructor of RecyclerAdapterCartItems
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
                map.put("add_product_id",ids);
                return map;
            }
        };
        queue.add(request);
    }
}
