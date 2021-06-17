package com.example.myapplication.Model;

import android.graphics.drawable.Drawable;

public class SliderItem {

    public String description;
    public int imageUrl;

    public SliderItem(String description, int imageUrl) {
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
