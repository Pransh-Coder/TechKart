package com.example.kenthomeopathicpharmacy;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerAdapterCartItems extends RecyclerView.Adapter<RecyclerAdapterCartItems.ViewHolder> {

    String cart_id;
    private Context context;
    List<CartItems> cartItemsList = new ArrayList<>();

    public RecyclerAdapterCartItems(Context context, List<CartItems> cartItemsList) {
        this.context = context;
        this.cartItemsList = cartItemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(cartItemsList.get(position).getP_name());
        holder.textView1.setText(cartItemsList.get(position).getP_price());
        Picasso.get().load(cartItemsList.get(position).getImg()).into(holder.imageView);

        cart_id = cartItemsList.get(position).getP_id();

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Button clicked",Toast.LENGTH_SHORT).show();
                removeproducts(cart_id);
            }
        });

    }

    private void removeproducts(final  String remove) {
        StringRequest request = new StringRequest(Request.Method.POST, "http://sakardeal.com/android/delete_item.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Toast.makeText(context,""+response,Toast.LENGTH_SHORT).show();
                System.out.println(response);

                if(response.equalsIgnoreCase("true"))
                {
                    Intent refresh = new Intent(context, items_cart.class);
                    context.startActivity(refresh);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id",remove) ;                                                        //map.put(String key,String value)
                return map;
            }
        };
        //queue1.add(request);
        Volley.newRequestQueue(context).add(request);
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView,textView1;
        Button removeItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.itemimages);
            textView = itemView.findViewById(R.id.itemnames);
            textView1 = itemView.findViewById(R.id.itemprices);
            removeItem=itemView.findViewById(R.id.removeitem);
        }
    }
}
