package com.example.biomarket.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biomarket.activities.CommandsActivity;
import com.example.biomarket.activities.LoginActivity;
import com.example.biomarket.activities.ProfileInformationActivity;
import com.example.biomarket.R;
import com.example.biomarket.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    TextView textViewWelcome, textViewFullName, textViewEmail;
    RelativeLayout progressBar;
    String fullName, email;
    FirebaseAuth authProfile;
    FirebaseUser firebaseUser;
    DatabaseReference referenceProfile;


    LinearLayout contentVisibility, profileSection;



    RelativeLayout profileInformationsLink, yourCommandsLink;

    ImageView imageViewProfileImg;

    NavController navController;
    RelativeLayout content;
    TextView loginLink;







//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        navController = Navigation.findNavController(view);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        content = view.findViewById(R.id.content);
        loginLink = view.findViewById(R.id.login);


        referenceProfile = FirebaseDatabase.getInstance().getReference("Clients");


        textViewWelcome = view.findViewById(R.id.textView_profile_welcome);
        textViewFullName = view.findViewById(R.id.textView_show_full_name);
        textViewEmail = view.findViewById(R.id.textView_show_email);

        imageViewProfileImg = view.findViewById(R.id.imageView_profile_dp);


        progressBar = view.findViewById(R.id.progressBar);

        contentVisibility = view.findViewById(R.id.linearLayout_profile_section);


        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        profileSection = view.findViewById(R.id.profile_section);
        profileInformationsLink = view.findViewById(R.id.relativeLayout_profile_informations);
        yourCommandsLink = view.findViewById(R.id.relativeLayout_your_commands);


        progressBar.setVisibility(View.VISIBLE);

        if (firebaseUser != null) {
            loginLink.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        } else {
//            Intent intent = new Intent(getContext(), LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
            content.setVisibility(View.INVISIBLE);
            loginLink.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }


        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


//        profileSection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ProfileInformationFragment profileInformationFragment = new ProfileInformationFragment();
////                Fragment fragment = new ProfileInformationFragment();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, profileInformationFragment).commit();
//            }
//        });


        profileInformationsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getContext(), )

//                ((DataTransferInterface) getActivity()).onDataReceived("Hello from Fragment A");

//                ProfileInformationsFragment profileInformationFragment = new ProfileInformationsFragment();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, profileInformationFragment).commit();

//                navController.navigate(R.id.action_profileFragment_to_profileInformationFragment);

                Intent intent = new Intent(getContext(), ProfileInformationActivity.class);
                intent.putExtra("important", "Don't show");
                startActivity(intent);



            }
        });

        yourCommandsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ProfileInformationFragment profileInformationFragment = new ProfileInformationFragment();
//                YourCommandsFragment yourCommandsFragment = new YourCommandsFragment(profileInformationFragment);
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, yourCommandsFragment).commit();

                Intent intent = new Intent(getContext(), CommandsActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }



    void showUserProfile(FirebaseUser firebaseUser){
        String userID = firebaseUser.getUid();

        //Extracting User Reference from Database for "Registered Users"
//        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Completed
                UserModel readUserModel = snapshot.getValue(UserModel.class);
                if(readUserModel != null){
                    fullName = readUserModel.fullName;
                    email = firebaseUser.getEmail();



//                    textViewWelcome.setText("Welcome, "+ fullName + "!");
                    textViewWelcome.setText(fullName);
                    textViewEmail.setText(email);




                    //Set User DP (after user has uploaded)
                    Uri uri = firebaseUser.getPhotoUrl();



                    //ImageView setImageURI should not be used with regular URIs. So we are using Picasso
                    Picasso.with(getContext()).load(uri).into(imageViewProfileImg);


//                    contentVisibility.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
//                    contentVisibility.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Error
//                contentVisibility.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }


}