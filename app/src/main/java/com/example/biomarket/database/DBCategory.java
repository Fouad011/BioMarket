package com.example.biomarket.database;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.biomarket.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DBCategory {
    Context context;
    String [] categories;
    ArrayList<String> list;

    FirebaseFirestore db;

    public DBCategory(Context context) {
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    public ArrayList<String> getCategories() {


        Query query = this.db.collection("HomeCategory") ;
        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Object object = document.toObject(Object.class);
                                String strObj = object.toString().substring(1, object.toString().length()-1);
                                for (String m : strObj.split(",")){
                                    if (m.split("=")[0].equals("type")){
                                        list.add(m.split("=")[1]);
                                    }
                                }
                            }
                        }else {
                            Toast.makeText(context, "ERROR, task is not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return list;
    }





}
