<template>
  <div class="app-container">

    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>

      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right"/>
      </el-form-item>

      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>

      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
          <!-- 头衔缩略图 -->
          <pan-thumb :image="teacher.avatar"/>
           <!-- 文件上传按钮 -->
          <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
          </el-button>
          <!--
      v-show：是否显示上传组件
      :key：类似于id，如果一个页面多个图片上传控件，可以做区分
      :url：后台上传的url地址
      @close：关闭上传组件
      @crop-upload-success：上传成功后的回调 -->
      <image-cropper
                   v-show="imagecropperShow"
                   :width="300"
                   :height="300"
                   :key="imagecropperKey"
                   :url="BASE_API+'/eduoss/fileOss'"
                   field="file"
                   @close="close"
                   @crop-upload-success="cropSuccess"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>

import teacherApi from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
    components: { ImageCropper, PanThumb },
    data() {
        return {
            teacher: {
                name: '',
                sort: 0,
                level: 1,
                career: '',
                intro: '',
                avatar: ''
                //avatar: process.env.OSS_PATH + '/2020/06/29/f2b2e4e068e24819abe9bfcdd9d50ff501.jpg'
            },
            saveBtnDisabled: false, // 保存按钮是否禁用
            imagecropperShow: false, // 上传弹框组件是否显示
            imagecropperKey: 0, //上传组件key值
            BASE_API: process.env.BASE_API, //地址
        }
    },
    //created只会执行一次
    created() {
      this.init()
    },
    watch: {
      //监听路由变化
      $route(to, from) {
        this.init()
      }
    },
    methods: {
        //关闭上传头像弹框
        close() {
            this.imagecropperShow = false
            //关闭时让组件初始化
            this.imagecropperKey = this.imagecropperKey + 1
        },
        //上传成功方法 里面做了封装直接写就可以
        cropSuccess(data) {
            this.imagecropperShow = false
            this.teacher.avatar = data.url
            //上传时让组件初始化
            this.imagecropperKey = this.imagecropperKey + 1
        },
        init() {
          if(this.$route.params && this.$route.params.id){
              const id = this.$route.params.id
              this.getInfo(id)
           }else{
             this.teacher = {}
           }
        },
        //根据讲师ID查询
        getInfo(id) {
          teacherApi.getTeacherInfo(id).then(response => { this.teacher = response.data.teacher })
        },
        //添加和保存
        saveOrUpdate() {
          if(this.teacher.id){
               this.updateTeacher()
              
          }else{
              this.saveTeacher()
          }
        },
        //添加讲师的方法
        saveTeacher() {
          //如果用户添加的时候没有传图片就默认给他一张图片
          if(this.teacher.avastar === undefined){
            this.teacher.avatar = process.env.OSS_PATH +'/2020/07/03/b97748a48fc24fc18071e4a6ab32cc24file.png'
            console.log(this.teacher.avatar)
          }
          teacherApi.addTeacher(this.teacher)
                    .then(response => {
                       this.$message({
                         type: 'success',
                         message: '添加成功!'
                       })
                      this.$router.push({path:'/edu/teacher/table'})
                    })
        },
        //修改讲师
        updateTeacher() {
          teacherApi.updateTeacher(this.teacher)
                    .then(response => {
                      this.$message({
                         type: 'success',
                         message: '修改成功!'
                       })
                      this.$router.push({path:'/edu/teacher/table'})
                    })
        }
    }
}
</script>