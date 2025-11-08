## 上下文
经过详细分析，当前aioserver项目已具备完整的DDD架构、微信消息处理功能、Guava Cache配置等基础设施。学习项目s-pay-mall-ddd的微信扫码登录功能可以在现有架构基础上无缝集成，无需重构。

## 目标 / 非目标
- 目标：在现有微信域基础上添加扫码登录功能
- 目标：利用现有GuavaCache管理登录状态
- 目标：扩展现有WeixinPortalController添加登录API
- 非目标：重构现有架构（已符合DDD标准）
- 非目标：重复实现已有功能（消息处理、签名验证等）
- 非目标：添加复杂权限管理系统

## 决策
- 决策：扩展现有IAuthService接口，添加微信登录相关方法
- 决策：在domain.auth下创建WeixinLoginService，保持认证职责统一
- 决策：扩展IWeixinApiPort添加二维码创建接口，供认证域调用
- 决策：IWeixinService保持处理知识星球消息的职责，不混合登录功能
- 决策：扩充User实体采用充血模型，将微信登录相关业务逻辑封装在User实体中
- 决策：利用现有GuavaConfig.shortTermCache管理登录状态，配置5分钟过期
- 考虑的替代方案：
  - 创建独立的微信用户实体 - 考虑到用户统一性，选择扩充User实体
  - 在微信域添加登录功能 - 考虑职责分离，选择在认证域处理登录

## User实体充血模型设计

### 扩充字段（聚焦核心功能）
- `weixinOpenId` - 微信OpenID（用户唯一标识）
- `weixinUnionId` - 微信UnionID（跨应用唯一标识，可选）
- `weixinNickname` - 微信昵称
- `weixinAvatar` - 微信头像URL
- `loginType` - 登录类型枚举（USERNAME_PASSWORD/WEIXIN）
- `bindTime` - 微信绑定时间
- `lastLoginTime` - 最后登录时间

### 充血方法（优先核心功能）
- `weixinLogin(openId)` - 微信登录验证
- `updateLastLoginTime()` - 更新最后登录时间
- `getLoginMethodDescription()` - 获取登录方式描述
- 保留现有充血方法：`login()`, `create()`, `updateInfo()`, `isValid()`

### 暂缓实现的功能（以后补充）
- 绑定/解绑微信账号的API接口
- 更新微信用户信息的方法
- 登录次数统计功能

## 技术现状
### 现有基础设施 ✅
- GuavaConfig已配置3种缓存策略
- WeixinConfig已存在，可扩展登录相关配置
- 微信API端口适配器已实现基础功能
- 完整的DDD分层架构

### 需要扩展的功能
- 微信二维码创建API调用
- 登录票据生成和验证
- 登录状态缓存管理
- 登录成功模板消息推送

## 风险 / 权衡
- [与现有功能冲突] → 通过合理的方法命名和包组织避免冲突
- [微信API调用失败] → 实现重试机制和异常处理
- [缓存键冲突] → 使用特定的前缀（如"login:"）管理登录缓存

## 迁移计划
1. 先扩展API接口和DTO类
2. 在现有Domain层添加登录服务
3. 扩展现有Infrastructure层适配器
4. 扩展现有Controller添加登录API
5. 验证现有功能不受影响

## 未决问题
- 微信公众号的appId和appSecret需要配置
- 登录成功后的重定向URL需要确定
- 是否需要记录用户登录日志