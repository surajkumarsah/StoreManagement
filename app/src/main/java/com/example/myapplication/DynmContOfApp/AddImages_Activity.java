package com.example.myapplication.DynmContOfApp;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.Handler.ImageUploadHandler;
import com.example.myapplication.Model.ImageUpload;
import com.example.myapplication.Model.Products;
import com.example.myapplication.PDFReader.Constants;
import com.example.myapplication.R;
import com.google.android.gms.tasks.*;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddImages_Activity extends AppCompatActivity {

    //these are the views
    TextView textViewStatus;
    ImageView image;
    Button uploadBtn;
    EditText editTextFilename;
    ProgressBar progressBar;
//    String downloadImageUrl, pdfUrl, saveCurrentDate, saveCurrentTime, productRandomKey;

    //the firebase objects for storage and database
    StorageReference prodImageRef;
    DatabaseReference mDatabaseReference;

    private static final int GalleryPick = 1;
    private Uri ImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_images_);

        //getting firebase objects
        prodImageRef = FirebaseStorage.getInstance().getReference().child("DynamicContent");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("DynamicContent");


        //getting the views
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextFilename = (EditText) findViewById(R.id.editTextFileName);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        image = (ImageView) findViewById(R.id.image);
        uploadBtn = (Button) findViewById(R.id.upload_pdf);


        //attaching listeners to views

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToDataBase();
            }
        });

    }

    private void AddToDataBase() {

        ImageUploadHandler imageHandler = new ImageUploadHandler();

        final ImageUpload imageUpload =  imageHandler.imageUploadHandler(ImageUri, prodImageRef, progressBar);

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> userDetails = new HashMap<>();
                userDetails.put("fileName", editTextFilename.getText());
                userDetails.put("imageUrl", imageUpload.getImageUrl());
                userDetails.put("createDate", imageUpload.getImageCreateDate());

                mDatabaseReference.updateChildren(userDetails)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Data Added Successfully.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure( Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            image.setImageURI(ImageUri);
        }
    }

}
