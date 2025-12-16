<template>
  <div class="app-container">
    <!-- 左侧侧边栏 -->
    <aside class="sidebar">
      <div class="sidebar-header">
        <h2 class="app-name">AIOServer</h2>
      </div>
      <div class="sidebar-menu">
        <div class="menu-item active">
          <span class="menu-icon">🤖</span>
          <span class="menu-text">AI助手</span>
        </div>
        <div class="menu-item">
          <span class="menu-icon">📚</span>
          <span class="menu-text">历史记录</span>
        </div>
        <div class="menu-item">
          <span class="menu-icon">💡</span>
          <span class="menu-text">创意中心</span>
        </div>
      </div>
      <div class="sidebar-footer">
        <div class="user-info">
          <div class="avatar">👤</div>
          <div class="user-status">{{ username || '访客模式' }}</div>
        </div>
        <div class="logout-btn" @click="handleLogout">退出登录</div>
      </div>
    </aside>
    
    <!-- 主聊天区域 -->
    <main class="main-content">
      <!-- 顶部导航栏 -->
      <header class="top-nav">
        <div class="nav-left">
          <h1 class="nav-title">AIOServer Assistant</h1>
        </div>
        <div class="nav-right">
          <button class="login-btn" @click="handleLogout">退出登录</button>
          <button class="settings-btn">⚙️</button>
        </div>
      </header>
      
      <!-- 聊天内容区域 -->
      <div class="chat-area">
        <!-- 初始欢迎消息 -->
        <div class="welcome-message" v-if="messages.length === 0">
          <h2 class="welcome-title">你好！我是AIOServer助手</h2>
          <p class="welcome-desc">一个强大的智能助手，为你提供高效、准确的信息服务和创意支持。</p>
          
          <div class="quick-questions">
            <div class="quick-question" v-for="(question, index) in quickQuestions" :key="index" @click="sendQuickQuestion(question)">
              {{ question }}
            </div>
          </div>
          
          <div class="feature-prompt">
            <span class="prompt-icon">✨</span>
            支持多轮对话、创意生成、知识问答等多种功能
          </div>
        </div>
        
        <!-- 消息列表 -->
        <div class="messages-list" v-else>
          <div 
            v-for="(message, index) in messages" 
            :key="index"
            :class="['message-item', message.sender]"
          >
            <div class="message-content">{{ message.content }}</div>
          </div>
        </div>
        
        <!-- 卡通形象 -->
        <div class="cartoon-character">
          <div class="character-image">🤖</div>
        </div>
      </div>
      
      <!-- 输入区域 -->
      <footer class="input-area">
        <div class="input-toolbar">
          <button class="toolbar-btn">
            <span class="btn-icon">🧠</span>
            <span class="btn-text">智能思考</span>
          </button>
          <button class="toolbar-btn">
            <span class="btn-icon">🔍</span>
            <span class="btn-text">信息搜索</span>
          </button>
          <button class="toolbar-btn">
            <span class="btn-icon">🎨</span>
            <span class="btn-text">创意生成</span>
          </button>
        </div>
        
        <div class="input-container">
          <input
            type="text"
            v-model="inputMessage"
            @keyup.enter="sendMessage"
            placeholder="有什么问题需要帮助？按Enter发送，Shift+Enter换行"
            class="message-input"
          >
          <div class="input-actions">
            <button class="action-btn">📎</button>
            <button class="action-btn">🎤</button>
            <button @click="sendMessage" class="send-btn">
              <span class="send-icon">→</span>
            </button>
          </div>
        </div>
        
        <div class="input-footer">
          <div class="footer-text">AIOServer Assistant - 让智能触手可及</div>
        </div>
      </footer>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// 用户信息
const username = ref('');

// 消息列表
const messages = ref([]);

// 输入消息
const inputMessage = ref('');

// 快捷问题
const quickQuestions = ref([
  '如何优化网站性能？',
  '什么是机器学习？',
  '如何提高工作效率？',
  '分享一个创意写作技巧',
  '解释量子计算的基本概念'
]);

