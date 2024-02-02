package com.example.biomarket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biomarket.R;
import com.example.biomarket.databinding.ActivityMainBinding;
import com.example.biomarket.fragments.HomeFragment;
import com.example.biomarket.fragments.ProfileFragment;
import com.example.biomarket.fragments.SearchFragment;
import com.example.biomarket.fragments.ShopCartFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Fragment currentFragment = new HomeFragment();

    int currentId = R.id.home;
    ActivityMainBinding binding;
//    SwipeRefreshLayout swipe;
TextView topTitle;
    View backSpace;
    String accountType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null){
//            actionBar.setCustomView(R.layout.custom_actionbar);
//            actionBar.setDisplayShowCustomEnabled(true);
//
//
//            View customView = actionBar.getCustomView();
//            topTitle = customView.findViewById(R.id.titleOfActionBar); // Assuming this is the ID of your title TextView
//            backSpace = customView.findViewById(R.id.backSpace); // Assuming this is the ID of your title TextView
//            topTitle.setText("Shop");
//            backSpace.setVisibility(View.GONE);
//        }



//        Bundle extras = getIntent().getExtras();
//        if(extras != null){
//            accountType = extras.getString("accountType");
//
//            assert accountType != null;
//            if(accountType.equals("client")){
//                replaceFragment(currentFragment);
//            } else {
//                Toast.makeText(getApplicationContext(), "Authentication Successful", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), AdminDeliveryHomeActivity.class);
//                intent.putExtra("accountType", accountType);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        }else {
            replaceFragment(currentFragment);
//        }


//        else if (accountType.equals("delivery")) {
//            Toast.makeText(getApplicationContext(), "Authentication Successful", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), DeliveryHomeActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {


            int id = item.getItemId();
            if(id==R.id.home && currentId!=id){
//                topTitle.setText("Shop");
                replaceFragment(new HomeFragment());
            } else if (id==R.id.search) {
//                topTitle.setText("Search");
                replaceFragment(new SearchFragment());
            } else if (id==R.id.profile && currentId!=id) {
//                topTitle.setText("Profile");
                replaceFragment(new ProfileFragment());
            } else if (id==R.id.cart_shop) {
//                topTitle.setText("Shop Cart");
                replaceFragment(new ShopCartFragment());
            }
            currentId = id;


//            return super.onOptionsItemSelected(item);
            return true;
        });






//        swipe = findViewById(R.id.swipe);
//
//        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Toast toast = Toast.makeText(getApplicationContext(), "The page is being updated", Toast.LENGTH_LONG);
//                toast.show();
//
//                if(currentId==R.id.home){
//                    replaceFragment(new HomeFragment());
//                } else if (currentId==R.id.search) {
//                    replaceFragment(new SearchFragment());
//                } else if (currentId==R.id.profile) {
//                    replaceFragment(new ProfileFragment());
//                } else if (currentId==R.id.cart_shop) {
//                    replaceFragment(new CartShopFragment());
//                }
//
//                swipe.setRefreshing(false);
//            }
//        });


    }





    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}