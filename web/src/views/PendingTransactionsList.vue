<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElDialog, ElTable, ElTableColumn, ElTag, ElButton, ElEmpty, ElInput, ElForm, ElFormItem, ElSelect, ElOption, ElInputNumber } from 'element-plus'
import { Plus, Edit, Delete, Search } from '@element-plus/icons-vue'
import { assetApi } from '../services/api'

const router = useRouter()

// 待入账列表
const pendingTransactions = ref([])

// 加载状态
const loading = ref(true)

// 对话框状态
const dialogVisible = ref(false)
const currentTransactionId = ref('')

// 部分入账对话框
const partialPostDialogVisible = ref(false)
const partialPostAmount = ref(0)

// 搜索表单
const searchForm = ref({
  status: '',
  keyword: ''
})

// 获取待入账列表
const getPendingTransactions = async () => {
  try {
    loading.value = true
    const response = await assetApi.getPendingTransactions(searchForm.value)
    pendingTransactions.value = response.data || []
  } catch (error) {
    console.error('获取待入账列表失败:', error)
    ElMessage.error('获取待入账列表失败，请稍后重试')
    pendingTransactions.value = []
  } finally {
    loading.value = false
  }
}

// 跳转到创建待入账页面
const goToCreate = () => {
  router.push('/asset/pending-transactions/create')
}

// 跳转到编辑待入账页面
const goToEdit = (id) => {
  router.push(`/asset/pending-transactions/${id}/edit`)
}

// 显示删除确认对话框
const showDeleteDialog = (id) => {
  currentTransactionId.value = id
  dialogVisible.value = true
}

// 删除待入账
const deleteTransaction = async () => {
  try {
    await assetApi.deletePendingTransaction(currentTransactionId.value)
    ElMessage.success('删除成功')
    dialogVisible.value = false
    // 重新获取列表
    getPendingTransactions()
  } catch (error) {
    console.error('删除待入账失败:', error)
    ElMessage.error('删除待入账失败，请稍后重试')
  }
}

// 显示部分入账对话框
const showPartialPostDialog = (id) => {
  currentTransactionId.value = id
  partialPostAmount.value = 0
  partialPostDialogVisible.value = true
}

// 执行部分入账
const handlePartialPost = async () => {
  if (partialPostAmount.value <= 0) {
    ElMessage.warning('请输入有效的入账金额')
    return
  }
  
  try {
    await assetApi.partialPostPendingTransaction(currentTransactionId.value, { amount: partialPostAmount.value })
    ElMessage.success('部分入账成功')
    partialPostDialogVisible.value = false
    // 重新获取列表
    getPendingTransactions()
  } catch (error) {
    console.error('部分入账失败:', error)
    ElMessage.error('部分入账失败，请稍后重试')
  }
}

// 执行完成入账
const handleCompletePost = async (id) => {
  try {
    await assetApi.completePostPendingTransaction(id)
    ElMessage.success('完成入账成功')
    // 重新获取列表
    getPendingTransactions()
  } catch (error) {
    console.error('完成入账失败:', error)
    ElMessage.error('完成入账失败，请稍后重试')
  }
}

// 执行搜索
const handleSearch = () => {
  getPendingTransactions()
}

// 重置搜索表单
const resetSearchForm = () => {
  searchForm.value = {
    status: '',
    keyword: ''
  }
  getPendingTransactions()
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

// 获取状态文本
const getStatusText = (status) => {
  const statusMap = {
    'PENDING': '待入账',
    'PARTIAL': '部分入账',
    'COMPLETED': '已完成'
  }
  return statusMap[status] || status
}

// 获取状态标签类型
const getStatusTagType = (status) => {
  const typeMap = {
    'PENDING': 'warning',
    'PARTIAL': 'info',
    'COMPLETED': 'success'
  }
  return typeMap[status] || 'info'
}

// 页面挂载时获取数据
onMounted(() => {
  getPendingTransactions()
})
</script>

<template>
  <div class="pending-transactions-list">
    <div class="page-header">
      <h2 class="page-title">待入账管理</h2>
      <el-button type="primary" @click="goToCreate">
        <el-icon><Plus /></el-icon>
        创建待入账
      </el-button>
    </div>
    
    <!-- 搜索表单 -->
    <el-card class="search-card" shadow="hover">
      <el-form :model="searchForm" inline>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="全部" style="width: 120px">
            <el-option label="全部" value=""></el-option>
            <el-option label="待入账" value="PENDING"></el-option>
            <el-option label="部分入账" value="PARTIAL"></el-option>
            <el-option label="已完成" value="COMPLETED"></el-option>
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
        :data="pendingTransactions"
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
        <el-table-column prop="totalAmount" label="总金额" width="150">
          <template #default="scope">
            {{ formatAmount(scope.row.totalAmount) }}
          </template>
        </el-table-column>
        <el-table-column prop="remainingAmount" label="剩余金额" width="150">
          <template #default="scope">
            <span :class="scope.row.remainingAmount === 0 ? 'completed-amount' : 'pending-amount'">
              {{ formatAmount(scope.row.remainingAmount) }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="getStatusTagType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column label="操作" width="280" fixed="right">
          <template #default="scope">
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
              type="success"
              size="small"
              @click="showPartialPostDialog(scope.row.id)"
              :disabled="scope.row.status === 'COMPLETED'"
              style="margin-right: 5px"
            >
              部分入账
            </el-button>
            <el-button
              type="warning"
              size="small"
              @click="handleCompletePost(scope.row.id)"
              :disabled="scope.row.status === 'COMPLETED'"
              style="margin-right: 5px"
            >
              完成入账
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
        <el-empty description="暂无待入账数据"></el-empty>
      </template>
    </el-card>
    
    <!-- 删除确认对话框 -->
    <el-dialog
      v-model="dialogVisible"
      title="删除确认"
      width="30%"
    >
      <span>确定要删除该待入账记录吗？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="danger" @click="deleteTransaction">确定</el-button>
        </span>
      </template>
    </el-dialog>
    
    <!-- 部分入账对话框 -->
    <el-dialog
      v-model="partialPostDialogVisible"
      title="部分入账"
      width="40%"
    >
      <el-form :model="{ amount: partialPostAmount }" label-width="100px">
        <el-form-item label="入账金额" required>
          <el-input-number
            v-model="partialPostAmount"
            :min="0.01"
            :step="0.01"
            :precision="2"
            placeholder="请输入入账金额"
            style="width: 100%"
          >
            <template #prefix>
              <span>¥</span>
            </template>
          </el-input-number>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="partialPostDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handlePartialPost">确定入账</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.pending-transactions-list {
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

.pending-amount {
  color: #e6a23c;
  font-weight: 500;
}

.completed-amount {
  color: #67c23a;
  font-weight: 500;
}
</style>