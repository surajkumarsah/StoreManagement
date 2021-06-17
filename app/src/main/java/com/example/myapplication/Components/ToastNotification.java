package com.example.myapplication.Components;

import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.emredavarci.noty.Noty;
import com.example.myapplication.R;

public class ToastNotification extends AppCompatActivity {

//    RelativeLayout notyLayout = (RelativeLayout) findViewById(R.id.noty_layout);

    public void notification(String s1, RelativeLayout layout) {
        Noty.init(this, s1, layout, Noty.WarningStyle.SIMPLE)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400, 400)
                .setWarningInset(0, 0, 0, 0)
                .setWarningBoxRadius(0, 0, 0, 0)
                .show();
    }
}
