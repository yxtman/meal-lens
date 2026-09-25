package com.meallens.analysis;

import java.util.List;

public record AnalysisResponse(
        Double mealKcal,
        Double protein,
        Double carb,
        Double fat,
        Double dailyTargetKcal,
        Double remainingKcal,
        Double percentOfDay,
        List<FoodReport> foodReports,
        List<String> suggestions
) {
}
