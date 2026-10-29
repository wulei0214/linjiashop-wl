<template>
  <div class="app-container">
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="后台管理API（8082）" name="admin">
        <iframe v-if="activeName === 'admin'" :src="adminUrl" width="100%" height="768px"
                frameborder="0" scrolling="auto"></iframe>
      </el-tab-pane>
      <el-tab-pane label="移动端API（8081）" name="mobile">
        <iframe v-if="activeName === 'mobile'" :src="mobileUrl" width="100%" height="768px"
                frameborder="0" scrolling="auto"></iframe>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  export default {
    name: 'ApiDocs',
    data() {
      return {
        activeName: 'admin'
      }
    },
    computed: {
      adminUrl() {
        //后台管理端Swagger文档，通过开发代理访问本机服务
        return process.env.VUE_APP_BASE_API + '/swagger-ui.html'
      },
      mobileUrl() {
        //移动端Swagger文档，通过/mobile-api代理访问本机8081服务（代理配置见vue.config.js）
        return '/mobile-api/swagger-ui.html'
      }
    }
  };
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>
