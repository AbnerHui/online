import request from '@/utils/request'

export default {
    //添加小节
    addVideo(eduVideo) {
        return request({
            url: '/eduservice/video/addVideo',
            method: 'post',
            data: eduVideo
        })
    },
    //根据id查询小节
    getVideo(id) {
        return request({
            url: '/eduservice/video/getVideoInfo/'+id,
            method: 'get'
        })
    },
    //修改小节
    updateVideo(eduVideo) {
        return request({
            url: '/eduservice/video/updateVideo',
            method: 'post',
            data: eduVideo
        })
    },
    //删除小节
    deleteVideo(id) {
        return request({
            url: '/eduservice/video/'+id,
            method: 'delete'
        })
    },
    //删除阿里云的视频
    deleteAliyunvod(id) {
        return request({
            url: '/eduvod/video/removeAliyunVideo/'+id,
            method: 'delete'
        })
    }
}
