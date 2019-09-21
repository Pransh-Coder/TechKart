package com.example.kenthomeopathicpharmacy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

//This Adapter is for category
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    List<Category> categoryList = new ArrayList<>();

    public RecyclerAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.name.setText(categoryList.get(position).getCategorynames());
        Picasso.get().load(categoryList.get(position).getImages()).into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id =categoryList.get(position).getId();
                Toast.makeText(context,id,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context.getApplicationContext(),ProductByCategory.class);
                intent.putExtra("id",id);                       //sending data from RecyclerAdapter Activity  to ProductByCategory Activity
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView imageView;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.category);
            imageView = itemView.findViewById(R.id.image_view);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}


