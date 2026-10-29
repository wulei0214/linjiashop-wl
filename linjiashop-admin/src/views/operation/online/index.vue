<template>
  <div class="app-container">
    <div class="block">
      <el-input v-model="listQuery.account" placeholder="按账号搜索" size="small" style="width: 200px"
                clearable @keyup.enter.native="search"/>
      <el-button size="small" type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button size="small" icon="el-icon-refresh" @click="getList">刷新</el-button>
    </div>

    <el-table :data="list" v-loading="listLoading" border size="small">
      <el-table-column prop="account" label="账号" min-width="120"/>
      <el-table-column prop="name" label="姓名" min-width="100"/>
      <el-table-column prop="deptName" label="部门" min-width="140"/>
      <el-table-column prop="roleNames" label="角色" min-width="140">
        <template slot-scope="scope">
          {{ (scope.row.roleNames || []).join('，') }}
        </template>
      </el-table-column>
      <el-table-column prop="loginTime" label="登录时间" min-width="150"/>
      <el-table-column prop="expireTime" label="有效期至" min-width="150"/>
      <el-table-column label="操作" width="120">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" icon="el-icon-remove-outline"
                     @click="handleKick(scope.row)">强制下线</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import { getOnlineList, kickOnline } from '@/api/monitor'

  export default {
    name: 'OnlineUsers',
    data() {
      return {
        list: [],
        listLoading: false,
        listQuery: {
          account: ''
        }
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        getOnlineList(this.listQuery).then(response => {
          this.listLoading = false
          if (response.code === 20000) {
            this.list = response.data
          }
        }).catch(() => {
          this.listLoading = false
        })
      },
      search() {
        this.getList()
      },
      handleKick(row) {
        this.$confirm('确认强制下线用户「' + (row.name || row.account) + '」吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          kickOnline(row.token).then(response => {
            if (response.code === 20000) {
              this.$message.success('已强制下线')
              this.getList()
            } else {
              this.$message.error(response.message)
            }
          })
        }).catch(() => {
        })
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>
