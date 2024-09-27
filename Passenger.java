package com.example.guesser.Model;

public class Passenger {
    private int pclass;
    private String sex; // Ensure this is compatible with how you are using it (e.g., "male" or "female")
    private double age;
    private int sibSp;
    private int parch;
    private double fare;
    private int embarked; // Add this line for the Embarked variable

    // Constructor
    public Passenger(int pclass, String sex, double age, int sibSp, int parch, double fare) {
        this.pclass = pclass;
        this.sex = sex;
        this.age = age;
        this.sibSp = sibSp;
        this.parch = parch;
        this.fare = fare;
        this.embarked = embarked; // Initialize embarked
    }

    // Getters
    public int getPclass() {
        return pclass;
    }

    public String getSex() {
        return sex;
    }

    public double getAge() {
        return age;
    }

    public int getSibSp() {
        return sibSp;
    }

    public int getParch() {
        return parch;
    }

    public double getFare() {
        return fare;
    }

    public int getEmbarked() { // Add this method
        return embarked;
    }

    // Setters (optional, if needed)
    public void setEmbarked(int embarked) {
        this.embarked = embarked;
    }
}
