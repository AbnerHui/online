<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li :class="{active:oneIndex==-2}">
                  <a title="全部" href="#" @click="searchOne(null,-2)">全部</a>
                </li>
                <li v-for="(item,index) in subjectNestedList" :key="index" :class="{active:oneIndex==index}">
                  <a :title="item.title" href="#" @click="searchOne(item.id,index)">{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="(item,index) in subSubjectList" :key="index" :class="{active:twoIndex==index}">
                  <a :title="item.title" href="#" @click="searchTwo(item.id,index)">{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current bg-orange':buyCountSort!=''}">
                <a title="销量" href="javascript:void(0);" @click="searchBuyCount()">销量
                <span :class="{hide:buyCountSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':gmtCreateSort!=''}">
                <a title="最新" href="javascript:void(0);" @click="searchGmtCreate()">最新
                <span :class="{hide:gmtCreateSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':priceSort!=''}">
                <a title="价格" href="javascript:void(0);" @click="searchPrice()">价格&nbsp;
                  <span :class="{hide:priceSort==''}">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="data.total == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>

          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="data.total > 0">
            <ul class="of" id="bna">
              <li v-for="item in data.items" :key="item.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="item.cover" class="img-responsive" :alt="item.title">
                    <div class="cc-mask">
                      <a :href="'/course/'+item.id" :title="item.title" class="comm-btn c-btn-1">{{item.title}}</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+item.id" :title="item.title" class="course-title fsize18 c-333">{{item.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span v-if="Number(item.price) === 0" class="fr jgTag bg-green">
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{item.viewCount}}人学习</i>
                      |
                      <i class="c-999 f-fA">9634评论</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>

        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)">首</a>
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(data.current-1)">&lt;</a>
            <a
              v-for="page in data.pages"
              :key="page"
              :class="{current: data.current == page, undisable: data.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="gotoPage(page)">{{ page }}</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(data.current+1)">&gt;</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="末页"
              @click.prevent="gotoPage(data.pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>

import courseApi from '@/api/course'

export default {
  data() {
    return {
        page:1,
        data:{},//课程列表
        subjectNestedList: [], // 一级分类列表
        subSubjectList: [], // 二级分类列表
        searchObj: {}, // 查询表单对象

        oneIndex:-1,
        twoIndex:-1,
        buyCountSort:"",
        gmtCreateSort:"",
        priceSort:""
    }
  },
  created() {
    this.initCourseFirst()
    this.initSubject()
  },
  methods: {
    //查询第一页数据
    initCourseFirst() {
      courseApi.getCourseList(1,8,this.searchObj)
               .then(response => {
                 this.data = response.data.data
               })
    },
    //查询所有的分类
    initSubject() {
      courseApi.getAllSubject()
               .then(response => {
                 console.log(response);
                 
                 this.subjectNestedList = response.data.data.list
               })
    },
    //分页切换的方法
    gotoPage(page) {
        courseApi.getCourseList(page,8,this.searchObj)
                .then(response => {
                   this.data = response.data.data
                })
    },
    //根据一级分类id查询二级分类 并查询一级所有的课程
    searchOne(subjectParentId,index) {
      if(subjectParentId === null) {
         this.oneIndex = index
         this.searchObj.subjectParentId = ''
         this.searchObj.subjectId = ''
         this.subSubjectList = []
         this.buyCountSort = ''
         this.gmtCreateSort = ''
         this.priceSort = ''
         this.gotoPage(1)
      }else{
       //把传递index值给oneIndex让样式显示
       this.oneIndex = index
       this.searchObj = {}
       this.subSubjectList = []
       this.twoIndex = -1
       
      //点击某个一级分类进行条件查询
      this.searchObj.subjectParentId = subjectParentId
      this.gotoPage(1)

      //用传过来的id和所有一级id比较,如果相同显示二级分类
      for(let i = 0 ; i < this.subjectNestedList.length; i++ ) {
          //获取每一个一级分类
          let oneSubject = this.subjectNestedList[i]
          //比较
          if(subjectParentId == oneSubject.id) {
            //根据一级分类id查询二级分类
            this.subSubjectList = oneSubject.children
          }
      }
     }
    },
    //根据二级分类查询课程
    searchTwo(subjectId,index) {
      //把传递index值给twoIndex让样式显示
      this.twoIndex = index
      //点击某个二级分类进行条件查询
      this.searchObj.subjectId = subjectId
      this.gotoPage(1)
    },
    //根据销量进行排序
    searchBuyCount() {
      //设置值,让样式显示 ''就是不显示
      this.buyCountSort = 1
      this.gmtCreateSort = ''
      this.priceSort = ''
      //赋值
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      //查询
      this.gotoPage(1)
     },
     //根据最新时间排序
     searchGmtCreate() {
//设置值,让样式显示 ''就是不显示
      this.buyCountSort = ''
      this.gmtCreateSort = 1
      this.priceSort = ''
      //赋值
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      //查询
      this.gotoPage(1)
     },
     //根据价格进行排序
     searchPrice() {
     //设置值,让样式显示 ''就是不显示
      this.buyCountSort = ''
      this.gmtCreateSort = ''
      this.priceSort = 1
      //赋值
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      //查询
      this.gotoPage(1)
     }
  }
};
</script>

<style scoped>
  .active {
    background: #bdbdbd;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }
</style>
