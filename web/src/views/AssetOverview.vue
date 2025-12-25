<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElEmpty } from 'element-plus'
import { Wallet, Money, DataAnalysis } from '@element-plus/icons-vue'
import { assetApi } from '../services/api'

// 资产数据
const assetData = ref({
  totalBalance: 0,
  totalSavings: 0,
  totalAssets: 0
})

// 加载状态
const loading = ref(true)

// 获取资产总览数据
const getAssetOverview = async () => {
  try {
    loading.value = true
    const response = await assetApi.getAccountOverview()
    assetData.value = response.data
  } catch (error) {
    console.error('获取资产总览失败:', error)
    ElMessage.error('获取资产总览失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

// 格式化金额
const formatAmount = (amount) => {
  return new Intl.NumberFormat('zh-CN', {
    style: 'currency',
    currency: 'CNY',
    minimumFractionDigits: 2
  }).format(amount)
}

// 页面挂载时获取数据
onMounted(() => {
  getAssetOverview()
})
</script>

<template>
  <div class="asset-overview">
    <h2 class="page-title">资产总览</h2>
    
    <el-row :gutter="20" class="asset-cards">
      <el-col :xs="24" :sm="12" :md="8">
        <el-card class="asset-card" shadow="hover" :loading="loading">
          <div class="card-content">
            <div class="card-header">
              <span class="card-label">现金余额</span>
              <el-icon class="card-icon"><Wallet /></el-icon>
            </div>
            <div class="card-value">{{ formatAmount(assetData.totalBalance) }}</div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :xs="24" :sm="12" :md="8">
        <el-card class="asset-card" shadow="hover" :loading="loading">
          <div class="card-content">
            <div class="card-header">
              <span class="card-label">总存款</span>
              <el-icon class="card-icon"><Money /></el-icon>
            </div>
            <div class="card-value">{{ formatAmount(assetData.totalSavings) }}</div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :xs="24" :sm="12" :md="8">
        <el-card class="asset-card" shadow="hover" :loading="loading">
          <div class="card-content">
            <div class="card-header">
              <span class="card-label">总资产</span>
              <el-icon class="card-icon"><DataAnalysis /></el-icon>
            </div>
            <div class="card-value">{{ formatAmount(assetData.totalAssets) }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-card class="card" :loading="loading">
      <template #header>
        <div class="card-header-title">
          <span>资产分布</span>
        </div>
      </template>
      
      <div class="asset-chart">
        <el-empty description="暂无资产分布数据"></el-empty>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.asset-overview {
  width: 100%;
}

.page-title {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 20px;
  color: #333;
}

.asset-cards {
  margin-bottom: 20px;
}

.asset-card {
  border-radius: 8px;
  transition: all 0.3s ease;
}

.asset-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.card-content {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.card-label {
  font-size: 14px;
  color: #666;
  font-weight: 500;
}

.card-icon {
  font-size: 24px;
  color: #1890ff;
}

.card-value {
  font-size: 28px;
  font-weight: 600;
  color: #333;
}

.card-header-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.asset-chart {
  height: 300px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>