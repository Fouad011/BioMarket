package com.example.biomarket.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.biomarket.R;
import com.example.biomarket.models.ProductModel;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    List<ProductModel> productModelList;
    TextView priceSubTotal, textViewPriceTotal;
    Double priceTotal = 0.0;
    int i=0;
    Double livPrice;

    DecimalFormat df = new DecimalFormat("0.00");


    public OrderAdapter(Context context, List<ProductModel> productModelList, TextView priceSubTotal, TextView textViewPriceTotal, Double livPrice) {
        this.context = context;
        this.productModelList = productModelList;
        this.priceSubTotal = priceSubTotal;
        this.textViewPriceTotal = textViewPriceTotal;
        this.livPrice = livPrice;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(productModelList.get(position).getImageUrl()).into(holder.imageViewProduct);
        holder.title.setText(productModelList.get(position).getTitle());

        int qnt = productModelList.get(position).getQuantity();
        holder.quantity.setText(String.valueOf(qnt));


        Double price = productModelList.get(position).getPrice();

        double total = qnt*price;
        String totalStr = Double.toString(Double.parseDouble(df.format(total)));

        holder.priceForAll.setText(totalStr);



        priceTotal += price*qnt;
//
//        ++i;
//        if(position==0 && i!=0){
//            priceTotal = price;
//            i=0;
//        }


        priceSubTotal.setText(PointToComma(Double.valueOf(df.format(priceTotal))));
        textViewPriceTotal.setText(PointToComma(Double.valueOf(df.format(priceTotal+livPrice))));

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduct;
        TextView title, priceForAll, quantity;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cartShopCV);

            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
//            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            title = itemView.findViewById(R.id.title);
//            priceForOne = itemView.findViewById(R.id.priceForOne);
            priceForAll = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);

//            textViewMinus = itemView.findViewById(R.id.textViewMinus);
//            textViewPlus = itemView.findViewById(R.id.textViewPlus);

        }
    }
}
