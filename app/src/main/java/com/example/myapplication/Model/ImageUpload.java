package com.example.myapplication.Model;

public class ImageUpload {

    String imageId, imageUrl, imageExt, imageName, imageCreateDate, imageUpdateDate, imageStatus;

    public ImageUpload() {
    }

    public ImageUpload(String imageId, String imageUrl, String imageExt, String imageName, String imageCreateDate, String imageUpdateDate, String imageStatus) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.imageExt = imageExt;
        this.imageName = imageName;
        this.imageCreateDate = imageCreateDate;
        this.imageUpdateDate = imageUpdateDate;
        this.imageStatus = imageStatus;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageExt() {
        return imageExt;
    }

    public void setImageExt(String imageExt) {
        this.imageExt = imageExt;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageCreateDate() {
        return imageCreateDate;
    }

    public void setImageCreateDate(String imageCreateDate) {
        this.imageCreateDate = imageCreateDate;
    }

    public String getImageUpdateDate() {
        return imageUpdateDate;
    }

    public void setImageUpdateDate(String imageUpdateDate) {
        this.imageUpdateDate = imageUpdateDate;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }
}
