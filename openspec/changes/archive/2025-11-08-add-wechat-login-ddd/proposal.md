# 变更：在现有DDD架构基础上添加微信扫码登录功能

## 为什么
基于对当前项目结构的详细分析，发现项目已具备完整的DDD架构和微信消息处理功能，但缺少微信扫码登录的核心功能。学习项目s-pay-mall-ddd已验证了技术方案的可行性。

## 什么变更
- **扩展认证服务** - 在现有IAuthService基础上添加微信扫码登录方法
- **创建登录域服务** - 在domain.auth下创建WeixinLoginService处理微信登录逻辑
- **扩展微信API端口** - 在IWeixinApiPort添加二维码创建接口供登录域使用
- **添加登录缓存管理** - 利用现有GuavaConfig.shortTermCache管理登录状态
- **保持微信域职责** - IWeixinService继续处理知识星球消息，不混合登录功能

## 影响
- 受影响的规范：auth-service、wechat-api
- 受影响的代码：domain.auth、api.IAuthService、infrastructure.adapter.weixin
- **NON-BREAKING** - 清晰分离职责，不破坏现有功能