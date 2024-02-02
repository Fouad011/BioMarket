package com.example.biomarket.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biomarket.R;
import com.example.biomarket.adapters.ProductAdapter;
import com.example.biomarket.database.ShowProducts;
import com.example.biomarket.databinding.ActivityCategoryBinding;
import com.example.biomarket.models.ProductModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;
    TextView topTitle;
    View backSpace;


    FirebaseFirestore db;

    RecyclerView recyclerViewAllProducts;
    List<ProductModel> productModelList;
    ProductAdapter productAdapter;
    ShowProducts showProducts;

    ImageView imageViewFilter;
    TextView textViewMsg;
    TextInputLayout textInputLayoutSearch;
    TextInputEditText inputSearch;



    String categoryType, categoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setCustomView(R.layout.custom_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);


        View customView = actionBar.getCustomView();
        topTitle = customView.findViewById(R.id.titleOfActionBar); // Assuming this is the ID of your title TextView
        backSpace = customView.findViewById(R.id.backSpace); // Assuming this is the ID of your title TextView
        backSpace.setVisibility(View.GONE);


        imageViewFilter = findViewById(R.id.imageViewFilter);
        textViewMsg = findViewById(R.id.textViewMsg);

        textInputLayoutSearch = findViewById(R.id.textInputLayoutSearch);
        inputSearch = findViewById(R.id.inputSearch);

        imageViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewMsg.getVisibility()==View.GONE){
//                    imageViewFilter.setVisibility(View.VISIBLE);
                    textViewMsg.setVisibility(View.VISIBLE);
                    textInputLayoutSearch.setVisibility(View.GONE);
                }else {
//                    imageViewFilter.setVisibility(View.GONE);
                    textViewMsg.setVisibility(View.GONE);
                    textInputLayoutSearch.setVisibility(View.VISIBLE);
                }
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ShowProducts showProducts = new ShowProducts(getApplicationContext(), productModelList, productAdapter);
                productModelList.clear();
                showProducts.All(categoryType, s.toString());
            }
        });







        db = FirebaseFirestore.getInstance();

        recyclerViewAllProducts = binding.allProducts;

        recyclerViewAllProducts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        productModelList = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(), productModelList);

        recyclerViewAllProducts.setAdapter(productAdapter);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            categoryType = extras.getString("category_type");
            categoryName = extras.getString("category_name");
            topTitle.setText(categoryName);
            ShowProducts showProducts = new ShowProducts(getApplicationContext(), productModelList, productAdapter);
            showProducts.ByCategory(categoryType);
        }



    }
}