<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElDialog, ElTable, ElTableColumn, ElTag, ElButton, ElEmpty } from 'element-plus'
import { Plus, Edit, Delete } from '@element-plus/icons-vue'
import { assetApi } from '../services/api'

const router = useRouter()

// 存款计划列表
const savingsList = ref([])

// 加载状态
const loading = ref(true)

// 对话框状态
const dialogVisible = ref(false)
const currentSavingsId = ref('')

// 获取存款计划列表
const getSavingsList = async () => {
  try {
    loading.value = true
    const response = await assetApi.getSavingsList()
    savingsList.value = response.data
  } catch (error) {
    console.error('获取存款计划列表失败:', error)
    ElMessage.error('获取存款计划列表失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

// 跳转到创建存款计划页面
const goToCreate = () => {
  router.push('/asset/savings/create')
}

// 跳转到编辑存款计划页面
const goToEdit = (id) => {
  router.push(`/asset/savings/${id}/edit`)
}

// 显示删除确认对话框
const showDeleteDialog = (id) => {
  currentSavingsId.value = id
  dialogVisible.value = true
}

// 删除存款计划
const deleteSavings = async () => {
  try {
    await assetApi.deleteSavings(currentSavingsId.value)
    ElMessage.success('删除成功')
    dialogVisible.value = false
    // 重新获取列表
    getSavingsList()
  } catch (error) {
    console.error('删除存款计划失败:', error)
    ElMessage.error('删除存款计划失败，请稍后重试')
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

// 格式化利率
const formatRate = (rate) => {
  return `${(rate * 100).toFixed(2)}%`
}

// 获取状态文本
const getStatusText = (status) => {
  const statusMap = {
    'ACTIVE': '活跃',
    'PAUSED': '暂停',
    'COMPLETED': '已完成'
  }
  return statusMap[status] || status
}

// 获取状态标签类型
const getStatusTagType = (status) => {
  const typeMap = {
    'ACTIVE': 'success',
    'PAUSED': 'warning',
    'COMPLETED': 'info'
  }
  return typeMap[status] || 'info'
}

// 页面挂载时获取数据
onMounted(() => {
  getSavingsList()
})
</script>

<template>
  <div class="savings-list">
    <div class="page-header">
      <h2 class="page-title">存款计划</h2>
      <el-button type="primary" @click="goToCreate">
        <el-icon><Plus /></el-icon>
        创建存款计划
      </el-button>
    </div>
    
    <el-card class="card" shadow="hover">
      <el-table
        :data="savingsList"
        :loading="loading"
        style="width: 100%"
        stripe
        border
      >
        <el-table-column prop="planName" label="计划名称" width="200"></el-table-column>
        <el-table-column prop="startDate" label="开始时间" width="180">
          <template #default="scope">
            {{ new Date(scope.row.startDate).toLocaleDateString() }}
          </template>
        </el-table-column>
        <el-table-column prop="monthlyDepositAmount" label="每月存入金额" width="150">
          <template #default="scope">
            {{ formatAmount(scope.row.monthlyDepositAmount) }}
          </template>
        </el-table-column>
        <el-table-column prop="interestRate" label="利率" width="100">
          <template #default="scope">
            {{ formatRate(scope.row.interestRate) }}
          </template>
        </el-table-column>
        <el-table-column prop="interestCalculationType" label="利息计算规则" width="150">
          <template #default="scope">
            <span v-if="scope.row.interestCalculationType === 'YEARLY'">按年</span>
            <span v-else-if="scope.row.interestCalculationType === 'MONTHLY'">按月</span>
            <span v-else-if="scope.row.interestCalculationType === 'DAILY'">按日</span>
            <span v-else>{{ scope.row.interestCalculationType }}</span>
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
        <el-empty description="暂无存款计划数据"></el-empty>
      </template>
    </el-card>
    
    <!-- 删除确认对话框 -->
    <el-dialog
      v-model="dialogVisible"
      title="删除确认"
      width="30%"
    >
      <span>确定要删除该存款计划吗？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="danger" @click="deleteSavings">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.savings-list {
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