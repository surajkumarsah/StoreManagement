package com.example.myapplication.Temple;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.R;

public class Temple_View_Activity extends AppCompatActivity {

    EditText fileName;
    Button btnFileName;
    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple__view_);

        fileName = (EditText) findViewById(R.id.fileName);
        btnFileName = (Button) findViewById(R.id.btn_fileName);
        browser = (WebView) findViewById(R.id.webview);


        btnFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browser.getSettings().setLoadsImagesAutomatically(true);
                browser.getSettings().setJavaScriptEnabled(true);
                browser.getSettings().setBuiltInZoomControls(true);

                browser.loadUrl("file:///android_asset/"+fileName.getText().toString());

            }
        });


//        browser.loadData(customHtml, "text/html", "UTF-8");

    }
}
