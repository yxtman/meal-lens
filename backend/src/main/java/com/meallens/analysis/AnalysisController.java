package com.meallens.analysis;

import com.meallens.food.FoodItem;
import com.meallens.food.FoodItemRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    private final AnalysisService analysisService;
    private final FoodItemRepository foodItemRepository;

    public AnalysisController(AnalysisService analysisService, FoodItemRepository foodItemRepository) {
        this.analysisService = analysisService;
        this.foodItemRepository = foodItemRepository;
    }

    @GetMapping("/foods")
    public List<FoodItem> foods() {
        return foodItemRepository.findAll();
    }

    @PostMapping("/analysis")
    public AnalysisResponse analyze(@Valid @RequestBody AnalysisRequest request) {
        return analysisService.analyze(request);
    }
}
