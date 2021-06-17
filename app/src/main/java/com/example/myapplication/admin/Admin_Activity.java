package com.example.myapplication.admin;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.emredavarci.noty.Noty;
import com.example.myapplication.Components.IntentTActivity;
import com.example.myapplication.Customer.CustomerHome_Activity;
import com.example.myapplication.Customer.ShowDetails_Activity;
import com.example.myapplication.Home_Activity;
import com.example.myapplication.Login_Activity;
import com.example.myapplication.R;

public class Admin_Activity extends AppCompatActivity {

    ImageView addSeller, showSeller, custHome, custDet;
    Intent intent = new Intent();
    RelativeLayout notyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);

        notyLayout = (RelativeLayout) findViewById(R.id.noti_layout);

        init();
        notyLayout.setVisibility(View.VISIBLE);
        Noty.init(this, "hello", notyLayout, Noty.WarningStyle.ACTION)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400, 200)
                .setWarningInset(0, 0, 0, 0)
                .setWarningBoxRadius(0, 0, 0, 0)
                .show();
    }

    public void init() {
        addSeller = (ImageView) findViewById(R.id.add_seller);
        showSeller = (ImageView) findViewById(R.id.show_seller);
        custHome = (ImageView) findViewById(R.id.custHome);
        custDet = (ImageView) findViewById(R.id.custDes);


        addSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
            }
        });

        showSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), Show_Seller_Activity.class);
                startActivity(intent);
            }
        });

        custHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), CustomerHome_Activity.class);
                startActivity(intent);
            }
        });

        custDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ShowDetails_Activity.class);
                startActivity(intent);
            }
        });
    }
}