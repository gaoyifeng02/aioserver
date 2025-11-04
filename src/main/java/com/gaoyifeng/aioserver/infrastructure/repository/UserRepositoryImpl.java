package com.gaoyifeng.aioserver.infrastructure.repository;

import com.gaoyifeng.aioserver.domain.auth.adapter.port.IUserRepository;
import com.gaoyifeng.aioserver.domain.auth.model.entity.User;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.UserMapper;
import com.gaoyifeng.aioserver.infrastructure.dao.po.UserPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 用户仓储实现 - Infrastructure层
 * 实现用户数据访问的具体逻辑
 */
@Slf4j
@Repository
public class UserRepositoryImpl implements IUserRepository {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        try {
            UserPO userPO = userMapper.findByUsername(username);
            if (userPO == null) {
                log.debug("根据用户名未找到用户：{}", username);
                return null;
            }
            return convertToEntity(userPO);
        } catch (Exception e) {
            log.error("根据用户名查找用户异常：{}", username, e);
            return null;
        }
    }

    @Override
    public User findById(String id) {
        try {
            UserPO userPO = userMapper.findById(id);
            if (userPO == null) {
                log.debug("根据ID未找到用户：{}", id);
                return null;
            }
            return convertToEntity(userPO);
        } catch (Exception e) {
            log.error("根据ID查找用户异常：{}", id, e);
            return null;
        }
    }

    @Override
    public void save(User user) {
        try {
            if (user == null) {
                throw new IllegalArgumentException("用户实体不能为空");
            }

            UserPO userPO = convertToPO(user);

            // 检查用户是否已存在
            UserPO existingUser = userMapper.findById(user.getId());
            if (existingUser == null) {
                // 新增用户
                userMapper.insert(userPO);
                log.debug("新增用户成功：{}", user.getSummary());
            } else {
                // 更新用户
                userMapper.update(userPO);
                log.debug("更新用户成功：{}", user.getSummary());
            }
        } catch (Exception e) {
            log.error("保存用户异常：{}", user.getSummary(), e);
            throw new RuntimeException("保存用户失败", e);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        try {
            int count = userMapper.countByUsername(username);
            return count > 0;
        } catch (Exception e) {
            log.error("检查用户名是否存在异常：{}", username, e);
            return false;
        }
    }

    @Override
    public boolean existsById(String id) {
        try {
            int count = userMapper.countById(id);
            return count > 0;
        } catch (Exception e) {
            log.error("检查用户ID是否存在异常：{}", id, e);
            return false;
        }
    }

    /**
     * 将PO转换为Entity
     */
    private User convertToEntity(UserPO userPO) {
        if (userPO == null) {
            return null;
        }

        User user = new User();
        user.setId(userPO.getId());
        user.setUsername(userPO.getUsername());
        user.setPassword(userPO.getPassword());
        return user;
    }

    /**
     * 将Entity转换为PO
     */
    private UserPO convertToPO(User user) {
        if (user == null) {
            return null;
        }

        UserPO userPO = new UserPO();
        userPO.setId(user.getId());
        userPO.setUsername(user.getUsername());
        userPO.setPassword(user.getPassword());

        // 设置时间字段
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        userPO.setCreateTime(now);
        userPO.setUpdateTime(now);

        return userPO;
    }
}