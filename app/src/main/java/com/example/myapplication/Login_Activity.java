package com.example.myapplication;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.Components.IntentTActivity;
import com.example.myapplication.Components.ToastNotification;
import com.example.myapplication.Model.Users;
import com.example.myapplication.UI.OnSwipeTouchListener;
import com.example.myapplication.seller.SellerHome_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_Activity extends AppCompatActivity{

    ImageView imageView;
    TextView textView;
    int count = 0;

    EditText email, password;
    Button signIn, signUp;
    RelativeLayout notyLayout;

    FirebaseAuth mAuth;
    DatabaseReference userRef;
    ProgressBar progressBar;

//    IntentTActivity intent = new IntentTActivity();
    Intent intent;
    Users user = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_);

//        FirebaseApp.initializeApp(this);

        init();

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null)
        {
            user.setUserId(mAuth.getCurrentUser().getUid());
            Intent intent = new Intent(getApplicationContext(), SellerHome_Activity.class);
            startActivity(intent);
        }

//        userRef = FirebaseDatabase.getInstance().getReference().child("Users");


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                loginUserValidation();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), SignUp_Activity.class);
                startActivity(intent);
            }
        });
    }


//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.sign_in:
//                loginUserValidation();
//                break;
//
//            case R.id.sign_up:
////                intent.gotoActivity(getApplicationContext(), SignUp_Activity.class);
//                intent = new Intent(this, SignUp_Activity.class);
//                startActivity(intent);
//                break;
//        }
//    }

    private void loginUserValidation(){
        user.setEmail(email.getText().toString().trim());
        user.setPassword(password.getText().toString().trim());

        if (TextUtils.isEmpty(user.getEmail())) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(Login_Activity.this, "Please, Enter login ID.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(user.getPassword())) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(Login_Activity.this, "Please, Enter Password.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            userLogin();
        }
    }

    private void userLogin()
    {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    user.setUserId(mAuth.getCurrentUser().getUid());
                    intent = new Intent(getApplicationContext(), SellerHome_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        signIn = (Button) findViewById(R.id.sign_in);
        signUp = (Button) findViewById(R.id.sign_up);

        notyLayout = (RelativeLayout) findViewById(R.id.noty_layout);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });

    }

}