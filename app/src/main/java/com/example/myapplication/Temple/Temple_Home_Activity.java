package com.example.myapplication.Temple;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Model.Products;
import com.example.myapplication.Model.Temple;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;
import com.example.myapplication.ViewHolder.ProductViewHolder;
import com.example.myapplication.ViewHolder.TempleViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Temple_Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference tempRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth mAuth;
    Users user = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple__home_);

        init();
    }

    private void init() {

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null)
        {
            user.setUserId(mAuth.getCurrentUser().getUid());
        }

        tempRef = FirebaseDatabase.getInstance().getReference().child("Temple");

        recyclerView = findViewById(R.id.temp_list_show);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Temple> options = new FirebaseRecyclerOptions.Builder<Temple>().setQuery(tempRef,Temple.class).build();

        FirebaseRecyclerAdapter<Temple, TempleViewHolder> adapter = new FirebaseRecyclerAdapter<Temple, TempleViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TempleViewHolder templeViewHolder, int i, @NonNull Temple temple)
            {
                templeViewHolder.tempName.setText(temple.getTempName());
                templeViewHolder.tempShortDesc.setText(temple.getTempDesc());

                Picasso.get().load(temple.getTempImage()).into(templeViewHolder.tempImage);
            }

            @NonNull
            @Override
            public TempleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temple_show_list,parent,false);
                TempleViewHolder holder = new TempleViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
