package com.example.myapplication.PDFReader;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.R;

public class PDFMain_Activity extends AppCompatActivity {

    Button uploadPDF, readPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_reader_);

        uploadPDF = (Button) findViewById(R.id.upload_pdf);
        readPDF = (Button) findViewById(R.id.read_pdf);

        uploadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PDFUpload_Activity.class);
                startActivity(intent);
            }
        });

        readPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PDFContainer_Activity.class);
                startActivity(intent);
            }
        });
    }
}
