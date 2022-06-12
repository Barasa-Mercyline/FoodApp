package com.example.foodapp;

public class Food {

    public Food() {
    }

    private String foodName, foodPrice, foodQty, imageUrl;

    public Food(String foodName, String foodPrice, String foodQty, String imageUrl) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodQty = foodQty;
        this.imageUrl = imageUrl;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodQty() {
        return foodQty;
    }

    public void setFoodQty(String foodQty) {
        this.foodQty = foodQty;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodName='" + foodName + '\'' +
                ", foodPrice='" + foodPrice + '\'' +
                ", foodQty='" + foodQty + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
