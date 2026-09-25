package com.meallens.meal;

import com.meallens.food.FoodItem;

import java.util.List;

public record MealPhotoResponse(
        Long mealId,
        List<FoodItem> candidateFoods,
        String message
) {
}
