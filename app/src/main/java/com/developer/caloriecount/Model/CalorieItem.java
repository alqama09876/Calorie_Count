package com.developer.caloriecount.Model;

public class CalorieItem {
    private String id;
    private String name;
    private int weight;
    private double density;
    private int quantity;
    private double totalCalories;
    private String dateCreated;

    public CalorieItem() {
    }

    public CalorieItem(String id, String name, int weight, double density, int quantity, double totalCalories) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.density = density;
        this.quantity = quantity;
        this.totalCalories = totalCalories;
    }

    public CalorieItem(String id, String name, int weight, double density, int quantity, double totalCalories, String dateCreated) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.density = density;
        this.quantity = quantity;
        this.totalCalories = totalCalories;
        this.dateCreated = dateCreated;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public double getDensity() {
        return density;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getTotalCalories() {
        return totalCalories;
    }
}
