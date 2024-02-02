package com.example.biomarket.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biomarket.R;
import com.example.biomarket.activities.OrderActivity;
import com.example.biomarket.activities.ProfileInformationActivity;
import com.example.biomarket.adapters.CartShopAdapter;
import com.example.biomarket.models.ProductModel;
import com.example.biomarket.models.ProductResume;
import com.example.biomarket.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ShopCartFragment extends Fragment {
    FirebaseFirestore database;
    RecyclerView recyclerViewPopularProducts;
    List<ProductModel> productModelList;
    CartShopAdapter cartShopAdapter;
    LinearLayout shopCartEmpty;

    TextView priceSubTotal, priceTotal, livPrice, orderClick;

    NavController navController;
    RelativeLayout progressBar;
    ConstraintLayout paySection;




    @Override
    public void onResume() {
        super.onResume();

        paySection.setVisibility(View.GONE);
        shopCartEmpty.setVisibility(View.VISIBLE);
        productModelList.clear();
        cartShopAdapter.notifyDataSetChanged();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_cart, container, false);

        paySection = view.findViewById(R.id.constraintLayoutPaySection);
        priceSubTotal = view.findViewById(R.id.priceSubTotal);
        priceTotal = view.findViewById(R.id.priceTotal);
        shopCartEmpty = view.findViewById(R.id.shopCartEmpty);
        livPrice = view.findViewById(R.id.livraisonPrice);


        progressBar = view.findViewById(R.id.progressBar);


        livPrice.setText("50.00");


        database = FirebaseFirestore.getInstance();

        recyclerViewPopularProducts = view.findViewById(R.id.cartShopProductsRV);

        recyclerViewPopularProducts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        productModelList = new ArrayList<>();
        cartShopAdapter = new CartShopAdapter(getContext(), productModelList, priceSubTotal, priceTotal, paySection, shopCartEmpty, 50.);

        recyclerViewPopularProducts.setAdapter(cartShopAdapter);




        priceTotal = view.findViewById(R.id.priceTotal);

        orderClick = view.findViewById(R.id.textViewOrder);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();



        if(currentUser == null){
//            LoginFragment loginFragment = new LoginFragment();
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, loginFragment).commit();
        }else {
            String userId = currentUser.getUid();
            showShopCartProducts(userId);

            orderClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {payClick();}
            });
        }

        return  view;
    }



    public void showShopCartProducts(String userId){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Clients");


        ref.child(userId).child("Pannier").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                DataSnapshot snapshot = task.getResult();

                if (task.isSuccessful()) {
                    Boolean add = true;
                    String val;

                    if(snapshot.exists()){
                        shopCartEmpty.setVisibility(View.GONE);
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                            ProductResume productResume = childSnapshot.getValue(ProductResume.class);
                            System.out.println("key: "+ childSnapshot.getKey() +"val: " + productResume.getId());
                            showProd(productResume.getId(), productResume.getQuantity());
                            progressBar.setVisibility(View.GONE);
                        }
                        paySection.setVisibility(View.VISIBLE);

                    }else {
                        shopCartEmpty.setVisibility(View.VISIBLE);
//                        Toast.makeText(getContext(), "Your CartShop is empty", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void showProd(String prodId, int quantity){

        DocumentReference docRef = database.collection("Products").document("bioMarketStore").collection("products").document(prodId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isComplete()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        ProductModel productModel = document.toObject(ProductModel.class);
                        productModel.setID(document.getId());
                        productModel.setQuantity(quantity);
//                        System.out.println("ID : " + productModel.getID());
                        productModelList.add(productModel);
                        cartShopAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getContext(), "ERROR+++", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "ERROR+++ END", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void payClick(){

//        navController.navigate(R.id.action_cartShopFragment_to_orderFragment);


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Clients");


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();
        // Use the uid as needed


        ref.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isComplete()) {
                    UserModel userModel = task.getResult().getValue(UserModel.class);
//                    userModel.setId(task.getResult().getKey());
                    assert userModel != null;
                    if(userModel.getAddressEmpty()){
                        System.out.println("CLEAR");
                        Intent intent = new Intent(getContext(), ProfileInformationActivity.class);
                        intent.putExtra("info", "Address");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }else {
                        System.out.println("SUBMIT");
                        if(productModelList.size() != 0){
                            Intent intent = new Intent(getContext(), OrderActivity.class);
                            intent.putExtra("size", productModelList.size());

                            int i = -1;
                            String productCle;
                            for (ProductModel product: productModelList){
                                productCle = "product_" + ++i;
                                intent.putExtra(productCle, product);
                            }

                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            };
        });




    }

}