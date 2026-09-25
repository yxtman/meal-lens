# MealLens

> 拍一张午餐或晚餐照片，识别食物、估算热量，并结合个人基础条件给出这顿饭的影响和建议。

## 项目定位

MealLens 是一个“吃饭影响分析工具”。它不追求医学级精度，而是把一餐饭变成可理解的数字：

- 这顿大概多少热量
- 占今天剩余热量的比例
- 蛋白质、碳水、脂肪大概多少
- 吃完这顿后，晚餐应该怎么调整

## 当前阶段

Phase 0：产品设计与技术方案确认。

## MVP 目标

第一版先做 Web Demo，再做手机 App：

1. 用户填写身高、体重、年龄、性别、目标、活动量
2. 上传或拍摄一餐照片
3. 选择识别出的食物，修正分量
4. 生成热量和三大营养素估算
5. 给出当日剩余热量与简单建议

## 目录结构

```text
meal-lens/
├── README.md
├── docs/
│   ├── 01-product-plan.md
│   ├── 02-technical-architecture.md
│   ├── 03-database-design.md
│   └── 04-mvp-milestones.md
```

## 技术方案

- 前端：先 Web，后 Flutter
- 后端：Spring Boot 3
- 数据库：MySQL
- 缓存：Redis
- AI 服务：Python FastAPI + 图像识别模型
- 部署：Docker Compose

## 后续计划

详细计划见 `docs/04-mvp-milestones.md`。
