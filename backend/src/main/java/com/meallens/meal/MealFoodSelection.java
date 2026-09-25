package com.meallens.meal;

import com.meallens.food.FoodItem;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class MealFoodSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MealRecord mealRecord;

    @ManyToOne(fetch = FetchType.EAGER)
    private FoodItem foodItem;

    private Double grams;

    protected MealFoodSelection() {
    }

    public MealFoodSelection(FoodItem foodItem, Double grams) {
        this.foodItem = foodItem;
        this.grams = grams;
    }

    public Long getId() {
        return id;
    }

    public MealRecord getMealRecord() {
        return mealRecord;
    }

    public void setMealRecord(MealRecord mealRecord) {
        this.mealRecord = mealRecord;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public Double getGrams() {
        return grams;
    }
}
