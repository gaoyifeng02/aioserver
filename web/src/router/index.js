import { createRouter, createWebHistory } from 'vue-router'

// 导入组件（先使用懒加载，后面再创建具体组件）
const Login = () => import('../views/Login.vue')
const Register = () => import('../views/Register.vue')
const AssetOverview = () => import('../views/AssetOverview.vue')
const SavingsList = () => import('../views/SavingsList.vue')
const SavingsForm = () => import('../views/SavingsForm.vue')
const RecurringTransactionsList = () => import('../views/RecurringTransactionsList.vue')
const RecurringTransactionsForm = () => import('../views/RecurringTransactionsForm.vue')
const TemporaryTransactionsList = () => import('../views/TemporaryTransactionsList.vue')
const TemporaryTransactionsForm = () => import('../views/TemporaryTransactionsForm.vue')
const TransactionFlowsList = () => import('../views/TransactionFlowsList.vue')
const PendingTransactionsList = () => import('../views/PendingTransactionsList.vue')
const PendingTransactionsForm = () => import('../views/PendingTransactionsForm.vue')
const NotFound = () => import('../views/NotFound.vue')

// 路由配置
const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { requiresAuth: false }
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
    meta: { requiresAuth: false }
  },
  {
    path: '/asset/overview',
    name: 'AssetOverview',
    component: AssetOverview,
    meta: { requiresAuth: true }
  },
  {
    path: '/asset/savings',
    name: 'SavingsList',
    component: SavingsList,
    meta: { requiresAuth: true }
  },
  {
    path: '/asset/savings/create',
    name: 'CreateSavings',
    component: SavingsForm,
    meta: { requiresAuth: true }
  },
  {
    path: '/asset/savings/:id/edit',
    name: 'EditSavings',
    component: SavingsForm,
    meta: { requiresAuth: true }
  },
  
  // 固定收支管理路由
  {
    path: '/asset/recurring-transactions',
    name: 'RecurringTransactionsList',
    component: RecurringTransactionsList,
    meta: { requiresAuth: true }
  },
  {
    path: '/asset/recurring-transactions/create',
    name: 'CreateRecurringTransaction',
    component: RecurringTransactionsForm,
    meta: { requiresAuth: true }
  },
  {
    path: '/asset/recurring-transactions/:id/edit',
    name: 'EditRecurringTransaction',
    component: RecurringTransactionsForm,
    meta: { requiresAuth: true }
  },
  
  // 临时收支记录路由
  {
    path: '/asset/temporary-transactions',
    name: 'TemporaryTransactionsList',
    component: TemporaryTransactionsList,
    meta: { requiresAuth: true }
  },
  {
    path: '/asset/temporary-transactions/create',
    name: 'CreateTemporaryTransaction',
    component: TemporaryTransactionsForm,
    meta: { requiresAuth: true }
  },
  {
    path: '/asset/temporary-transactions/:id/edit',
    name: 'EditTemporaryTransaction',
    component: TemporaryTransactionsForm,
    meta: { requiresAuth: true }
  },
  
  // 资产流水查询路由
  {
    path: '/asset/flows',
    name: 'TransactionFlowsList',
    component: TransactionFlowsList,
    meta: { requiresAuth: true }
  },
  
  // 待入账管理路由
  {
    path: '/asset/pending-transactions',
    name: 'PendingTransactionsList',
    component: PendingTransactionsList,
    meta: { requiresAuth: true }
  },
  {
    path: '/asset/pending-transactions/create',
    name: 'CreatePendingTransaction',
    component: PendingTransactionsForm,
    meta: { requiresAuth: true }
  },
  {
    path: '/asset/pending-transactions/:id/edit',
    name: 'EditPendingTransaction',
    component: PendingTransactionsForm,
    meta: { requiresAuth: true }
  },
  
  // 404页面
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFound
  }
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 检查是否需要认证
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  // 从本地存储获取token
  const token = localStorage.getItem('token')
  
  if (requiresAuth && !token) {
    // 需要认证但没有token，重定向到登录页
    next('/login')
  } else if (!requiresAuth && token && to.path === '/login') {
    // 已经登录且访问登录页，重定向到资产总览
    next('/asset/overview')
  } else {
    // 其他情况正常跳转
    next()
  }
})

export default router