// 页面加载时获取用户信息
onMounted(() => {
  username.value = localStorage.getItem('username');
});

// 发送消息
const sendMessage = async () => {
  if (!inputMessage.value.trim()) return;
  
  // 添加用户消息
  messages.value.push({
    sender: 'user',
    content: inputMessage.value
  });
  
  // 保存用户输入
  const userMessage = inputMessage.value;
  
  // 清空输入框
  inputMessage.value = '';
  
  // 添加加载状态
  const loadingMessageIndex = messages.value.length;
  messages.value.push({
    sender: 'ai',
    content: '正在思考...'
  });
  
  try {
    // 发送API请求
    console.log('发送API请求:', userMessage);
    const response = await fetch('http://127.0.0.1:10001/api/v1/ai/chat', {
      method: 'POST',
      headers: {
        'Authorization': '1',
        'Content-Type': 'application/json',
        'Accept': '*/*'
      },
      body: JSON.stringify({
        message: userMessage
      })
    });
    
    console.log('API响应状态:', response.status);
    
    // 获取原始响应文本
    const responseText = await response.text();
    console.log('API响应原始文本:', responseText);
    
    // 解析响应数据
    let data;
    try {
      data = JSON.parse(responseText);
      console.log('API响应解析结果:', data);
    } catch (parseError) {
      console.error('JSON解析失败:', parseError);
      throw new Error('API返回格式错误，无法解析JSON');
    }
    
    // 移除加载状态
    messages.value.splice(loadingMessageIndex, 1);
    
    // 根据返回的code判断请求是否成功
    if (data.code === '200' || data.code === 200) {
      // 请求成功，添加AI回复
      messages.value.push({
        sender: 'ai',
        content: typeof data === 'object' && data !== null 
          ? (data.data?.reply || data.content || data.message || JSON.stringify(data, null, 2) || 'AI回复了一条消息')
          : String(data)
      });
    } else {
      // 请求失败，显示错误信息
      messages.value.push({
        sender: 'ai',
        content: `抱歉，请求失败: ${data.message || '未知错误'}`
      });
    }
  } catch (error) {
    console.error('API请求失败:', error);
    
    // 移除加载状态并添加错误提示
    messages.value.splice(loadingMessageIndex, 1);
    messages.value.push({
      sender: 'ai',
      content: `抱歉，请求失败: ${error.message}`
    });
  }
};

// 发送快捷问题
const sendQuickQuestion = async (question) => {
  // 添加用户消息
  messages.value.push({
    sender: 'user',
    content: question
  });
  
  // 添加加载状态
  const loadingMessageIndex = messages.value.length;
  messages.value.push({
    sender: 'ai',
    content: '正在思考...'
  });
  
  try {
    // 发送API请求
    console.log('发送快捷问题API请求:', question);
    const response = await fetch('http://127.0.0.1:10001/api/v1/ai/chat', {
      method: 'POST',
      headers: {
        'Authorization': '1',
        'Content-Type': 'application/json',
        'Accept': '*/*'
      },
      body: JSON.stringify({
        message: question
      })
    });
    
    console.log('API响应状态:', response.status);
    
    // 获取原始响应文本
    const responseText = await response.text();
    console.log('API响应原始文本:', responseText);
    
    // 解析响应数据
    let data;
    try {
      data = JSON.parse(responseText);
      console.log('API响应解析结果:', data);
    } catch (parseError) {
      console.error('JSON解析失败:', parseError);
      throw new Error('API返回格式错误，无法解析JSON');
    }
    
    // 移除加载状态
    messages.value.splice(loadingMessageIndex, 1);
    
    // 根据返回的code判断请求是否成功
    if (data.code === '200' || data.code === 200) {
      // 请求成功，添加AI回复
      messages.value.push({
        sender: 'ai',
        content: typeof data === 'object' && data !== null 
          ? (data.data?.reply || data.content || data.message || JSON.stringify(data, null, 2) || 'AI回复了一条消息')
          : String(data)
      });
    } else {
      // 请求失败，显示错误信息
      messages.value.push({
        sender: 'ai',
        content: `抱歉，请求失败: ${data.message || '未知错误'}`
      });
    }
  } catch (error) {
    console.error('API请求失败:', error);
    
    // 移除加载状态并添加错误提示
    messages.value.splice(loadingMessageIndex, 1);
    messages.value.push({
      sender: 'ai',
      content: `抱歉，请求失败: ${error.message}`
    });
  }
};

