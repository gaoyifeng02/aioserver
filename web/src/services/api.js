import axios from 'axios'

// 创建Axios实例
const api = axios.create({
  baseURL: 'http://127.0.0.1:10001/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
api.interceptors.request.use(
  config => {
    // 从本地存储获取token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  response => {
    // 直接返回响应数据，兼容不同的API响应格式
    return response.data
  },
  error => {
    // 统一处理错误
    console.error('API请求错误:', error)
    return Promise.reject(error)
  }
)

// 认证相关API
export const authApi = {
  login: (data) => api.post('/idaas/auth/login', data),
  register: (data) => api.post('/idaas/auth/register', data),
  getUserInfo: () => api.get('/idaas/auth/getUserInfo')
}

// 资产管理相关API
export const assetApi = {
  // 资产总览
  getAccountOverview: () => api.get('/asset/account'),
  
  // 存款计划
  getSavingsList: () => api.get('/asset/savings/list'),
  getSavingsById: (id) => api.get(`/asset/savings/${id}`),
  createSavings: (data) => api.post('/asset/savings', data),
  updateSavings: (id, data) => api.put(`/asset/savings/${id}`, data),
  deleteSavings: (id) => api.delete(`/asset/savings/${id}`),
  
  // 固定收支配置
  getRecurringTransactions: () => api.get('/asset/recurring/list'),
  getRecurringTransactionById: (id) => api.get(`/asset/recurring/${id}`),
  createRecurringTransaction: (data) => api.post('/asset/recurring', data),
  updateRecurringTransaction: (id, data) => api.put(`/asset/recurring/${id}`, data),
  deleteRecurringTransaction: (id) => api.delete(`/asset/recurring/${id}`),
  
  // 临时收支记录
  getTemporaryTransactions: () => api.get('/asset/temporary/list'),
  getTemporaryTransactionById: (id) => api.get(`/asset/temporary/${id}`),
  createTemporaryTransaction: (data) => api.post('/asset/temporary', data),
  updateTemporaryTransaction: (id, data) => api.put(`/asset/temporary/${id}`, data),
  deleteTemporaryTransaction: (id) => api.delete(`/asset/temporary/${id}`),
  
  // 资产流水
  getFlowById: (id) => api.get(`/asset/flow/${id}`),
  getTransactionFlows: () => api.get('/asset/flow/list'),
  
  // 待入账记录
  getPendingTransactions: () => api.get('/asset/pending/list'),
  getPendingTransactionById: (id) => api.get(`/asset/pending/${id}`),
  createPendingTransaction: (data) => api.post('/asset/pending', data),
  updatePendingTransaction: (id, data) => api.put(`/asset/pending/${id}`, data),
  deletePendingTransaction: (id) => api.delete(`/asset/pending/${id}`)
}

// 博客相关API（虽然不是资产管理核心，但API中包含了）
export const blogApi = {
  getBlogs: (params) => api.get('/blog/blogs', { params }),
  createBlog: (data) => api.post('/blog/blogs', data),
  updateBlog: (id, data) => api.put(`/blog/blogs/${id}`, data),
  deleteBlog: (id) => api.delete(`/blog/blogs/${id}`)
}

// 分类相关API
export const cateApi = {
  getCategories: () => api.get('/blog/categories'),
  createCategory: (data) => api.post('/blog/categories', data),
  updateCategory: (id, data) => api.put(`/blog/categories/${id}`, data),
  deleteCategory: (id) => api.delete(`/blog/categories/${id}`)
}

// AI聊天API
export const aiApi = {
  chat: (data) => api.post('/ai/chat', data)
}

// 订单相关API
export const orderApi = {
  createOrder: (data) => api.post('/order/create', data),
  queryOrder: (orderId) => api.get('/order/query', { params: { orderId } }),
  queryUserOrders: (userId) => api.get('/order/user-orders', { params: { userId } })
}

export default api