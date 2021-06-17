package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.myapplication.admin.Admin_Activity;
import com.google.firebase.FirebaseApp;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class Home_Activity extends AppCompatActivity implements View.OnClickListener {

    private DuoDrawerLayout drawerLayout;
    public TextView adminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

//        elementAction();

        init();

    }

    private void elementAction(){
        adminBtn = (TextView) findViewById(R.id.admin_btn);
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin_Activity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        View contentView = drawerLayout.getContentView();
        View menuView = drawerLayout.getMenuView();

        LinearLayout ll_Home = menuView.findViewById(R.id.ll_Home);
        LinearLayout ll_Profile = menuView.findViewById(R.id.ll_Profile);
        LinearLayout ll_Setting = menuView.findViewById(R.id.ll_Setting);
        LinearLayout ll_Share = menuView.findViewById(R.id.ll_Share);
        LinearLayout ll_Login = menuView.findViewById(R.id.ll_Login);
        LinearLayout ll_Logout = menuView.findViewById(R.id.ll_Logout);


        ll_Home.setOnClickListener(this);
        ll_Profile.setOnClickListener(this);
        ll_Setting.setOnClickListener(this);
        ll_Share.setOnClickListener(this);
        ll_Login.setOnClickListener(this);
        ll_Logout.setOnClickListener(this);


        replace(new HomeFragment());


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_Home:
                replace(new HomeFragment(),"Home");
                break;

            case R.id.ll_Profile:
                replace(new ProfileFragment(),"Profile");
                break;

            case R.id.ll_Setting:
                replace(new SettingFragment(),"Setting");
                break;

            case R.id.ll_Share:
                Toast.makeText(this, "Share...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_Login:
                Intent intent = new Intent(this, Login_Activity.class);
                startActivity(intent);
                break;

            case R.id.ll_Logout:
                Toast.makeText(this, "Logout...", Toast.LENGTH_SHORT).show();
                break;

        }
        drawerLayout.closeDrawer();
    }

    private void replace(Fragment fragment, String s) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.addToBackStack(s);
        transaction.commit();
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
}