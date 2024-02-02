package com.example.biomarket.activities;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.biomarket.R;
import com.example.biomarket.models.Address;
import com.example.biomarket.models.ProductResume;
import com.example.biomarket.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Objects;


public class ProfileInformationActivity extends AppCompatActivity {
    DatePickerDialog picker;
    RelativeLayout importantInformation;
    String important;

    TextView textViewWelcome, textViewFullName, textViewEmail, textViewDoB;
    EditText editViewGender, editViewMobile,
            editViewCountry, editViewCity, editViewZipCode, editViewStreet;
    String fullName, email, doB, gender, mobile, country, city, zipCode, street;

    Button saveBtn;
    TextView logoutBtn;

    LinearLayout saveBtnSection;

    ShapeableImageView imageViewProfileImg;
    ImageView imageViewEditDetails, imageViewSaveDetails;
    FirebaseAuth authProfile;
    FirebaseUser firebaseUser;
    DatabaseReference referenceProfile;
    RelativeLayout progressBar;
    static final int PICK_IMAGE_REQUEST = 1;
    Uri uriImage;
    int n = 0;

    NavController navController;
    ActionBar actionBar;
    TextView topTitle;
    View backSpace;
    ImageView countryImportant, cityImportant, mobileImportant, zipcodeImportant, streetImportant;
    RelativeLayout rLImportantInformation;


    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else {
            Uri uri = firebaseUser.getPhotoUrl();
//        Picasso.with(getApplicationContext()).load(uri).into(imageViewProfileImg);
            Glide.with(getApplicationContext()).load(uri).into(imageViewProfileImg);
        }
    }


    public void important(){

        boolean cond = false;

        if(editViewCountry.getText().toString().isEmpty() || editViewCountry.getText().toString().trim().equals("")){
            countryImportant.setVisibility(View.VISIBLE);
            cond = true;
        }else {
            countryImportant.setVisibility(View.INVISIBLE);
        }

        if(editViewCity.getText().toString().isEmpty() || editViewCity.getText().toString().trim().equals("")){
            cityImportant.setVisibility(View.VISIBLE);
            cond = true;
        }else {
            cityImportant.setVisibility(View.INVISIBLE);
        }


        if(editViewZipCode.getText().toString().isEmpty() || editViewZipCode.getText().toString().trim().equals("")){
            zipcodeImportant.setVisibility(View.VISIBLE);
            cond = true;
        }else {
            zipcodeImportant.setVisibility(View.INVISIBLE);
        }

        if(editViewStreet.getText().toString().isEmpty() || editViewStreet.getText().toString().trim().equals("")){
            streetImportant.setVisibility(View.VISIBLE);
            cond = true;
        }else {
            streetImportant.setVisibility(View.INVISIBLE);
        }

        if(editViewMobile.getText().toString().isEmpty() || editViewMobile.getText().toString().trim().equals("")){
            mobileImportant.setVisibility(View.VISIBLE);
            cond = true;
        }else {
            mobileImportant.setVisibility(View.INVISIBLE);
        }

        if(cond){
            rLImportantInformation.setVisibility(View.VISIBLE);

        }else {
            rLImportantInformation.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user==null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_information);



        countryImportant = findViewById(R.id.ic_country_important);
        cityImportant = findViewById(R.id.ic_city_important);
        mobileImportant = findViewById(R.id.ic_mobile_important);
        zipcodeImportant = findViewById(R.id.ic_zipcode_important);
        streetImportant = findViewById(R.id.ic_street_important);
        rLImportantInformation = findViewById(R.id.importantInformation);



        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user==null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else {




            ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.setCustomView(R.layout.custom_actionbar);
            actionBar.setDisplayShowCustomEnabled(true);


            View customView = actionBar.getCustomView();
            topTitle = customView.findViewById(R.id.titleOfActionBar); // Assuming this is the ID of your title TextView
            backSpace = customView.findViewById(R.id.backSpace); // Assuming this is the ID of your title TextView
            topTitle.setText("Profile Details");
            backSpace.setVisibility(View.GONE);




//            importantInformation = findViewById(R.id.importantInformation);
//            Bundle extras = getIntent().getExtras();
//            assert extras != null;
//            important = extras.getString("important");
//            if(Objects.equals(important, "show")){
//                importantInformation.setVisibility(View.VISIBLE);
//            }





            referenceProfile = FirebaseDatabase.getInstance().getReference("Clients");


            textViewDoB     =  findViewById(R.id.show_dob);
            editViewGender  =  findViewById(R.id.show_gender);
            editViewMobile  =  findViewById(R.id.show_mobile);
            editViewCountry =  findViewById(R.id.show_country);
            editViewCity    =  findViewById(R.id.show_city);
            editViewZipCode =  findViewById(R.id.show_zipcode);
            editViewStreet  =  findViewById(R.id.show_street);



            textViewWelcome = findViewById(R.id.textView_profile_welcome);
            textViewFullName = findViewById(R.id.textView_show_full_name);
            textViewEmail = findViewById(R.id.textView_show_email);

            saveBtn = findViewById(R.id.save_button);
            saveBtnSection = findViewById(R.id.save_button_section);
            logoutBtn = findViewById(R.id.logout);

            imageViewProfileImg = findViewById(R.id.imageView_profile_dp);
            imageViewEditDetails = findViewById(R.id.imageView_edit_link);
            imageViewSaveDetails  = findViewById(R.id.imageView_save_link);

            progressBar = findViewById(R.id.progressBar);

            authProfile = FirebaseAuth.getInstance();
            firebaseUser = authProfile.getCurrentUser();


            assert firebaseUser != null;
            showUserProfile(firebaseUser);



            imageViewProfileImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ProfilePicActivity.class);
                    startActivity(intent);
                }
            });

            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signOut();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });

