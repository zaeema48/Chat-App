package com.example.chatapp.Model;

public class User {
    String user_id, phone_no, user_name, image_link,token;

    public User(String user_id, String phone_no, String user_name, String image_link) {
        this.user_id = user_id;
        this.phone_no = phone_no;
        this.user_name = user_name;
        this.image_link = image_link;
    }

    public User() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
