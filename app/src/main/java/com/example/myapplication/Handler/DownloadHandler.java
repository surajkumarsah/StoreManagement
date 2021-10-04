package com.example.myapplication.Handler;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DownloadHandler {

    public boolean downloadFile(String url, String name) {
        if (isExternalStorageAvailable() || isExternalStorageReadable()) {

            DownloadManager downloadManager = (DownloadManager) getApplicationContext()
                    .getSystemService(Context.DOWNLOAD_SERVICE);

            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalFilesDir(getApplicationContext(),
                    getStorageDir(), name + ".pdf");

            downloadManager.enqueue(request);
            return true;

        }else{
            return false;
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

        public String getStorageDir() {
            //create folder
            File file = new File(Environment.getExternalStorageDirectory(), "BookStore");
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            String filePath = file.getAbsolutePath();
            return filePath;
        }

    }

