<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElForm, ElFormItem, ElInput, ElDatePicker, ElInputNumber, ElSelect, ElOption, ElButton } from 'element-plus'
import { assetApi } from '../services/api'

const router = useRouter()
const route = useRoute()

// 判断是编辑还是创建模式
const isEditMode = computed(() => {
  return route.name === 'EditSavings'
})

// 表单数据
const form = ref({
  planName: '',
  startDate: new Date().toISOString().split('T')[0],
  monthlyDepositAmount: 0,
  interestCalculationType: 'MONTHLY',
  interestRate: 0.03,
  status: 'ACTIVE',
  description: ''
})

// 表单验证规则
const rules = {
  planName: [
    { required: true, message: '请输入计划名称', trigger: 'blur' }
  ],
  startDate: [
    { required: true, message: '请选择开始时间', trigger: 'change' }
  ],
  monthlyDepositAmount: [
    { required: true, message: '请输入每月存入金额', trigger: 'blur' },
    { type: 'number', min: 0.01, message: '每月存入金额必须大于0', trigger: 'blur' }
  ],
  interestCalculationType: [
    { required: true, message: '请选择利息计算规则', trigger: 'change' }
  ],
  interestRate: [
    { required: true, message: '请输入利率', trigger: 'blur' },
    { type: 'number', min: 0, max: 1, message: '利率必须在0-1之间', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
}

// 加载状态
const loading = ref(false)

// 表单引用
const savingsFormRef = ref(null)

// 获取单个存款计划数据（编辑模式下）
const getSavingsById = async (id) => {
  try {
    loading.value = true
    const response = await assetApi.getSavingsById(id)
    const data = response.data
    // 格式化日期
    data.startDate = data.startDate.split('T')[0]
    form.value = data
  } catch (error) {
    console.error('获取存款计划失败:', error)
    ElMessage.error('获取存款计划失败，请稍后重试')
    router.push('/asset/savings')
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
          await assetApi.updateSavings(route.params.id, form.value)
          ElMessage.success('更新成功')
        } else {
          // 创建模式
          await assetApi.createSavings(form.value)
          ElMessage.success('创建成功')
        }
        
        // 跳转到列表页面
        router.push('/asset/savings')
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
  router.push('/asset/savings')
}

// 页面挂载时，如果是编辑模式则获取数据
onMounted(() => {
  if (isEditMode.value) {
    getSavingsById(route.params.id)
  }
})
</script>

<template>
  <div class="savings-form">
    <div class="page-header">
      <h2 class="page-title">{{ isEditMode ? '编辑存款计划' : '创建存款计划' }}</h2>
    </div>
    
    <el-card class="card" shadow="hover">
      <el-form
        :model="form"
        :rules="rules"
        ref="savingsFormRef"
        label-width="120px"
        :disabled="loading"
      >
        <el-form-item label="计划名称" prop="planName">
          <el-input v-model="form.planName" placeholder="请输入计划名称"></el-input>
        </el-form-item>
        
        <el-form-item label="开始时间" prop="startDate">
          <el-date-picker
            v-model="form.startDate"
            type="date"
            placeholder="选择开始时间"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          ></el-date-picker>
        </el-form-item>
        
        <el-form-item label="每月存入金额" prop="monthlyDepositAmount">
          <el-input-number
            v-model="form.monthlyDepositAmount"
            :min="0.01"
            :step="0.01"
            :precision="2"
            placeholder="请输入每月存入金额"
            style="width: 100%"
          >
            <template #prefix>
              <span>¥</span>
            </template>
          </el-input-number>
        </el-form-item>
        
        <el-form-item label="利息计算规则" prop="interestCalculationType">
          <el-select v-model="form.interestCalculationType" placeholder="请选择利息计算规则">
            <el-option label="按年" value="YEARLY"></el-option>
            <el-option label="按月" value="MONTHLY"></el-option>
            <el-option label="按日" value="DAILY"></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="利率" prop="interestRate">
          <el-input-number
            v-model="form.interestRate"
            :min="0"
            :max="1"
            :step="0.001"
            :precision="3"
            placeholder="请输入利率"
            style="width: 100%"
          >
            <template #suffix>
              <span>%</span>
            </template>
          </el-input-number>
        </el-form-item>
        
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="活跃" value="ACTIVE"></el-option>
            <el-option label="暂停" value="PAUSED"></el-option>
            <el-option label="已完成" value="COMPLETED"></el-option>
          </el-select>
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
            @click="submitForm(savingsFormRef)"
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
.savings-form {
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