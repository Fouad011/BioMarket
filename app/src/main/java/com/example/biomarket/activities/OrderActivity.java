package com.example.biomarket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biomarket.R;
import com.example.biomarket.adapters.OrderAdapter;
import com.example.biomarket.database.DBProductResume;
import com.example.biomarket.models.DeliveryModel;
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

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    TextView topTitle;
    View backSpace;

    TextView priceSubTotal, priceTotal, livPrice, submitLink;

    RecyclerView recyclerViewOrder;
    List<ProductModel> productModelList =  new ArrayList<>();
    OrderAdapter orderAdapter;
    boolean cond;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);




        priceSubTotal = findViewById(R.id.priceSubTotal);
        priceTotal = findViewById(R.id.priceTotal);
        livPrice = findViewById(R.id.livraisonPrice);
        submitLink = findViewById(R.id.submitLink);


        recyclerViewOrder = findViewById(R.id.recycleViewProductsOrder);

//        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));

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
        topTitle.setText("Order Page");
        backSpace.setVisibility(View.GONE);



        Bundle extras = getIntent().getExtras();

//        ProductModel receivedObject = (ProductModel) getIntent().getSerializableExtra("object_key"); // For Serializable


        if(extras != null){
            int size = (int) extras.get("size");
            System.out.println(size);


            String product_cle;

            for (int  i=0; i<size; ++i){
                product_cle = "product_" + i;
//                System.out.println(product_cle);
                ProductModel product = (ProductModel) extras.getSerializable(product_cle);
                System.out.println(product.getID());
                productModelList.add(product);
                orderAdapter.notifyDataSetChanged();
            }

        }





        submitLink.setOnClickListener(v -> testSubmit());
    }


    private void submit() {

        List<ProductResume> productResumes = new ArrayList<>();
        ProductResume productResume = new ProductResume();

        productResumes = productResume.toProductResume(productModelList);

        DBProductResume DBProductResume = new DBProductResume();
        DBProductResume.submitOrder(getApplicationContext(), productResumes);

        Intent intent = new Intent(getApplicationContext(), CommandsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    private void testSubmit(){
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
                        Intent intent = new Intent(getApplicationContext(), ProfileInformationActivity.class);
                        intent.putExtra("info", "Address");
                        startActivity(intent);
                        finish();
                    }else {
                        System.out.println("SUBMIT");
                        submit();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
