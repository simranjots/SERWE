package com.serwe.serwe.Login;

/**
 * Created by simranjot on 15-06-2017.
 */

public class users {


    String name = "";
    String email = "";
    String phoneno = "";
    String password = "";
    String image = "";
    String token = "";

    public users() {

    }

    public users(String image, String name, String email, String phoneno, String password, String token) {

        this.image = image;
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.password = password;
        this.token = token;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
