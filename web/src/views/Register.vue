<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { authApi } from '../services/api'

const router = useRouter()

// 表单数据
const form = ref({
  username: '',
  password: '',
  confirmPassword: ''
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== form.value.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

// 加载状态
const loading = ref(false)

// 表单引用
const registerFormRef = ref(null)

// 处理注册
const handleRegister = async (formEl) => {
  if (!formEl) return
  
  // 表单验证
  await formEl.validate(async (valid) => {
    if (valid) {
      try {
        loading.value = true
        
        // 调用注册API
        await authApi.register({
          username: form.value.username,
          password: form.value.password
        })
        
        // 注册成功，显示成功消息并跳转到登录页面
        ElMessage.success('注册成功，请登录')
        router.push('/login')
      } catch (error) {
        console.error('注册失败:', error)
        ElMessage.error('注册失败，请稍后重试')
      } finally {
        loading.value = false
      }
    }
  })
}

// 跳转到登录页面
const goToLogin = () => {
  router.push('/login')
}
</script>

<template>
  <div class="register-container">
    <el-card class="register-card" shadow="hover">
      <template #header>
        <div class="register-header">
          <h2 class="register-title">资产管理系统</h2>
          <p class="register-subtitle">创建新账号</p>
        </div>
      </template>
      
      <el-form
        :model="form"
        :rules="rules"
        ref="registerFormRef"
        class="register-form"
        label-position="top"
      >
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            size="large"
            prefix-icon="User"
          ></el-input>
        </el-form-item>
        
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码（至少6位）"
            size="large"
            prefix-icon="Lock"
            show-password
          ></el-input>
        </el-form-item>
        
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="form.confirmPassword"
            type="password"
            placeholder="请再次输入密码"
            size="large"
            prefix-icon="Lock"
            show-password
          ></el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            size="large"
            :loading="loading"
            @click="handleRegister(registerFormRef)"
            block
          >
            注册
          </el-button>
        </el-form-item>
        
        <div class="register-footer">
          <span>已有账号？</span>
          <el-button type="text" @click="goToLogin">立即登录</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
}

.register-card {
  width: 100%;
  border-radius: 12px;
  overflow: hidden;
}

.register-header {
  text-align: center;
  padding: 20px 0;
}

.register-title {
  font-size: 24px;
  font-weight: 600;
  color: #1890ff;
  margin: 0 0 10px 0;
}

.register-subtitle {
  font-size: 14px;
  color: #666;
  margin: 0;
}

.register-form {
  padding: 0 20px 20px;
}

.register-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 5px;
  margin-top: 20px;
  font-size: 14px;
  color: #666;
}
</style>