import request from '@/utils/request'

export default {
    //条件分页查询课程
    getCourseList(page,limit,searchObj) {
        return request({
            url: `/eduservice/coursefont/getFrontCourseList/${page}/${limit}`,
            method: 'post',
            data: searchObj
        })
    },
    //查询所有分类
    getAllSubject() {
        return request({
            url: '/eduservice/subject/getAllSubject',
            method: 'get'
        })
    },
    //课程详情方法
    getCourseInfo(id) {
        return request({
            url: '/eduservice/coursefont/getFrontCourseInfo/'+id,
            method: 'get'
        })
    }
   
}