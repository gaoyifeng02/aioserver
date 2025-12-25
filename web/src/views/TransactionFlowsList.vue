<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElTable, ElTableColumn, ElTag, ElButton, ElEmpty, ElDatePicker, ElInput, ElForm, ElFormItem, ElSelect, ElOption } from 'element-plus'
import { Search, Refresh } from '@element-plus/icons-vue'
import { assetApi } from '../services/api'

const router = useRouter()

// 资产流水列表
const transactionFlows = ref([])

// 加载状态
const loading = ref(true)

// 搜索表单
const searchForm = ref({
  flowType: '',
  transactionType: '',
  keyword: '',
  startDate: null,
  endDate: null
})

// 获取资产流水列表
const getTransactionFlows = async () => {
  try {
    loading.value = true
    const response = await assetApi.getTransactionFlows(searchForm.value)
    transactionFlows.value = response.data || []
  } catch (error) {
    console.error('获取资产流水列表失败:', error)
    ElMessage.error('获取资产流水列表失败，请稍后重试')
    transactionFlows.value = []
  } finally {
    loading.value = false
  }
}

// 执行搜索
const handleSearch = () => {
  getTransactionFlows()
}

// 重置搜索表单
const resetSearchForm = () => {
  searchForm.value = {
    flowType: '',
    transactionType: '',
    keyword: '',
    startDate: null,
    endDate: null
  }
  getTransactionFlows()
}

// 刷新数据
const refreshData = () => {
  getTransactionFlows()
}

// 格式化金额
const formatAmount = (amount) => {
  return new Intl.NumberFormat('zh-CN', {
    style: 'currency',
    currency: 'CNY',
    minimumFractionDigits: 2
  }).format(amount)
}

// 获取流水来源类型文本
const getFlowTypeText = (type) => {
  const typeMap = {
    'RECURRING': '固定收支',
    'TEMPORARY': '临时收支',
    'PENDING': '待入账'
  }
  return typeMap[type] || type
}

// 获取流水来源类型标签类型
const getFlowTypeTagType = (type) => {
  const typeMap = {
    'RECURRING': 'info',
    'TEMPORARY': 'success',
    'PENDING': 'warning'
  }
  return typeMap[type] || 'info'
}

// 获取交易类型文本
const getTransactionTypeText = (type) => {
  const typeMap = {
    'INCOME': '收入',
    'EXPENSE': '支出'
  }
  return typeMap[type] || type
}

// 获取交易类型标签类型
const getTransactionTypeTagType = (type) => {
  const typeMap = {
    'INCOME': 'success',
    'EXPENSE': 'danger'
  }
  return typeMap[type] || 'info'
}

// 页面挂载时获取数据
onMounted(() => {
  getTransactionFlows()
})
</script>

<template>
  <div class="transaction-flows-list">
    <div class="page-header">
      <h2 class="page-title">资产流水查询</h2>
      <el-button type="primary" @click="refreshData">
        <el-icon><Refresh /></el-icon>
        刷新
      </el-button>
    </div>
    
    <!-- 搜索表单 -->
    <el-card class="search-card" shadow="hover">
      <el-form :model="searchForm" inline>
        <el-form-item label="流水类型">
          <el-select v-model="searchForm.flowType" placeholder="全部" style="width: 120px">
            <el-option label="全部" value=""></el-option>
            <el-option label="固定收支" value="RECURRING"></el-option>
            <el-option label="临时收支" value="TEMPORARY"></el-option>
            <el-option label="待入账" value="PENDING"></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="交易类型">
          <el-select v-model="searchForm.transactionType" placeholder="全部" style="width: 120px">
            <el-option label="全部" value=""></el-option>
            <el-option label="收入" value="INCOME"></el-option>
            <el-option label="支出" value="EXPENSE"></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="关键字">
          <el-input
            v-model="searchForm.keyword"
            placeholder="交易名称"
            prefix-icon="Search"
            style="width: 200px"
          ></el-input>
        </el-form-item>
        
        <el-form-item label="交易时间">
          <el-date-picker
            v-model="searchForm.startDate"
            type="date"
            placeholder="开始日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 150px"
          ></el-date-picker>
        </el-form-item>
        
        <el-form-item label="至">
          <el-date-picker
            v-model="searchForm.endDate"
            type="date"
            placeholder="结束日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 150px"
          ></el-date-picker>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="resetSearchForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    
    <!-- 数据列表 -->
    <el-card class="card" shadow="hover" style="margin-top: 20px">
      <el-table
        :data="transactionFlows"
        :loading="loading"
        style="width: 100%"
        stripe
        border
      >
        <el-table-column prop="transactionName" label="交易名称" width="200"></el-table-column>
        <el-table-column prop="flowType" label="流水类型" width="120">
          <template #default="scope">
            <el-tag :type="getFlowTypeTagType(scope.row.flowType)">
              {{ getFlowTypeText(scope.row.flowType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="transactionType" label="交易类型" width="120">
          <template #default="scope">
            <el-tag :type="getTransactionTypeTagType(scope.row.transactionType)">
              {{ getTransactionTypeText(scope.row.transactionType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="amount" label="金额" width="150">
          <template #default="scope">
            <span :class="scope.row.transactionType === 'INCOME' ? 'income-amount' : 'expense-amount'">
              {{ scope.row.transactionType === 'INCOME' ? '+' : '-' }}{{ formatAmount(Math.abs(scope.row.amount)) }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="balance_before" label="交易前余额" width="180">
          <template #default="scope">
            {{ formatAmount(scope.row.balance_before) }}
          </template>
        </el-table-column>
        <el-table-column prop="balance_after" label="交易后余额" width="180">
          <template #default="scope">
            {{ formatAmount(scope.row.balance_after) }}
          </template>
        </el-table-column>
        <el-table-column prop="transaction_datetime" label="交易时间" width="200">
          <template #default="scope">
            {{ new Date(scope.row.transaction_datetime).toLocaleString() }}
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
      </el-table>
      
      <!-- 空数据提示 -->
      <template #empty>
        <el-empty description="暂无资产流水数据"></el-empty>
      </template>
    </el-card>
  </div>
</template>

<style scoped>
.transaction-flows-list {
  width: 100%;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-title {
  font-size: 20px;
  font-weight: 600;
  margin: 0;
  color: #333;
}

.search-card {
  margin-bottom: 20px;
}

.income-amount {
  color: #67c23a;
  font-weight: 500;
}

.expense-amount {
  color: #f56c6c;
  font-weight: 500;
}
</style>