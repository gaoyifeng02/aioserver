# 变更：集成BigModel HTTP API服务

## 为什么
为AIOServer项目集成BigModel（智谱清言）AI对话能力，提供智能客服、内容生成和对话管理功能，增强平台的AI服务能力。

## 什么变更
- 添加BigModel HTTP API集成支持
- 实现AI对话服务域（AI Service Domain）
- 提供对话历史管理和上下文保持
- 支持流式响应和批量处理
- 集成API认证和限流机制
- **BREAKING** 无破坏性变更，纯新增功能

## 影响
- 受影响的规范：新增 `ai-service` 功能规范
- 受影响的代码：
  - 新增域：`domain/ai/`
  - 新增API接口：`api/IAIService.java`
  - 新增控制器：`trigger/AIController.java`
  - 新增基础设施适配器：`infrastructure/adapter/port/BigModelApiPortAdapter.java`
  - 新增配置：`app/config/BigModelConfig.java`
  - 数据库变更：新增对话历史表
- 外部依赖：BigModel HTTP API集成
- 技术栈：复用现有Retrofit2、Guava Cache、MyBatis等技术