// 退出登录
const handleLogout = () => {
  // 清除登录状态
  localStorage.removeItem('isLoggedIn');
  localStorage.removeItem('username');
  
  // 跳转到登录页面
  router.push('/login');
};
</script>

<style scoped>
/* 全局样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  color: #333;
  background-color: #f8fafc;
}

/* 应用容器 */
.app-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
  max-width: 100%;
}

/* 侧边栏样式 */
.sidebar {
  width: 280px;
  background-color: #ffffff;
  border-right: 1px solid #e2e8f0;
  display: flex;
  flex-direction: column;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
}

.sidebar-header {
  padding: 20px;
  border-bottom: 1px solid #e2e8f0;
}

.app-name {
  font-size: 20px;
  font-weight: 700;
  color: #6366f1;
}

.sidebar-menu {
  flex: 1;
  padding: 10px 0;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 12px 20px;
  cursor: pointer;
  transition: all 0.2s;
  border-radius: 0 20px 20px 0;
  margin: 4px 0;
}

.menu-item:hover {
  background-color: #f1f5f9;
}

.menu-item.active {
  background-color: #ede9fe;
  color: #6366f1;
}

.menu-icon {
  font-size: 18px;
  margin-right: 12px;
}

.menu-text {
  font-size: 14px;
  font-weight: 500;
}

.sidebar-footer {
  padding: 20px;
  border-top: 1px solid #e2e8f0;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 12px;
}

.avatar {
  font-size: 24px;
}

.user-status {
  font-size: 14px;
  color: #64748b;
}

.logout-btn {
  padding: 8px 12px;
  background-color: #fef2f2;
  color: #dc2626;
  border-radius: 6px;
  font-size: 13px;
  cursor: pointer;
  text-align: center;
  transition: all 0.2s;
}

.logout-btn:hover {
  background-color: #fee2e2;
}

/* 主内容区域 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
  overflow: hidden;
}

/* 顶部导航栏 */
.top-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30px;
  height: 60px;
  border-bottom: 1px solid #e2e8f0;
  background-color: #ffffff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.nav-left {
  display: flex;
  align-items: center;
}

.nav-title {
  font-size: 20px;
  font-weight: 600;
  color: #1e293b;
}

.nav-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.login-btn {
  padding: 8px 16px;
  border: 1px solid #6366f1;
  border-radius: 6px;
  background-color: transparent;
  color: #6366f1;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
}

.login-btn:hover {
  background-color: #ede9fe;
}

.settings-btn {
  padding: 8px;
  border: none;
  border-radius: 6px;
  background-color: transparent;
  font-size: 18px;
  cursor: pointer;
  transition: all 0.3s;
}

.settings-btn:hover {
  background-color: #f1f5f9;
}

/* 聊天内容区域 */
.chat-area {
  flex: 1;
  padding: 40px;
  overflow-y: auto;
  position: relative;
}

/* 欢迎消息 */
.welcome-message {
  max-width: 1200px;
  margin: 0 auto;
  text-align: left;
  padding: 0 20px;
}

.welcome-title {
  font-size: 32px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 16px;
}

.welcome-desc {
  font-size: 16px;
  color: #64748b;
  line-height: 1.6;
  margin-bottom: 32px;
}

/* 快捷问题 */
.quick-questions {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 32px;
}

