package com.example.myapplication.Model;

public class Products {

    private String productId;
    private String prodName, prodCategory, prodSubCat, prodShortDesc, prodDesc, status, createDate, prodImage, sellerId;
    private String prodPrice, prodDiscount;
    public Products() {
    }

    public Products(String productId, String prodName, String prodCategory, String prodSubCat, String prodShortDesc,
                    String prodDesc, String status, String createDate, String prodImage, String sellerId,
                    String prodPrice, String prodDiscount) {
        this.productId = productId;
        this.prodName = prodName;
        this.prodCategory = prodCategory;
        this.prodSubCat = prodSubCat;
        this.prodShortDesc = prodShortDesc;
        this.prodDesc = prodDesc;
        this.status = status;
        this.createDate = createDate;
        this.prodImage = prodImage;
        this.sellerId = sellerId;
        this.prodPrice = prodPrice;
        this.prodDiscount = prodDiscount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(String prodCategory) {
        this.prodCategory = prodCategory;
    }

    public String getProdSubCat() {
        return prodSubCat;
    }

    public void setProdSubCat(String prodSubCat) {
        this.prodSubCat = prodSubCat;
    }

    public String getProdShortDesc() {
        return prodShortDesc;
    }

    public void setProdShortDesc(String prodShortDesc) {
        this.prodShortDesc = prodShortDesc;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getProdImage() {
        return prodImage;
    }

    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdDiscount() {
        return prodDiscount;
    }

    public void setProdDiscount(String prodDiscount) {
        this.prodDiscount = prodDiscount;
    }
}
