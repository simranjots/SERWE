package com.serwe.serwe.Home;

/**
 * Created by simranjot on 01-09-2017.
 */

public class Menu {

    String dishName = "";

    public Menu() {


    }

    public Menu(String dishName) {
        this.dishName = dishName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}
