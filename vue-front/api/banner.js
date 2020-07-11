import request from '@/utils/request'

export default {
    //查询前两天banner地址
    getListBanner() {
        return request({
            url: '/educms/bannerfront/getAllBanner',
            method: 'get'
        })
    }
}