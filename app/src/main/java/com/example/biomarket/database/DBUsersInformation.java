package com.example.biomarket.database;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.biomarket.models.DeliveryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBUsersInformation {
    Context context;

    public void showDeliveryInformation(Context applicationContext, String deliveryId, TextView tVDeliveryName, TextView tVDeliveryPhone, TextView tVDeliveryState, LinearLayout llFullName, LinearLayout llPhone) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Deliverers");

        if(deliveryId != null){
            ref.child(deliveryId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        DeliveryModel deliveryModel = task.getResult().getValue(DeliveryModel.class);
                        deliveryModel.setId(task.getResult().getKey());
                        tVDeliveryState.setText(deliveryModel.isState()?"In the process of delivery":"Yet to be confirmed");
                        if(deliveryModel.isState()){
                            tVDeliveryName.setText(deliveryModel.getFullName());
                            tVDeliveryPhone.setText(deliveryModel.getMobile());
                            llFullName.setVisibility(View.VISIBLE);
                            llPhone.setVisibility(View.VISIBLE);
                        }
                    }else {
                        Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
