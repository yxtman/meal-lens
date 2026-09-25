package com.meallens.analysis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserProfile(
        @NotBlank String gender,
        @NotNull @Positive Integer age,
        @NotNull @Positive Double heightCm,
        @NotNull @Positive Double weightKg,
        @NotBlank String goal,
        @NotBlank String activityLevel
) {
}
