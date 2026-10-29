<template>
  <div class="app-container">
    <el-row style="margin-bottom: 10px">
      <el-button icon="el-icon-refresh" size="small" @click="getCacheInfo">刷新</el-button>
    </el-row>

    <el-row :gutter="20" style="margin-bottom: 15px">
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="stat-title">缓存区数量</div>
          <div class="stat-value">{{ cacheInfo.cacheCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="stat-title">缓存条目总数</div>
          <div class="stat-value">{{ cacheInfo.totalSize }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="stat-title">平均命中率</div>
          <div class="stat-value">{{ cacheInfo.hitRate }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="hover">
      <div slot="header"><span><i class="el-icon-cpu"></i> 缓存使用情况</span></div>
      <el-table :data="cacheInfo.caches" border size="small">
        <el-table-column prop="name" label="缓存名称" min-width="120"/>
        <el-table-column prop="size" label="条目数" min-width="100"/>
        <el-table-column prop="memorySize" label="内存占用" min-width="120"/>
        <el-table-column prop="hits" label="命中次数" min-width="110"/>
        <el-table-column prop="misses" label="未命中次数" min-width="110"/>
        <el-table-column label="命中率" min-width="200">
          <template slot-scope="scope">
            <el-progress :percentage="percent(scope.row.hitRate)" :stroke-width="12"/>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
  import { getCacheInfo } from '@/api/monitor'

  export default {
    name: 'CacheMonitor',
    data() {
      return {
        cacheInfo: {
          cacheCount: 0,
          totalSize: 0,
          hitRate: '0%',
          caches: []
        },
        timer: null
      }
    },
    created() {
      this.getCacheInfo()
      // 每30秒自动刷新
      this.timer = setInterval(this.getCacheInfo, 30000)
    },
    beforeDestroy() {
      if (this.timer) {
        clearInterval(this.timer)
      }
    },
    methods: {
      getCacheInfo() {
        getCacheInfo().then(response => {
          if (response.code === 20000) {
            this.cacheInfo = response.data
          }
        })
      },
      percent(str) {
        const num = parseFloat(str)
        return isNaN(num) ? 0 : Math.min(100, Math.max(0, num))
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";

  .stat-title {
    color: #909399;
    font-size: 13px;
    margin-bottom: 8px;
  }

  .stat-value {
    font-size: 24px;
    font-weight: 600;
  }
</style>
