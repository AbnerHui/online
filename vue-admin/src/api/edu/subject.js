import request from '@/utils/request'

export default {
    //课程分类
    getSubjectList() {
        return request({
            url: '/eduservice/subject/getAllSubject',
            method: 'get',
        })
    },
}

