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

import com.example.kenthomeopathicpharmacy.ProdByCategory;
import com.example.kenthomeopathicpharmacy.R;
import com.example.kenthomeopathicpharmacy.RecyclerAdapterTopSeller;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterProductByCategory extends RecyclerView.Adapter<RecyclerAdapterProductByCategory.ViewHolder> {

    Context context;
    List<ProdByCategory> prodByCategoryList = new ArrayList<>();

    public RecyclerAdapterProductByCategory(Context context, List<ProdByCategory> prodByCategoryList) {
        this.context = context;
        this.prodByCategoryList = prodByCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topseller_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemname.setText(prodByCategoryList.get(position).getName());
        holder.itemprice.setText("Rs. " +prodByCategoryList.get(position).getPrice());
        Picasso.get().load(prodByCategoryList.get(position).getImgs()).into(holder.imageView);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id =prodByCategoryList.get(position).getProd_id();
                Toast.makeText(context,id,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(),ShowData.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return prodByCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

