package com.example.kenthomeopathicpharmacy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerAdapterOrderList extends RecyclerView.Adapter<RecyclerAdapterOrderList.ViewHolder> {

    Context context;
    List<Order> orderList = new ArrayList<>();

    public RecyclerAdapterOrderList(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.itemName.setText(orderList.get(position).getName());
        holder.itemPrice.setText("Rs." + orderList.get(position).getPrice());
        holder.textView.setText("Delievery "+orderList.get(position).getDelivery());
        holder.textView.setText("order:"+orderList.get(position).getStatus());
        Picasso.get().load(orderList.get(position).getImg()).into(holder.imageView);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        holder.date.setText(currentDate);

        final String ord_id = orderList.get(position).getOrder_id();
        System.out.println(ord_id);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textView.setText("order:"+orderList.get(position).getStatus());
                Toast.makeText(context,"clicked"+ord_id,Toast.LENGTH_SHORT).show();
                canclelOrder(ord_id);
            }
        });
    }

    private void canclelOrder(final String p_id){
        StringRequest request = new StringRequest(Request.Method.POST, "http://sakardeal.com/android/cancel.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("order_id",p_id) ;                                                        //map.put(String key,String value)
                return map;
            }
        };
        //queue1.add(request);
        Volley.newRequestQueue(context).add(request);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemPrice,textView,date;
        Button button;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.prod_name);
            itemPrice = itemView.findViewById(R.id.prod_price);
            textView=itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date);
            button=itemView.findViewById(R.id.cnclorder);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
