<!-- OPENSPEC:START -->
# OpenSpec Instructions

These instructions are for AI assistants working in this project.

Always open `@/openspec/AGENTS.md` when the request:
- Mentions planning or proposals (words like proposal, spec, change, plan)
- Introduces new capabilities, breaking changes, architecture shifts, or big performance/security work
- Sounds ambiguous and you need the authoritative spec before coding

Use `@/openspec/AGENTS.md` to learn:
- How to create and apply change proposals
- Spec format and conventions
- Project structure and guidelines

Keep this managed block so 'openspec update' can refresh the instructions.

<!-- OPENSPEC:END -->

# AIOServer 项目开发指南

## 项目基本信息
1. 项目采用DDD架构。
2. /study是你的学习参考项目，因为现在其实就是对study项目的功能进行搬运，实现的时候可以直接搬运study项目的代码，部分内容进行我的替代。
3. 每次提问的模式都是：理解我的需求，给我你的修改规划，同意后进行修改。

## 项目限制条件

### 1. 架构限制
- **不使用Maven多模块设计** - 保持单体项目结构
- **不搞复杂的回退实现** - 避免过度复杂的设计
- **不要创建新的响应类** - 必须使用项目中Types层已有的Result类体系

### 2. 技术选择限制
- **数据库** - 使用名为"aioserver"的数据库
- **DDD架构** - 必须按照DDD六边形架构进行标准化
- **统一响应体系** - 使用现有的Result类，不允许创建Response等替代类


## 项目基本要求

### 1. DDD标准化
- **学习参考项目s-pay-mall-ddd** - 按照其DDD架构模式标准化
- **六边形架构分层** - API、Trigger、Domain、Infrastructure、Types
- **Port/Adapter模式** - 实现依赖倒置原则
- **API层设计** - Trigger层实现API层的接口


## 项目运行管理

### 🚀 启动项目
每次完成需求开发后，必须通过以下命令启动项目验证功能正常运行：

```bash
mvn spring-boot:run
```

### 🛑 停止项目
验证完成后，使用以下命令停止项目：

```bash
# 查找占用端口10001的进程PID
netstat -ano | findstr :10001

# 使用PowerShell强制停止进程（将PID替换为实际值）
powershell "Stop-Process -Id [PID] -Force"
```

### 📋 开发流程规范
1. 先理解目前项目背景，可以通过查看git提交记录等方式了解项目背景和架构以及目前拥有的功能
2. **需求分析** → 理解需求并制定修改规划
3. **获得确认** → 等待用户同意修改方案
4. **代码实现** → 按照DDD架构和项目规范编码
5. **启动验证** → 运行 `mvn spring-boot:run` 确保项目正常启动，运行之前需要先Clean
6. **停止项目** → 验证完成后停止应用
7. **完成开发** → 开发流程结束

### 🔧 环境信息
- **Java版本**：21
- **Spring Boot版本**：3.1.5
- **服务端口**：10001

## API规范

GET - 获取资源（幂等）
• 用于查询和检索数据

• 示例：GET /class/st udents?name="Jake"

POST - 创建资源（非幂等）
• 创建新资源，不指定ID

• 服务端生成ID并返回

• 示例：POST /class/students + JSON payload

PUT - 整体替换资源（幂等）
• 需要资源ID在路径中

• 完全替换资源的所有属性

• 空payload {} 表示重置资源

• 示例：PUT /class/students/2

PATCH - 部分更新资源（非幂等）
• 需要资源ID在路径中

• 只更新指定的属性，保留其他属性

• 支持嵌套结构的点分语法更新

• 数组操作：通过 _arrayop=add|remove 参数控制

PATCH - 部分更新资源（非幂等）
• 需要资源ID在路径中

• 只更新指定的属性，保留其他属性

• 支持嵌套结构的点分语法更新

• 数组操作：通过 _arrayop=add|remove 参数控制

• 示例：PATCH /class/students/2?_arrayop=add

DELETE - 删除资源（幂等）
• 支持带payload（虽然HTTP规范模糊，但此规范明确支持）

• 不支持时可用 DELETE Over POST：POST /resource?_method=DELETE

• 示例：DELETE /class/students/2