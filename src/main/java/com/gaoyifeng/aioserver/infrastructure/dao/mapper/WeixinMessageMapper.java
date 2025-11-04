package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.WeixinMessagePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 微信消息Mapper接口
 *
 * @author gaoyifeng
 */
@Mapper
public interface WeixinMessageMapper {

    /**
     * 根据对话ID查找消息列表
     * @param conversationId 对话ID
     * @return 消息列表
     */
    List<WeixinMessagePO> selectByConversationId(@Param("conversationId") String conversationId);

    /**
     * 根据OpenID和消息方向查找消息列表
     * @param openId 用户OpenID
     * @param msgDirection 消息方向
     * @return 消息列表
     */
    List<WeixinMessagePO> selectByOpenIdAndDirection(@Param("openId") String openId,
                                                   @Param("msgDirection") Integer msgDirection);

    /**
     * 批量插入消息
     * @param messageList 消息列表
     * @return 插入数量
     */
    int batchInsert(@Param("messageList") List<WeixinMessagePO> messageList);
}