package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.ClickListener.TempleClickListener;
import com.example.myapplication.R;

public class TempleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tempName, tempShortDesc, tempDesc, tempRate, tempAddress;
    public ImageView tempImage;
    TempleClickListener templeClickListener;

    public TempleViewHolder(@NonNull View itemView) {
        super(itemView);

        tempName = (TextView) itemView.findViewById(R.id.temp_name);
        tempShortDesc = (TextView) itemView.findViewById(R.id.temp_shortDesc);
        tempImage = (ImageView) itemView.findViewById(R.id.temp_image);
    }

    public void setTempleClickListener(TempleClickListener templeClickListener){
        this.templeClickListener = templeClickListener;
    }

    @Override
    public void onClick(View v) {
        templeClickListener.onClick(v, getAdapterPosition(), false);
    }

}
