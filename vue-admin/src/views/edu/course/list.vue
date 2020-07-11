<template>
  <div class="app-container">
    
     <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item style="width:16%">
        <el-input v-model="courseQuery.title" placeholder="课程名称"/>
      </el-form-item>

      <el-form-item style="width:16%">
        <el-select v-model="courseQuery.status" clearable placeholder="状态">
          <el-option :value="'Normal'" label="已发布"/>
          <el-option :value="'Draft'" label="未发布"/>
        </el-select>
      </el-form-item>

      <el-form-item >
        <el-date-picker
          v-model="courseQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
    
      <el-form-item>
        <el-date-picker
          v-model="courseQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title"  align="center" label="课程名称" />

      <el-table-column label="课程状态"  align="center" width="80">
        <template slot-scope="scope">
          {{ scope.row.status==='Normal'?'已发布':'未发布' }}
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum"  align="center" label="课时数" width="60" />

      <el-table-column prop="gmtCreate"  align="center" label="添加时间" width="160"/>

      <el-table-column prop="viewCount" label="浏览数量" align="center" width="60" />

      <el-table-column label="操作" width="350" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/course/info/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程</el-button>
          </router-link>
          <router-link :to="'/edu/course/chapter/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑大纲</el-button>
          </router-link>
          <el-button type="danger"  size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除课程</el-button>
        </template>
      </el-table-column>

    </el-table>

    <!-- 分页 getList不需要传,默认封装好了-->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList" 
    />
  </div>
</template>

<script>

//引入teacher.js文件
import course from '@/api/edu/course'

export default {

    data() {
        return {
            list: null, //查询之后接口返回集合
            page: 1, //当前页
            limit: 10, //每页记录数
            total: 0, //总记录数
            courseQuery: {} //条件封装对象 值可以不定义会自动加进去
        }
    },
    created() {
        this.getList()
    },
    methods: {
        //显示所有课程列表
        getList(page = 1) {
            this.page = page
            course.pageCourseCondition(this.page,this.limit,this.courseQuery)
                  .then(response => {
                       this.list = response.data.rows
                       this.total = response.data.total
                  })  
        },
        //清空方法
        resetData() {
            this.courseQuery = {}
            this.getList()
        },
        //删除课程
        removeDataById(id) {
            this.$confirm('此操作将永久删除课程记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            course.deleteCourse(id)
                  .then(response => {
                       this.$message({
                    type: 'success',
                    message: '删除成功!'
                    })   
                    this.getList()
                  })
          })
        }
    }
}
</script>