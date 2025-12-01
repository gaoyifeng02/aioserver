# Design Document: Enhanced Study Features Migration

## Architecture Overview

This migration follows the existing DDD hexagonal architecture in aioserver while integrating proven patterns from the study project. The design maintains the single-module constraint and preserves existing functionality.

## Domain Architecture

### Enhanced Auth Domain
```
auth/
├── model/
│   ├── entity/User.java (enhanced)
│   ├── entity/LoginTicket.java (enhanced from study)
│   └── valobj/TicketStatus.java (migrated)
├── adapter/
│   ├── repository/IUserRepository.java (enhanced)
│   └── port/IWeixinLoginPort.java (migrated)
└── service/
    ├── UserAuthService.java (existing)
    └── WeixinLoginService.java (enhanced from study)
```

### Complete Order Domain
```
order/
├── model/
│   ├── aggregate/CreateOrderAggregate.java (enhanced from study)
│   ├── entity/OrderEntity.java (enhanced from study)
│   ├── entity/OrderItemEntity.java (new)
│   └── valobj/OrderStatusVO.java (enhanced from study)
├── adapter/
│   ├── repository/IOrderRepository.java (enhanced)
│   └── port/IProductPort.java (migrated from study)
└── service/
    ├── AbstractOrderService.java (migrated from study)
    └── OrderService.java (enhanced from study)
```

### New Product Domain
```
product/
├── model/
│   ├── entity/ProductEntity.java (migrated from study)
│   └── valobj/ProductStatusVO.java (new)
├── adapter/
│   └── repository/IProductRepository.java (new)
└── service/
    └── ProductService.java (migrated from study)
```

### Enhanced Weixin Domain
```
weixin/
├── model/
│   ├── entity/WeixinMessage.java (existing)
│   ├── entity/WeixinUser.java (new)
│   └── valobj/MessageTypeVO.java (enhanced)
├── adapter/
│   ├── repository/WeixinMessageRepository.java (existing)
│   └── port/IWeixinApiPort.java (enhanced)
└── service/
    ├── MessageService.java (existing)
    ├── SignatureService.java (existing)
    └── WeixinApiService.java (new)
```

## Infrastructure Enhancements

### Gateway Layer Additions
```
gateway/
├── IWeixinApiGateway.java (enhanced)
├── IProductRPC.java (migrated from study)
├── IPaymentGateway.java (new)
└── dto/
    ├── WeixinQrCodeRequestDTO.java (migrated)
    ├── WeixinQrCodeResponseDTO.java (migrated)
    ├── WeixinTokenResponseDTO.java (migrated)
    ├── ProductDTO.java (migrated)
    └── PaymentRequestDTO.java (new)
```

### Repository Implementations
```
repository/
├── UserRepositoryImpl.java (enhanced)
├── OrderRepositoryImpl.java (enhanced)
├── ProductRepositoryImpl.java (new)
└── WeixinMessageRepositoryImpl.java (enhanced)
```

### Port Adapters
```
adapter/port/
├── WeixinLoginPortAdapter.java (enhanced from study)
├── ProductPortAdapter.java (migrated from study)
├── CachePortAdapter.java (existing)
└── PaymentPortAdapter.java (new)
```

## API Layer Updates

### New/Enhanced Controllers
```
trigger/
├── AuthController.java (enhanced)
│   ├── GET /idaas/auth/weixin_qrcode_ticket (migrated)
│   ├── GET /idaas/auth/check_login (enhanced)
│   └── POST /idaas/auth/weixin_callback (migrated)
├── OrderController.java (enhanced)
│   ├── POST /order/create (enhanced)
│   ├── GET /order/list (existing)
│   └── GET /order/pay_url (new)
└── ProductController.java (new)
    ├── GET /product/list (new)
    └── GET /product/detail (new)
```

### API Interfaces
```
api/
├── IAuthService.java (enhanced)
├── IOrderApi.java (enhanced)
└── IProductApi.java (new)
```

## Integration Points

