## ADDED Requirements

### Requirement: 微信扫码登录服务
系统 SHALL 提供基于微信公众号的扫码登录功能。

#### Scenario: 生成登录二维码
- **WHEN** 用户请求登录二维码
- **THEN** 系统生成唯一的登录票据并调用微信API创建二维码
- **AND** 返回二维码供用户扫描

#### Scenario: 用户扫码登录
- **WHEN** 用户使用微信扫描二维码并确认登录
- **THEN** 系统接收微信回调通知
- **AND** 验证用户身份并保存登录状态
- **AND** 发送登录成功的模板消息通知

#### Scenario: 检查登录状态
- **WHEN** 前端轮询检查登录状态
- **THEN** 系统根据登录票据查询缓存中的登录状态
- **AND** 返回用户的openid或登录未完成状态

### Requirement: 登录状态缓存管理
系统 SHALL 使用缓存管理用户的登录状态信息。

#### Scenario: 保存登录状态
- **WHEN** 用户完成微信授权登录
- **THEN** 系统将登录票据与用户openid的映射关系保存到缓存
- **AND** 设置合理的缓存过期时间

#### Scenario: 查询登录状态
- **WHEN** 系统需要验证用户的登录状态
- **THEN** 根据登录票据从缓存中获取对应的openid
- **AND** 验证用户是否已成功登录

### Requirement: 微信API集成
系统 SHALL 集成微信公众号API实现登录相关功能。

#### Scenario: 调用微信创建二维码接口
- **WHEN** 需要生成登录二维码
- **THEN** 系统通过HTTP客户端调用微信创建临时二维码API
- **AND** 解析响应获取二维码票据和URL

#### Scenario: 发送模板消息
- **WHEN** 用户成功登录
- **THEN** 系统调用微信模板消息API
- **AND** 发送登录成功的通知消息给用户

## MODIFIED Requirements

### Requirement: DDD架构标准化
项目 SHALL 遵循DDD六边形架构模式组织代码结构。

#### Scenario: API层接口定义
- **WHEN** 定义对外提供服务接口
- **THEN** 在API层创建服务接口，如IAuthService
- **AND** 定义清晰的接口契约和DTO

#### Scenario: Domain层业务逻辑
- **WHEN** 实现核心业务逻辑
- **THEN** 在Domain层实现服务类，如WeixinLoginService
- **AND** 通过Port接口与基础设施层解耦

#### Scenario: Infrastructure层适配器实现
- **WHEN** 实现外部依赖的适配器
- **THEN** 在Infrastructure层实现Port的具体实现，如LoginPort
- **AND** 处理外部API调用和数据持久化

#### Scenario: Trigger层API端点
- **WHEN** 暴露HTTP接口
- **THEN** 在Trigger层创建Controller类处理HTTP请求
- **AND** 调用Application层或直接调用Domain层服务