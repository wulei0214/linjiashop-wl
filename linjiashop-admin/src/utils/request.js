import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import router from '@/router'
// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: false, // send cookies when cross-domain requests
  timeout: 30000, // request timeout
  // 处理中文乱码
  transformRequest: [function(data) {
    // 对 data 进行 UTF-8 编码处理
    return data
  }],
  // 强制使用 UTF-8 编码解析响应
  responseType: 'json',
  transformResponse: [function(data) {
    // 确保响应数据按 UTF-8 解析
    return data
  }],
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    'Accept': 'application/json;charset=UTF-8'
  }
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    var token = getToken()
    if (token) {
      config.headers['Authorization'] = token // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config
  },
  error => {
    // do something with request error
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    if(response.headers.token){
      //如果后台通过header返回token，说明token已经更新，则更新客户端本地token
      store.dispatch('user/updateToken',{token:response.headers.token})
    }
    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 20000) {
      Message({
        message: res.msg || 'error',
        type: 'error',
        duration: 5 * 1000
      })

      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        // to re-login
        MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
          confirmButtonText: 'Re-Login',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(res)
    } else {
      return res
    }
  },
  error => {
    if(error.response && error.response.status === 401){
      store.dispatch('user/logout').then(()=>{
        router.replace({
          path: '/login',
          query:{redirect:router.currentRoute.path}
        })
      })
      return
    }
    let errorMsg = '请求失败，请稍后重试'
    try {
      if (error.response && error.response.data) {
        // 尝试获取错误信息，处理可能的乱码
        const errorData = error.response.data
        if (errorData.errors && errorData.errors[0] && errorData.errors[0].defaultMessage) {
          errorMsg = errorData.errors[0].defaultMessage
        } else if (errorData.message) {
          errorMsg = errorData.message
        } else if (errorData.msg) {
          errorMsg = errorData.msg
        }
      }
    }catch (e) {
      errorMsg = '网络错误，请检查网络连接'
    }
    Message({
      message: errorMsg,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
