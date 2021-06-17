package com.example.myapplication.Model;

public class Users {

    private String name, mobile, email, password, cpassword, userId, image;

    public Users(String name, String mobile, String email, String password, String cpassword, String userId, String image) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.cpassword = cpassword;
        this.userId = userId;
        this.image = image;
    }

    public Users() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
