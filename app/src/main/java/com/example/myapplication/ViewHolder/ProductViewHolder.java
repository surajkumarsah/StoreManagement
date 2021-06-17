package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.ClickListener.ProductClickListener;
import com.example.myapplication.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView pName, pShortDesc, pDesc, pPrice, pDiscount;
    public CircleImageView imageView;
    public ProductClickListener listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        pName = (TextView) itemView.findViewById(R.id.p_name);
        pShortDesc = (TextView) itemView.findViewById(R.id.p_shortDesc);
        pDesc = (TextView) itemView.findViewById(R.id.p_desc);
        pPrice = (TextView) itemView.findViewById(R.id.p_price);
        pDiscount = (TextView) itemView.findViewById(R.id.p_discount);
        imageView = (CircleImageView) itemView.findViewById(R.id.p_image);

    }

    @Override
    public void onClick(View v) {
        listner.onClick(v,getAdapterPosition(),false);
    }
}
