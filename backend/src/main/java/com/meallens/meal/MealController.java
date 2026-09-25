package com.meallens.meal;

import com.meallens.analysis.AnalysisRequest;
import com.meallens.analysis.AnalysisResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping("/photo")
    public MealPhotoResponse uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        try {
            return mealService.uploadPhoto(photo);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(BAD_REQUEST, exception.getMessage(), exception);
        }
    }

    @GetMapping("/{mealId}/photo")
    public ResponseEntity<byte[]> photo(@PathVariable Long mealId) {
        try {
            MealRecord mealRecord = mealService.getMeal(mealId);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mealRecord.getPhotoContentType()))
                    .body(mealRecord.getPhoto());
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PostMapping("/{mealId}/analysis")
    public AnalysisResponse analyze(@PathVariable Long mealId,
                                    @Valid @RequestBody AnalysisRequest request) {
        try {
            return mealService.analyze(mealId, request);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(BAD_REQUEST, exception.getMessage(), exception);
        }
    }
}
