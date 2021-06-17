package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.ClickListener.UserClickListener;
import com.example.myapplication.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView sName, sMobile, sEmail, deactivate;
    public CircleImageView imageView;
    public UserClickListener listner;


    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        sName = (TextView) itemView.findViewById(R.id.s_name);
        sMobile = (TextView) itemView.findViewById(R.id.s_mob);
        sEmail = (TextView) itemView.findViewById(R.id.s_email);
        deactivate = (TextView) itemView.findViewById(R.id.s_deac);

        imageView = (CircleImageView) itemView.findViewById(R.id.p_image);
    }

    public void setUserClickListener(UserClickListener listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v)
    {
        listner.onClick(v,getAdapterPosition(),false);
    }
}
