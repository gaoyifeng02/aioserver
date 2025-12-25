<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElDialog, ElTable, ElTableColumn, ElTag, ElButton, ElEmpty, ElDatePicker, ElInput, ElForm, ElFormItem, ElSelect, ElOption } from 'element-plus'
import { Plus, Edit, Delete, Search } from '@element-plus/icons-vue'
import { assetApi } from '../services/api'

const router = useRouter()

// 临时收支列表
const temporaryTransactions = ref([])

// 加载状态
const loading = ref(true)

// 对话框状态
const dialogVisible = ref(false)
const currentTransactionId = ref('')

// 搜索表单
const searchForm = ref({
  transactionType: '',
  keyword: '',
  startDate: null,
  endDate: null
})

// 获取临时收支列表
const getTemporaryTransactions = async () => {
  try {
    loading.value = true
    const response = await assetApi.getTemporaryTransactions(searchForm.value)
    temporaryTransactions.value = response.data || []
  } catch (error) {
    console.error('获取临时收支列表失败:', error)
    ElMessage.error('获取临时收支列表失败，请稍后重试')
    temporaryTransactions.value = []
  } finally {
    loading.value = false
  }
}

// 跳转到创建临时收支页面
const goToCreate = () => {
  router.push('/asset/temporary-transactions/create')
}

// 跳转到编辑临时收支页面
const goToEdit = (id) => {
  router.push(`/asset/temporary-transactions/${id}/edit`)
}

// 显示删除确认对话框
const showDeleteDialog = (id) => {
  currentTransactionId.value = id
  dialogVisible.value = true
}

// 删除临时收支
const deleteTransaction = async () => {
  try {
    await assetApi.deleteTemporaryTransaction(currentTransactionId.value)
    ElMessage.success('删除成功')
    dialogVisible.value = false
    // 重新获取列表
    getTemporaryTransactions()
  } catch (error) {
    console.error('删除临时收支失败:', error)
    ElMessage.error('删除临时收支失败，请稍后重试')
  }
}

// 执行搜索
const handleSearch = () => {
  getTemporaryTransactions()
}

// 重置搜索表单
const resetSearchForm = () => {
  searchForm.value = {
    transactionType: '',
    keyword: '',
    startDate: null,
    endDate: null
  }
  getTemporaryTransactions()
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

// 页面挂载时获取数据
onMounted(() => {
  getTemporaryTransactions()
})
</script>

<template>
  <div class="temporary-transactions-list">
    <div class="page-header">
      <h2 class="page-title">临时收支记录</h2>
      <el-button type="primary" @click="goToCreate">
        <el-icon><Plus /></el-icon>
        创建临时收支
      </el-button>
    </div>
    
    <!-- 搜索表单 -->
    <el-card class="search-card" shadow="hover">
      <el-form :model="searchForm" inline>
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
        :data="temporaryTransactions"
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
        <el-table-column prop="transactionDatetime" label="交易时间" width="200">
          <template #default="scope">
            {{ new Date(scope.row.transactionDatetime).toLocaleString() }}
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
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
        <el-empty description="暂无临时收支数据"></el-empty>
      </template>
    </el-card>
    
    <!-- 删除确认对话框 -->
    <el-dialog
      v-model="dialogVisible"
      title="删除确认"
      width="30%"
    >
      <span>确定要删除该临时收支记录吗？</span>
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
.temporary-transactions-list {
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
</style>