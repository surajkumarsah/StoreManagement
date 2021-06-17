package com.example.myapplication.seller;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.emredavarci.noty.Noty;
import com.example.myapplication.Model.Products;
import com.example.myapplication.R;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProduct_Activity extends AppCompatActivity {

    EditText pName, shortDesc, desc;
    String notyMsg;
    Button addProd, signUp;
    ImageView image;
    ProgressBar progressBar;

    RelativeLayout notyLayout;

    Products product = new Products();
    private static final int GalleryPick = 1;
    private Uri ImageUri;

    private String saveCurrentDate, saveCurrentTime, prodCategory;
    private String productRandomKey;
    private String downloadImageUrl;
    private StorageReference prodImageRef;
    private DatabaseReference prodRef;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_);

        init();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });


    }

    public void init() {
        pName = (EditText) findViewById(R.id.p_name);
        shortDesc = (EditText) findViewById(R.id.p_shortDesc);
        desc = (EditText) findViewById(R.id.p_desc);

        addProd = (Button) findViewById(R.id.add_product_btn);
        image = (ImageView) findViewById(R.id.image);

        notyLayout = (RelativeLayout) findViewById(R.id.noty_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        prodRef = FirebaseDatabase.getInstance().getReference();

        prodCategory = "New";

        if (mAuth.getCurrentUser() != null){
            product.setSellerId(mAuth.getCurrentUser().getUid());
        }

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

    private void validateForm() {

        product.setProdName(pName.getText().toString());
        product.setProdShortDesc(shortDesc.getText().toString());
        product.setProdDesc(desc.getText().toString());

        if (ImageUri == null) {
            notyMsg = "Please, Select Image.";
            notyMessage(notyMsg);
        } else if (TextUtils.isEmpty(product.getProdName())) {
            notyMsg = "Please, Enter Product name.";
            notyMessage(notyMsg);
        } else if (TextUtils.isEmpty(product.getProdShortDesc())) {
            notyMsg = "Please, Enter Short Desc";
            notyMessage(notyMsg);
        } else if (TextUtils.isEmpty(product.getProdDesc())) {
            notyMsg = "Please, Enter Description";
            notyMessage(notyMsg);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            saveToDB();
        }

    }

    private void saveToDB() {
        prodImageRef = FirebaseStorage.getInstance().getReference().child(product.getSellerId()).child("product");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = prodImageRef.child(productRandomKey);
        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                String message = e.toString();

                notyMessage("Error: "+message);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                notyMessage("Image Uploaded Successfully.");

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            downloadImageUrl = task.getResult().toString();

                            notyMessage("got the Photo image URL Successfully.");
                            addToRT();
                        }
                    }
                });
            }
        });
    }


    private void addToRT() {
        prodRef.child("products").child(product.getSellerId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> userDetails = new HashMap<>();
                userDetails.put("prodName", product.getProdName());
                userDetails.put("prodShortDesc", product.getProdShortDesc());
                userDetails.put("prodDesc", product.getProdDesc());
                userDetails.put("createDate", productRandomKey);
                userDetails.put("category", prodCategory);
                userDetails.put("prodImage", downloadImageUrl);
                userDetails.put("status", "active");

                prodRef.child("products").child(product.getSellerId()).child(productRandomKey).updateChildren(userDetails)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    notyMessage("Congratulation, Details has been updated Successfully.");

                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    notyMessage("Network Error, Please try again after Sometime.");
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void notyMessage(String msg){
        Noty.init(this, msg, notyLayout, Noty.WarningStyle.SIMPLE)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 200, 200)
                .setWarningInset(0, 0, 0, 0)
                .setWarningBoxRadius(0, 0, 0, 0)
                .show();
    }
}

