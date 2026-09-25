package com.meallens.food;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private Double kcalPer100g;
    private Double proteinPer100g;
    private Double carbPer100g;
    private Double fatPer100g;
    private Double defaultGrams;

    protected FoodItem() {
    }

    public FoodItem(String name, String category, Double kcalPer100g, Double proteinPer100g,
                    Double carbPer100g, Double fatPer100g, Double defaultGrams) {
        this.name = name;
        this.category = category;
        this.kcalPer100g = kcalPer100g;
        this.proteinPer100g = proteinPer100g;
        this.carbPer100g = carbPer100g;
        this.fatPer100g = fatPer100g;
        this.defaultGrams = defaultGrams;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getKcalPer100g() {
        return kcalPer100g;
    }

    public Double getProteinPer100g() {
        return proteinPer100g;
    }

    public Double getCarbPer100g() {
        return carbPer100g;
    }

    public Double getFatPer100g() {
        return fatPer100g;
    }

    public Double getDefaultGrams() {
        return defaultGrams;
    }
}
