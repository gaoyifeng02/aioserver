package com.gaoyifeng.aioserver.api.service;

import com.gaoyifeng.aioserver.api.service.ITransactionFlowService;
import com.gaoyifeng.aioserver.domain.adapter.repository.ITransactionFlowRepository;
import com.gaoyifeng.aioserver.domain.model.entity.TransactionFlowEntity;
import com.gaoyifeng.aioserver.infrastructure.threadlocal.LoginUserContext;
import com.gaoyifeng.aioserver.types.vo.TransactionFlowVO;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 资产流水服务实现
 */
@Service
public class TransactionFlowService implements ITransactionFlowService {

    @Resource
    private ITransactionFlowRepository transactionFlowRepository;

    @Override
    public TransactionFlowVO getById(String id) {
        String userId = LoginUserContext.getUserId();
        TransactionFlowEntity entity = transactionFlowRepository.queryById(id);

        if (entity == null) {
            return null;
        }

        if (!entity.getUserId().equals(userId)) {
            throw new RuntimeException("无权查看此流水");
        }

        return convertToVO(entity);
    }

    @Override
    public List<TransactionFlowVO> list() {
        String userId = LoginUserContext.getUserId();
        List<TransactionFlowEntity> entityList = transactionFlowRepository.queryByUserId(userId);

        List<TransactionFlowVO> voList = new ArrayList<>();
        for (TransactionFlowEntity entity : entityList) {
            voList.add(convertToVO(entity));
        }

        return voList;
    }

    private TransactionFlowVO convertToVO(TransactionFlowEntity entity) {
        TransactionFlowVO vo = new TransactionFlowVO();
        vo.setId(entity.getId());
        vo.setUserId(entity.getUserId());
        vo.setFlowType(entity.getFlowType());
        vo.setSourceId(entity.getSourceId());
        vo.setTransactionType(entity.getTransactionType());
        vo.setTransactionName(entity.getTransactionName());
        vo.setAmount(entity.getAmount());
        vo.setBalanceBefore(entity.getBalanceBefore());
        vo.setBalanceAfter(entity.getBalanceAfter());
        vo.setTransactionDatetime(entity.getTransactionDatetime());
        vo.setDescription(entity.getDescription());
        vo.setCreateTime(entity.getCreateTime());
        return vo;
    }
}
