# AIOServer

基于DDD（领域驱动设计）六边形架构的Java后端项目，集成微信服务、用户认证和博客管理功能。

## 🏗️ 项目架构

采用**简化DDD六边形架构**，分层清晰：
```
aioserver/
├── api/                    # API层（接口定义）
├── trigger/                # Trigger层（HTTP适配器）
├── domain/                 # Domain层（领域逻辑）
├── infrastructure/         # Infrastructure层（基础设施）
└── types/                  # Types层（通用类型）
```

## 🎯 核心功能

- **Auth认证域** - 用户注册/登录系统，JWT Token认证
- **Blog博客域** - 博文CRUD管理，分类管理，分页查询
- **Weixin微信域** - 微信服务器验证，消息接收处理，自动回复机制

## 🔧 技术栈

- **Java 17** + **Spring Boot 3.0.2**
- **MySQL 8.0** + **MyBatis**
- **Guava Cache**（缓存）
- **Retrofit2**（HTTP客户端）
- **XStream**（XML处理）


## 🛠️ AI开发工具

### Claude Code - AI编程助手
```bash
# 安装Claude Code
npm install -g @anthropic-ai/claude-code

# 初始化项目
claude-code init

# 进入交互模式
claude-code
```

### OpenSpec - 规范驱动开发
```bash
# 安装OpenSpec
npm install -g openspec

# 初始化项目
openspec init

# 查看项目状态
openspec list
```

## 🎯 开发指南

### 代码规范
1. **严格遵循DDD分层架构**
2. **使用统一Result响应体系**
3. **实现充血模型设计**
4. **遵循Port/Adapter模式**

### 提交规范
```bash
# 功能开发
git commit -m "feat: 添加用户认证功能"

# 问题修复
git commit -m "fix: 修复跨域配置问题"

# 文档更新
git commit -m "docs: 更新API文档"
```

### 分支策略
- `main` - 主分支，生产环境代码
- `feature-*` - 功能开发分支
- `hotfix-*` - 紧急修复分支

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。