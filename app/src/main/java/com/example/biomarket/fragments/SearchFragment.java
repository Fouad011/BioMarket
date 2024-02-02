package com.example.biomarket.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.biomarket.R;
import com.example.biomarket.adapters.ProductAdapter;
import com.example.biomarket.database.DBCategory;
import com.example.biomarket.database.ShowProducts;
import com.example.biomarket.models.ProductModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {


    FirebaseFirestore db;

    RecyclerView recyclerViewAllProducts;
    List<ProductModel> productModelList;
    ProductAdapter productAdapter;
    ShowProducts showProducts;

    ImageView imageViewFilter;
    LinearLayout linearLayoutForFilter, linearLayoutSearchSection, llFilter;
    int filterNbr = 0, dyN=0, dyP=0, dyi;
    TextView textViewSearchLink, textViewMsg, textViewAlert;
    TextInputLayout textInputLayoutSearch;
    EditText inputSearch, inputMinPrice, inputMaxPrice;

    ScrollView scrollView;







//    // update title in Activity from fragment
//    public interface OnTitleChangeListener {
//        void onTitleChanged(String newTitle, int newValue);
//    }
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof PopularProductsFragment.OnTitleChangeListener) {
//            titleChangeListener = (PopularProductsFragment.OnTitleChangeListener) context;
//        } else {
//            throw new RuntimeException("Activity must implement OnTitleChangeListener");
//        }
//    }
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        titleChangeListener = null;
//    }
//
//    private PopularProductsFragment.OnTitleChangeListener titleChangeListener;
//
//    private void updateTitle(String newTitle, int newValue){
//        if (titleChangeListener != null) {
//            titleChangeListener.onTitleChanged(newTitle, newValue);
//        }
//    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);


//        updateTitle("All Products", View.VISIBLE);





        db = FirebaseFirestore.getInstance();

        recyclerViewAllProducts = view.findViewById(R.id.allProducts);

        recyclerViewAllProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));

        productModelList = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(), productModelList);

        recyclerViewAllProducts.setAdapter(productAdapter);


        linearLayoutSearchSection = view.findViewById(R.id.llSearchSection);
        llFilter = view.findViewById(R.id.llFilter);
        imageViewFilter = view.findViewById(R.id.imageViewFilter);


//        imageViewFilter = view.findViewById(R.id.imageViewFilter);
        linearLayoutForFilter = view.findViewById(R.id.linearLayoutForFilter);
        textViewSearchLink = view.findViewById(R.id.textViewSearch);
//        textViewMsg = view.findViewById(R.id.textViewMsg);
        inputSearch= view.findViewById(R.id.inputSearch);
        inputMinPrice = view.findViewById(R.id.inputMinPrice);
        inputMaxPrice = view.findViewById(R.id.inputMaxPrice);
        textViewAlert = view.findViewById(R.id.textViewAlert);
//        scrollView = view.findViewById(R.id.scrollView);




//        recyclerViewAllProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//
//                if (dy>100){
//                    linearLayoutSearchSection.setVisibility(View.GONE);
//                }
//                if (dy<-200){
//                    linearLayoutSearchSection.setVisibility(View.VISIBLE);
//                    dy=0;
//                }
//
//                System.out.println(dy);
//            }
//        });





        inputMaxPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(s.toString());
                String minPrice = inputMinPrice.getText().toString();
                if(!minPrice.equals("")){
                    if(Integer.parseInt(minPrice)>Integer.parseInt(s.toString())){
                        textViewAlert.setVisibility(View.VISIBLE);
                        String aleart = "Enter a value that is equal to or greater than ";
                        aleart = aleart + minPrice;
                        textViewAlert.setText(aleart);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String minPrice = inputMinPrice.getText().toString();
                if(!minPrice.equals("")){
                    if(Integer.parseInt(minPrice)<=Integer.parseInt(s.toString())){
                        textViewAlert.setVisibility(View.GONE);
                    }
                }

            }
        });



        imageViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutSearchSection.setVisibility(View.VISIBLE);
                llFilter.setVisibility(View.GONE);
            }
        });



        textViewSearchLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayoutSearchSection.setVisibility(View.GONE);
                llFilter.setVisibility(View.VISIBLE);



                int minPrice;
                int maxPrice;
                String title;

//                if(inputMinPrice.getText().toString().equals("")){
//                    minPrice = 0;
//                }else{
                String minPriceStr = inputMinPrice.getText().toString();
                if(minPriceStr.equals("")){
                    minPrice = -1;
                }else {
                    minPrice = Integer.parseInt(minPriceStr);
                }


                String maxPriceStr = inputMaxPrice.getText().toString();
                if(maxPriceStr.equals("")){
                    maxPrice = -1;
                }else {
                    maxPrice = Integer.parseInt(maxPriceStr);
                }


                title = inputSearch.getText().toString();

                if(title.equals("")){
                    title = null;
                }

                showProducts.Search(title, minPrice, maxPrice, getContext(), productModelList, productAdapter);

            }
        });


        showProducts = new ShowProducts(getContext(), productModelList, productAdapter);
        showProducts.All();






        return view;
    }
}