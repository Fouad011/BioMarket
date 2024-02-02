package com.example.biomarket.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biomarket.activities.ClassificationActivity;
import com.example.biomarket.R;
import com.example.biomarket.adapters.HomeCategoryAdapter;
import com.example.biomarket.adapters.HomeProductAdapters;
import com.example.biomarket.database.ShowProductsHome;
import com.example.biomarket.models.HomeCategoryModel;
import com.example.biomarket.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    FirebaseFirestore db;
    RecyclerView RecyclerViewPopularProducts, RecyclerViewCategories, RecyclerViewRecomendedProducts, recyclerViewAll;
    List<ProductModel> productModelList, recomendedProductsModelList, viewAllModelList;
    //    ProductAdapter productAdapter;
    List<HomeCategoryModel> homeCategoryModelList;
    HomeProductAdapters homeProductAdapters, recomendedProductsAdapters, productAdapter;
    HomeCategoryAdapter homeCategoryAdapter;

    SwipeRefreshLayout swipe;



    TextView popularProductsViewAllLink, recomendedProductsViewAllLink, viewAllProductsLink;


    NavController navController;



//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        navController = Navigation.findNavController(view);
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();


        RecyclerViewPopularProducts = view.findViewById(R.id.popularProducts);
        RecyclerViewPopularProducts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        productModelList = new ArrayList<>();
        homeProductAdapters = new HomeProductAdapters(getContext(), productModelList);
        RecyclerViewPopularProducts.setAdapter(homeProductAdapters);



        RecyclerViewRecomendedProducts = view.findViewById(R.id.recomendedProducts);
        RecyclerViewRecomendedProducts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recomendedProductsModelList = new ArrayList<>();
        recomendedProductsAdapters = new HomeProductAdapters(getContext(), recomendedProductsModelList);
        RecyclerViewRecomendedProducts.setAdapter(recomendedProductsAdapters);



        RecyclerViewCategories = view.findViewById(R.id.homeCategory);
        RecyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        homeCategoryModelList = new ArrayList<>();
        homeCategoryAdapter= new HomeCategoryAdapter(getContext(), homeCategoryModelList);
        RecyclerViewCategories.setAdapter(homeCategoryAdapter);



        recyclerViewAll = view.findViewById(R.id.allProducts);
//        recyclerViewAll.setLayoutManager(new LinearLayoutManager(getContext()));
        viewAllModelList = new ArrayList<>();
        productAdapter = new HomeProductAdapters(getContext(), viewAllModelList);
        recyclerViewAll.setAdapter(productAdapter);




        popularProductsViewAllLink = view.findViewById(R.id.popularProductsViewAll);
        recomendedProductsViewAllLink = view.findViewById(R.id.recomendedProductsViewAll);
        viewAllProductsLink = view.findViewById(R.id.viewAllProducts);



        popularProductsViewAllLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navController.navigate(R.id.action_homeFragment_to_popularProductsFragment);
                Intent intent = new Intent(getContext(), ClassificationActivity.class);
                intent.putExtra("class", "popular");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        recomendedProductsViewAllLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navController.navigate(R.id.action_homeFragment_to_recomendedProductsFragment);
                Intent intent = new Intent(getContext(), ClassificationActivity.class);
                intent.putExtra("class", "recommended");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        viewAllProductsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navController.navigate(R.id.action_homeFragment_to_allProductsFragment);
                Intent intent = new Intent(getContext(), ClassificationActivity.class);
                intent.putExtra("class", "all");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });





        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                HomeCategoryModel homeCategoryModel = document.toObject(HomeCategoryModel.class);
                                homeCategoryModelList.add(homeCategoryModel);
                                homeCategoryAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(getContext(), "Error "+task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });




//        db.collection("PopularProducts")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
////                            int i = 0;
//                            for (QueryDocumentSnapshot document : task.getResult()){
//                                ProductModelV2 productModel1 = document.toObject(ProductModelV2.class);
//                                productModelList.add(productModel1);
////                                System.out.println("---------- productModel");
//                                homeProductAdapters.notifyDataSetChanged();
////                                i++;
////                                if(i>2){break;}
//                            }
//                        }else {
////                            Toast.makeText(DashBoardActivity.this, "Error "+task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });


        ShowProductsHome showProductsHome = new ShowProductsHome(getContext(), productModelList, homeProductAdapters);
        showProductsHome.ByClassification("popular");


        ShowProductsHome showProductsHome2 = new ShowProductsHome(getContext(), recomendedProductsModelList, recomendedProductsAdapters);
        showProductsHome2.ByClassification("recommended");

        ShowProductsHome showProducts = new ShowProductsHome(getContext(), viewAllModelList, productAdapter);
        showProducts.All();







//        swipe = view.findViewById(R.id.swipe);
//
//        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                HomeFragment homeFragment = new HomeFragment();
////                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
//
//                swipe.setRefreshing(false);
//
//                Toast toast = Toast.makeText(getContext(), "The page is being updated", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });




        return view;
    }
}