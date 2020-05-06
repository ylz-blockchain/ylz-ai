<template>
  <div class="app-container calendar-list-container">
    <el-card class="box-card">
      <!-- 查询区域 -->
      <div class="filter-container">
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入用户评论" v-model="listQuery.commentDescription"></el-input>
          <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
          <el-button class="filter-item" v-if="imageComment_btn_remove" style="margin-left: 10px;" @click="handleDeleteBatch" type="danger" icon="delete">删除</el-button>
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
        <el-table-column align="center" prop="userName" label="评论用户"> <template slot-scope="scope">
              <span>{{scope.row.userName}}</span>
            </template> </el-table-column>
        <el-table-column align="center" prop="imageName" label="照片名称"> <template slot-scope="scope">
              <span>{{scope.row.imageName}}</span>
            </template> </el-table-column>
        <el-table-column align="center" prop="commentDescription" label="评论"> <template slot-scope="scope">
              <span>{{scope.row.commentDescription}}</span>
            </template> </el-table-column>
        <el-table-column align="center" label="操作" width="150"> <template slot-scope="scope">
            <el-button size="small" v-if="imageComment_btn_remove" type="danger" @click="handleDelete(scope.row)">删除
            </el-button>
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
    getImageCommentPageList,
    expurgateImageCommentById,
    expurgateImageCommentBatch
  } from 'api/mobile/imageComment';
  import { mapGetters } from 'vuex';

  export default {
    name: 'mobileImageComment',
    data() {
      return {
        list: undefined,
        total: undefined,
        selections: undefined,
        listLoading: true,
        listQuery: {
          pageNo: 1,
          pageSize: 10
        },
        imageComment_btn_remove: false,
        tableKey: 0
      }
    },
    created() {
      this.getList();
      this.imageComment_btn_remove = this.buttons['/mobile/imageComment/remove'] || false;
    },
    computed: {
      ...mapGetters([
        'buttons'
      ])
    },
    methods: {
      getList() {
        this.listLoading = true;
        getImageCommentPageList(this.listQuery).then(response => {
          this.list = response.result.records;
          this.total = response.result.total;
          this.listLoading = false;
        })
      },
      handleFilter() {
        this.listQuery.pageNo = 1;
        this.getList();
      },
      handleDelete(row) {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            expurgateImageCommentById({id: row.id})
              .then(() => {
                this.$notify({
                  title: '成功',
                  message: '删除成功',
                  type: 'success',
                  duration: 2000
                });
                const index = this.list.indexOf(row);
                this.list.splice(index, 1);
              });
          });
      },
      handleSelectionChange(val) {
        this.selections = val;
      },
      handleDeleteBatch() {
        if (this.selections == undefined || this.selections.length == 0) {
          this.$notify({
            title: '批量删除',
            message: '请选择列',
            type: 'warning',
            duration: 2000
          });
          return
        }
        var ids = '';
        this.selections.forEach((item, index, array) => {
          ids += item.id + ",";
        });
        ids = ids.substring(0, ids.length -1);
        this.$confirm('此操作将永久删除选择列, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            expurgateImageCommentBatch({ids: ids})
              .then(() => {
                this.$notify({
                  title: '成功',
                  message: '删除成功',
                  type: 'success',
                  duration: 2000
                });
                this.getList();
            });
        });
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