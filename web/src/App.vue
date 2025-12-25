<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { User, ArrowDown, SwitchButton, DataAnalysis, Money, Calendar, Document, TrendCharts, Timer } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

// 计算是否显示布局（登录和注册页面不显示）
const showLayout = computed(() => {
  return !['Login', 'Register'].includes(route.name)
})

// 退出登录
const handleLogout = () => {
  localStorage.removeItem('token')
  router.push('/login')
}
</script>

<template>
  <div class="app-container">
    <!-- 登录和注册页面 -->
    <div v-if="!showLayout" class="auth-container">
      <router-view></router-view>
    </div>
    
    <!-- 主应用布局 -->
    <el-container v-else>
      <!-- 顶部导航栏 -->
      <el-header class="app-header">
        <div class="header-left">
          <h1 class="app-title">资产管理系统</h1>
        </div>
        <div class="header-right">
          <el-dropdown>
            <span class="user-info">
              <el-icon><User /></el-icon>
              <span>用户</span>
              <el-icon class="el-icon--right"><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">
                  <el-icon><SwitchButton /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      
      <el-container>
        <!-- 侧边栏 -->
        <el-aside width="200px" class="app-aside">
          <el-menu
            :default-active="route.path"
            class="el-menu-vertical-demo"
            router
          >
            <el-menu-item index="/asset/overview">
              <el-icon><DataAnalysis /></el-icon>
              <span>资产总览</span>
            </el-menu-item>
            <el-menu-item index="/asset/savings">
              <el-icon><Money /></el-icon>
              <span>存款计划</span>
            </el-menu-item>
            <el-menu-item index="/asset/recurring-transactions">
              <el-icon><Calendar /></el-icon>
              <span>固定收支</span>
            </el-menu-item>
            <el-menu-item index="/asset/temporary-transactions">
              <el-icon><Document /></el-icon>
              <span>临时收支</span>
            </el-menu-item>
            <el-menu-item index="/asset/flows">
              <el-icon><TrendCharts /></el-icon>
              <span>资产流水</span>
            </el-menu-item>
            <el-menu-item index="/asset/pending-transactions">
              <el-icon><Timer /></el-icon>
              <span>待入账管理</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        
        <!-- 主内容区域 -->
        <el-main class="app-main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style>
/* 全局样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
  background-color: #f5f7fa;
  color: #333;
}

/* 应用容器 */
.app-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 认证页面容器 */
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f7fa;
}

/* 顶部导航栏 */
.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  z-index: 100;
}

.header-left .app-title {
  font-size: 18px;
  font-weight: 600;
  color: #1890ff;
  margin: 0;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
  padding: 5px 10px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.user-info:hover {
  background-color: #f5f7fa;
}

/* 侧边栏 */
.app-aside {
  background-color: #fff;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.1);
}

/* 主内容区域 */
.app-main {
  padding: 20px;
  overflow-y: auto;
}

/* 卡片样式 */
.card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 20px;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 15px;
  color: #333;
}
</style>
