# AIO Server

一个基于Spring Boot的综合性业务服务项目，用于整合和管理各类业务代码，为后续微服务拆分做准备。

## 项目概述

AIO Server是一个单体应用架构项目，旨在统一管理所有业务功能模块。项目采用分层架构设计，提供标准化的API接口和统一的数据返回格式，便于后续进行微服务化改造。

## 技术栈

- **Java 8** - 主要编程语言
- **Spring Boot 2.3.12** - 应用框架
- **Maven** - 项目构建工具
- **Lombok** - 代码简化工具

## 项目结构

```
src/main/java/com/gaoyifeng/aioserver/
├── AioserverApplication.java          # 应用程序启动类
├── trigger/                           # 触发器层（控制器）
│   └── DemoController.java           # 演示控制器
└── types/common/                      # 通用类型定义
    ├── Result.java                   # 统一返回结果封装
    ├── ResultCode.java               # 结果代码枚举
    ├── PageRequest.java              # 分页请求参数
    └── PageResult.java               # 分页结果封装
```

## 核心功能

### 1. 统一结果封装

项目提供了完整的API响应封装体系：

- **Result<T>**: 统一的结果返回格式
- **ResultCode**: 预定义的状态码枚举
- 支持成功/失败状态的快速构建
- 提供自定义消息和错误码的能力

### 2. HTTP方法演示

`DemoController` 提供了完整的HTTP方法使用示例：

- **GET**: 路径参数和查询参数处理
- **POST**: 请求体和头部参数处理
- **PUT**: 路径参数、请求体和头部参数处理
- **DELETE**: 路径参数和头部参数处理
- **PATCH**: 查询参数、请求体和头部参数处理

所有接口都包含：
- 完整的参数验证
- 统一的错误处理
- 详细的代码注释和使用示例

## 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+


## 开发规范

### 代码结构

- **trigger**: 控制器层，处理HTTP请求
- **types**: 数据类型定义，包含DTO、VO等
- **service**: 业务逻辑层（待扩展）
- **repository**: 数据访问层（待扩展）

### 响应格式

所有API接口统一使用 `Result<T>` 格式返回：

```json
{
  "code": "200",
  "info": "success",
  "data": {
    // 具体数据内容
  }
}
```

### 错误处理

- 参数验证使用预定义的错误码
- 支持自定义错误消息
- 统一的异常处理机制

## 未来规划

1. **业务模块扩展**: 逐步添加各类业务功能模块
2. **数据持久化**: 集成数据库访问层
3. **安全认证**: 添加用户认证和授权机制
4. **微服务拆分**: 根据业务边界进行服务拆分
5. **容器化部署**: 支持Docker容器化部署
6. **监控告警**: 集成应用监控和日志系统

## 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 联系方式

- 作者: 高艺峰
- 项目地址: [GitHub Repository]

---

**注意**: 这是一个过渡阶段的单体项目，主要用于业务功能的快速开发和验证。后续会根据业务发展需要进行微服务化改造。