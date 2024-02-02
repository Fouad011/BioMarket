package com.example.biomarket.database;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.biomarket.adapters.HomeProductAdapters;
import com.example.biomarket.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ShowProductsHome {
    Context context;
    private List<ProductModel> productModelList;
    private HomeProductAdapters productAdapter;
    FirebaseFirestore db;

    public ShowProductsHome(Context context, List<ProductModel> productModelList, HomeProductAdapters productAdapter) {
        this.context = context;
        this.productModelList = productModelList;
        this.productAdapter = productAdapter;
        this.db = FirebaseFirestore.getInstance();
    }


    public void ByClassification(String classification){
        Query query = this.db.collection("Products").document("bioMarketStore").collection("products");
        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ProductModel productModel = document.toObject(ProductModel.class);
                                if(productModel.getClassification().equals(classification) && productModel.getVisible()){
//                                    System.out.println(productModel.getVisible().toString());
                                    productModel.setID(document.getId());
                                    productModelList.add(productModel);
                                    productAdapter.notifyDataSetChanged();
                                }
                            }
                        }else {
                            Toast.makeText(context, "ERROR, task is not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void ByCategory(String category){
        Query query = this.db.collection("Products").document("bioMarketStore").collection("products");
        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ProductModel productModel = document.toObject(ProductModel.class);
                                if(productModel.getCategory().equals(category) && productModel.getVisible()){
                                    productModel.setID(document.getId());
                                    productModelList.add(productModel);
                                    productAdapter.notifyDataSetChanged();
                                }
                            }
                        }else {
                            Toast.makeText(context, "ERROR, task is not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void All(){
        Query query = this.db.collection("Products").document("bioMarketStore").collection("products");
        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ProductModel productModel = document.toObject(ProductModel.class);
                                productModel.setID(document.getId());
                                productModelList.add(productModel);
                                productAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(context, "ERROR, task is not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}
