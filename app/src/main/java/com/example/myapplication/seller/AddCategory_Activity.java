package com.example.myapplication.seller;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.emredavarci.noty.Noty;
import com.example.myapplication.Model.Category;
import com.example.myapplication.Model.Products;
import com.example.myapplication.R;
import com.example.myapplication.SignUp_Activity;
import com.example.myapplication.ViewHolder.CategoryViewHolder;
import com.example.myapplication.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.*;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddCategory_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    EditText prodCat;
    Button addCategory;
    ImageView image;

    Category cate = new Category();

    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String saveCurrentDate, saveCurrentTime;
    private String productRandomKey;
    private String downloadImageUrl;

    private StorageReference catImageRef;
    private DatabaseReference catRef;
    FirebaseAuth mAuth;

    String notyMsg;
    RelativeLayout notyLayout;
    ProgressBar progressBar;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_);

        init();
    }

    private void init() {
        prodCat = (EditText) findViewById(R.id.prod_category);
        addCategory = (Button) findViewById(R.id.add_category_btn);
        notyLayout = (RelativeLayout) findViewById(R.id.noty_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        image = (ImageView) findViewById(R.id.image);


        mAuth = FirebaseAuth.getInstance();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });


        recyclerView = findViewById(R.id.category_recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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

        cate.setCategoryName(prodCat.getText().toString());

        if (ImageUri == null) {
            notyMsg = "Please, Select Image.";
            notyMessage(notyMsg);
        } else if (TextUtils.isEmpty(cate.getCategoryName())) {
            notyMsg = "Please, Enter Category name.";
            notyMessage(notyMsg);
        }else{
            saveToDB();
        }
    }


    private void saveToDB() {
        catImageRef = FirebaseStorage.getInstance().getReference().child(cate.getSellerId()).child("category");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = catImageRef.child(productRandomKey);
        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                String message = e.toString();
                notyMessage("Error:"+message);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddCategory_Activity.this, "Image Uploaded Successfully.", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(AddCategory_Activity.this, "got the Photo image URL Successfully.", Toast.LENGTH_SHORT).show();

                            addToRT();
                        }
                    }
                });
            }
        });
    }


    private void addToRT() {
        catRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> userDetails = new HashMap<>();
                userDetails.put("categoryName", cate.getCategoryName());
                userDetails.put("categoryId", productRandomKey);
                userDetails.put("sellerId", cate.getSellerId());
                userDetails.put("categoryImage", downloadImageUrl);
                userDetails.put("categoryStatus", "active");

                catRef.child(productRandomKey).updateChildren(userDetails)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(AddCategory_Activity.this, "Congratulation, Details has been updated Successfully.", Toast.LENGTH_SHORT).show();

                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(AddCategory_Activity.this, "Network Error, Please try again after Sometime.", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            cate.setSellerId(mAuth.getCurrentUser().getUid());
        }
        catRef = FirebaseDatabase.getInstance().getReference().child("category").child(cate.getSellerId());


        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>().setQuery(catRef,Category.class).build();

        FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i, @NonNull final Category category)
            {
                categoryViewHolder.cName.setText(category.getCategoryName());
                Picasso.get().load(category.getCategoryImage()).into(categoryViewHolder.imageView);
                categoryViewHolder.status.setChecked(true);

                if(category.getCategoryStatus() == "active"){
                    categoryViewHolder.status.setChecked(true);
                }else{
                    categoryViewHolder.status.setChecked(false);
                }

                categoryViewHolder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        catRef.child(category.getCategoryId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                HashMap<String, Object> catDetails = new HashMap<>();

                                if (category.getCategoryStatus() == "active"){
                                    category.setCategoryStatus("inActive");
                                    catDetails.put("categoryStatus", "inActive");
                                }else{
                                    category.setCategoryStatus("active");
                                    catDetails.put("categoryStatus", "active");
                                }

                                catRef.child(category.getCategoryId()).updateChildren(catDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            notyMessage("Category: "+category.getCategoryStatus());
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_category_layout,parent,false);
                CategoryViewHolder holder = new CategoryViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}