//        logoutBtn.setOnClickListener(newOnClickListener() {
//            @Override
//            public void onClick(View v) {
//                authProfile.signOut();
////                Intent intent = new Intent(getContext(), MainActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////                startActivity(intent);
////                        .then(function() {
////                    // Sign-out successful.
////                }, function(error) {
////                    // An error happened.
////                });
////                navController.navigate(R.id.action_profileInformationFragment_to_loginFragment);
//
////                Intent intent = new Intent(getContext(), LoginActivity.class);
////                startActivity(intent);
//            }
//        });


            imageViewEditDetails.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    editDetails(true);
                    imageViewEditDetails.setVisibility(View.GONE);
                    imageViewSaveDetails.setVisibility(View.VISIBLE);
                    changeColor(R.color.red);
                }
            });


//


            imageViewSaveDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String iDoB, iGender, iMobile, iCountry, iCity, iZipCode, iStreet;

                    iDoB        =   textViewDoB.getText().toString().trim();
                    iGender     =   editViewGender.getText().toString().trim();
                    iMobile     =   editViewMobile.getText().toString().trim();
                    iCountry    =   editViewCountry.getText().toString().trim();
                    iCity       =   editViewCity.getText().toString().trim();
                    iZipCode    =   editViewZipCode.getText().toString().trim();
                    iStreet     =   editViewStreet.getText().toString().trim();

                    UserModel writeUserModel = new UserModel(fullName, iDoB, iGender, iMobile, "client", null);
                    Address address = new Address(iCountry, iCity, iStreet, iZipCode);
                    writeUserModel.setAddress(address);


                    if(!writeUserModel.isEmpty()){
                        progressBar.setVisibility(View.VISIBLE);
                        referenceProfile.child(firebaseUser.getUid()).setValue(writeUserModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isComplete()){
                                    editDetails(false);
                                    changeColor(R.color.black);
                                    imageViewEditDetails.setVisibility(View.VISIBLE);
                                    imageViewSaveDetails.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
//                                startActivity(getIntent());
                                    Toast.makeText(getApplicationContext(), "Saved",
                                            Toast.LENGTH_LONG).show();
                                    important();

                                }else {
                                    imageViewEditDetails.setVisibility(View.VISIBLE);
                                    imageViewSaveDetails.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Save Field. Please try again",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        editDetails(false);
                        changeColor(R.color.black);
                        imageViewEditDetails.setVisibility(View.VISIBLE);
                        imageViewSaveDetails.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "You haven't changed",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }





    }





    void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }





    private void editDetails(Boolean b){
        textViewDoB.setEnabled(b);
//        editViewGender.setEnabled(b);
        editViewMobile.setEnabled(b);
        editViewCountry.setEnabled(b);
        editViewCity.setEnabled(b);
        editViewZipCode.setEnabled(b);
        editViewStreet.setEnabled(b);
    }




//    Users coming to Profile Activity

    void showUserProfile(FirebaseUser firebaseUser){
        String userID = firebaseUser.getUid();

        //Extracting User Reference from Database for "Registered Users"
//        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Clients");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Completed
                UserModel readUserModel = snapshot.getValue(UserModel.class);
                if(readUserModel != null){
                    fullName = readUserModel.getFullName();
                    email = firebaseUser.getEmail();
                    doB = readUserModel.getDoB();
                    gender = readUserModel.getGender();
                    mobile = readUserModel.getMobile();

                    if(readUserModel.address != null){
                        country = readUserModel.address.getCountry();
                        city = readUserModel.address.getCity();
                        zipCode = readUserModel.address.getZipCode();
                        street = readUserModel.address.getStreet();
                    }else {
                        country=null; city=null; zipCode=null; street=null;
                    }



//                    textViewWelcome.setText("Welcome, "+ fullName + "!");
                    textViewWelcome.setText(fullName);
                    textViewFullName.setText(fullName);
                    textViewEmail.setText(email);


                    textViewDoB.setText(doB==null?"":doB);
                    editViewGender.setText(gender==null?"":gender);
                    editViewMobile.setText(mobile==null?"":mobile);
                    editViewCountry.setText(country==null?"":country);
                    editViewCity.setText(city==null?"":city);
                    editViewZipCode.setText(zipCode==null?"":zipCode);
                    editViewStreet.setText(street==null?"":street);



                    important();




                    //Set User DP (after user has uploaded)
                    Uri uri = firebaseUser.getPhotoUrl();



                    //ImageView setImageURI should not be used with regular URIs. So we are using Picasso
//                    Picasso.with(getApplicationContext()).load(uri).into(imageViewProfileImg);
                    Glide.with(getApplicationContext()).load(uri).into(imageViewProfileImg);


//                    contentVisibility.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
//                    contentVisibility.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Error
//                contentVisibility.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void showCalendar(View v){

        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        //Date Picker Dialog
        picker = new DatePickerDialog(ProfileInformationActivity.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                textViewDoB.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        }, year, month, day);

        picker.show();
    }


    private void changeColor(int color){
        textViewDoB.setTextColor(color);
        editViewGender.setTextColor(color);
        editViewMobile.setTextColor(color);
        editViewCountry.setTextColor(color);
        editViewCity.setTextColor(color);
        editViewZipCode.setTextColor(color);
        editViewStreet.setTextColor(color);
    }


}