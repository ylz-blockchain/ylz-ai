<template>
  <div class="app-container calendar-list-container">
    <el-card class="box-card">
      <!-- 查询区域 -->
      <div class="filter-container">
        <el-input
          @keyup.enter.native="handleFilter"
          style="width: 200px;"
          class="filter-item"
          placeholder="请输入名称"
          v-model="listQuery.name"
        ></el-input>
        <el-input
          @keyup.enter.native="handleFilter"
          style="width: 200px;"
          class="filter-item"
          placeholder="请输入标题"
          v-model="listQuery.title"
        ></el-input>
        <el-select v-model="listQuery.isOpen" placeholder="请选择是否公开">
          <el-option
            v-for="item in isOpens"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-select v-model="listQuery.processStatus" placeholder="请选择是否处理">
          <el-option
            v-for="item in processStatus"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-select v-model="listQuery.recognitionTypeId" placeholder="请选择识别类型">
          <el-option
            v-for="item in recognitionTypes"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-input
          @keyup.enter.native="handleFilter"
          style="width: 200px;"
          class="filter-item"
          placeholder="请输入描述"
          v-model="listQuery.description"
        ></el-input>
        <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
        <el-button
          class="filter-item"
          v-if="image_btn_remove"
          style="margin-left: 10px;"
          @click="handleDeleteBatch"
          type="danger"
          icon="delete"
        >删除</el-button>
      </div>

      <!-- table区域-begin -->
      <el-table
        :key="tableKey"
        :data="list"
        v-loading.body="listLoading"
        @sort-change="sortChange"
        @selection-change="handleSelectionChange"
        border
        fit
        highlight-current-row
        style="width: 100%"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column
          label="序号"
          sortable="custom"
          type="index"
          :index="(index)=>{return (index+1) + (listQuery.pageNo-1)*listQuery.pageSize}"
          align="center"
          width="50"
        />
        <el-table-column align="center" prop="name" label="name">
          <template slot-scope="scope">
            <span>{{scope.row.name}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="title" label="title">
          <template slot-scope="scope">
            <span>{{scope.row.title}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="recognitionTypeName" label="识别类型">
          <template slot-scope="scope">
            <span>{{scope.row.recognitionTypeName}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" sortable="custom" prop="isOpen" label="是否公开">
          <template slot-scope="scope">
            <span v-if="scope.row.isOpen === 1">公开</span>
            <span v-else>未公开</span>
          </template>
        </el-table-column>
        <el-table-column align="center" sortable="custom" prop="processStatus" label="是否处理">
          <template slot-scope="scope">
            <span v-if="scope.row.processStatus === 1">已处理</span>
            <span v-else-if="scope.row.processStatus === 0">未处理</span>
            <span v-else-if="scope.row.processStatus === 2">失败</span>
            <span v-else>审核失败</span>
          </template>
        </el-table-column>
        <el-table-column align="center" sortable="custom" prop="size" label="size">
          <template slot-scope="scope">
            <span>{{scope.row.size}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" sortable="custom" prop="likeNumber" label="点赞数">
          <template slot-scope="scope">
            <span>{{scope.row.likeNumber}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" sortable="custom" prop="redirectNumber" label="转发次数">
          <template slot-scope="scope">
            <span>{{scope.row.redirectNumber}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" sortable="custom" prop="browseNumber" label="浏览次数">
          <template slot-scope="scope">
            <span>{{scope.row.browseNumber}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="uploadUserName" label="上传用户昵称">
          <template slot-scope="scope">
            <span>{{scope.row.uploadUserName}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="prototypeVisitAddress" label="原图">
          <template slot-scope="scope">
            <el-tooltip placement="right" popper-class="light" :enterable="false" trigger="hover">
              <img
                slot="content"
                :src="scope.row.prototypeVisitAddress == null ? defaultImg:scope.row.prototypeVisitAddress"
                style="max-width: 500px; max-height: 500px;"
              />
              <img
                :src="scope.row.prototypeVisitAddress == null ? defaultImg:scope.row.prototypeVisitAddress"
                style="width: 50px; height: 50px;"
              />
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="recognitionVisitAddress" label="识别图">
          <template slot-scope="scope">
            <el-tooltip placement="right" popper-class="light" :enterable="false" trigger="hover">
              <img
                slot="content"
                :src="scope.row.recognitionVisitAddress == null ? defaultImg:scope.row.recognitionVisitAddress"
                style="max-width: 500px; max-height: 500px;"
              />
              <img
                :src="scope.row.recognitionVisitAddress == null ? defaultImg:scope.row.recognitionVisitAddress"
                style="width: 50px; height: 50px;"
              />
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column align="center" sortable="custom" prop="isEnable" label="是否启用">
          <template slot-scope="scope">
            <el-tooltip :content="scope.row.isEnable === 1?'启用':'禁用'" placement="top">
              <el-switch
                v-model="scope.row.isEnable"
                :active-value="1"
                :inactive-value="0"
                @change="handleEnableChange(scope.row)"
              ></el-switch>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="150">
          <template slot-scope="scope">
            <el-button
              size="small"
              v-if="image_btn_remove"
              type="danger"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 表单区域 -->
      <div v-show="!listLoading" class="pagination-container">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="listQuery.pageNo"
          :page-sizes="[10,20,30, 50]"
          :page-size="listQuery.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
import {
  getImagePageList,
  expurgateImageById,
  changeImageEnable,
  expurgateImageBatch
} from "api/mobile/image";
import { getRecognitionTypes } from "api/mobile/recognitionType";
import { mapGetters } from "vuex";
import defaultImage from "@/assets/empty.jpg";

export default {
  name: "mobileImage",
  data() {
    return {
      list: undefined,
      total: undefined,
      selections: undefined,
      listLoading: true,
      defaultImg: defaultImage, // 默认显示照片
      recognitionTypes: [], // 识别类型
      isOpens: [
        {
          value: 0,
          label: "不公开"
        },
        {
          value: 1,
          label: "公开"
        }
      ],
      processStatus: [
        {
          value: 0,
          label: "未处理"
        },
        {
          value: 1,
          label: "已处理"
        },
        {
          value: 2,
          label: "失败"
        },
        {
          value: 3,
          label: "审核失败"
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
    };
  },
  created() {
    this.getList();
    this.getRecognitionTypeList();
    this.image_btn_remove = this.buttons["/mobile/image/remove"] || false;
  },
  computed: {
    ...mapGetters(["buttons"])
  },
  methods: {
    getList() {
      this.listLoading = true;
      getImagePageList(this.listQuery).then(response => {
        this.list = response.result.records;
        this.total = response.result.total;
        this.listLoading = false;
      });
    },
    getRecognitionTypeList() {
      // 获取识别类型
      getRecognitionTypes().then(response => {
        const data = response.result;
        data.forEach((item, index, array) => {
          this.recognitionTypes.push({ value: item.id, label: item.name });
        });
      });
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
      this.$confirm("此操作将永久删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        expurgateImageById({ id: row.id }).then(() => {
          this.$notify({
            title: "成功",
            message: "删除成功",
            type: "success",
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
          title: "批量删除",
          message: "请选择列",
          type: "warning",
          duration: 2000
        });
        return;
      }
      var ids = "";
      this.selections.forEach((item, index, array) => {
        ids += item.id + ",";
      });
      ids = ids.substring(0, ids.length - 1);
      this.$confirm("此操作将永久删除选择列, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        expurgateImageBatch({ ids: ids }).then(() => {
          this.$notify({
            title: "成功",
            message: "删除成功",
            type: "success",
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
    },
    handleEnableChange(row) {
      // 修改照片是否启用
      changeImageEnable({
        id: row.id,
        isEnable: row.isEnable
      }).then(() => {
        this.getList();
        this.$notify({
          title: "成功",
          message: "修改成功",
          type: "success",
          duration: 2000
        });
      });
    }
  }
};
</script>

<style>
.light {
  background: #ffffff !important;
}

.el-tooltip__popper {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.el-tooltip__popper[x-placement^="left"] .popper__arrow {
  border-left-color: #ffffff;
}
.el-tooltip__popper[x-placement^="left"] .popper__arrow:after {
  border-left-color: #ffffff;
}
.el-tooltip__popper[x-placement^="right"] .popper__arrow {
  border-right-color: #ffffff;
}
.el-tooltip__popper[x-placement^="right"] .popper__arrow:after {
  border-right-color: #ffffff;
}
.el-tooltip__popper[x-placement^="top"] .popper__arrow {
  border-top-color: #ffffff;
}
.el-tooltip__popper[x-placement^="top"] .popper__arrow:after {
  border-top-color: #ffffff;
}
.el-tooltip__popper[x-placement^="bottom"] .popper__arrow {
  border-bottom-color: #ffffff;
}
.el-tooltip__popper[x-placement^="bottom"] .popper__arrow:after {
  border-bottom-color: #ffffff;
}
</style>