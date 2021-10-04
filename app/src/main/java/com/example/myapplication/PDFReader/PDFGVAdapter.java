package com.example.myapplication.PDFReader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Model.PDFUploadModel;
import com.example.myapplication.PDFReader.Model.PDFDataModel;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
import java.util.List;

public class PDFGVAdapter extends ArrayAdapter<PDFUploadModel> {

    // constructor for our list view adapter.
    public PDFGVAdapter(@NonNull Context context, List<PDFUploadModel> uploadModelList) {
        super(context, 0, uploadModelList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.pdf_image_list, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        final PDFUploadModel dataModal = getItem(position);

        // initializing our UI components of list view item.
        TextView nameTV = listitemView.findViewById(R.id.idTVtext);
        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        nameTV.setText(dataModal.getName());

        // in below line we are using Picasso to load image
        // from URL in our Image VIew.
        Picasso.get().load(dataModal.getImageUrl()).into(courseIV);

        // below line is use to add item
        // click listener for our item of list view.
//        listitemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                // on the item click on our list view.
//                // we are displaying a toast message.
////                Toast.makeText(getContext(), "Item clicked is : " + dataModal.getName(), Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(v.getContext(), ViewPdfActivity.class);
////                intent.putExtra("imageUrl", dataModal.getImageUrl());
////                intent.putExtra("url", dataModal.getPdfUrl());
////                intent.putExtra("name", dataModal.getName());
////                startActivity(intent);
////
//            }
//        });
        return listitemView;
    }
}
