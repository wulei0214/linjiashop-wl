<template>
  <div class="app-container">
    <el-row style="margin-bottom: 10px">
      <el-button icon="el-icon-refresh" size="small" @click="getServerInfo">刷新</el-button>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover">
          <div slot="header"><span><i class="el-icon-cpu"></i> CPU</span></div>
          <el-row>
            <el-col :span="12">
              <div class="cell-item">核心数</div>
              <div class="cell-value">{{ server.cpu.cpuNum }}</div>
            </el-col>
            <el-col :span="12">
              <div class="cell-item">CPU使用率</div>
              <div class="cell-value">{{ server.cpu.used }}</div>
            </el-col>
          </el-row>
          <el-progress :percentage="percent(server.cpu.used)" :stroke-width="12" style="margin-top: 10px"/>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <div slot="header"><span><i class="el-icon-odometer"></i> 内存</span></div>
          <el-row>
            <el-col :span="8">
              <div class="cell-item">总量</div>
              <div class="cell-value">{{ server.memory.total }}</div>
            </el-col>
            <el-col :span="8">
              <div class="cell-item">已用</div>
              <div class="cell-value">{{ server.memory.used }}</div>
            </el-col>
            <el-col :span="8">
              <div class="cell-item">剩余</div>
              <div class="cell-value">{{ server.memory.free }}</div>
            </el-col>
          </el-row>
          <el-progress :percentage="percent(server.memory.usage)" :stroke-width="12" style="margin-top: 10px"/>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 15px">
      <el-col :span="12">
        <el-card shadow="hover">
          <div slot="header"><span><i class="el-icon-monitor"></i> 服务器信息</span></div>
          <el-row>
            <el-col :span="12">
              <div class="cell-item">操作系统</div>
              <div class="cell-value">{{ server.os.osName }}</div>
            </el-col>
            <el-col :span="12">
              <div class="cell-item">系统架构</div>
              <div class="cell-value">{{ server.os.osArch }}</div>
            </el-col>
          </el-row>
          <el-row style="margin-top: 8px">
            <el-col :span="12">
              <div class="cell-item">主机名称</div>
              <div class="cell-value">{{ server.os.computerName }}</div>
            </el-col>
            <el-col :span="12">
              <div class="cell-item">主机IP</div>
              <div class="cell-value">{{ server.os.computerIp }}</div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <div slot="header"><span><i class="el-icon-coffee-cup"></i> Java虚拟机信息</span></div>
          <el-row>
            <el-col :span="12">
              <div class="cell-item">Java版本</div>
              <div class="cell-value">{{ server.jvm.javaVersion }}</div>
            </el-col>
            <el-col :span="12">
              <div class="cell-item">JVM名称</div>
              <div class="cell-value">{{ server.jvm.javaName }}</div>
            </el-col>
          </el-row>
          <el-row style="margin-top: 8px">
            <el-col :span="12">
              <div class="cell-item">启动时间</div>
              <div class="cell-value">{{ server.jvm.startTime }}</div>
            </el-col>
            <el-col :span="12">
              <div class="cell-item">运行时长</div>
              <div class="cell-value">{{ server.jvm.runTime }}</div>
            </el-col>
          </el-row>
          <el-row style="margin-top: 8px">
            <el-col :span="8">
              <div class="cell-item">最大内存</div>
              <div class="cell-value">{{ server.jvm.max }}</div>
            </el-col>
            <el-col :span="8">
              <div class="cell-item">已分配</div>
              <div class="cell-value">{{ server.jvm.total }}</div>
            </el-col>
            <el-col :span="8">
              <div class="cell-item">已使用</div>
              <div class="cell-value">{{ server.jvm.used }}（{{ server.jvm.usage }}）</div>
            </el-col>
          </el-row>
          <el-progress :percentage="percent(server.jvm.usage)" :stroke-width="12" style="margin-top: 10px"/>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="hover" style="margin-top: 15px">
      <div slot="header"><span><i class="el-icon-download"></i> 磁盘状态</span></div>
      <el-table :data="server.disks" border size="small">
        <el-table-column prop="dirName" label="盘符" min-width="90"/>
        <el-table-column prop="total" label="总大小" min-width="110"/>
        <el-table-column prop="used" label="已用" min-width="110"/>
        <el-table-column prop="free" label="可用" min-width="110"/>
        <el-table-column label="使用率" min-width="220">
          <template slot-scope="scope">
            <el-progress :percentage="percent(scope.row.usage)"/>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
  import { getServerInfo } from '@/api/monitor'

  export default {
    name: 'ServerMonitor',
    data() {
      return {
        server: {
          cpu: {},
          memory: {},
          os: {},
          jvm: {},
          disks: []
        },
        timer: null
      }
    },
    created() {
      this.getServerInfo()
      // 每10秒自动刷新
      this.timer = setInterval(this.getServerInfo, 10000)
    },
    beforeDestroy() {
      if (this.timer) {
        clearInterval(this.timer)
      }
    },
    methods: {
      getServerInfo() {
        getServerInfo().then(response => {
          if (response.code === 20000) {
            this.server = response.data
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

  .cell-item {
    color: #909399;
    font-size: 13px;
    margin-bottom: 4px;
  }

  .cell-value {
    font-size: 14px;
    font-weight: 600;
    word-break: break-all;
  }
</style>
