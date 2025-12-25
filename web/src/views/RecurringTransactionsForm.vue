<script setup>
import { ref, onMounted, computed, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElForm, ElFormItem, ElInput, ElSelect, ElOption, ElButton, ElDatePicker, ElInputNumber } from 'element-plus'
import { assetApi } from '../services/api'

const router = useRouter()
const route = useRoute()

// 判断是编辑还是创建模式
const isEditMode = computed(() => {
  return route.name === 'EditRecurringTransaction'
})

// 表单数据
const form = ref({
  transactionType: 'INCOME',
  transactionName: '',
  amount: 0,
  triggerType: 'MONTHLY',
  triggerValue: '',
  status: 'ACTIVE',
  endDate: null
})

// 表单验证规则
const rules = {
  transactionName: [
    { required: true, message: '请输入交易名称', trigger: 'blur' }
  ],
  transactionType: [
    { required: true, message: '请选择交易类型', trigger: 'change' }
  ],
  amount: [
    { required: true, message: '请输入金额', trigger: 'blur' },
    { type: 'number', min: 0.01, message: '金额必须大于0', trigger: 'blur' }
  ],
  triggerType: [
    { required: true, message: '请选择触发类型', trigger: 'change' }
  ],
  triggerValue: [
    {
      validator: (rule, value, callback) => {
        if (form.value.triggerType === 'DAILY') {
          // 按日类型时不要求填写
          callback()
        } else if (!value) {
          // 其他类型必须填写
          callback(new Error('请输入触发值'))
        } else {
          callback()
        }
      },
      trigger: ['blur', 'change']
    }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
}

// 加载状态
const loading = ref(false)

// 表单引用
const recurringFormRef = ref(null)

// 触发值占位符
const triggerValuePlaceholder = computed(() => {
  const placeholderMap = {
    'DAILY': '按日无需填写',
    'WEEKLY': '1,2,3,4,5,6,7(周一到周日)',
    'MONTHLY': '1-31(日期)',
    'YEARLY': 'MM-DD(如01-15表示1月15日)'
  }
  return placeholderMap[form.value.triggerType] || ''
})

// 监听触发类型变化，重置触发值
watch(() => form.value.triggerType, (newType) => {
  if (newType === 'DAILY') {
    form.value.triggerValue = ''
  } else if (newType === 'MONTHLY') {
    form.value.triggerValue = new Date().getDate().toString()
  } else if (newType === 'YEARLY') {
    const now = new Date()
    form.value.triggerValue = `${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}`
  }
})

// 获取单个固定收支数据（编辑模式下）
const getRecurringTransactionById = async (id) => {
  try {
    loading.value = true
    const response = await assetApi.getRecurringTransactionById(id)
    const data = response.data
    // 格式化日期
    if (data.endDate) {
      data.endDate = data.endDate.split('T')[0]
    }
    form.value = data
  } catch (error) {
    console.error('获取固定收支数据失败:', error)
    ElMessage.error('获取固定收支数据失败，请稍后重试')
    router.push('/asset/recurring-transactions')
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
          await assetApi.updateRecurringTransaction(route.params.id, form.value)
          ElMessage.success('更新成功')
        } else {
          // 创建模式
          await assetApi.createRecurringTransaction(form.value)
          ElMessage.success('创建成功')
        }
        
        // 跳转到列表页面
        router.push('/asset/recurring-transactions')
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
  router.push('/asset/recurring-transactions')
}

// 页面挂载时，如果是编辑模式则获取数据
onMounted(() => {
  if (isEditMode.value) {
    getRecurringTransactionById(route.params.id)
  }
})
</script>

<template>
  <div class="recurring-transactions-form">
    <div class="page-header">
      <h2 class="page-title">{{ isEditMode ? '编辑固定收支' : '创建固定收支' }}</h2>
    </div>
    
    <el-card class="card" shadow="hover">
      <el-form
        :model="form"
        :rules="rules"
        ref="recurringFormRef"
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
          <el-input v-model="form.transactionName" placeholder="请输入交易名称（如:月薪、房租）"></el-input>
        </el-form-item>
        
        <el-form-item label="金额" prop="amount">
          <el-input-number
            v-model="form.amount"
            :min="0.01"
            :step="0.01"
            :precision="2"
            placeholder="请输入金额"
            style="width: 100%"
          >
            <template #prefix>
              <span>¥</span>
            </template>
          </el-input-number>
        </el-form-item>
        
        <el-form-item label="触发类型" prop="triggerType">
          <el-select v-model="form.triggerType" placeholder="请选择触发类型">
            <el-option label="按日" value="DAILY"></el-option>
            <el-option label="按周" value="WEEKLY"></el-option>
            <el-option label="按月" value="MONTHLY"></el-option>
            <el-option label="按年" value="YEARLY"></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="触发值" prop="triggerValue">
          <el-input v-model="form.triggerValue" :placeholder="triggerValuePlaceholder"></el-input>
        </el-form-item>
        
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="启用" value="ACTIVE"></el-option>
            <el-option label="禁用" value="DISABLED"></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="自动停止日期">
          <el-date-picker
            v-model="form.endDate"
            type="date"
            placeholder="选择自动停止日期（可选）"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          ></el-date-picker>
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            @click="submitForm(recurringFormRef)"
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
.recurring-transactions-form {
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