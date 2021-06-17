package com.example.myapplication;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.emredavarci.noty.Noty;
import com.example.myapplication.Components.ToastNotification;
import com.example.myapplication.Model.Users;
import com.example.myapplication.admin.AdminInit;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SignUp_Activity extends AppCompatActivity {

    EditText name, mobno, email, password, cpassword;
    String sname, smobno, semail, spassword, scpassword, notyMsg;
    Button signIn, signUp;
    ImageView image;
    ProgressBar progressBar;

    RelativeLayout notyLayout;

    Users user = new Users();
    private static final int GalleryPick = 1;
    private Uri ImageUri;

    private String saveCurrentDate, saveCurrentTime;
    private String productRandomKey;
    private String downloadImageUrl;
    private StorageReference userImageRef;
    private DatabaseReference userRef;
    FirebaseAuth mAuth;

//    ToastNotification noti = new ToastNotification();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);

        init();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
            }
        });


    }

    public void init() {
        name = (EditText) findViewById(R.id.name);
        mobno = (EditText) findViewById(R.id.mobno);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        cpassword = (EditText) findViewById(R.id.cpassword);

        signIn = (Button) findViewById(R.id.signin);
        signUp = (Button) findViewById(R.id.signup);
        image = (ImageView) findViewById(R.id.image);

        notyLayout = (RelativeLayout) findViewById(R.id.noty_layout);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
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

        user.setName(name.getText().toString());
        user.setMobile(mobno.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setCpassword(cpassword.getText().toString());

        if (ImageUri == null) {
            notyMsg = "Please, Select Image.";
            notyMessage(notyMsg);
        } else if (TextUtils.isEmpty(user.getName())) {
            notyMsg = "Please, Enter name.";
            notyMessage(notyMsg);
        } else if (TextUtils.isEmpty(user.getMobile())) {
            notyMsg = "Please, Enter Mobile No.";
            notyMessage(notyMsg);
        } else if (TextUtils.isEmpty(user.getEmail())) {
            notyMsg = "Please, Enter Email Id";
            notyMessage(notyMsg);
        } else if (TextUtils.isEmpty(user.getPassword())) {
            notyMsg = "Please, Enter Password";
            notyMessage(notyMsg);
        } else if (TextUtils.isEmpty(user.getCpassword())) {
            notyMsg = "Please, Enter Confirm Password";
            notyMessage(notyMsg);
        } else if (!(user.getPassword().equals(user.getCpassword()))) {
            notyMsg = "Password Mismatch, please try again.";
            notyMessage(notyMsg);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            saveToDB();
        }

    }

    private void saveToDB() {
        userImageRef = FirebaseStorage.getInstance().getReference().child("Users");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = userImageRef.child(productRandomKey);
        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                String message = e.toString();
                Toast.makeText(SignUp_Activity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SignUp_Activity.this, "Image Uploaded Successfully.", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(SignUp_Activity.this, "got the Photo image URL Successfully.", Toast.LENGTH_SHORT).show();

                            saveStudentInfoToDatabase();
                        }
                    }
                });
            }
        });
    }


    public void saveStudentInfoToDatabase() {

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("users").child("seller");

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.setUserId(mAuth.getCurrentUser().getUid());
                            addToRT();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "User Registration Sussessfully", Toast.LENGTH_SHORT).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "Mail is Already Registered.", Toast.LENGTH_SHORT).show();

                                mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            user.setUserId(mAuth.getCurrentUser().getUid());
//                                            userRef = FirebaseDatabase.getInstance().getReference().child("Users").child("seller").child(user.getUserId());
                                            addToRT();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void addToRT() {
        userRef.child(user.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> userDetails = new HashMap<>();
                userDetails.put("name", user.getName());
                userDetails.put("mobile", user.getMobile());
                userDetails.put("email", user.getEmail());
                userDetails.put("password", user.getPassword());
                userDetails.put("image", downloadImageUrl);

                userRef.child(user.getUserId()).updateChildren(userDetails)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(SignUp_Activity.this, "Congratulation, Details has been updated Successfully.", Toast.LENGTH_SHORT).show();

                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(SignUp_Activity.this, "Network Error, Please try again after Sometime.", Toast.LENGTH_SHORT).show();
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

