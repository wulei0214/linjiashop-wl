<template>
  <div class="app-container">
    <div class="block">
      <iframe :src="druidUrl" width="100%" height="768px" frameborder="0" scrolling="auto"></iframe>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'DruidMonitor',
    computed: {
      druidUrl() {
        //通过/druid代理访问本机8082服务的Druid监控台（代理配置见vue.config.js，账号密码见application.properties）
        return '/druid/index.html'
      }
    },
    created: function () {
      this.init()
    },
    methods: {
      init() {
        this.$notify({
          title: '提示',
          message: 'Druid监控台账号：druiduser / druidpassword（配置于application.properties）',
          duration: 5000
        })
      },
    }
  };
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>
