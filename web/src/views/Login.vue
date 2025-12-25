<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { authApi } from '../services/api'

const router = useRouter()

// 表单数据
const form = ref({
  username: '',
  password: ''
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

// 加载状态
const loading = ref(false)

// 表单引用
const loginFormRef = ref(null)

// 处理登录
const handleLogin = async (formEl) => {
  if (!formEl) return
  
  // 表单验证
  await formEl.validate(async (valid) => {
    if (valid) {
      try {
        loading.value = true
        const response = await authApi.login(form.value)
        
        // 登录成功，保存token到本地存储
        localStorage.setItem('token', response.data.token)
        
        // 跳转到资产总览页面
        router.push('/asset/overview')
      } catch (error) {
        console.error('登录失败:', error)
        ElMessage.error('登录失败，请检查用户名和密码')
      } finally {
        loading.value = false
      }
    }
  })
}

// 跳转到注册页面
const goToRegister = () => {
  router.push('/register')
}
</script>

<template>
  <div class="login-container">
    <el-card class="login-card" shadow="hover">
      <template #header>
        <div class="login-header">
          <h2 class="login-title">资产管理系统</h2>
          <p class="login-subtitle">登录您的账号</p>
        </div>
      </template>
      
      <el-form
        :model="form"
        :rules="rules"
        ref="loginFormRef"
        class="login-form"
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
            placeholder="请输入密码"
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
            @click="handleLogin(loginFormRef)"
            block
          >
            登录
          </el-button>
        </el-form-item>
        
        <div class="login-footer">
          <span>还没有账号？</span>
          <el-button type="text" @click="goToRegister">立即注册</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
}

.login-card {
  width: 100%;
  border-radius: 12px;
  overflow: hidden;
}

.login-header {
  text-align: center;
  padding: 20px 0;
}

.login-title {
  font-size: 24px;
  font-weight: 600;
  color: #1890ff;
  margin: 0 0 10px 0;
}

.login-subtitle {
  font-size: 14px;
  color: #666;
  margin: 0;
}

.login-form {
  padding: 0 20px 20px;
}

.login-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 5px;
  margin-top: 20px;
  font-size: 14px;
  color: #666;
}
</style>