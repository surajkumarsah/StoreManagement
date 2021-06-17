package com.example.myapplication.seller;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.R;

public class SellerHome_Activity extends AppCompatActivity {

    ImageView addCategory, addProduct, showCategory, showProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home_);

        init();
    }

    private void init() {
        addCategory = (ImageView) findViewById(R.id.add_category);
        addProduct = (ImageView) findViewById(R.id.add_product);
        showCategory = (ImageView) findViewById(R.id.show_category);
        showProduct = (ImageView) findViewById(R.id.show_products);


        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddCategory_Activity.class);
                startActivity(intent);
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProduct_Activity.class);
                startActivity(intent);
            }
        });

        showProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowProduct_Activity.class);
                startActivity(intent);
            }
        });
    }
}