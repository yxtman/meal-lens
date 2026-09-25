# 数据库设计

## 核心表

### 1. user_profile

| 字段 | 类型 | 说明 |
|---|---|---|
| id | bigint | 主键 |
| user_id | bigint | 用户 ID |
| height_cm | decimal | 身高 |
| weight_kg | decimal | 体重 |
| age | int | 年龄 |
| gender | varchar | 性别 |
| goal | varchar | 减脂 / 保持 / 增肌 |
| activity_level | varchar | 活动量 |
| daily_kcal_target | decimal | 每日目标热量 |
| created_at | datetime | 创建时间 |

### 2. food_item

| 字段 | 类型 | 说明 |
|---|---|---|
| id | bigint | 主键 |
| name | varchar | 食物名称 |
| category | varchar | 主食 / 肉类 / 蔬菜 / 水果 |
| kcal_per_100g | decimal | 每 100g 热量 |
| protein_per_100g | decimal | 蛋白质 |
| carb_per_100g | decimal | 碳水 |
| fat_per_100g | decimal | 脂肪 |
| default_grams | decimal | 默认分量 |

### 3. meal_record

| 字段 | 类型 | 说明 |
|---|---|---|
| id | bigint | 主键 |
| user_id | bigint | 用户 ID |
| meal_type | varchar | 早餐 / 午餐 / 晚餐 / 加餐 |
| image_url | varchar | 照片地址 |
| total_kcal | decimal | 总热量 |
| protein_g | decimal | 蛋白质 |
| carb_g | decimal | 碳水 |
| fat_g | decimal | 脂肪 |
| created_at | datetime | 创建时间 |

### 4. meal_item

| 字段 | 类型 | 说明 |
|---|---|---|
| id | bigint | 主键 |
| meal_id | bigint | 餐食 ID |
| food_id | bigint | 食物 ID |
| grams | decimal | 实际分量 |
| confidence | decimal | 识别置信度 |

### 5. advice_record

| 字段 | 类型 | 说明 |
|---|---|---|
| id | bigint | 主键 |
| meal_id | bigint | 餐食 ID |
| advice_type | varchar | 热量 / 蛋白质 / 运动 |
| content | text | 建议内容 |
| created_at | datetime | 创建时间 |

## 关系

```text
user_profile 1 --- N meal_record
meal_record 1 --- N meal_item
food_item 1 --- N meal_item
meal_record 1 --- N advice_record
```

## 计算公式

### BMR

```text
男：BMR = 66 + 13.7 × 体重 + 5 × 身高 - 6.8 × 年龄
女：BMR = 655 + 9.6 × 体重 + 1.8 × 身高 - 4.7 × 年龄
```

### TDEE

```text
TDEE = BMR × 活动系数
```

### 目标热量

- 减脂：TDEE - 300
- 保持：TDEE
- 增肌：TDEE + 200
