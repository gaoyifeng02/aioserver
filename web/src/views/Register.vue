<template>
  <div class="login-container">
    <!-- 左侧品牌展示区 -->
    <div class="login-brand">
      <div class="brand-content">
        <h1 class="brand-title">AIOServer</h1>
        <p class="brand-subtitle">AI Assistant</p>
        <div class="brand-description">
          强大的智能助手，为你提供高效、准确的信息服务和创意支持
        </div>
        <div class="brand-features">
          <div class="feature-item">🤖 智能对话</div>
          <div class="feature-item">💡 创意生成</div>
          <div class="feature-item">📚 知识问答</div>
        </div>
      </div>
    </div>
    
    <!-- 右侧注册表单区 -->
    <div class="login-form-container">
      <div class="login-box">
        <div class="login-header">
          <h2 class="login-title">账号注册</h2>
          <p class="login-subtitle">创建一个新账号，开始智能之旅</p>
        </div>
        
        <form class="login-form" @submit.prevent="handleRegister">
          <div class="form-group">
            <div class="form-label-container">
              <label for="username" class="form-label">账号</label>
            </div>
            <div class="input-wrapper">
              <input
                type="text"
                id="username"
                v-model="username"
                class="form-input"
                placeholder="请输入账号"
                required
              >
            </div>
          </div>
          
          <div class="form-group">
            <div class="form-label-container">
              <label for="password" class="form-label">密码</label>
            </div>
            <div class="input-wrapper">
              <input
                type="password"
                id="password"
                v-model="password"
                class="form-input"
                placeholder="请输入密码"
                required
              >
            </div>
          </div>
          
          <button type="submit" class="login-btn" :disabled="isLoading">
            <span v-if="isLoading" class="loading-spinner"></span>
            <span>{{ isLoading ? '注册中...' : '注册' }}</span>
          </button>
          
          <div v-if="errorMessage" class="error-message">
            <span class="error-icon">⚠️</span>
            <span>{{ errorMessage }}</span>
          </div>
        </form>
        
        <div class="login-footer">
          <div class="register-link">
            已有账号？ <a href="/login" class="register-text">立即登录</a>
          </div>
          <p class="footer-text">AIOServer Assistant - 让智能触手可及</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// 表单数据
const username = ref('');
const password = ref('');
const isLoading = ref(false);
const errorMessage = ref('');

