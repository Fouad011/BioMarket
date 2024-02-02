package com.example.biomarket.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.biomarket.R;
import com.example.biomarket.database.DBProductResume;
import com.example.biomarket.models.ProductModel;
import com.example.biomarket.models.ProductResume;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.List;

public class CartShopAdapter extends RecyclerView.Adapter<CartShopAdapter.ViewHolder> {
    Context context;
    List<ProductModel> productModelList;
    TextView priceSubTotal;
    TextView textViewPriceTotal;
    ConstraintLayout paySection;
    LinearLayout shopCartEmpty;
    Double priceTotal = 0.0;
    int i=0;
    Double livPrice;

    DecimalFormat df = new DecimalFormat("0.00");


    public CartShopAdapter(Context context, List<ProductModel> productModelList, TextView priceSubTotal, TextView textViewPriceTotal, ConstraintLayout paySection, LinearLayout shopCartEmpty, Double livPrice) {
        this.context = context;
        this.productModelList = productModelList;
        this.priceSubTotal = priceSubTotal;
        this.textViewPriceTotal = textViewPriceTotal;
        this.paySection = paySection;
        this.shopCartEmpty = shopCartEmpty;
        this.livPrice = livPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shopcart_product_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(productModelList.get(position).getImageUrl()).into(holder.imageViewProduct);
        holder.title.setText(productModelList.get(position).getTitle());

        int qnt = productModelList.get(position).getQuantity();
        holder.quantity.setText(String.valueOf(qnt));


        Double price = productModelList.get(position).getPrice();

        holder.priceForAll.setText(PointToComma(qnt*price));
        holder.priceForOne.setText(PointToComma(price));



        priceTotal += price*qnt;

//        ++i;
//        if(position==0 && i!=0){
//            priceTotal = price;
//            i=0;
//        }


        priceSubTotal.setText(PointToComma(priceTotal));

        textViewPriceTotal.setText(PointToComma(priceTotal+livPrice));


        if(productModelList.get(position).getQuantity()==1){
//            holder.textViewMinus.setVisibility(View.INVISIBLE);
            holder.cardMinus.setVisibility(View.INVISIBLE);
        }
        if(productModelList.get(position).getQuantity()==productModelList.get(position).getQuantityInStock()){
//            holder.textViewPlus.setVisibility(View.INVISIBLE);
            holder.cardPlus.setVisibility(View.INVISIBLE);
        }





        holder.textViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                holder.textViewMinus.setVisibility(View.VISIBLE);
                holder.textViewMinus.setVisibility(View.VISIBLE);
                holder.cardMinus.setVisibility(View.VISIBLE);




                FirebaseFirestore ff = FirebaseFirestore.getInstance();
                Query query = ff.collection("Products").document("bioMarketStore").collection("products");
                query.whereEqualTo("title", productModelList.get(position).getTitle())
                        .whereGreaterThanOrEqualTo("price", productModelList.get(position).getPrice())
                        .whereEqualTo("visibility", productModelList.get(position).getVisible());
//                holder.textViewPlus.setVisibility(View.GONE);
                holder.cardPlus.setVisibility(View.INVISIBLE);

                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int qtNbr;
//                        holder.textViewPlus.setVisibility(View.VISIBLE);
                        holder.cardPlus.setVisibility(View.VISIBLE);

                        if(task.isComplete()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ProductModel productModel = document.toObject(ProductModel.class);
                                productModel.setID(document.getId());

                                if(productModel.getID().equals(productModelList.get(position).getID())){

                                    if (productModel.getQuantityInStock()>productModelList.get(position).getQuantity()){
                                        if(productModel.getQuantityInStock()==productModelList.get(position).getQuantity()+1){
//                                            holder.textViewPlus.setVisibility(View.INVISIBLE);
                                            holder.cardPlus.setVisibility(View.INVISIBLE);

                                            Toast.makeText(context, "Available quantity : "+productModel.getQuantityInStock(), Toast.LENGTH_SHORT).show();
                                        }

                                        qtNbr = Integer.parseInt(holder.quantity.getText().toString());
                                        String qt = Integer.toString(++qtNbr);
                                        holder.quantity.setText(qt);

                                        String priceNormalise = PointToComma(qtNbr*price);
                                        holder.priceForAll.setText(priceNormalise);

                                        priceTotal += price;

                                        priceSubTotal.setText(PointToComma(priceTotal));

                                        Double totalPrice = priceTotal+livPrice;
                                        String priceTotalStr = PointToComma(totalPrice);
                                        textViewPriceTotal.setText(priceTotalStr);

                                        // add this to editQuantity method
                                        DBProductResume DBProductResume = new DBProductResume(context, productModelList.get(position).getID(), qtNbr);
                                        DBProductResume.editQuantity();

                                        productModelList.get(position).setQuantity(qtNbr);
                                    }
                                }
                            }
                        }else {
                            Toast.makeText(context, "Field", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





        holder.textViewMinus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if(holder.cardPlus.getVisibility()==View.INVISIBLE){
//                    holder.textViewPlus.setVisibility(View.VISIBLE);
                    holder.cardPlus.setVisibility(View.VISIBLE);
                }


                if(Integer.parseInt(holder.quantity.getText().toString()) > 1){

                    if(Integer.parseInt(holder.quantity.getText().toString())==2){
                        holder.textViewMinus.setVisibility(View.INVISIBLE);
                        holder.cardMinus.setVisibility(View.GONE);
                    }

                    int nbr = Integer.parseInt(holder.quantity.getText().toString());

                    holder.quantity.setText(Integer.toString(--nbr));

//                    String priceTmp = Double.toString(nbr*price);
//                    priceTmp += "00";
//                    String [] priceTmpL = priceTmp.split("\\.");

                    holder.priceForAll.setText(PointToComma(nbr*price));

                    priceTotal -= price;
//                    priceTotal = Double.parseDouble(df.format(CommaToPoint(priceTotal.toString())));

                    priceSubTotal.setText(PointToComma(priceTotal));

                    String priceTotalStr = PointToComma(priceTotal+livPrice);
                    textViewPriceTotal.setText(priceTotalStr);

                    // add this to editQuantity method
//                    holder.textViewMinus.setVisibility(View.GONE);
                    holder.cardMinus.setVisibility(View.INVISIBLE);
                    DBProductResume DBProductResume = new DBProductResume(context, productModelList.get(position).getID(), nbr);
                    DBProductResume.editQuantity();
//                    holder.textViewMinus.setVisibility(View.VISIBLE);
                    holder.cardMinus.setVisibility(View.VISIBLE);



                    productModelList.get(position).setQuantity(nbr);


                }
            }
        });





        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                assert currentUser != null;
                String userId = currentUser.getUid();

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference("Clients");
                ref.child(userId).child("Pannier").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isComplete()) {
                            DataSnapshot snapshot = task.getResult();
                            Boolean add = true;
                            String key;

                            if(snapshot.exists()){
//                                shopCartEmpty.setVisibility(View.GONE);
                                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                    ProductResume productResume = childSnapshot.getValue(ProductResume.class);

                                    assert productResume != null;
                                    if(productResume.getId().equals(productModelList.get(position).getID())){


//                                        priceTotal -= productModelList.get(position).getPrice()*productModelList.get(position).getQuantity();
//                                        priceSubTotal.setText(PointToComma(priceTotal));


                                        childSnapshot.getRef().removeValue();
                                        productModelList.remove(productModelList.get(position));
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Product Deleted", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                            }else {
                                Toast.makeText(context, "database is empty", Toast.LENGTH_SHORT).show();
                            }








                            if(productModelList.size() < 1){
                                paySection.setVisibility(View.GONE);
                                shopCartEmpty.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(context, "task is not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }



    public Double CommaToPoint(String priceStr){
        priceStr += "00";
        String [] priceList = priceStr.split(",");
        return Double.parseDouble(priceList[0]+"."+priceList[1]);
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
        ImageView imageViewProduct, imageViewDelete, textViewMinus, textViewPlus;
        CardView cardMinus, cardPlus;
        TextView title, priceForOne, priceForAll;
        TextView quantity;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cartShopCV);

            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            title = itemView.findViewById(R.id.title);
            priceForOne = itemView.findViewById(R.id.priceForOne);
            priceForAll = itemView.findViewById(R.id.priceForAll);
            quantity = itemView.findViewById(R.id.quantity);

            textViewMinus = itemView.findViewById(R.id.textViewMinus);
            textViewPlus = itemView.findViewById(R.id.textViewPlus);

            cardMinus = itemView.findViewById(R.id.cardViewMinusSection);
            cardPlus = itemView.findViewById(R.id.cardViewPlusSection);



        }


    }






}
