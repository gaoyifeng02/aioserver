<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElDialog, ElTable, ElTableColumn, ElTag, ElButton, ElEmpty, ElSelect, ElOption } from 'element-plus'
import { Plus, Edit, Delete, RefreshRight } from '@element-plus/icons-vue'
import { assetApi } from '../services/api'

const router = useRouter()

// 固定收支列表
const recurringTransactions = ref([])

// 加载状态
const loading = ref(true)

// 对话框状态
const dialogVisible = ref(false)
const currentTransactionId = ref('')

// 获取固定收支列表
const getRecurringTransactions = async () => {
  try {
    loading.value = true
    const response = await assetApi.getRecurringTransactions()
    recurringTransactions.value = response.data || []
  } catch (error) {
    console.error('获取固定收支列表失败:', error)
    ElMessage.error('获取固定收支列表失败，请稍后重试')
    recurringTransactions.value = []
  } finally {
    loading.value = false
  }
}

// 跳转到创建固定收支页面
const goToCreate = () => {
  router.push('/asset/recurring-transactions/create')
}

// 跳转到编辑固定收支页面
const goToEdit = (id) => {
  router.push(`/asset/recurring-transactions/${id}/edit`)
}

// 显示删除确认对话框
const showDeleteDialog = (id) => {
  currentTransactionId.value = id
  dialogVisible.value = true
}

// 删除固定收支
const deleteTransaction = async () => {
  try {
    await assetApi.deleteRecurringTransaction(currentTransactionId.value)
    ElMessage.success('删除成功')
    dialogVisible.value = false
    // 重新获取列表
    getRecurringTransactions()
  } catch (error) {
    console.error('删除固定收支失败:', error)
    ElMessage.error('删除固定收支失败，请稍后重试')
  }
}

// 切换固定收支状态
const toggleStatus = async (id, currentStatus) => {
  try {
    const newStatus = currentStatus === 'ACTIVE' ? 'DISABLED' : 'ACTIVE'
    await assetApi.toggleRecurringTransactionStatus(id, newStatus)
    ElMessage.success('状态更新成功')
    // 重新获取列表
    getRecurringTransactions()
  } catch (error) {
    console.error('更新状态失败:', error)
    ElMessage.error('更新状态失败，请稍后重试')
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

// 获取触发类型文本
const getTriggerTypeText = (type) => {
  const typeMap = {
    'DAILY': '按日',
    'WEEKLY': '按周',
    'MONTHLY': '按月',
    'YEARLY': '按年'
  }
  return typeMap[type] || type
}

// 获取状态文本
const getStatusText = (status) => {
  const statusMap = {
    'ACTIVE': '启用',
    'DISABLED': '禁用',
    'ENDED': '已结束'
  }
  return statusMap[status] || status
}

// 获取状态标签类型
const getStatusTagType = (status) => {
  const typeMap = {
    'ACTIVE': 'success',
    'DISABLED': 'warning',
    'ENDED': 'info'
  }
  return typeMap[status] || 'info'
}

// 页面挂载时获取数据
onMounted(() => {
  getRecurringTransactions()
})
</script>

<template>
  <div class="recurring-transactions-list">
    <div class="page-header">
      <h2 class="page-title">固定收支管理</h2>
      <el-button type="primary" @click="goToCreate">
        <el-icon><Plus /></el-icon>
        创建固定收支
      </el-button>
    </div>
    
    <el-card class="card" shadow="hover">
      <el-table
        :data="recurringTransactions"
        :loading="loading"
        style="width: 100%"
        stripe
        border
      >
        <el-table-column prop="transactionName" label="交易名称" width="200"></el-table-column>
        <el-table-column prop="transactionType" label="交易类型" width="120">
          <template #default="scope">
            <el-tag :type="getTransactionTypeTagType(scope.row.transactionType)">
              {{ getTransactionTypeText(scope.row.transactionType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="amount" label="金额" width="150">
          <template #default="scope">
            {{ formatAmount(scope.row.amount) }}
          </template>
        </el-table-column>
        <el-table-column prop="triggerType" label="触发类型" width="120">
          <template #default="scope">
            <el-tag type="info">{{ getTriggerTypeText(scope.row.triggerType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="triggerValue" label="触发值" width="150"></el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="getStatusTagType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="endDate" label="自动停止日期" width="150">
          <template #default="scope">
            {{ scope.row.endDate ? new Date(scope.row.endDate).toLocaleDateString() : '无' }}
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="scope">
            <el-button
              type="success"
              size="small"
              @click="toggleStatus(scope.row.id, scope.row.status)"
              style="margin-right: 5px"
            >
              <el-icon><RefreshRight /></el-icon>
              {{ scope.row.status === 'ACTIVE' ? '禁用' : '启用' }}
            </el-button>
            <el-button
              type="primary"
              size="small"
              @click="goToEdit(scope.row.id)"
              style="margin-right: 5px"
            >
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="showDeleteDialog(scope.row.id)"
            >
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 空数据提示 -->
      <template #empty>
        <el-empty description="暂无固定收支数据"></el-empty>
      </template>
    </el-card>
    
    <!-- 删除确认对话框 -->
    <el-dialog
      v-model="dialogVisible"
      title="删除确认"
      width="30%"
    >
      <span>确定要删除该固定收支吗？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="danger" @click="deleteTransaction">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.recurring-transactions-list {
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
</style>