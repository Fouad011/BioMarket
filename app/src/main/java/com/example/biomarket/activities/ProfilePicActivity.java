package com.example.biomarket.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biomarket.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfilePicActivity extends AppCompatActivity {

    Button setImg, uploadImg;
    ImageView imageViewLoadImg, profileLink;
    RelativeLayout progressBar;
    FirebaseAuth authProfile;
    StorageReference storageReference;
    static final int PICK_IMAGE_REQUEST = 1;
    Uri uriImage;

    TextView topTitle;
    View backSpace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic);





        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setCustomView(R.layout.custom_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);


        View customView = actionBar.getCustomView();
        topTitle = customView.findViewById(R.id.titleOfActionBar); // Assuming this is the ID of your title TextView
        backSpace = customView.findViewById(R.id.backSpace); // Assuming this is the ID of your title TextView
        topTitle.setText("Upload Profile Image");
        backSpace.setVisibility(View.GONE);





//        setImg = findViewById(R.id.set_img_button);
        uploadImg = findViewById(R.id.upload_img_button);
        imageViewLoadImg = findViewById(R.id.imageView_profile_img);

//        profileLink = findViewById(R.id.imageView_profile_link);


        progressBar = findViewById(R.id.progressBar);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        // profile image
        storageReference = FirebaseStorage.getInstance().getReference("ProfileImages");

        Uri uri = firebaseUser.getPhotoUrl();

        //Set User's current DP in ImageView (if uploaded already). We will Picasso since imageViewer setImage
        //Regular URIs.
        Picasso.with(ProfilePicActivity.this).load(uri).into(imageViewLoadImg);


        //Choosing image to upload
        imageViewLoadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                UploadImg();
            }
        });

//        profileLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ProfilePicActivity.this, ProfileActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });


    }


    void UploadImg(){
        if(uriImage != null){

            //Save the image with uid of the current logged user
            StorageReference fileReference = storageReference.child(authProfile.getCurrentUser().getUid() + "." + getFileExtension(uriImage));

            //Ipload image to Storage
            fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri = uri;
                            FirebaseUser firebaseUser = authProfile.getCurrentUser();

                            //Finally set the display image of the user after upload
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(downloadUri).build();
                            firebaseUser.updateProfile(profileUpdate);
                        }
                    });

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfilePicActivity.this, "Upload Successful!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfilePicActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else{
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ProfilePicActivity.this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }

    //Obtain File Extention of the image
    String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriImage = data.getData();
            imageViewLoadImg.setImageURI(uriImage);
        }
    }
}