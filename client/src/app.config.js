export default {
  pages: [
    'pages/index/index',
    'pages/login/index',
    'pages/selfTour/index',
    'pages/groupTour/index',
    'pages/more/index',
    'pages/enroll/index',
    'pages/goods/index',
  ],
  window: {
    backgroundTextStyle: 'light',
    navigationBarBackgroundColor: '#fff',
    navigationBarTitleText: 'WeChat',
    navigationBarTextStyle: 'black'
  },
  tabBar: {
    list: [{
      iconPath: './asset/images/index.png',
      selectedIconPath: './asset/images/index_focus.png',
      pagePath: 'pages/index/index',
      text: '首页'
    }, {
      iconPath: './asset/images/grouptour.png',
      selectedIconPath: './asset/images/grouptour_focus.png',
      pagePath: 'pages/groupTour/index',
      text: '跟团游'
    }, {
      iconPath: './asset/images/selftour.png',
      selectedIconPath: './asset/images/selftour_focus.png',
      pagePath: 'pages/selfTour/index',
      text: '自由行'
    },
    {
      iconPath: './asset/images/me.png',
      selectedIconPath: './asset/images/me_focus.png',
      pagePath: 'pages/more/index',
      text: '我的'
    }],
    color: '#000',
    selectedColor: '#56abe4',
    backgroundColor: '#fff',
    borderStyle: 'white'
  },
}
