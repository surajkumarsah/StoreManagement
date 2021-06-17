package com.example.myapplication.Model;

import java.util.List;

public class Category {
    private String categoryId;
    private String categoryName, createDate, sellerId, categoryImage, categoryStatus;
    private List<SubCategory> subCategories;

    public Category() {
    }

    public Category(String categoryId, String categoryName, String createDate, String sellerId,
                    List<SubCategory> subCategories, String categoryImage, String categoryStatus) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createDate = createDate;
        this.sellerId = sellerId;
        this.subCategories = subCategories;
        this.categoryImage = categoryImage;
        this.categoryStatus = categoryStatus;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(String categoryStatus) {
        this.categoryStatus = categoryStatus;
    }
}
