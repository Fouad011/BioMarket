package com.example.biomarket.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.biomarket.R;
import com.example.biomarket.adapters.ProductAdapter;
import com.example.biomarket.database.ShowProducts;
import com.example.biomarket.models.ProductModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ClassificationActivity extends AppCompatActivity {

    FirebaseFirestore db;

    RecyclerView recyclerViewPopularProducts;
    List<ProductModel> productModelList;
    ProductAdapter productAdapter;
    TextView topTitle;
    View backSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setCustomView(R.layout.custom_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);


        View customView = actionBar.getCustomView();
        topTitle = customView.findViewById(R.id.titleOfActionBar); // Assuming this is the ID of your title TextView
        backSpace = customView.findViewById(R.id.backSpace); // Assuming this is the ID of your title TextView




        db = FirebaseFirestore.getInstance();

        recyclerViewPopularProducts = findViewById(R.id.popularProducts);

        recyclerViewPopularProducts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        productModelList = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(), productModelList);

        recyclerViewPopularProducts.setAdapter(productAdapter);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String classisication = extras.getString("class");
            ShowProducts showProducts = new ShowProducts(getApplicationContext(), productModelList, productAdapter);
            if(classisication.equals("all")){
                topTitle.setText("All Products");
                backSpace.setVisibility(View.GONE);
                showProducts.All();
            }else {
                topTitle.setText(classisication);
                backSpace.setVisibility(View.GONE);
                showProducts.ByClassification(classisication);
            }
        }



    }
}