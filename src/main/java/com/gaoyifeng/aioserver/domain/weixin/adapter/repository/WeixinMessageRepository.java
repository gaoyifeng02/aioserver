package com.gaoyifeng.aioserver.domain.weixin.adapter.repository;

import com.gaoyifeng.aioserver.domain.weixin.adapter.port.IMessageRepositoryPort;
import com.gaoyifeng.aioserver.domain.weixin.model.aggregate.MessageConversation;
import com.gaoyifeng.aioserver.domain.weixin.model.entity.WeixinMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 微信消息仓储接口 - Domain层
 * 通过依赖Port接口实现依赖倒置，遵循六边形架构
 *
 * @deprecated 请直接使用 IMessageRepositoryPort 接口
 */
@Deprecated
@Repository
public interface WeixinMessageRepository extends IMessageRepositoryPort {
    // 此接口保持向后兼容，实际使用请直接使用 IMessageRepositoryPort
}