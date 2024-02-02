package com.example.biomarket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biomarket.R;
import com.example.biomarket.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {
    EditText editTextLoginEmail, editTextLoginPwd ;
    TextView signupLink, forgotLink;

    ActivityLoginBinding binding;
    RelativeLayout progressBar;
    Button loginBtn;
    FirebaseAuth mAuth;
    static final String TAG = "LoginActivity";
    TextView topTitle;
    View backSpace;
    TextView emailAleart, passwordAleart;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
//        actionBar.setCustomView(R.layout.custom_actionbar);
//        actionBar.setDisplayShowCustomEnabled(true);
//
//
//        View customView = actionBar.getCustomView();
//        topTitle = customView.findViewById(R.id.titleOfActionBar); // Assuming this is the ID of your title TextView
//        backSpace = customView.findViewById(R.id.backSpace); // Assuming this is the ID of your title TextView
//        topTitle.setText("Login");
//        backSpace.setVisibility(View.GONE);





        editTextLoginEmail = findViewById(R.id.editText_login_email);
        editTextLoginPwd = findViewById(R.id.editText_login_password);

        progressBar = findViewById(R.id.progressBar);

        loginBtn = findViewById(R.id.login_button);

        signupLink = findViewById(R.id.signup_text_button);
        forgotLink = findViewById(R.id.forgot_text_button);


        emailAleart=findViewById(R.id.emailAleart);
        passwordAleart=findViewById(R.id.passwordAleart);


        mAuth = FirebaseAuth.getInstance();


        /*//Show Hide Password using Eye Icon
        ImageView imageViewShowHidePwd = findViewById(R.id.imageView_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.baseline_visibility_off_24);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextLoginPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //If password is visible then Hide it
                    editTextLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.baseline_visibility_off_24);
                } else{
                    editTextLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.baseline_visibility_24);
                }
            }
        });*/





        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = editTextLoginEmail.getText().toString().trim();
                String pwd = editTextLoginPwd.getText().toString().trim();

                if(email.isEmpty()){
                    passwordAleart.setText("");
                    emailAleart.setText("Email is required");
//                    editTextLoginEmail.setError("Email is required");
//                    editTextLoginEmail.requestFocus();
                } else if (pwd.isEmpty()) {
                    emailAleart.setText("");
                    passwordAleart.setText("Password is required");
//                    editTextLoginPwd.setError("Password is required");
//                    editTextLoginPwd.requestFocus();
                }else{
                    emailAleart.setText("");
                    passwordAleart.setText("");
                    loginUser(email, pwd);
                }


            }
        });

        forgotLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "You can reset your password now!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });




    }

    void loginUser(String email, String pwd){

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "signInWithEmail:success");

                            //Get instance of the current User
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userId = mAuth.getUid();


                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference dbRef = db.getReference("Clients");

                            assert userId != null;
                            dbRef.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {

                                    if(task.isSuccessful()) {
                                        if(task.isComplete()){
                                            Toast.makeText(LoginActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("accountType", "client");
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }

                                }
                            });



//                            dbRef.child("Clients").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DataSnapshot> task) {
//
//                                    if(task.isSuccessful()) {
//                                        if(task.isComplete()){
//                                            Toast.makeText(LoginActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(LoginActivity.this, AdminDeliveryHomeActivity.class);
//                                            intent.putExtra("accountType", "client");
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(intent);
//                                        }
//                                    }
//
//                                }
//                            });

                            //Check if email is verified user can access access their profile
//                            if(firebaseUser.isEmailVerified()){


                            //Open User Profile
//                            } else {
//                                firebaseUser.sendEmailVerification();
//                                mAuth.signOut();
////                                showAlertDialog();
//                            }
                        } else {
                            // If sign in fails, display a message to the user.

                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                emailAleart.setText("User does not exists or no longer valid. Please register again.");
//                                editTextLoginEmail.setError("User does not exists or no longer valid. Please register again.");
//                                editTextLoginEmail.requestFocus();

                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                emailAleart.setText("Invalid credentials. Kindly, check and re-enter.");
                                passwordAleart.setText("Invalid credentials. Kindly, check and re-enter.");
//                                editTextLoginEmail.setError("Invalid credentials. Kindly, check and re-enter.");
//                                editTextLoginEmail.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(LoginActivity.this, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });


    }

    private void showAlertDialog() {
        //Setup the Alert Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You can not login without email verification.");

        //Open Email Apps if User clicks/taps Continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //To email app in new window and within our app
                startActivity(intent);
            }
        });

        //Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        //Show the AlertDialog
        alertDialog.show();
    }

    //Chek if User is already logged in. In such case, straightway take the User to the User's profile
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            Toast.makeText(LoginActivity.this, "Already Logged In!", Toast.LENGTH_SHORT).show();

            //Start activity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish(); //Close login Activity
        }
//        else{
//            Toast.makeText(LoginActivity.this, "You can login now!", Toast.LENGTH_SHORT).show();
//        }
    }


}