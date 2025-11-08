## 1. 扩展认证API接口
- [ ] 1.1 扩展IAuthService接口添加微信登录相关方法
- [ ] 1.2 在api.dto.auth包下创建微信登录相关的Request/Response DTO类
- [ ] 1.3 添加LoginQrCodeResponseDto、LoginCheckRequestDto等

## 2. 扩展认证域实体和值对象（Domain层）
- [ ] 2.1 扩充User实体添加微信登录核心字段（移除登录次数字段）
- [ ] 2.2 在User实体中添加核心充血方法（weixinLogin, updateLastLoginTime等）
- [ ] 2.3 在domain.auth.model.valobj下创建LoginType枚举类
- [ ] 2.4 在domain.auth.service包下创建WeixinLoginService
- [ ] 2.5 在domain.auth.adapter.port下创建IWeixinLoginPort接口
- [ ] 2.6 在domain.auth.model.entity下创建LoginTicket实体类
- [ ] 2.7 在domain.auth.model.valobj下创建登录相关值对象

## 3. 扩展微信API端口（复用现有）
- [ ] 3.1 在IWeixinApiPort接口添加二维码创建方法供登录域使用
- [ ] 3.2 在WeixinApiPortAdapter实现二维码创建和模板消息发送
- [ ] 3.3 添加微信登录相关的HTTP调用接口
- [ ] 3.4 保持IWeixinService处理知识星球消息的现有职责不变

## 4. 扩展认证触发器层
- [ ] 4.1 在AuthController添加微信登录相关API端点
- [ ] 4.2 实现二维码生成接口：/api/v1/auth/weixin/qrcode
- [ ] 4.3 实现登录状态检查接口：/api/v1/auth/weixin/check
- [ ] 4.4 实现登录回调接口：/api/v1/auth/weixin/callback

## 5. 扩展微信配置
- [ ] 5.1 在WeixinConfig中添加登录相关配置项
- [ ] 5.2 添加二维码过期时间配置
- [ ] 5.3 配置登录成功模板消息ID
- [ ] 5.4 更新application.yml添加微信登录配置

## 6. 集成现有缓存系统
- [ ] 6.1 利用现有GuavaConfig.shortTermCache存储登录票据
- [ ] 6.2 配置登录票据过期时间（建议5分钟）
- [ ] 6.3 创建登录状态管理的缓存工具类
- [ ] 6.4 实现登录票据的唯一性验证

## 7. 验证和测试
- [ ] 7.1 启动项目验证新功能正常工作
- [ ] 7.2 测试二维码生成API接口
- [ ] 7.3 测试登录状态检查接口
- [ ] 7.4 验证现有微信消息功能不受影响

## 8. 文档更新
- [ ] 8.1 更新API文档记录新增的登录接口
- [ ] 8.2 完善微信登录功能的使用说明
- [ ] 8.3 更新CLAUDE.md项目开发指南
- [ ] 8.4 记录新增配置项的使用方法