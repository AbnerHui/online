import request from '@/utils/request'

export default {
    //分页查询讲师
    getTeacherList(page,limit) {
        return request({
            url: `/eduservice/teacherfront/getTeacherFrontList/${page}/${limit}`,
            method: 'post'
        })
    },
    //查询讲师详情
    getTeacherInfo(id) {
        return request({
            url: '/eduservice/teacherfront/getTeacherFrontInfo/'+id,
            method: 'get'
        })
    }
}