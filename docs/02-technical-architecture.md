# 技术方案

## 总体架构

```text
用户
  ↓
Web / App
  ↓
Spring Boot 后端
  ├── 用户信息
  ├── 餐食记录
  ├── 热量计算
  └── 建议生成
        ↓
     Python AI 服务
        ├── 食物识别
        └── 分量估算
```

## 技术选型

| 模块 | 技术 |
|---|---|
| Web 前端 | Vue 3 |
| App | Flutter |
| 后端 | Spring Boot 3 |
| 数据库 | MySQL |
| 缓存 | Redis |
| AI 服务 | Python FastAPI |
| 图像识别 | YOLO / CLIP |
| 对象存储 | MinIO 或云 OSS |
| 部署 | Docker Compose |

## 后端模块

### 1. 用户模块

- 注册 / 登录
- 保存基础信息
- 计算基础代谢率 BMR
- 计算每日总消耗 TDEE

### 2. 餐食模块

- 上传照片
- 保存餐食记录
- 保存食物识别结果
- 保存用户修正后的分量

### 3. 分析模块

- 热量计算
- 三大营养素计算
- 今日剩余热量计算

### 4. 建议模块

- 根据目标生成建议
- 根据剩余热量生成晚餐建议
- 根据蛋白质缺口生成补充建议

## AI 服务模块

第一版可以先做“伪 AI”：

- 上传照片
- 返回一组候选食物
- 用户手动确认

第二版再接真正的图像识别模型。

## 关键接口

| 接口 | 说明 |
|---|---|
| POST /api/users/profile | 保存用户基础信息 |
| POST /api/meals | 创建餐食记录 |
| POST /api/meals/{id}/image | 上传餐食照片 |
| POST /api/meals/{id}/analyze | 触发分析 |
| GET /api/meals/{id}/report | 获取分析报告 |

## 部署方案

- Spring Boot：Docker
- Python AI：Docker
- MySQL：Docker
- Redis：Docker
- 前端：Nginx / 静态部署

## 安全与合规

- 不存储敏感健康数据
- 图片默认只用于分析
- 输出内容标明“参考建议，非医疗建议”
