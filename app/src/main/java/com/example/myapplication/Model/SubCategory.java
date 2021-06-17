package com.example.myapplication.Model;

public class SubCategory {

    private long subCatId;
    private String subCatName, createDate, sellerId;

    public SubCategory() {
    }

    public SubCategory(long subCatId, String subCatName, String createDate, String sellerId) {
        this.subCatId = subCatId;
        this.subCatName = subCatName;
        this.createDate = createDate;
        this.sellerId = sellerId;
    }

    public long getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(long subCatId) {
        this.subCatId = subCatId;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
