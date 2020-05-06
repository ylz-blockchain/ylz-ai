<template>
  <div class="app-container calendar-list-container">
    <el-card class="box-card">
      <!-- 查询区域 -->
      <div class="filter-container">
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入名称" v-model="listQuery.name"></el-input>
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入标题" v-model="listQuery.title"></el-input>
          <el-select v-model="listQuery.isOpen" placeholder="请选择是否公开">
            <el-option
              v-for="item in isOpens"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-select v-model="listQuery.recognitionTypeId" placeholder="请选择识别类型">
            <el-option
              v-for="item in recognitionTypes"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入描述" v-model="listQuery.description"></el-input>
          <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
          <el-button class="filter-item" v-if="image_btn_remove" style="margin-left: 10px;" @click="handleDeleteBatch" type="danger" icon="delete">删除</el-button>
      </div>

      <!-- table区域-begin -->
      <el-table
        :key='tableKey'
        :data="list"
        v-loading.body="listLoading"
        @sort-change="sortChange"
        @selection-change="handleSelectionChange"
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
        <el-table-column align="center" prop="name" label="name"> <template slot-scope="scope">
              <span>{{scope.row.name}}</span>
            </template> </el-table-column>
        <el-table-column align="center" prop="title" label="title"> <template slot-scope="scope">
              <span>{{scope.row.title}}</span>
            </template> </el-table-column>
        <el-table-column align="center" prop="recognitionTypeName" label="识别类型"> <template slot-scope="scope">
              <span>{{scope.row.recognitionTypeName}}</span>
            </template> </el-table-column>
        <el-table-column align="center" sortable="custom" prop="isOpen" label="是否公开"> <template slot-scope="scope">
              <span v-if="scope.row.isOpen === 1">公开</span>
              <span v-else>未公开</span>
            </template> </el-table-column>
        <el-table-column align="center" sortable="custom" prop="processStatus" label="是否处理"> <template slot-scope="scope">
              <span v-if="scope.row.processStatus === 1">已处理</span>
              <span v-else>未处理</span>
            </template> </el-table-column>
        <el-table-column align="center" sortable="custom" prop="size" label="size"> <template slot-scope="scope">
              <span>{{scope.row.size}}</span>
            </template> </el-table-column>
        <el-table-column align="center" sortable="custom" prop="likeNumber" label="点赞数"> <template slot-scope="scope">
              <span>{{scope.row.likeNumber}}</span>
            </template> </el-table-column>
        <el-table-column align="center" sortable="custom" prop="redirectNumber" label="转发次数"> <template slot-scope="scope">
              <span>{{scope.row.redirectNumber}}</span>
            </template> </el-table-column>
        <el-table-column align="center" sortable="custom" prop="browseNumber" label="浏览次数"> <template slot-scope="scope">
              <span>{{scope.row.browseNumber}}</span>
            </template> </el-table-column>
        <el-table-column align="center" prop="uploadUserName" label="上传用户昵称"> <template slot-scope="scope">
              <span>{{scope.row.uploadUserName}}</span>
            </template> </el-table-column>
        <el-table-column align="center" prop="prototypeVisitAddress" label="原图"> <template slot-scope="scope">
            <el-popover
              placement="right"
              title=""
              trigger="hover">
                <img :src="scope.row.prototypeVisitAddress" style="max-width: 500px; max-height: 500px;" />
                <img slot="reference" :src="scope.row.prototypeVisitAddress" :alt="scope.row.prototypeVisitAddress" style="width: 50px; height: 50px;">
            </el-popover>
            </template> </el-table-column>
        <el-table-column align="center" prop="recognitionVisitAddress" label="识别图"> <template slot-scope="scope">
            <el-popover
              placement="right"
              title=""
              trigger="hover">
                <img :src="scope.row.recognitionVisitAddress" style="max-width: 500px; max-height: 500px;" />
                <img slot="reference" :src="scope.row.recognitionVisitAddress" :alt="scope.row.recognitionVisitAddress" style="width: 50px; height: 50px;">
            </el-popover>
            </template> </el-table-column>
        <el-table-column align="center" label="操作" width="150"> <template slot-scope="scope">
            <el-button size="small" v-if="image_btn_remove" type="danger" @click="handleDelete(scope.row)">删除
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
    getImagePageList,
    expurgateImageById,
    expurgateImageBatch
  } from 'api/mobile/image';
  import {
    getRecognitionTypes
  } from 'api/mobile/recognitionType';
  import { mapGetters } from 'vuex';

  export default {
    name: 'mobileImage',
    data() {
      return {
        list: undefined,
        total: undefined,
        selections: undefined,
        listLoading: true,
        recognitionTypes: [], // 识别类型
        isOpens: [{
            value: 0,
            label: '不公开'
          }, {
            value: 1,
            label: '公开'
          }
        ],
        listQuery: {
          pageNo: 1,
          pageSize: 10,
          sortProp: undefined,
          sortType: undefined
        },
        image_btn_remove: false,
        tableKey: 0
      }
    },
    created() {
      this.getList();
      this.getRecognitionTypeList();
      this.image_btn_remove = this.buttons['/mobile/image/remove'] || false;
    },
    computed: {
      ...mapGetters([
        'buttons'
      ])
    },
    methods: {
      getList() {
        this.listLoading = true;
        getImagePageList(this.listQuery).then(response => {
          this.list = response.result.records;
          this.total = response.result.total;
          this.listLoading = false;
        })
      },
      getRecognitionTypeList() { // 获取识别类型
        getRecognitionTypes().then(response => {
          const data = response.result;
          data.forEach((item, index, array) => {
            this.recognitionTypes.push({value: item.id, label: item.name});
          });
        })
      },
      handleFilter() {
        this.listQuery.pageNo = 1;
        this.getList();
      },
      sortChange(data) {
        const { prop, order } = data;
        this.listQuery.sortProp = prop;
        this.listQuery.sortType = order;
        this.handleFilter();
      },
      handleDelete(row) {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            expurgateImageById({id: row.id})
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
            expurgateImageBatch({ids: ids})
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