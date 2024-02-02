package com.example.biomarket.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.biomarket.R;
import com.example.biomarket.adapters.OrderAdapter;
import com.example.biomarket.database.DBProductResume;
import com.example.biomarket.database.DBUsersInformation;
import com.example.biomarket.models.LinkModel;
import com.example.biomarket.models.ProductModel;
import com.example.biomarket.models.ProductResume;

import java.util.ArrayList;
import java.util.List;

public class CommandDetailsActivity extends AppCompatActivity {
    TextView topTitle;
    View backSpace;

    TextView priceSubTotal, priceTotal, livPrice, tVDeliveryName, tVDeliveryPhone, tVDeliveryState;
    LinearLayout llFullName, llPhone;

    RecyclerView recyclerViewOrder;
    List<ProductModel> productModelList =  new ArrayList<>();
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_details);




        priceSubTotal = findViewById(R.id.priceSubTotal);
        priceTotal = findViewById(R.id.priceTotal);
        livPrice = findViewById(R.id.livraisonPrice);

        tVDeliveryName = findViewById(R.id.deliveryName);
        tVDeliveryPhone = findViewById(R.id.deliveryPhone);
        tVDeliveryState = findViewById(R.id.deliveryState);

        llFullName = findViewById(R.id.llFullname);
        llPhone = findViewById(R.id.llPhone);


        recyclerViewOrder = findViewById(R.id.recycleViewProductsOrder);

        productModelList = new ArrayList<>();
        orderAdapter = new OrderAdapter(getApplicationContext(), productModelList, priceSubTotal, priceTotal, 50.0);

        recyclerViewOrder.setAdapter(orderAdapter);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setCustomView(R.layout.custom_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);


        View customView = actionBar.getCustomView();
        topTitle = customView.findViewById(R.id.titleOfActionBar); // Assuming this is the ID of your title TextView
        backSpace = customView.findViewById(R.id.backSpace); // Assuming this is the ID of your title TextView
        topTitle.setText("Command Details");
        backSpace.setVisibility(View.GONE);



        Bundle extras = getIntent().getExtras();
//        ProductModel receivedObject = (ProductModel) getIntent().getSerializableExtra("object_key"); // For Serializable


        assert extras != null;
//        System.out.println("ID:: " + extras.getString("linkModel"));

        LinkModel linkModel = (LinkModel) extras.getSerializable("linkModel");
        System.out.println("Here :   "+linkModel.toString());


        DBUsersInformation deliveryInformation = new DBUsersInformation();
        deliveryInformation.showDeliveryInformation(getApplicationContext(), linkModel.getDeliveryId(), tVDeliveryName, tVDeliveryPhone, tVDeliveryState, llFullName, llPhone);


        DBProductResume dbProductResume = new DBProductResume();
//        set Information of Delivery
        dbProductResume.showCommandProducts(getApplicationContext(), linkModel, productModelList, orderAdapter);

        for (ProductModel productModel : productModelList){
            System.out.println(productModel.getID() +" "+ productModel.getQuantity());
        }


    }



}