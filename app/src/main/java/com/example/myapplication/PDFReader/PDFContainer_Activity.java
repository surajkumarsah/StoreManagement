package com.example.myapplication.PDFReader;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.PDFUploadModel;
import com.example.myapplication.PDFReader.Model.PDFDataModel;
import com.example.myapplication.R;
import com.google.firebase.database.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class PDFContainer_Activity extends AppCompatActivity {

    //the listview
    //ListView listView;

    //database reference to get imageName data
    DatabaseReference mDatabaseReference;
    GridView pdfGridView;
    ImageView downloadPDF;

    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;

    ArrayList<PDFDataModel> dataModalArrayList;
    ProgressBar progressBar;

    File myDir, fileDir;

    //list to store imageName data
    List<PDFUploadModel> uploadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_read_);

        dataModalArrayList = new ArrayList<>();
        uploadList = new ArrayList<>();
//        listView = (ListView) findViewById(R.id.listView);

        pdfGridView = (GridView) findViewById(R.id.gvlistView);
        downloadPDF = (ImageView) findViewById(R.id.downloadPDF);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        myDir = new File(Environment.getExternalStorageDirectory().toString(), "/BookStore");

        //adding a clicklistener on listview
        pdfGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                PDFUploadModel pdfFile = uploadList.get(i);

                File[] listFile = myDir.listFiles();

                fn_permission();

                if (listFile != null && listFile.length > 0 ) {

                    for (int j = 0; j < listFile.length; j++) {

                        if (listFile[i].getName().equals(pdfFile.getName()) && !myDir.mkdirs()) {
                            Intent intent = new Intent(getApplicationContext(), ViewPdfOfflineActivity.class);
                            intent.putExtra("filename", listFile[i].getName());
                            startActivity(intent);
                        } else {
                            //getting the upload
                            downloadPdf(pdfFile);

                            Intent intent = new Intent(view.getContext(), ViewPdfActivity.class);
                            intent.putExtra("imageUrl", pdfFile.getImageUrl());
                            intent.putExtra("url", pdfFile.getPdfUrl());
                            intent.putExtra("name", pdfFile.getName());
                            startActivity(intent);
                        }
                    }
                }else{
                    //getting the upload
//                    downloadPdf(pdfFile);

                    Intent intent = new Intent(view.getContext(), ViewPdfActivity.class);
                    intent.putExtra("imageUrl", pdfFile.getImageUrl());
                    intent.putExtra("url", pdfFile.getPdfUrl());
                    intent.putExtra("name", pdfFile.getName());
                    startActivity(intent);
                }

            }
        });

        //getting the database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    PDFUploadModel upload = postSnapshot.getValue(PDFUploadModel.class);
                    uploadList.add(upload);
                }

                String[] imageName = new String[uploadList.size()];
                String[] imageUrl = new String[uploadList.size()];
                for (int i = 0; i < imageName.length; i++) {
                    imageName[i] = uploadList.get(i).getName();
                    imageUrl[i] = uploadList.get(i).getImageUrl();
                }

                progressBar.setVisibility(View.INVISIBLE);

                //displaying it to list
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, imageName);
//                pdfGridView.setAdapter(adapter);

                PDFGVAdapter adapter1 = new PDFGVAdapter(getApplicationContext(), uploadList);
                pdfGridView.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void downloadPdf(PDFUploadModel pdfFile) {

        if (isExternalStorageAvailable() || isExternalStorageReadable()) {

            DownloadManager downloadManager = (DownloadManager) getApplicationContext()
                    .getSystemService(Context.DOWNLOAD_SERVICE);

            Uri uri = Uri.parse(pdfFile.getPdfUrl());
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalFilesDir(getApplicationContext(),
                    getStorageDir(pdfFile.getName()), pdfFile.getName()+".pdf");

            downloadManager.enqueue(request);

        }
    }

    //checks if external storage is available for read and write
    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    //checks if external storage is available for read
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public String getStorageDir(String fileName) {
        //create folder
        File file = new File(Environment.getExternalStorageDirectory(), "BookStore");
        if (!file.mkdirs()) {
            file.mkdirs();
        }
        String filePath = file.getAbsolutePath();
        return filePath;
    }

    private boolean fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(PDFContainer_Activity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)))
            {
            }else {
                ActivityCompat.requestPermissions(PDFContainer_Activity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            boolean_permission = true;

            return boolean_permission;
//            getfile(dir);
//            obj_adapter = new PDFAdapter(getApplicationContext(), fileList);
//            lv_pdf.setAdapter(obj_adapter);

        }
        return true;
    }

}
