package com.example.task;

import org.json.JSONArray;

public class DataAdapter {
    private String NameArray;
    private Double PriceArray;
    private Double ChangeArray;
    private String ImageArray;


    public DataAdapter(String nameArray, Double priceArray, Double changeArray,String imageArray) {
        NameArray = nameArray;
        PriceArray = priceArray;
        ChangeArray = changeArray;
        ImageArray=imageArray;
    }

    public String getImageArray() {
        return ImageArray;
    }

    public void setImageArray(String imageArray) {
        ImageArray = imageArray;
    }

    public String getNameArray() {
        return NameArray;
    }

    public void setNameArray(String nameArray) {
        NameArray = nameArray;
    }

    public Double getPriceArray() {
        return PriceArray;
    }

    public void setPriceArray(Double priceArray) {
        PriceArray = priceArray;
    }

    public Double getChangeArray() {
        return ChangeArray;
    }

    public void setChangeArray(Double changeArray) {
        ChangeArray = changeArray;
    }
}
