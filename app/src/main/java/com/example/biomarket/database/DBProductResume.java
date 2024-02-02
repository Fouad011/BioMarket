package com.example.biomarket.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.biomarket.adapters.LinkAdapter;
import com.example.biomarket.adapters.OrderAdapter;
import com.example.biomarket.models.LinkModel;
import com.example.biomarket.models.ProductModel;
import com.example.biomarket.models.ProductResume;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class DBProductResume {
    Context context;
    String prodId;
    int quantity;
    DatabaseReference dbRef;
    List<ProductModel> productModelList = new ArrayList<>();



    public DBProductResume() {
    }

    public DBProductResume(Context context, String prodId, int quantity) {
        this.context = context;
        this.prodId = prodId;
        this.quantity = quantity;
    }

    public void submit(){

        String userId;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        assert currentUser != null;
        userId = currentUser.getUid();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Clients");

        ref.child(userId).child("Pannier").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                int nbr = 0;
                String val;

                Map<String, ProductResume> values = new HashMap<>();

                DataSnapshot snapshot = task.getResult();
//                System.out.println("count :: " + snapshot.getChildrenCount());

                if (task.isSuccessful()) {
                    boolean add = true;
                    for (DataSnapshot childSnapshot : snapshot.getChildren()){

                        ProductResume productResume = childSnapshot.getValue(ProductResume.class);

//                        System.out.println("++ " + Integer.toString(nbr) + " " + val);
                        if(productResume.getId().equals(prodId)){
                            add = false;
                            break;
                        }
                        values.put(Integer.toString(nbr), productResume);
                        ++nbr;
                    }
                    if(add){
                        // add new ProductResume object
                        ProductResume newProductResume = new ProductResume(prodId, quantity);
                        values.put(Integer.toString(nbr), newProductResume);

                        System.out.println("values :: " + values);
                        ref.child(userId).child("Pannier").setValue(values);
                        values.clear();

                        Toast.makeText(context, "This product has been added to Pannier", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "This product is in Pannier", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }






    public void editQuantity(){
        String userId;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        assert currentUser != null;
        userId = currentUser.getUid();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Clients");

        ref.child(userId).child("Pannier").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                int nbr = 0;
                String val;

                Map<String, ProductResume> values = new HashMap<>();

                DataSnapshot snapshot = task.getResult();

                if (task.isSuccessful()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()){

                        ProductResume productResume = childSnapshot.getValue(ProductResume.class);

                        if(productResume.getId().equals(prodId)){
                            for (DataSnapshot childSnapshot1 : childSnapshot.getChildren()){
                                if(childSnapshot1.getKey().equals("quantity")){
                                    dbRef = childSnapshot1.getRef();
                                }
                            }
                            dbRef.setValue(quantity);
                        }


                    }


                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }


    public void submitOrder(Context context1 ,List<ProductResume> productResumes){

        String userId;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        assert currentUser != null;
        userId = currentUser.getUid();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference refClient = db.getReference("Clients");
        DatabaseReference refCommand = db.getReference("Commands");


        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  // Specify desired format
        String formattedDate0 = formatter.format(date);  // Output: 01-01-2024
        String [] formattedDateList = formattedDate0.split("-");
        StringBuilder formattedDate = new StringBuilder(formattedDateList[0] + formattedDateList[1] + formattedDateList[2]);  // Output: 01012024

        Random random = new Random();
        int randomNumber = random.nextInt(1000000);  // Generates a random integer between 0 and 1000 (inclusive)
        System.out.println("Random number: " + randomNumber);



//        formattedDate += " " + randomNumber;



        LocalTime time = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            time = LocalTime.now();
            formattedDate.append(time.getHour() + time.getMinute() + time.getSecond());;
        } else {
            formattedDate.append(randomNumber);
        }


        StringBuilder id = new StringBuilder(formattedDate.toString());
        id.reverse();
        id.append(random.nextInt(1000));



        Map<String, ProductResume> list = new HashMap<>();



        int i = -1;
        for (ProductResume product : productResumes){
            list.put(Integer.toString(++i), product);
        }

        refCommand.child("Admin").child(userId).child(String.valueOf(id)).setValue(list).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
//                    Toast.makeText(context1, "Succ", Toast.LENGTH_SHORT).show();
                    refClient.child(userId).child("Pannier").removeValue();

                }else {
                    Toast.makeText(context1, "Field to submit products", Toast.LENGTH_SHORT).show();
                }
            }
        });

        }




    public void getCommandData(List<LinkModel> linkList, LinkAdapter linkAdapter){


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Commands");


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String userId = user.getUid();


        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get data from snapshot
//                Object value = dataSnapshot.getValue(Object.class);  // Or any specific data type
//                Log.d("TAG", "Value is: " + value);
                System.out.println("-V->" + dataSnapshot.getValue());
                linkList.clear();
                linkAdapter.notifyDataSetChanged();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    System.out.println("-V1->" + dataSnapshot1.getKey());

                    if(Objects.equals(dataSnapshot1.getKey(), "Admin")){
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                            System.out.println("\t-V2->" + dataSnapshot2.getKey());
                            if(userId.equals(dataSnapshot2.getKey())){
                                for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()){
                                    System.out.println("\t\t-V3->" + dataSnapshot3.getKey());
                                    LinkModel linkModel = new LinkModel(dataSnapshot3.getKey());
                                    linkModel.setValidity(false);
                                    linkModel.setUserId(dataSnapshot2.getKey());
                                    linkList.add(linkModel);
                                    linkAdapter.notifyDataSetChanged();
                                }
                                break;
                            }
                        }
                    } else if (Objects.equals(dataSnapshot1.getKey(), "Deliverers")) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                            System.out.println("\t-V2->" + dataSnapshot2.getKey());
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()){
                                System.out.println("\t\t-V3->" + dataSnapshot3.getKey());
                                if(userId.equals(dataSnapshot3.getKey())){
                                    for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()){
                                        System.out.println("\t\t\t-V4->" + dataSnapshot4.getKey());
                                        LinkModel linkModel = new LinkModel(dataSnapshot4.getKey());
                                        linkModel.setDeliveryId(dataSnapshot2.getKey());
                                        linkModel.setUserId(dataSnapshot3.getKey());
                                        linkModel.setValidity(true);
                                        linkList.add(linkModel);
                                        linkAdapter.notifyDataSetChanged();
                                    }
                                    break;
                                }
                            }
                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }
    
    
    public void showCommandProducts(Context context1, LinkModel linkModelPass, List<ProductModel> productModelList, OrderAdapter orderAdapter){

        context = context1;
        this.productModelList = productModelList;


        List<ProductResume> productResumeList = new ArrayList<>();





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Commands");



        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String userId = user.getUid();


        if(linkModelPass.isValidity()){
            myRef
                    .child("Deliverers")
                    .child(linkModelPass.getDeliveryId())
                    .child(linkModelPass.getUserId())
                    .child(linkModelPass.getId())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                System.out.println(dataSnapshot);
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                                ProductResume productResume = dataSnapshot1.getValue(ProductResume.class);

//                    ProductModel productModel = new ProductModel();
//                    assert productResume != null;
//                    productModel.setID(productResume.getId());
//                    productModel.setQuantity(productResume.getQuantity());

                                productResumeList.add(productResume);
                            }

                            showProducts(productResumeList, orderAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle errors
                            Log.w("TAG", "Failed to read value.", error.toException());
                        }
                    });
        }else {
            myRef
                    .child("Admin")
                    .child(linkModelPass.getUserId())
                    .child(linkModelPass.getId())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                System.out.println(dataSnapshot);
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                                ProductResume productResume = dataSnapshot1.getValue(ProductResume.class);

//                    ProductModel productModel = new ProductModel();
//                    assert productResume != null;
//                    productModel.setID(productResume.getId());
//                    productModel.setQuantity(productResume.getQuantity());

                                productResumeList.add(productResume);
                            }

                            showProducts(productResumeList, orderAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle errors
                            Log.w("TAG", "Failed to read value.", error.toException());
                        }
                    });
        }



    }




    public void showProducts(List<ProductResume> productResumeList, OrderAdapter orderAdapter){



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("Products").document("bioMarketStore").collection("products");

        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.isComplete()){
                                for (QueryDocumentSnapshot document : task.getResult()){
                                    for (ProductResume productResume : productResumeList){
                                        if(document.getId().equals(productResume.getId())){

                                            ProductModel product = document.toObject(ProductModel.class);
                                            product.setID(productResume.getId());
                                            product.setQuantity(productResume.getQuantity());

                                            productModelList.add(product);
                                            orderAdapter.notifyDataSetChanged();

                                            System.out.print("--+ ");
                                            System.out.println(product);

                                        }
                                    }

                                }
                            }
                        }else {
                            Toast.makeText(context, "ERROR, task is not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public ProductModel addProductResumeToProductModel(ProductModel productModel, ProductResume productResume){

        productModel.setID(productResume.getId());
        productModel.setQuantity(productModel.getQuantity());

        return productModel;
    }


}
