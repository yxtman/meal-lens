package com.meallens.analysis;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FoodIntake(
        @NotNull Long foodId,
        @NotNull @Positive Double grams
) {
}
