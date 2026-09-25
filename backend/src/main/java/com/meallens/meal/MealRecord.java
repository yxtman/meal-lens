package com.meallens.meal;

import com.meallens.analysis.UserProfile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MealRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] photo;

    private String photoContentType;

    private LocalDateTime createdAt;

    private String gender;

    private Integer age;

    private Double heightCm;

    private Double weightKg;

    private String goal;

    private String activityLevel;

    @OneToMany(mappedBy = "mealRecord", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MealFoodSelection> foods = new ArrayList<>();

    protected MealRecord() {
    }

    public MealRecord(byte[] photo, String photoContentType) {
        this.photo = photo;
        this.photoContentType = photoContentType;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public Double getHeightCm() {
        return heightCm;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public String getGoal() {
        return goal;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public List<MealFoodSelection> getFoods() {
        return foods;
    }

    public void updateProfile(UserProfile profile) {
        this.gender = profile.gender();
        this.age = profile.age();
        this.heightCm = profile.heightCm();
        this.weightKg = profile.weightKg();
        this.goal = profile.goal();
        this.activityLevel = profile.activityLevel();
    }

    public void replaceFoods(List<MealFoodSelection> foods) {
        this.foods.clear();
        foods.forEach(food -> food.setMealRecord(this));
        this.foods.addAll(foods);
    }
}