// 注册处理
const handleRegister = async () => {
  if (!username.value.trim() || !password.value.trim()) {
    errorMessage.value = '请输入账号和密码';
    return;
  }
  
  isLoading.value = true;
  errorMessage.value = '';
  
  try {
    // 真实的注册API调用
    const response = await fetch('http://127.0.0.1:10001/api/v1/idaas/auth/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: username.value,
        password: password.value
      })
    });
    
    const data = await response.json();
    
    // 根据返回的code判断注册是否成功
    if (data.code === '200' || data.code === 200) {
      // 注册成功后跳转到登录页面
      router.push('/login');
    } else {
      // 注册失败，显示错误信息
      errorMessage.value = data.message || '注册失败';
      console.error('注册失败:', data);
    }
  } catch (error) {
    errorMessage.value = error.message || '注册失败，请检查网络连接';
    console.error('注册失败:', error);
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>
/* 登录容器 - 左右分栏布局 */
.login-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

/* 左侧品牌展示区 */
.login-brand {
  flex: 1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  color: white;
  position: relative;
  overflow: hidden;
}

/* 品牌内容 */
.brand-content {
  max-width: 600px;
  z-index: 1;
}

.brand-title {
  font-size: 48px;
  font-weight: 800;
  margin-bottom: 8px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.brand-subtitle {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 24px;
  opacity: 0.9;
}

.brand-description {
  font-size: 18px;
  line-height: 1.6;
  margin-bottom: 32px;
  opacity: 0.9;
}

/* 品牌特性 */
.brand-features {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 16px;
  padding: 12px 16px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  transition: all 0.3s ease;
}

.feature-item:hover {
  background: rgba(255, 255, 255, 0.15);
  transform: translateX(8px);
}

/* 右侧注册表单区 */
.login-form-container {
  width: 480px;
  background: white;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  box-shadow: -4px 0 20px rgba(0, 0, 0, 0.05);
  overflow-y: auto;
}

/* 注册框 */
.login-box {
  width: 100%;
}

/* 注册头部 */
.login-header {
  margin-bottom: 32px;
}

.login-title {
  font-size: 32px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 8px;
  text-align: left;
}

.login-subtitle {
  font-size: 16px;
  color: #64748b;
  margin-bottom: 0;
}

/* 注册表单 */
.login-form {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.form-label-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.form-label {
  font-size: 14px;
  font-weight: 500;
  color: #334155;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.form-input {
  width: 100%;
  padding: 16px 20px;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  font-size: 16px;
  transition: all 0.3s ease;
  outline: none;
  font-family: inherit;
  background: #fafafa;
}

.form-input:focus {
  border-color: #6366f1;
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
  background: white;
}

.form-input::placeholder {
  color: #94a3b8;
}

/* 表单选项 */
.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: -8px;
}

.remember-me {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.remember-me input[type="checkbox"] {
  width: 18px;
  height: 18px;
  cursor: pointer;
  accent-color: #6366f1;
}

.remember-label {
  font-size: 14px;
  color: #64748b;
  cursor: pointer;
  user-select: none;
}

.forgot-password {
  font-size: 14px;
  color: #6366f1;
  text-decoration: none;
  transition: color 0.3s ease;
}

.forgot-password:hover {
  color: #4f46e5;
  text-decoration: underline;
}

/* 注册按钮 */
.login-btn {
  padding: 16px 20px;
  background-color: #6366f1;
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  margin-top: 8px;
  position: relative;
  overflow: hidden;
}

.login-btn:hover:not(:disabled) {
  background-color: #4f46e5;
  box-shadow: 0 6px 20px rgba(99, 102, 241, 0.4);
  transform: translateY(-1px);
}

.login-btn:active:not(:disabled) {
  transform: translateY(0);
  box-shadow: 0 4px 12px rgba(99, 102, 241, 0.3);
}

.login-btn:disabled {
  background-color: #a5b4fc;
  cursor: not-allowed;
  opacity: 0.7;
  transform: none;
  box-shadow: none;
}

/* 加载动画 */
.loading-spinner {
  width: 16px;
  height: 16px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

/* 错误消息 */
.error-message {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #ef4444;
  font-size: 14px;
  padding: 12px 16px;
  background-color: #fef2f2;
  border: 1px solid #fee2e2;
  border-radius: 8px;
  margin-top: 8px;
}

.error-icon {
  font-size: 16px;
}

/* 注册页脚 */
.login-footer {
  margin-top: 32px;
  text-align: center;
}

/* 页脚分隔线 */
.footer-divider {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
}

.footer-divider::before,
.footer-divider::after {
  content: '';
  flex: 1;
  height: 1px;
  background-color: #e2e8f0;
}

.divider-text {
  font-size: 14px;
  color: #94a3b8;
  white-space: nowrap;
}

/* 其他登录选项 */
.other-login-options {
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
}

.social-login-btn {
  flex: 1;
  padding: 12px 16px;
  background: white;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  font-size: 14px;
  color: #64748b;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.social-login-btn:hover {
  border-color: #6366f1;
  color: #6366f1;
  background: #f8f5ff;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.social-icon {
  font-size: 18px;
}

.footer-text {
  font-size: 14px;
  color: #94a3b8;
  margin: 0;
  text-align: center;
}

/* 注册链接 */
.register-link {
  margin-bottom: 24px;
  font-size: 14px;
  color: #64748b;
}

.register-text {
  color: #6366f1;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s ease;
}

.register-text:hover {
  color: #4f46e5;
  text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .login-container {
    flex-direction: column;
  }
  
  .login-brand {
    padding: 30px;
    min-height: 300px;
  }
  
  .login-form-container {
    width: 100%;
    padding: 30px;
  }
  
  .brand-title {
    font-size: 36px;
  }
  
  .brand-subtitle {
    font-size: 20px;
  }
}
</style>