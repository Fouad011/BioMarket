package com.example.biomarket.database;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.biomarket.adapters.HomeProductAdapters;
import com.example.biomarket.adapters.ProductAdapter;
import com.example.biomarket.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShowProducts {
    Context context;
    private List<ProductModel> productModelList;
    private ProductAdapter productAdapter;
    FirebaseFirestore db;

    public ShowProducts() {
        this.db = FirebaseFirestore.getInstance();
    }


    public ShowProducts(Context context, List<ProductModel> productModelList, ProductAdapter productAdapter) {
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




    public void All(String category, String title){
        Query query = this.db.collection("Products").document("bioMarketStore").collection("products");
        query.whereEqualTo("category", category);
        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ProductModel productModel = document.toObject(ProductModel.class);
                                productModel.setID(document.getId());

//                                if(productModel.getCategory().equals(category) && productModel.getVisible()){
                                if (title==null){
                                    productAdapter.addProduct(productModel);
                                }else if(visibility(title.toLowerCase(), productModel.getTitle().toLowerCase())){
                                    productAdapter.addProduct(productModel);

                                }

//                                }
                            }
                        }else {
                            Toast.makeText(context, "ERROR, task is not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Filter(String category, Double minPrice, Double maxPrice){
        Query query = this.db.collection("Products").document("bioMarketStore").collection("products");
        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ProductModel productModel = document.toObject(ProductModel.class);

                                if(
                                        (productModel.getCategory().equals(category) || productModel.getCategory()==null)
                                                && (productModel.getPrice()>=minPrice || productModel.getPrice()==null)
                                                && (productModel.getPrice()<=maxPrice || productModel.getPrice()==null)
                                                && productModel.getVisible()
                                ) {

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


    public void Search(String title, int minPrice, int maxPrice, Context context1, List<ProductModel> productModelList1, ProductAdapter productAdapter1){


//        CollectionReference collectionRef = this.db.collection("Products").document("bioMarketStore").collection("products");
//        Query query = collectionRef.whereEqualTo("title", title);

//        String [] mots = title.split(title);
//
//        for (String mot : mots){
//            System.out.println(mot);
//        }



//        Query query = collectionRef.whereGreaterThanOrEqualTo("title", title)
//                .whereLessThanOrEqualTo("title", title + "\uf8ff");



        CollectionReference reference = this.db.collection("Products").document("bioMarketStore").collection("products");
        Query query;
        if(minPrice!=-1 && maxPrice!=-1){
            query = reference
                    .whereGreaterThan("price", minPrice)
                    .whereLessThan("price", maxPrice);
        }else{
            if(minPrice==-1 && maxPrice==-1){
                query = reference;
            }else{
                if (minPrice==-1){
                    query = reference
                            .whereLessThanOrEqualTo("price", maxPrice);
                }else {
                    query = reference
                            .whereGreaterThanOrEqualTo("price", minPrice);
                }
            }

        }

//        Query query = reference
//                .whereGreaterThan("price", minPrice)
//                .whereLessThan("price", maxPrice);


        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            productAdapter1.clear();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ProductModel productModel = document.toObject(ProductModel.class);
                                if(productModel.getVisible()) {
                                    String titleProduct = productModel.getTitle().toLowerCase()+" "+productModel.getTitle().toLowerCase();

//                                    System.out.println(productModel);
//                                    System.out.println(">>> "+finalTitle);
//                                    if(finalTitle!=""){
                                    if (title==null){
                                        productModel.setID(document.getId());
                                        productModelList1.add(productModel);
                                        productAdapter1.notifyDataSetChanged();
                                    }else {
                                        if(visibility(title.toLowerCase(), titleProduct.toLowerCase())){
                                            productModel.setID(document.getId());
                                            productModelList1.add(productModel);
                                            productAdapter1.notifyDataSetChanged();
                                        }
                                    }

                                }
                            }
                        }else {
                            Toast.makeText(context1, "ERROR, task is not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void SearchSec(String title, int minPrice, int maxPrice, Context context1, List<ProductModel> productModelList1, HomeProductAdapters productAdapter1){


//        CollectionReference collectionRef = this.db.collection("Products").document("bioMarketStore").collection("products");
//        Query query = collectionRef.whereEqualTo("title", title);

//        String [] mots = title.split(title);
//
//        for (String mot : mots){
//            System.out.println(mot);
//        }



//        Query query = collectionRef.whereGreaterThanOrEqualTo("title", title)
//                .whereLessThanOrEqualTo("title", title + "\uf8ff");



        CollectionReference reference = this.db.collection("Products").document("bioMarketStore").collection("products");
        Query query;
        if(minPrice!=-1 && maxPrice!=-1){
            query = reference
                    .whereGreaterThan("price", minPrice)
                    .whereLessThan("price", maxPrice);
        }else{
            if(minPrice==-1 && maxPrice==-1){
                query = reference;
            }else{
                if (minPrice==-1){
                    query = reference
                            .whereLessThanOrEqualTo("price", maxPrice);
                }else {
                    query = reference
                            .whereGreaterThanOrEqualTo("price", minPrice);
                }
            }

        }

//        Query query = reference
//                .whereGreaterThan("price", minPrice)
//                .whereLessThan("price", maxPrice);


        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ProductModel productModel = document.toObject(ProductModel.class);
                                if(productModel.getVisible()) {
                                    String titleProduct = productModel.getTitle().toLowerCase()+" "+productModel.getTitle().toLowerCase();

//                                    System.out.println(productModel);
//                                    System.out.println(">>> "+finalTitle);
//                                    if(finalTitle!=""){
                                    if (title==null){
                                        productModel.setID(document.getId());
                                        productModelList1.add(productModel);
                                        productAdapter1.notifyDataSetChanged();
                                    }else {
                                        if(visibility(title.toLowerCase(), titleProduct)){
                                            productModel.setID(document.getId());
                                            productModelList1.add(productModel);
                                            productAdapter1.notifyDataSetChanged();
                                        }
                                    }

                                }
                            }
                        }else {
                            Toast.makeText(context1, "ERROR, task is not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean visibility(String search, String title) {
        int i = 0, len;

        Map<String, Integer> iDic = new LinkedHashMap<>();

        for(String x : search.split("\\ ")){
            for (String y : title.split("\\ ")){
                if (x.equals(y)){
                    ++i;
                }
            }
            iDic.put(x, i);
            i = 0;
        }

        int total = 0;
        for (int val : iDic.values()) {
            total = total + val;
        }

//        System.out.println(iDic);
//        System.out.println(total);

        len = search.split("\\ ").length;

        Float per = ((total/(float)len)*100)/2;

        System.out.println(per);
        return per>=20.0F;
    }



}
