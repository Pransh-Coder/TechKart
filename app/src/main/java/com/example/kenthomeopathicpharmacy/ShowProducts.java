package com.example.kenthomeopathicpharmacy;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

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
import java.util.List;

public class ShowProducts extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private StaggeredGridLayoutManager gridLayoutManager;

    List<TopSeller> topSellerList = new ArrayList<>();

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
        }

        recyclerView = findViewById(R.id.recycler_view);
        queue= Volley.newRequestQueue(this);
        gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        parseTopSeller();
    }

    private void parseTopSeller() {
        StringRequest request = new StringRequest(Request.Method.POST, "http://sakardeal.com/android/product_list.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        final TopSeller topSeller = new TopSeller();    //topSeller-obj

                        topSeller.setItemname(jsonObject.getString("product_name"));
                        topSeller.setPics(jsonObject.getString("feature_url"));
                        topSeller.setId(jsonObject.getString("add_product_id"));

                        topSellerList.add(topSeller);
                    }
                    adapter = new RecyclerAdapterTopSeller(ShowProducts.this,topSellerList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
}
