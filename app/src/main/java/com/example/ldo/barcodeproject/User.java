package com.example.ldo.barcodeproject;

public class User
{
    String login;
    String password;
    boolean admin;

    public User(String p_login,String p_password)
    {
        login = p_login;
        password = p_password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
