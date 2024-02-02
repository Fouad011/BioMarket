package com.example.biomarket.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.biomarket.activities.CategoryActivity;
import com.example.biomarket.R;
import com.example.biomarket.models.HomeCategoryModel;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    Context context;
    List<HomeCategoryModel> homeCategoryModelList;

    public HomeCategoryAdapter(Context context, List<HomeCategoryModel> homeCategoryModelList) {
        this.context = context;
        this.homeCategoryModelList = homeCategoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(homeCategoryModelList.get(position).getImage()).into(holder.img);
        String category_type = homeCategoryModelList.get(position).getType();
        String category_name = homeCategoryModelList.get(position).getName();
        holder.name.setText(category_name);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("category_type", category_type);
                intent.putExtra("category_name", category_name);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeCategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView img;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.homeCategoryName);
            img = itemView.findViewById(R.id.homeCategoryImg);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
