package com.meallens.meal;

import com.meallens.analysis.AnalysisRequest;
import com.meallens.analysis.AnalysisResponse;
import com.meallens.analysis.AnalysisService;
import com.meallens.analysis.FoodIntake;
import com.meallens.food.FoodItem;
import com.meallens.food.FoodItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@Service
public class MealService {

    private static final List<String> CANDIDATE_FOOD_NAMES = List.of(
            "米饭", "鸡胸肉", "鸡蛋", "西兰花", "番茄", "牛奶"
    );

    private final MealRecordRepository mealRecordRepository;
    private final FoodItemRepository foodItemRepository;
    private final AnalysisService analysisService;

    public MealService(MealRecordRepository mealRecordRepository,
                       FoodItemRepository foodItemRepository,
                       AnalysisService analysisService) {
        this.mealRecordRepository = mealRecordRepository;
        this.foodItemRepository = foodItemRepository;
        this.analysisService = analysisService;
    }

    @Transactional
    public MealPhotoResponse uploadPhoto(MultipartFile photo) {
        if (photo == null || photo.isEmpty()) {
            throw new IllegalArgumentException("请先上传餐食照片。");
        }
        String contentType = photo.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("只支持上传图片文件。");
        }

        MealRecord mealRecord = mealRecordRepository.save(new MealRecord(readPhoto(photo), contentType));
        return new MealPhotoResponse(
                mealRecord.getId(),
                candidateFoods(),
                "照片已收到，请确认识别出的食物和分量。"
        );
    }

    @Transactional(readOnly = true)
    public MealRecord getMeal(Long mealId) {
        return mealRecordRepository.findById(mealId)
                .orElseThrow(() -> new IllegalArgumentException("餐食记录不存在：" + mealId));
    }

    @Transactional
    public AnalysisResponse analyze(Long mealId, AnalysisRequest request) {
        MealRecord mealRecord = getMeal(mealId);
        mealRecord.updateProfile(request.userProfile());
        mealRecord.replaceFoods(request.foods().stream()
                .map(this::toFoodSelection)
                .toList());
        mealRecordRepository.save(mealRecord);

        return analysisService.analyze(request);
    }

    private List<FoodItem> candidateFoods() {
        return CANDIDATE_FOOD_NAMES.stream()
                .map(name -> foodItemRepository.findByName(name)
                        .orElseThrow(() -> new IllegalStateException("候选食物缺失：" + name)))
                .toList();
    }

    private MealFoodSelection toFoodSelection(FoodIntake intake) {
        FoodItem foodItem = foodItemRepository.findById(intake.foodId())
                .orElseThrow(() -> new IllegalArgumentException("食物不存在：" + intake.foodId()));
        return new MealFoodSelection(foodItem, intake.grams());
    }

    private byte[] readPhoto(MultipartFile photo) {
        try {
            return photo.getBytes();
        } catch (IOException exception) {
            throw new UncheckedIOException("读取照片失败。", exception);
        }
    }
}
