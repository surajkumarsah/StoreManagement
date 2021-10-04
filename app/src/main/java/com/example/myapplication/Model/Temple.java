package com.example.myapplication.Model;

public class Temple {

    public String tempId, tempName, tempImage, tempShortDesc, tempDesc, tempRate, tempCategory, tempAddress;

    public Temple(String tempId, String tempName, String tempImage, String tempShortDesc, String tempDesc, String tempRate,
                  String tempCategory, String tempAddress) {
        this.tempId = tempId;
        this.tempName = tempName;
        this.tempImage = tempImage;
        this.tempShortDesc = tempShortDesc;
        this.tempDesc = tempDesc;
        this.tempRate = tempRate;
        this.tempCategory = tempCategory;
        this.tempAddress = tempAddress;
    }

    public Temple() {
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getTempImage() {
        return tempImage;
    }

    public void setTempImage(String tempImage) {
        this.tempImage = tempImage;
    }

    public String getTempShortDesc() {
        return tempShortDesc;
    }

    public void setTempShortDesc(String tempShortDesc) {
        this.tempShortDesc = tempShortDesc;
    }

    public String getTempDesc() {
        return tempDesc;
    }

    public void setTempDesc(String tempDesc) {
        this.tempDesc = tempDesc;
    }

    public String getTempRate() {
        return tempRate;
    }

    public void setTempRate(String tempRate) {
        this.tempRate = tempRate;
    }

    public String getTempCategory() {
        return tempCategory;
    }

    public void setTempCategory(String tempCategory) {
        this.tempCategory = tempCategory;
    }

    public String getTempAddress() {
        return tempAddress;
    }

    public void setTempAddress(String tempAddress) {
        this.tempAddress = tempAddress;
    }
}
