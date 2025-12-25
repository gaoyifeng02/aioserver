<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElForm, ElFormItem, ElInput, ElSelect, ElOption, ElButton, ElInputNumber } from 'element-plus'
import { assetApi } from '../services/api'

const router = useRouter()
const route = useRoute()

// 判断是编辑还是创建模式
const isEditMode = computed(() => {
  return route.name === 'EditPendingTransaction'
})

// 表单数据
const form = ref({
  transactionType: 'INCOME',
  transactionName: '',
  totalAmount: 0,
  remainingAmount: 0,
  status: 'PENDING',
  description: ''
})

// 表单验证规则
const rules = {
  transactionName: [
    { required: true, message: '请输入交易名称', trigger: 'blur' }
  ],
  transactionType: [
    { required: true, message: '请选择交易类型', trigger: 'change' }
  ],
  totalAmount: [
    { required: true, message: '请输入总金额', trigger: 'blur' },
    { type: 'number', min: 0.01, message: '总金额必须大于0', trigger: 'blur' }
  ]
}

// 加载状态
const loading = ref(false)

// 表单引用
const pendingFormRef = ref(null)

// 监听总金额变化，同步更新剩余金额（仅在创建模式下）
const handleTotalAmountChange = (value) => {
  if (!isEditMode.value) {
    form.value.remainingAmount = value
  }
}

// 获取单个待入账数据（编辑模式下）
const getPendingTransactionById = async (id) => {
  try {
    loading.value = true
    const response = await assetApi.getPendingTransactionById(id)
    const data = response.data
    form.value = data
  } catch (error) {
    console.error('获取待入账数据失败:', error)
    ElMessage.error('获取待入账数据失败，请稍后重试')
    router.push('/asset/pending-transactions')
  } finally {
    loading.value = false
  }
}

// 提交表单
const submitForm = async (formEl) => {
  if (!formEl) return
  
  // 表单验证
  await formEl.validate(async (valid) => {
    if (valid) {
      try {
        loading.value = true
        
        if (isEditMode.value) {
          // 编辑模式
          await assetApi.updatePendingTransaction(route.params.id, form.value)
          ElMessage.success('更新成功')
        } else {
          // 创建模式
          await assetApi.createPendingTransaction(form.value)
          ElMessage.success('创建成功')
        }
        
        // 跳转到列表页面
        router.push('/asset/pending-transactions')
      } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error('操作失败，请稍后重试')
      } finally {
        loading.value = false
      }
    }
  })
}

// 取消操作
const cancel = () => {
  router.push('/asset/pending-transactions')
}

// 页面挂载时，如果是编辑模式则获取数据
onMounted(() => {
  if (isEditMode.value) {
    getPendingTransactionById(route.params.id)
  }
})
</script>

<template>
  <div class="pending-transactions-form">
    <div class="page-header">
      <h2 class="page-title">{{ isEditMode ? '编辑待入账' : '创建待入账' }}</h2>
    </div>
    
    <el-card class="card" shadow="hover">
      <el-form
        :model="form"
        :rules="rules"
        ref="pendingFormRef"
        label-width="150px"
        :disabled="loading"
      >
        <el-form-item label="交易类型" prop="transactionType">
          <el-select v-model="form.transactionType" placeholder="请选择交易类型">
            <el-option label="收入" value="INCOME"></el-option>
            <el-option label="支出" value="EXPENSE"></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="交易名称" prop="transactionName">
          <el-input v-model="form.transactionName" placeholder="请输入交易名称（如:向张三借款）"></el-input>
        </el-form-item>
        
        <el-form-item label="总金额" prop="totalAmount">
          <el-input-number
            v-model="form.totalAmount"
            :min="0.01"
            :step="0.01"
            :precision="2"
            placeholder="请输入总金额"
            style="width: 100%"
            @change="handleTotalAmountChange"
          >
            <template #prefix>
              <span>¥</span>
            </template>
          </el-input-number>
        </el-form-item>
        
        <el-form-item label="剩余待入账金额" prop="remainingAmount" :disabled="isEditMode">
          <el-input-number
            v-model="form.remainingAmount"
            :min="0"
            :step="0.01"
            :precision="2"
            placeholder="剩余待入账金额"
            style="width: 100%"
            :disabled="isEditMode"
          >
            <template #prefix>
              <span>¥</span>
            </template>
          </el-input-number>
          <div v-if="isEditMode" style="margin-top: 5px; font-size: 12px; color: #999;">
            编辑模式下剩余金额不可修改，可通过部分入账或完成入账功能更新
          </div>
        </el-form-item>
        
        <el-form-item label="状态" prop="status" :disabled="true">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="待入账" value="PENDING"></el-option>
            <el-option label="部分入账" value="PARTIAL"></el-option>
            <el-option label="已完成" value="COMPLETED"></el-option>
          </el-select>
          <div style="margin-top: 5px; font-size: 12px; color: #999;">
            状态由系统自动更新，不可手动修改
          </div>
        </el-form-item>
        
        <el-form-item label="描述">
          <el-input
            v-model="form.description"
            type="textarea"
            rows="4"
            placeholder="请输入描述（可选）"
          ></el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            @click="submitForm(pendingFormRef)"
            style="margin-right: 10px"
          >
            {{ isEditMode ? '更新' : '创建' }}
          </el-button>
          <el-button @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
.pending-transactions-form {
  width: 100%;
}

.page-header {
  margin-bottom: 20px;
}

.page-title {
  font-size: 20px;
  font-weight: 600;
  margin: 0;
  color: #333;
}
</style>