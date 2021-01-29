export default {
    //用户接口
    login: 'POST /user/mini/session',
    updateUserInfo: 'PUT /user/mini/info',
    
    //首页广告
    getBannerList: '/banner',

    //查询跟团游
    queryGroupTour: '/grouptour',
    getGroupTourDetail: '/grouptour/:id',

    //查询每日价格
    queryDatePrice: '/dateprice',

    //查询自由行
    querySelfTour: '/selftour',
    getSelfTourDetail: '/selftour/:id',

    //联系人接口
    queryTourists: '/tourist',
    addTourist: 'POST /tourist',
    modifyTourist: 'PUT /tourist',
    delTourist: 'DELETE /tourist/:id',

    //订单接口
    queryOrders: '/order',
    getOrder: '/order/:id',
    commitOrder: 'POST /order',

}