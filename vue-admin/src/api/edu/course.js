import request from '@/utils/request'

export default {
    //添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: '/eduservice/course/addCourse',
            method: 'post',
            data: courseInfo
        })
    },
    //查询所有讲师
    getListTeacher() {
        return request({
            url: '/eduservice/teacher/findAll',
            method: 'get'
        })
    },
    //根据课程id查询课程基本信息
    getCourseInfoId(id) {
        return request({
            url: '/eduservice/course/getCourseInfo/'+id,
            method: 'get'
        })
    },
    //修改课程信息
    updateCourseInfo(courseInfo) {
        return request({
            url: '/eduservice/course/updateCourseInfo',
            method: 'post',
            data: courseInfo
        })
    },
    //课程确认信息得显示
    getPublihCourseInfo(id) {
        return request({
            url: '/eduservice/course/getPublishCourseInfo/'+id,
            method: 'get'
        })
    },
    //课程的最终发布
    publihCourse(id) {
        return request({
            url: '/eduservice/course/pulishCourse/'+id,
            method: 'post'
        })
    },
    //课程列表展示
    pageCourseCondition(current,limit,courseQuery) {
        return request({
            url: `/eduservice/course/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            data: courseQuery
        })
    },
    //删除课程
    deleteCourse(courseId) {
        return request({
            url: '/eduservice/course/'+courseId,
            method: 'delete'
        })
    }

}
