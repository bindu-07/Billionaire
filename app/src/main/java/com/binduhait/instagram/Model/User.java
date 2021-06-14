package com.binduhait.instagram.Model;

public class User {

    private String id;
    private String username;
    private String fullname;
    private String imageurl;
    private String bio;
    private String gender;
    private  String website;
    private String birthday;

    public User(String id, String username, String fullname, String imageurl, String bio, String gender, String website, String birthday) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.imageurl = imageurl;
        this.bio = bio;
        this.gender = gender;
        this.website = website;
        this.birthday = birthday;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getwebsite() { return website; }

    public  void setwebsite(String website) {
        this.website = website;
    }

    public String getBirthday() { return birthday; }

    public  void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
