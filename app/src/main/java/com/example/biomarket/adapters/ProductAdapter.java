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
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.biomarket.activities.LoginActivity;
import com.example.biomarket.activities.ProductDetailsActivity;
import com.example.biomarket.R;
import com.example.biomarket.database.DBProductResume;
import com.example.biomarket.models.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    List<ProductModel> productModelList;
    ArrayList<String> arrayList = new ArrayList<String>();

    int nbr = 0;

    NavController navController;


    public ProductAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_template, parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear(){
        productModelList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(productModelList.get(position).getImageUrl()).into(holder.img);
        String titl = productModelList.get(position).getTitle(), titlval;
        if(titl.length()>59){
            titlval = titl.substring(0, 60);
        }else {
            titlval = titl;
        }
        holder.title.setText(titlval + "...");

        String desc = productModelList.get(position).getDescription().toString(), descval;
        if(desc.length()>59){
            descval = desc.substring(0, 60);
        }else {
            descval = desc;
        }

        String [] price = productModelList.get(position).getPrice().toString().split("\\.");

        holder.integerPrice.setText(price[0]);
        holder.decimalPrice.setText(price[1]);

        holder.availability.setText(productModelList.get(position).getInStock()?"Available":"Unavailable");


        holder.infoProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);

                intent.putExtra("ID", productModelList.get(position).getID());
                intent.putExtra("title", productModelList.get(position).getTitle());
                intent.putExtra("description", productModelList.get(position).getDescription());
                intent.putExtra("price", productModelList.get(position).getPrice().toString());
                intent.putExtra("image_url", productModelList.get(position).getImageUrl());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                context.startActivity(intent);

//                navController = Navigation.findNavController(view);
//                navController.navigate(R.id.action_homeFragment_to_productFragment);





            }
        });

        holder.addToCartShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                if(user!=null){
                    DBProductResume DBProductResume = new DBProductResume(context, productModelList.get(position).getID().toString(), 1);
                    DBProductResume.submit();
                }else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }


            }
        });

    }

    public String PointToComma(Double price){
        String priceStr = Double.toString(price);
        priceStr += "00";
        String [] priceList = priceStr.split("\\.");
        return priceList[0]+","+priceList[1].substring(0, 2);
    }


    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addProduct(ProductModel product) {
        if (!productModelList.contains(product)) {
            productModelList.add(product);
            notifyDataSetChanged();
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, description, integerPrice, decimalPrice;

        CardView cardView;
        LinearLayout infoProd;
        TextView addToCartShop, availability;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageProduct);
            title = itemView.findViewById(R.id.productName);
//            description = itemView.findViewById(R.id.productDescription);
            integerPrice = itemView.findViewById(R.id.integerPrice);
            decimalPrice = itemView.findViewById(R.id.decimalPrice);

//            cardView = itemView.findViewById(R.id.cardView);
            infoProd = itemView.findViewById(R.id.infoProd);
            addToCartShop = itemView.findViewById(R.id.textView_add_product);

            availability = itemView.findViewById(R.id.availability);

        }
    }
}
