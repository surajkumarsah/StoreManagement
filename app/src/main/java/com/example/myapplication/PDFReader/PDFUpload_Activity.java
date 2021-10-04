package com.example.myapplication.PDFReader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.example.myapplication.Handler.ImageUploadHandler;
import com.example.myapplication.Model.ImageUpload;
import com.example.myapplication.Model.PDFUploadModel;
import com.example.myapplication.R;
import com.google.android.gms.tasks.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PDFUpload_Activity extends AppCompatActivity implements View.OnClickListener {

    final static int PICK_PDF_CODE = 1;

    //these are the views
    TextView textViewStatus;
    ImageView image;
    ImageView uploadPDF;
    EditText editTextFilename;
    ProgressBar progressBar;
    Boolean selectImage = false;
    String downloadImageUrl, pdfUrl;

    //the firebase objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private Uri pdfUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_upload_);

        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //getting the views
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextFilename = (EditText) findViewById(R.id.editTextFileName);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        image = (ImageView) findViewById(R.id.image);
        uploadPDF = (ImageView) findViewById(R.id.upload);


        //attaching listeners to views
        findViewById(R.id.buttonUploadFile).setOnClickListener(this);
        findViewById(R.id.textViewUploads).setOnClickListener(this);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                selectImage = true;
            }
        });

        uploadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
    }


    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected, pdf data
            if (data.getData() != null) {
                if (!selectImage) {
                    //uploading the file
//                    uploadFile(data.getData());
                    pdfUri = data.getData();
                } else {
                    //Image data
                    selectImage = false;
                    ImageUri = data.getData();
                    image.setImageURI(ImageUri);
                }
            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //this method is uploading the file
    //the code is same as the previous tutorial
    //so we are not explaining it
    private void uploadFile() {
        progressBar.setVisibility(View.VISIBLE);
        addImageToStore();
    }

    private void addImageToStore() {

        final StorageReference prodImageRef = FirebaseStorage.getInstance().getReference().child("bookImage");
        final UploadTask uploadTask = prodImageRef.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                String message = e.toString();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                //media player for cancel
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.error);
                mediaPlayer.start();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Image Uploaded successfully", Toast.LENGTH_LONG).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = prodImageRef.getDownloadUrl().toString();
                        return prodImageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            downloadImageUrl = task.getResult().toString();

//                            notyMessage("got the Photo image URL Successfully.");
//                            Add PDF To Store
                            addPDFToStore();
                        }
                    }
                });
            }
        });


    }

    private void addPDFToStore() {

        StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + editTextFilename.getText().toString()
                +"_" +System.currentTimeMillis() + ".pdf");
        sRef.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                pdfUrl = uri.toString();
                                progressBar.setVisibility(View.GONE);
//                                add data to database
                                addToRT();
                                //media player for done
                                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.done);
                                mediaPlayer.start();

                                textViewStatus.setText("File Uploaded Successfully");
                                uploadPDF.setImageResource(R.drawable.done_complete1);

//                        PDFUploadModel upload = new PDFUploadModel(editTextFilename.getText().toString(), pdfUrl, downloadImageUrl);
//                        mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        //media player for cancel
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.error);
                        mediaPlayer.start();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });

    }

    private void addToRT() {
        PDFUploadModel upload = new PDFUploadModel(editTextFilename.getText().toString(), pdfUrl, downloadImageUrl);
        mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);// Important Action
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonUploadFile:
                getPDF();
                break;
            case R.id.textViewUploads:
                startActivity(new Intent(this, PDFContainer_Activity.class));
                break;
        }
    }



}
