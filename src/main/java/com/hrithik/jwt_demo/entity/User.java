package com.hrithik.jwt_demo.entity;

public class User {

    private Long id;
    private String emailId;
    private String password;
    private String userType;

    public User() {
    }

    public User(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public User(String emailId, String password, String userType) {
        this.emailId = emailId;
        this.password = password;
        this.userType = userType;
    }

    public User(Long id, String userType, String emailId, String password) {
        this.id = id;
        this.userType = userType;
        this.emailId = emailId;
        this.password = password;
    }

    public User(Long id, String userType) {
        this.id = id;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
