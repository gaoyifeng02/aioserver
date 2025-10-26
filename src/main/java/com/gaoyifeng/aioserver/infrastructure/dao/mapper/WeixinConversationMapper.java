package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gaoyifeng.aioserver.infrastructure.dao.po.WeixinConversationPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 微信消息对话Mapper接口
 *
 * @author gaoyifeng
 */
@Mapper
public interface WeixinConversationMapper extends BaseMapper<WeixinConversationPO> {

    /**
     * 根据OpenID查找最近的活跃对话
     * @param openId 用户OpenID
     * @return 对话PO对象
     */
    WeixinConversationPO selectLatestByOpenId(@Param("openId") String openId);

    /**
     * 根据OpenID查找所有对话
     * @param openId 用户OpenID
     * @return 对话列表
     */
    List<WeixinConversationPO> selectByOpenId(@Param("openId") String openId);

    /**
     * 更新对话最后消息时间和消息数量
     * @param conversationId 对话ID
     * @param lastMessageTime 最后消息时间
     * @return 更新结果
     */
    int updateLastMessageInfo(@Param("conversationId") String conversationId,
                             @Param("lastMessageTime") LocalDateTime lastMessageTime);
}