### WeChat Login Flow
1. **QR Code Generation**: Study project's proven QR code ticket system
2. **OAuth2 Callback**: Enhanced callback handling with user info retrieval
3. **Session Management**: Integration with existing auth system
4. **State Tracking**: Ticket-based login state management

### Order Processing Flow
1. **Product Selection**: Product catalog integration
2. **Cart Management**: Enhanced order aggregate creation
3. **Payment Integration**: Gateway pattern for payment processing
4. **Status Tracking**: Comprehensive order status management

### WeChat API Integration
1. **Message Handling**: Enhanced XML processing
2. **Template Messages**: User notification system
3. **User Information**: Enhanced user profile management
4. **API Client**: Robust Retrofit2-based implementation

## Data Model Enhancements

### Database Schema Updates
```sql
-- Enhanced User table
ALTER TABLE user ADD COLUMN weixin_openid VARCHAR(64);
ALTER TABLE user ADD COLUMN weixin_unionid VARCHAR(64);

-- Product table (new)
CREATE TABLE product (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2),
    status TINYINT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

-- Order items table (new)
CREATE TABLE order_item (
    id BIGINT PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    product_name VARCHAR(255),
    quantity INT,
    unit_price DECIMAL(10,2),
    total_price DECIMAL(10,2),
    create_time DATETIME,
    FOREIGN KEY (order_id) REFERENCES order(id)
);

-- Enhanced Order table
ALTER TABLE order ADD COLUMN product_snapshot TEXT;
ALTER TABLE order ADD COLUMN payment_url TEXT;
```

## Configuration Management

### Application Properties
```yaml
# WeChat Integration (enhanced)
weixin:
  appid: ${WEIXIN_APPID}
  secret: ${WEIXIN_SECRET}
  redirect_uri: ${WEIXIN_REDIRECT_URI}
  token: ${WEIXIN_TOKEN}
  aes_key: ${WEIXIN_AES_KEY}

# Product Service (new)
product:
  service:
    url: ${PRODUCT_SERVICE_URL:http://localhost:8081}

# Payment Gateway (new)
payment:
  alipay:
    app_id: ${ALIPAY_APP_ID}
    private_key: ${ALIPAY_PRIVATE_KEY}
    public_key: ${ALIPAY_PUBLIC_KEY}
    notify_url: ${ALIPAY_NOTIFY_URL}
    return_url: ${ALIPAY_RETURN_URL}
```

## Technology Stack Alignment

### Consistent with Current Project
- **Framework**: Spring Boot 3.1.5
- **Java Version**: 21
- **Database**: MySQL with MyBatis
- **Caching**: Guava Cache
- **HTTP Client**: Retrofit2
- **Architecture**: DDD Hexagonal

### Dependencies to Add
```xml
<!-- Payment Integration (if needed) -->
<dependency>
    <groupId>com.alipay.sdk</groupId>
    <artifactId>alipay-sdk-java</artifactId>
    <version>4.35.79.ALL</version>
</dependency>

<!-- Enhanced XML Processing (if needed) -->
<dependency>
    <groupId>dom4j</groupId>
    <artifactId>dom4j</artifactId>
    <version>1.6.1</version>
</dependency>
```

## Migration Strategy

### Phase 1: Foundation Enhancement
- Enhanced WeChat login system
- Improved user management
- Enhanced caching strategies

### Phase 2: Order Completion
- Product domain integration
- Order processing completion
- Payment gateway integration

### Phase 3: Advanced Features
- Template message system
- Enhanced WeChat API integration
- Performance optimizations

## Quality Assurance

### Testing Strategy
- **Unit Tests**: Domain services and business logic
- **Integration Tests**: API endpoints and database operations
- **Component Tests**: WeChat API integration
- **End-to-End Tests**: Complete user flows

### Performance Considerations
- **Caching Strategy**: Multi-level caching for WeChat data
- **Database Optimization**: Indexing and query optimization
- **Connection Pooling**: HikariCP configuration tuning
- **API Rate Limiting**: WeChat API call throttling