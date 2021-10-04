package com.example.myapplication.PDFReader.Model;

public class PDFDataModel {

    private String name;
    private String imgUrl;
    private String uploadDate;

    public PDFDataModel() {
    }

    public PDFDataModel(String name, String imgUrl, String uploadDate) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.uploadDate = uploadDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

}
