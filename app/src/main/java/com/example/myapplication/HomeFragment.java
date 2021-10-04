package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.Model.SliderItem;
import com.example.myapplication.Model.Users;
import com.example.myapplication.PDFReader.PDFMain_Activity;
import com.example.myapplication.Temple.Temple_Home_Activity;
import com.example.myapplication.Temple.Temple_View_Activity;
import com.example.myapplication.admin.Admin_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView adminBtn;
    View view;
    FirebaseAuth mAuth;
    ImageView profileImage;

    private DatabaseReference sliderImgRef;

    ImageView devImg1, devImg2, devImg3, devImg4, flagImg1, flagImg2, flagImg3, flagImg4, login;
    Button readersClub;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        adminBtn = (TextView) view.findViewById(R.id.admin_btn);
        mAuth = FirebaseAuth.getInstance();

        init(view);

        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Admin_Activity.class);
                startActivity(intent);
            }
        });

        devImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Temple_Home_Activity.class);
                startActivity(intent);
            }
        });

        devImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Temple_View_Activity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Login_Activity.class);
                startActivity(intent);
            }
        });

        readersClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PDFMain_Activity.class);
                startActivity(intent);
            }
        });

        //Code Touch
        flagImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void init(View view) {

        Users user = new Users();

        sliderImgRef = FirebaseDatabase.getInstance().getReference().child("SliderImage");

        devImg1 = (ImageView) view.findViewById(R.id.dev_img1);
        devImg2 = (ImageView) view.findViewById(R.id.dev_img2);
        devImg3 = (ImageView) view.findViewById(R.id.dev_img3);
        devImg4 = (ImageView) view.findViewById(R.id.dev_img4);

        flagImg1 = (ImageView) view.findViewById(R.id.flag_img1);
        flagImg2 = (ImageView) view.findViewById(R.id.flag_img2);
        flagImg3 = (ImageView) view.findViewById(R.id.flag_img3);
        flagImg4 = (ImageView) view.findViewById(R.id.flag_img4);

        login = (ImageView) view.findViewById(R.id.login);

        readersClub = (Button) view.findViewById(R.id.reader_club);



        if (mAuth.getCurrentUser() != null)
        {
            user.setUserId(mAuth.getCurrentUser().getUid());
            user.setEmail(mAuth.getCurrentUser().getEmail());
            user.setMobile(mAuth.getCurrentUser().getPhoneNumber());
        }

        //Image Slider
        SliderView sliderView = view.findViewById(R.id.imageSlider);

        SliderAdapterExample adapter = new SliderAdapterExample(getContext());

        SliderItem s1 = new SliderItem("Intellectual Mind App Development", R.drawable.banner1);
        SliderItem s2 = new SliderItem("Modern Soft. Solution", R.drawable.banner2);
        SliderItem s3 = new SliderItem("Contacts", R.drawable.banner3);
        SliderItem s4 = new SliderItem("Contacts", R.drawable.banner4);
//
//
//        sliderImgRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange( DataSnapshot dataSnapshot) {
//                SliderAdapterExample adapter1 = new SliderAdapterExample(getContext());
//
//                for (DataSnapshot url : dataSnapshot.getChildren()){
//                    SliderItem sI = url.getValue(SliderItem.class);
//
//                    adapter1.addItem(sI);
//                }
//            }
//
//            @Override
//            public void onCancelled( DatabaseError databaseError) {
//
//            }
//        });


        adapter.addItem(s1);
        adapter.addItem(s2);
        adapter.addItem(s3);
        adapter.addItem(s4);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();




        //Add images to development layout
        devImg1.setImageResource(R.drawable.ic_baseline_person_24);
        devImg2.setImageResource(R.drawable.ic_baseline_person_24);
        devImg3.setImageResource(R.drawable.ic_baseline_person_24);
        devImg4.setImageResource(R.drawable.ic_baseline_person_24);

        flagImg1.setImageResource(R.drawable.ic_baseline_person_24);
        flagImg2.setImageResource(R.drawable.ic_baseline_person_24);
        flagImg3.setImageResource(R.drawable.ic_baseline_person_24);
        flagImg4.setImageResource(R.drawable.ic_baseline_person_24);


    }

}
