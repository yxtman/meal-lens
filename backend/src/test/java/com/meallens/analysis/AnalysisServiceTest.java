package com.meallens.analysis;

import com.meallens.food.FoodItem;
import com.meallens.food.FoodItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalysisServiceTest {

    @Mock
    private FoodItemRepository repository;

    private AnalysisService service;

    @BeforeEach
    void setUp() {
        service = new AnalysisService(repository);
    }

    @Test
    void shouldCalculateNutritionAndDailyTarget() {
        FoodItem rice = new FoodItem("米饭", "主食", 100.0, 3.0, 22.0, 1.0, 200.0);
        when(repository.findById(1L)).thenReturn(Optional.of(rice));

        AnalysisRequest request = new AnalysisRequest(
                new UserProfile("male", 21, 175.0, 70.0, "lose", "light"),
                List.of(new FoodIntake(1L, 200.0))
        );

        AnalysisResponse response = service.analyze(request);

        assertThat(response.mealKcal()).isEqualTo(200.0);
        assertThat(response.protein()).isEqualTo(6.0);
        assertThat(response.carb()).isEqualTo(44.0);
        assertThat(response.fat()).isEqualTo(2.0);
        assertThat(response.dailyTargetKcal()).isEqualTo(2116.2);
        assertThat(response.remainingKcal()).isEqualTo(1916.2);
        assertThat(response.percentOfDay()).isEqualTo(9.5);
        assertThat(response.suggestions()).isNotEmpty();
    }
}
