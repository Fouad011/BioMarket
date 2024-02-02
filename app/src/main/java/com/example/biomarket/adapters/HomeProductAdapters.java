package com.example.biomarket.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.biomarket.activities.LoginActivity;
import com.example.biomarket.activities.ProductDetailsActivity;
import com.example.biomarket.R;
import com.example.biomarket.database.DBProductResume;
import com.example.biomarket.models.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomeProductAdapters extends RecyclerView.Adapter<HomeProductAdapters.ViewHolder> {

    Context context;
    List<ProductModel> productModelList;
    int nbr = 0;

    public HomeProductAdapters(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(productModelList.get(position).getImageUrl()).into(holder.img);
        String titl = productModelList.get(position).getTitle().toString(), titlval;
        if(titl.length()>49){
            titlval = titl.substring(0, 50);
        }else {
            titlval = titl;
        }
        holder.title.setText(titlval + "...");
//        holder.description.setText(productModelList.get(position).getDescription());

        String [] price = productModelList.get(position).getPrice().toString().split("\\.");

        holder.integerPrice.setText(price[0]);
        holder.decimalPrice.setText(price[1]);
//        int clr = R.color.blue;
//        holder.availability.setTextColor(clr);
        holder.availability.setText(productModelList.get(position).getInStock()?"Available":"Unavailable");



        holder.infoProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, titl, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ProductDetailsActivity.class);

                intent.putExtra("ID", productModelList.get(position).getID());
                intent.putExtra("title", productModelList.get(position).getTitle());
                intent.putExtra("description", productModelList.get(position).getDescription());
                intent.putExtra("price", productModelList.get(position).getPrice().toString());
                intent.putExtra("image_url", productModelList.get(position).getImageUrl());

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        holder.addToCartShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                if(user!=null){
                    DBProductResume DBProductResume = new DBProductResume(context, productModelList.get(position).getID(), 1);
                    DBProductResume.submit();
                }else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            }


        });


    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, description, integerPrice, decimalPrice;
        CardView cardView;
        LinearLayout infoProd;
        TextView addToCartShop, availability;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageProduct);
            title = itemView.findViewById(R.id.productName);
//            description = itemView.findViewById(R.id.productName);
            integerPrice = itemView.findViewById(R.id.integerPrice);
            decimalPrice = itemView.findViewById(R.id.decimalPrice);

//            cardView = itemView.findViewById(R.id.cardViewHomeProduct);
            infoProd = itemView.findViewById(R.id.infoProd);
            addToCartShop = itemView.findViewById(R.id.textView_add_product);

            availability = itemView.findViewById(R.id.availability);
        }
    }
}
