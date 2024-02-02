package com.example.biomarket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biomarket.R;
import com.example.biomarket.models.Address;
import com.example.biomarket.models.ProductResume;
import com.example.biomarket.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextRegisterFullName, editTextRegisterEmail,
            editTextRegisterMobile, editTextRegisterPwd;
    TextView editTextRegisterDoB;

    TextView loginLink;

    RelativeLayout progressBar;
    Button registerBtn;
    FirebaseAuth mAuth;

    static final String Tag = "RegisterActivity";

    DatePickerDialog picker;

    TextView topTitle;
    View backSpace;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


//
//        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
//        actionBar.setCustomView(R.layout.custom_actionbar);
//        actionBar.setDisplayShowCustomEnabled(true);
//
//
//        View customView = actionBar.getCustomView();
//        topTitle = customView.findViewById(R.id.titleOfActionBar); // Assuming this is the ID of your title TextView
//        backSpace = customView.findViewById(R.id.backSpace); // Assuming this is the ID of your title TextView
//        topTitle.setText("Register page");
//        backSpace.setVisibility(View.GONE);





        editTextRegisterFullName = findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterDoB = findViewById(R.id.editText_register_date_of_brith_day);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);
        editTextRegisterPwd = findViewById(R.id.editText_register_password);


        loginLink = findViewById(R.id.login_text_button);

        progressBar = findViewById(R.id.progressBar);

        registerBtn = findViewById(R.id.register_button);

//        mAuth = FirebaseAuth.getInstance();


//        editTextRegisterDoB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(RegisterActivity.this, "123456789", Toast.LENGTH_SHORT).show();
//                showCalendar();
//            }
//        });





        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String fullName = editTextRegisterFullName.getText().toString().trim();
                String email = editTextRegisterEmail.getText().toString().trim();
                String mobile = editTextRegisterMobile.getText().toString().trim();
                String pwd = editTextRegisterPwd.getText().toString().trim();
                String dob = editTextRegisterDoB.getText().toString().trim();




                if(fullName.isEmpty()) {
                    editTextRegisterFullName.setError("Full name is required");
                    editTextRegisterFullName.requestFocus();
                } else if (email.isEmpty()) {
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                } else if (mobile.isEmpty()) {
                    editTextRegisterMobile.setError("Mobile no. is required");
                    editTextRegisterMobile.requestFocus();
                } else if (mobile.length() != 10) {
                    editTextRegisterMobile.setError("Mobile no. not equal 10 digits");
                    editTextRegisterMobile.requestFocus();
                } else if (pwd.isEmpty()) {
                    editTextRegisterPwd.setError("Password name is required");
                    editTextRegisterPwd.requestFocus();
                } else {
                    setUser(fullName, email,dob, mobile, pwd);
                }
            }
        });




    }



    private void setUser(String fullName, String email, String dob, String mobile, String pwd) {

        //Create User Profile

        mAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
//

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();

                    //Enter User Data into the Firebase Realtime Database. (ReadWriteUserDetails is my class)
                    UserModel writeUserModel = new UserModel(fullName, dob, null, mobile, "client", null);
//                    writeUserModel.setAddress(new Address());

                    //Extracting User reference from Database for "Registered Users"
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Clients");

                    assert firebaseUser != null;
                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                //Send Verification Email
//                                firebaseUser.sendEmailVerification();

                                Toast.makeText(RegisterActivity.this, "Reagistration Successful, Please verify your email",
                                        Toast.LENGTH_SHORT).show();

                                //Open User Profile after successful registration
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.putExtra("accountType", "client");
                                //To Prevent User from returning back to Register Activity on pressing back button after registration
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); //To close Register Activity

                            }else {

                                Toast.makeText(RegisterActivity.this, "Reagistration Field. Please try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });













                } else {
                    // If sign in fails, display a message to the user.

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwd.setError("Your password is too weak.");
                        editTextRegisterPwd.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterEmail.setError("Your email is invalid or already in use. kindly re-enter.");
                        editTextRegisterEmail.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterEmail.setError("User is already registered with this email. Use another email.");
                        editTextRegisterEmail.requestFocus();
                    } catch (Exception e) {
                        Log.e(Tag, e.getMessage());
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Reagistration Faild", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    public void showCalendar(View v){

        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        //Date Picker Dialog
        picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editTextRegisterDoB.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        }, year, month, day);

        picker.show();
    }

}