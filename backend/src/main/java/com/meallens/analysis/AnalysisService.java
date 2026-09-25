package com.meallens.analysis;

import com.meallens.food.FoodItem;
import com.meallens.food.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalysisService {

    private final FoodItemRepository repository;

    public AnalysisService(FoodItemRepository repository) {
        this.repository = repository;
    }

    public AnalysisResponse analyze(AnalysisRequest request) {
        UserProfile profile = request.userProfile();
        double bmr = calculateBmr(profile);
        double dailyTargetKcal = calculateDailyTarget(bmr, profile.activityLevel(), profile.goal());

        List<FoodReport> foodReports = new ArrayList<>();
        double mealKcal = 0;
        double protein = 0;
        double carb = 0;
        double fat = 0;

        for (FoodIntake intake : request.foods()) {
            FoodItem food = repository.findById(intake.foodId())
                    .orElseThrow(() -> new IllegalArgumentException("食物不存在：" + intake.foodId()));
            double ratio = intake.grams() / 100.0;
            double foodKcal = food.getKcalPer100g() * ratio;
            double foodProtein = food.getProteinPer100g() * ratio;
            double foodCarb = food.getCarbPer100g() * ratio;
            double foodFat = food.getFatPer100g() * ratio;

            mealKcal += foodKcal;
            protein += foodProtein;
            carb += foodCarb;
            fat += foodFat;

            foodReports.add(new FoodReport(food.getName(), intake.grams(), foodKcal, foodProtein, foodCarb, foodFat));
        }

        double remainingKcal = dailyTargetKcal - mealKcal;
        double percentOfDay = mealKcal / dailyTargetKcal * 100.0;
        List<String> suggestions = buildSuggestions(profile.goal(), mealKcal, dailyTargetKcal, protein);

        return new AnalysisResponse(
                round(mealKcal),
                round(protein),
                round(carb),
                round(fat),
                round(dailyTargetKcal),
                round(remainingKcal),
                round(percentOfDay),
                foodReports,
                suggestions
        );
    }

    private double calculateBmr(UserProfile profile) {
        if ("male".equalsIgnoreCase(profile.gender())) {
            return 66.0 + 13.7 * profile.weightKg() + 5.0 * profile.heightCm() - 6.8 * profile.age();
        }
        return 655.0 + 9.6 * profile.weightKg() + 1.8 * profile.heightCm() - 4.7 * profile.age();
    }

    private double calculateDailyTarget(double bmr, String activityLevel, String goal) {
        double activityFactor = switch (activityLevel.toLowerCase()) {
            case "sedentary" -> 1.2;
            case "light" -> 1.375;
            case "moderate" -> 1.55;
            case "high" -> 1.725;
            default -> 1.375;
        };

        double tdee = bmr * activityFactor;
        return switch (goal.toLowerCase()) {
            case "lose" -> tdee - 300.0;
            case "gain" -> tdee + 200.0;
            default -> tdee;
        };
    }

    private List<String> buildSuggestions(String goal, double mealKcal, double dailyTargetKcal, double protein) {
        List<String> suggestions = new ArrayList<>();
        double remaining = dailyTargetKcal - mealKcal;

        if (remaining < 0) {
            suggestions.add("这一餐已经超过今日目标，建议接下来以蔬菜和优质蛋白为主。");
        } else if (remaining < 300) {
            suggestions.add("今日剩余热量不多，晚餐建议减少主食，保留蛋白质。");
        } else {
            suggestions.add("今日还有较充足热量空间，可以正常安排下一餐。");
        }

        if (protein < 20.0) {
            suggestions.add("这一餐蛋白质偏低，可以补充鸡胸肉、鸡蛋、豆腐或牛奶。");
        } else {
            suggestions.add("蛋白质摄入不错，有助于增强饱腹感和保持肌肉。");
        }

        if ("lose".equalsIgnoreCase(goal) && mealKcal > dailyTargetKcal * 0.5) {
            suggestions.add("这一餐占减脂目标热量比例偏高，建议控制油量和主食分量。");
        }

        return suggestions;
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
