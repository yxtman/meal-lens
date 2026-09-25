package com.meallens.analysis;

public record FoodReport(
        String name,
        Double grams,
        Double kcal,
        Double protein,
        Double carb,
        Double fat
) {
}
