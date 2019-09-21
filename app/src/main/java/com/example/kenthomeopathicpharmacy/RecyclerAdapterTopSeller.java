package com.example.kenthomeopathicpharmacy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecyclerAdapterTopSeller extends RecyclerView.Adapter<RecyclerAdapterTopSeller.ViewHolder>{

    RequestQueue queue;

    private Context context;
    List<TopSeller> topSellerList = new ArrayList<>();

    public RecyclerAdapterTopSeller(Context context, List<TopSeller> topSellerList) {       //constructor
        this.context = context;
        this.topSellerList = topSellerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topseller_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemname.setText(topSellerList.get(position).getItemname());
        holder.itemprice.setText("Rs. " +topSellerList.get(position).getPrice());
        Picasso.get().load(topSellerList.get(position).getPics()).into(holder.imageView);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = topSellerList.get(position).getId();

                Intent intent = new Intent(context.getApplicationContext(),ShowData.class);
                intent.putExtra("id",id);                   //sending data from RecyclerAdapterTopSeller  to showData activity  (intent.putExtra(String key, Object data))
                context.startActivity(intent);                  //we can directly use startActivity(intent)
            }
        });
    }

    @Override
    public int getItemCount() {
        return topSellerList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemname,itemprice;
        ImageView imageView;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.medicine);
            itemprice = itemView.findViewById(R.id.med_price);
            imageView = itemView.findViewById(R.id.imageView);
            constraintLayout = itemView.findViewById(R.id.constraintView2);
        }
    }

}
