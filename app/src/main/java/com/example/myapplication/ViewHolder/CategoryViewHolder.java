package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.ClickListener.CategoryClickListener;
import com.example.myapplication.ClickListener.UserClickListener;
import com.example.myapplication.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView cName;
    public CircleImageView imageView;
    public Switch status;
    public CategoryClickListener listner;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        cName = (TextView) itemView.findViewById(R.id.c_name);
        imageView = (CircleImageView) itemView.findViewById(R.id.c_image);
        status = (Switch) itemView.findViewById(R.id.status);

    }

    @Override
    public void onClick(View v) {
        listner.onClick(v,getAdapterPosition(),false);
    }
}
