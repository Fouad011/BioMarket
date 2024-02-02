package com.example.biomarket.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.biomarket.R;
import com.example.biomarket.adapters.HomeProductAdapters;
import com.example.biomarket.database.DBProductResume;
import com.example.biomarket.database.ShowProducts;
import com.example.biomarket.models.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {


    TextView title, description, priceLeft, priceRight, addToCartShop;
    ImageView image;

    FirebaseFirestore db;

    RecyclerView recyclerViewPopularProducts, recyclerViewPopularProducts2;
    List<ProductModel> productModelList, productModelList2;
    HomeProductAdapters productAdapters, productAdapters2;
    String prodId;

    TextView topTitle;
    String titleStr, priceStr;
    View backSpace;
    LinearLayout llOtherProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);



        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setCustomView(R.layout.custom_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);


        View customView = actionBar.getCustomView();
        topTitle = customView.findViewById(R.id.titleOfActionBar); // Assuming this is the ID of your title TextView
        backSpace = customView.findViewById(R.id.backSpace); // Assuming this is the ID of your title TextView
        topTitle.setText("Product Details");
        backSpace.setVisibility(View.GONE);


        llOtherProducts = findViewById(R.id.otherProducts);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        priceLeft = findViewById(R.id.priceLeft);
        priceRight = findViewById(R.id.priceRight);
        image = findViewById(R.id.image);

        addToCartShop = findViewById(R.id.addProductToCartShop);


        Bundle extras = getIntent().getExtras();
        if(extras != null){

            prodId = extras.getString("ID");
            titleStr = extras.getString("title");
            title.setText(titleStr);
            description.setText(extras.getString("description"));
//            priceLeft.setText(extras.getString("price"));
            priceStr = extras.getString("price");
            assert priceStr != null;
            priceLeft.setText(priceStr.split("\\.")[0]);
            priceRight.setText(priceStr.split("\\.")[1]);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);
            Glide.with(ProductDetailsActivity.this).load(extras.getString("image_url")).apply(options).into(image);

//            image.setImageBitmap(getBitmapFromURL(extras.getString("image_url")));
        }



        addToCartShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                if(user!=null){
                    DBProductResume DBProductResume = new DBProductResume(getApplicationContext(), prodId, 1);
                    DBProductResume.submit();
                }else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });









        db = FirebaseFirestore.getInstance();

        recyclerViewPopularProducts = findViewById(R.id.recycleViewProductContext);

        recyclerViewPopularProducts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));

        productModelList = new ArrayList<>();
        productAdapters = new HomeProductAdapters(getApplicationContext(), productModelList);

        recyclerViewPopularProducts.setAdapter(productAdapters);


        ShowProducts showProducts = new ShowProducts();
        if(titleStr!=null){
            llOtherProducts.setVisibility(View.VISIBLE);
            if (!priceStr.isEmpty()){
                int a = (int) (Double.parseDouble(priceStr) - 100);
                int b = (int) (Double.parseDouble(priceStr) + 100);
                showProducts.SearchSec(titleStr, a, b, getApplicationContext(), productModelList, productAdapters);
            }

        }



    }
    public static Bitmap getBitmapFromURL(String src){
        try{
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        } catch (IOException e){
            return null;
        }
    }

}