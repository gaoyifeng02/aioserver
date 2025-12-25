<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElForm, ElFormItem, ElInput, ElSelect, ElOption, ElButton, ElDateTimePicker, ElInputNumber } from 'element-plus'
import { assetApi } from '../services/api'

const router = useRouter()
const route = useRoute()

// 判断是编辑还是创建模式
const isEditMode = computed(() => {
  return route.name === 'EditTemporaryTransaction'
})

// 表单数据
const form = ref({
  transactionType: 'INCOME',
  transactionName: '',
  amount: 0,
  transactionDatetime: new Date().toISOString().slice(0, 16), // 格式化为 YYYY-MM-DDTHH:MM
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
  amount: [
    { required: true, message: '请输入金额', trigger: 'blur' },
    { type: 'number', min: 0.01, message: '金额必须大于0', trigger: 'blur' }
  ],
  transactionDatetime: [
    { required: true, message: '请选择交易时间', trigger: 'change' }
  ]
}

// 加载状态
const loading = ref(false)

// 表单引用
const temporaryFormRef = ref(null)

// 获取单个临时收支数据（编辑模式下）
const getTemporaryTransactionById = async (id) => {
  try {
    loading.value = true
    const response = await assetApi.getTemporaryTransactionById(id)
    const data = response.data
    // 格式化日期时间
    if (data.transactionDatetime) {
      data.transactionDatetime = new Date(data.transactionDatetime).toISOString().slice(0, 16)
    }
    form.value = data
  } catch (error) {
    console.error('获取临时收支数据失败:', error)
    ElMessage.error('获取临时收支数据失败，请稍后重试')
    router.push('/asset/temporary-transactions')
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
          await assetApi.updateTemporaryTransaction(route.params.id, form.value)
          ElMessage.success('更新成功')
        } else {
          // 创建模式
          await assetApi.createTemporaryTransaction(form.value)
          ElMessage.success('创建成功')
        }
        
        // 跳转到列表页面
        router.push('/asset/temporary-transactions')
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
  router.push('/asset/temporary-transactions')
}

// 页面挂载时，如果是编辑模式则获取数据
onMounted(() => {
  if (isEditMode.value) {
    getTemporaryTransactionById(route.params.id)
  }
})
</script>

<template>
  <div class="temporary-transactions-form">
    <div class="page-header">
      <h2 class="page-title">{{ isEditMode ? '编辑临时收支' : '创建临时收支' }}</h2>
    </div>
    
    <el-card class="card" shadow="hover">
      <el-form
        :model="form"
        :rules="rules"
        ref="temporaryFormRef"
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
          <el-input v-model="form.transactionName" placeholder="请输入交易名称（如:奖金、买手机）"></el-input>
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
        
        <el-form-item label="交易时间" prop="transactionDatetime">
          <el-datetime-picker
            v-model="form.transactionDatetime"
            type="datetime"
            placeholder="选择交易时间"
            format="YYYY-MM-DD HH:mm"
            value-format="YYYY-MM-DDTHH:mm"
            style="width: 100%"
          ></el-datetime-picker>
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
            @click="submitForm(temporaryFormRef)"
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
.temporary-transactions-form {
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