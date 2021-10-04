package com.example.myapplication.Model;

public class PDFUploadModel {

    public String pdfId;
    public String name;
    public String pdfUrl;
    public String imageUrl;
    public String pdfExtension;
    public String pdfCreateDate;

    public PDFUploadModel(String name, String pdfUrl, String imageUrl) {
        this.name = name;
        this.pdfUrl = pdfUrl;
        this.imageUrl = imageUrl;
    }

    public PDFUploadModel(String pdfId, String name, String pdfUrl, String imageUrl, String pdfExtension, String pdfCreateDate) {
        this.pdfId = pdfId;
        this.name = name;
        this.pdfUrl = pdfUrl;
        this.imageUrl = imageUrl;
        this.pdfExtension = pdfExtension;
        this.pdfCreateDate = pdfCreateDate;
    }

    public String getPdfId() {
        return pdfId;
    }

    public void setPdfId(String pdfId) {
        this.pdfId = pdfId;
    }

    public String getPdfExtension() {
        return pdfExtension;
    }

    public void setPdfExtension(String pdfExtension) {
        this.pdfExtension = pdfExtension;
    }

    public String getPdfCreateDate() {
        return pdfCreateDate;
    }

    public void setPdfCreateDate(String pdfCreateDate) {
        this.pdfCreateDate = pdfCreateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PDFUploadModel() {
    }


}
