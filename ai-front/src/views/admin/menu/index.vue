<template>
  <div class="app-container calendar-list-container">
    <el-card class="box-card">
      <!-- 查询区域 -->
      <div class="filter-container">
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入菜单名称" v-model="listQuery.name"></el-input>
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入菜单路径" v-model="listQuery.url"></el-input>
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入描述" v-model="listQuery.description"></el-input>
          <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
          <el-button class="filter-item" v-if="menu_btn_add" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="edit">添加</el-button>
          <el-button class="filter-item" v-if="menu_btn_remove" style="margin-left: 10px;" @click="handleDeleteBatch" type="danger" icon="delete">删除</el-button>
      </div>

      <!-- table区域-begin -->
      <el-table
        :key='tableKey'
        :data="list"
        v-loading.body="listLoading"
        @sort-change="sortChange"
        row-key="id"
        @selection-change="handleSelectionChange"
        :tree-props="{children: 'children'}"
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
        <el-table-column align="center" sortable="custom" prop="name" label="菜单名称"> <template slot-scope="scope">
              <span>{{scope.row.name}}</span>
            </template> </el-table-column>
        <el-table-column align="center" sortable="custom" prop="url" label="菜单路径"> <template slot-scope="scope">
              <span>{{scope.row.url}}</span>
            </template> </el-table-column>
        <el-table-column align="center" sortable="custom" prop="description" label="描述"> <template slot-scope="scope">
              <span>{{scope.row.description}}</span>
            </template> </el-table-column>
        <el-table-column align="center" label="操作" width="150"> <template slot-scope="scope">
            <el-button size="small" v-if="menu_btn_edit" type="success" @click="handleUpdate(scope.row)">编辑
            </el-button>
            <el-button size="small" v-if="menu_btn_remove" type="danger" @click="handleDelete(scope.row)">删除
            </el-button>
          </template> </el-table-column>
      </el-table>

      <!-- 表单区域 -->
      <div v-show="!listLoading" class="pagination-container">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.pageNo" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"> </el-pagination>
      </div>
      <el-dialog :before-close="cancel" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
        <el-form :model="form" :rules="rules" ref="form" label-width="100px">
          <el-form-item label="菜单名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入菜单名称"></el-input>
          </el-form-item>
          <el-form-item label="菜单路径" prop="url">
            <el-input v-model="form.url" placeholder="请输入菜单路径"></el-input>
          </el-form-item>
          <el-form-item label="父菜单" prop="menuId">
            <tree-select 
              :options="menuOptions"
              :value="form.parentId"
              :currentValue="form.id"
              @setValue="setParentId"
              />
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input v-model="form.description" placeholder="请输入描述"></el-input>
          </el-form-item>
        </el-form>
        <div scope="footer" class="dialog-footer">
          <el-button @click="cancel()">取 消</el-button>
          <el-button v-if="dialogStatus=='create'" type="primary" @click="create('form')">确 定</el-button>
          <el-button v-else type="primary" @click="update('form')">确 定</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
  import {
    getMenuPageList,
    generateMenu,
    changeMenuById,
    expurgateMenuById,
    expurgateMenuBatch,
    getMenuById,
    getAllMenus
  } from 'api/admin/menu';
  import { mapGetters } from 'vuex';
  import TreeSelect from "@/components/TreeSelect"

  export default {
    name: 'adminMenu',
    components: {
      TreeSelect
    },
    data() {
      return {
        form: {
          name: undefined,
          url: undefined,
          parentId: undefined,
          description: undefined,
        },
        rules: {
          name: [
            {
              required: true,
              message: '请输入菜单名称',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 255,
              message: '长度在 1 到 255 个字符',
              trigger: 'blur'
            }
          ],
          url: [
            {
              required: true,
              message: '请输入菜单路径',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 255,
              message: '长度在 1 到 255 个字符',
              trigger: 'blur'
            }
          ]
        },
        list: undefined,
        total: undefined,
        selections: undefined,
        listLoading: true,
        listQuery: {
          pageNo: 1,
          pageSize: 10,
          sortProp: undefined,
          sortType: undefined
        },
        menu_btn_add: false,
        menu_btn_edit: false,
        menu_btn_remove: false,
        dialogFormVisible: false,
        dialogStatus: '',
        textMap: {
          update: '编辑',
          create: '创建'
        },
        tableKey: 0,
        menuOptions: undefined // 菜单节点数据
      }
    },
    created() {
      this.getList();
      this.menu_btn_edit = this.buttons['/admin/menu/edit'] || false;
      this.menu_btn_remove = this.buttons['/admin/menu/remove'] || false;
      this.menu_btn_add = this.buttons['/admin/menu/add'] || false;
    },
    computed: {
      ...mapGetters([
        'buttons'
      ])
    },
    methods: {
      getList() {
        this.listLoading = true;
        getMenuPageList(this.listQuery).then(response => {
          this.list = response.result.records;
          this.total = response.result.total;
          this.listLoading = false;
        })
        this.getAllMenus();
      },
      getAllMenus() {
          getAllMenus().then(response => {
              this.menuOptions = response.result;
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
      handleCreate() {
        this.resetTemp();
        this.dialogStatus = 'create';
        this.dialogFormVisible = true;
      },
      handleUpdate(row) {
        this.dialogFormVisible = true;
        this.dialogStatus = 'update';
        getMenuById({id: row.id})
          .then(response => {
            const { result } = response;
            this.form = {
              id: result.id,
              name: result.name,
              url: result.url,
              parentId: result.parentId == 0?undefined:result.parentId,
              description: result.description,
            };
          });
      },
      handleDelete(row) {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            expurgateMenuById({id: row.id})
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
            expurgateMenuBatch({ids: ids})
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
      },
      cancel() {
        this.dialogFormVisible = false;
        this.$refs['form'].resetFields();
        this.getAllMenus();
      },
      setParentId(value) {
        this.form.parentId = value;
      },
      create(formName) {
        const set = this.$refs;
        set[formName].validate(valid => {
          if (valid) {
            generateMenu(this.form)
              .then(() => {
                this.dialogFormVisible = false;
                  this.getList();
                  this.$notify({
                  title: '成功',
                  message: '创建成功',
                  type: 'success',
                  duration: 2000
                });
              })
          } else {
            return false;
          }
        });
      },
      update(formName) {
        const set = this.$refs;
        set[formName].validate(valid => {
          if (valid) {
            changeMenuById(this.form).then(() => {
              this.dialogFormVisible = false;
              this.getList();
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success',
                duration: 2000
              });
            });
          } else {
            return false;
          }
        });
      },
      resetTemp() {
        this.form = {
          name: undefined,
          url: undefined,
          parentId: undefined,
          description: undefined,
        };
      }
    }
  }
</script>

<style scoped>
</style>
