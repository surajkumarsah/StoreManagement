package com.example.myapplication.Handler;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.myapplication.Home_Activity;
import com.example.myapplication.Model.ImageUpload;
import com.example.myapplication.Model.Products;
import com.google.android.gms.tasks.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ImageUploadHandler {

    String saveCurrentDate, saveCurrentTime, productRandomKey, downloadImageUrl;
    ImageUpload products = new ImageUpload();

    public ImageUpload imageUploadHandler(final Uri imageUri, final StorageReference prodImageRef, final ProgressBar progressBar){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = prodImageRef.child(productRandomKey);
        final UploadTask uploadTask = filePath.putFile(imageUri);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(4000);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully.", Toast.LENGTH_SHORT).show();

                            downloadImageUrl = taskSnapshot.getTask().getResult().toString();

                            products.setImageUrl(downloadImageUrl);
                            products.setImageCreateDate(productRandomKey);
                            products.setImageStatus("true");
                        }
                    });

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();

        return products;
    }
}
