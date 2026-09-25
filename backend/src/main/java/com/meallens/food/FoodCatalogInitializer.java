package com.meallens.food;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FoodCatalogInitializer implements CommandLineRunner {

    private final FoodItemRepository repository;

    public FoodCatalogInitializer(FoodItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        repository.save(new FoodItem("米饭", "主食", 116.0, 2.6, 25.9, 0.3, 200.0));
        repository.save(new FoodItem("面条", "主食", 110.0, 3.9, 22.0, 0.4, 250.0));
        repository.save(new FoodItem("全麦面包", "主食", 246.0, 9.0, 45.0, 3.4, 80.0));
        repository.save(new FoodItem("鸡胸肉", "肉类", 133.0, 24.0, 0.0, 2.5, 150.0));
        repository.save(new FoodItem("牛腱肉", "肉类", 125.0, 21.0, 1.0, 3.5, 150.0));
        repository.save(new FoodItem("三文鱼", "肉类", 139.0, 17.0, 0.0, 7.0, 120.0));
        repository.save(new FoodItem("鸡蛋", "蛋类", 139.0, 13.0, 1.5, 8.6, 60.0));
        repository.save(new FoodItem("豆腐", "豆制品", 82.0, 8.0, 2.0, 5.0, 100.0));
        repository.save(new FoodItem("西兰花", "蔬菜", 34.0, 2.8, 6.6, 0.4, 150.0));
        repository.save(new FoodItem("生菜", "蔬菜", 15.0, 1.3, 2.0, 0.2, 100.0));
        repository.save(new FoodItem("番茄", "蔬菜", 18.0, 0.9, 3.9, 0.2, 150.0));
        repository.save(new FoodItem("苹果", "水果", 52.0, 0.3, 13.8, 0.2, 200.0));
        repository.save(new FoodItem("香蕉", "水果", 89.0, 1.1, 22.0, 0.3, 120.0));
        repository.save(new FoodItem("牛奶", "饮品", 65.0, 3.4, 5.0, 3.6, 250.0));
    }
}