.quick-question {
  padding: 18px 24px;
  background-color: #f8fafc;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  font-size: 15px;
  color: #334155;
  cursor: pointer;
  transition: all 0.3s;
  line-height: 1.6;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.quick-question:hover {
  background-color: #f1f5f9;
  border-color: #cbd5e1;
  transform: translateY(-1px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
}

/* 功能提示 */
.feature-prompt {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 14px 20px;
  background-color: #f0fdf4;
  border: 1px solid #bbf7d0;
  border-radius: 12px;
  font-size: 14px;
  color: #15803d;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.prompt-icon {
  font-size: 18px;
}

/* 消息列表 */
.messages-list {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding: 0 20px;
}

.message-item {
  display: flex;
  margin-bottom: 16px;
  animation: fadeIn 0.3s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.message-item.user {
  justify-content: flex-end;
}

.message-item.ai {
  justify-content: flex-start;
}

.message-content {
  max-width: 65%;
  padding: 14px 20px;
  border-radius: 20px;
  font-size: 15px;
  line-height: 1.6;
  word-wrap: break-word;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.message-item.user .message-content {
  background-color: #6366f1;
  color: #ffffff;
  border-bottom-right-radius: 8px;
}

.message-item.ai .message-content {
  background-color: #f8fafc;
  color: #334155;
  border: 1px solid #e2e8f0;
  border-bottom-left-radius: 8px;
}

/* 卡通形象 */
.cartoon-character {
  position: absolute;
  bottom: 180px;
  right: 60px;
}

.character-image {
  font-size: 72px;
  opacity: 0.7;
}

/* 输入区域 */
.input-area {
  padding: 20px 30px;
  border-top: 1px solid #e2e8f0;
  background-color: #fafafa;
  display: flex;
  flex-direction: column;
  align-items: center;
}

/* 输入工具栏 */
.input-toolbar {
  display: flex;
  gap: 12px;
  margin-bottom: 12px;
  width: 100%;
  max-width: 1200px;
}

.toolbar-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  border: 1px solid #cbd5e1;
  border-radius: 8px;
  background-color: transparent;
  font-size: 13px;
  color: #64748b;
  cursor: pointer;
  transition: all 0.3s;
}

.toolbar-btn:hover {
  border-color: #6366f1;
  color: #6366f1;
  background-color: #f8f5ff;
}

.btn-icon {
  font-size: 16px;
}

.btn-text {
  font-size: 13px;
  font-weight: 500;
}

/* 输入容器 */
.input-container {
  display: flex;
  align-items: center;
  gap: 12px;
  background-color: #ffffff;
  border: 2px solid #e2e8f0;
  border-radius: 28px;
  padding: 12px 20px;
  transition: all 0.3s;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
  width: 100%;
  max-width: 1200px;
}

.input-container:focus-within {
  border-color: #6366f1;
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
}

.message-input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 15px;
  padding: 10px 0;
  resize: none;
  background-color: transparent;
  font-family: inherit;
}

.message-input::placeholder {
  color: #94a3b8;
}

/* 输入操作按钮 */
.input-actions {
  display: flex;
  align-items: center;
  gap: 8px;
}

.action-btn {
  padding: 10px;
  border: none;
  border-radius: 50%;
  background-color: transparent;
  font-size: 18px;
  cursor: pointer;
  transition: all 0.3s;
  color: #64748b;
}

.action-btn:hover {
  background-color: #f1f5f9;
  color: #334155;
}

.send-btn {
  padding: 12px 16px;
  border: none;
  border-radius: 50%;
  background-color: #6366f1;
  color: white;
  font-size: 18px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 2px 4px rgba(99, 102, 241, 0.3);
}

.send-btn:hover {
  background-color: #4f46e5;
  transform: scale(1.05);
}

.send-btn:active {
  transform: scale(0.95);
}

/* 输入区域底部 */
.input-footer {
  margin-top: 12px;
  text-align: center;
}

.footer-text {
  font-size: 13px;
  color: #94a3b8;
}

/* 滚动条样式 */
.chat-area::-webkit-scrollbar {
  width: 10px;
}

.chat-area::-webkit-scrollbar-track {
  background: #f1f5f9;
}

.chat-area::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 5px;
}

.chat-area::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}
</style>