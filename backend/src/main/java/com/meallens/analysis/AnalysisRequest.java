package com.meallens.analysis;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AnalysisRequest(
        @Valid UserProfile userProfile,
        @NotEmpty @Valid List<FoodIntake> foods
) {
}
