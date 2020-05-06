<template>
  <div class="app-container calendar-list-container">
    <el-card class="box-card">
      <!-- 查询区域 -->
      <div class="filter-container">
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入转发目标地" v-model="listQuery.redirectPlace"></el-input>
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入描述" v-model="listQuery.description"></el-input>
          <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
      </div>

      <!-- table区域-begin -->
      <el-table
        :key='tableKey'
        :data="list"
        v-loading.body="listLoading"
        border fit highlight-current-row
        style="width: 100%">
        <el-table-column
            type="selection"
            width="55">
          </el-table-column>
        <el-table-column
            label="序号"
            sortable="custom"
            type="index"
            :index="(index)=>{return (index+1) + (listQuery.pageNo-1)*listQuery.pageSize}"
            align="center"
            width="50"
          />
        <el-table-column align="center" prop="userName" label="转发用户"> <template slot-scope="scope">
              <span>{{scope.row.userName}}</span>
            </template> </el-table-column>
        <el-table-column align="center" prop="imageName" label="照片名称"> <template slot-scope="scope">
              <span>{{scope.row.imageName}}</span>
            </template> </el-table-column>
        <el-table-column align="center" prop="redirectPlace" label="转发目标地"> <template slot-scope="scope">
              <span>{{scope.row.redirectPlace}}</span>
            </template> </el-table-column>
        <el-table-column align="center" prop="description" label="描述"> <template slot-scope="scope">
              <span>{{scope.row.description}}</span>
            </template> </el-table-column>
      </el-table>
      <!-- 表单区域 -->
      <div v-show="!listLoading" class="pagination-container">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.pageNo" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"> </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
  import {
    getUserImageRedirectPageList
  } from 'api/mobile/userImageRedirect';
  import { mapGetters } from 'vuex';

  export default {
    name: 'mobileUserImageRedirect',
    data() {
      return {
        list: undefined,
        total: undefined,
        listLoading: true,
        listQuery: {
          pageNo: 1,
          pageSize: 10
        },
        userImageRedirect_btn_add: false,
        userImageRedirect_btn_edit: false,
        userImageRedirect_btn_remove: false,
        tableKey: 0
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        this.listLoading = true;
        getUserImageRedirectPageList(this.listQuery).then(response => {
          this.list = response.result.records;
          this.total = response.result.total;
          this.listLoading = false;
        })
      },
      handleFilter() {
        this.listQuery.pageNo = 1;
        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.limit = val;
        this.getList();
      },
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getList();
      }
    }
  }
</script>

<style scoped>
</style>