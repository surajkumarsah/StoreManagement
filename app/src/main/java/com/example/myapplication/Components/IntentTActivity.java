package com.example.myapplication.Components;

import android.content.Context;
import android.content.Intent;

public class IntentTActivity {
    Intent intent;
    public void gotoActivity(Context context, Class transferClass){
        intent = new Intent(context, transferClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
