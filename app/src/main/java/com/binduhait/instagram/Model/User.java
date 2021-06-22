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
    boolean isBlocked;
    String onlineStatus;
    String typingTo;

    public User(String id, String username, String fullname, String imageurl, String bio, String gender, String website, String birthday, boolean isBlocked, String onlineStatus, String typingTo) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.imageurl = imageurl;
        this.bio = bio;
        this.gender = gender;
        this.website = website;
        this.birthday = birthday;
        this.isBlocked = isBlocked;
        this.onlineStatus = onlineStatus;
        this.typingTo = typingTo;
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

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getTypingTo() {
        return typingTo;
    }

    public void setTypingTo(String typingTo) {
        this.typingTo = typingTo;
    }


    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
