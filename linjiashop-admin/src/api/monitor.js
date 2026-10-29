import request from '@/utils/request'

// 服务器监控：CPU、内存、JVM、磁盘、系统信息
export function getServerInfo() {
  return request({
    url: '/monitor/server',
    method: 'get'
  })
}

// 缓存监控：各缓存区使用情况
export function getCacheInfo() {
  return request({
    url: '/monitor/cache',
    method: 'get'
  })
}

// 在线用户列表
export function getOnlineList(params) {
  return request({
    url: '/monitor/online/list',
    method: 'get',
    params
  })
}

// 强制下线
export function kickOnline(token) {
  return request({
    url: '/monitor/online/kick',
    method: 'post',
    params: { token: token }
  })
}
