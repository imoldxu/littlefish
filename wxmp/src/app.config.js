module.exports = {
  pages: [
    'pages/index/index',
    'pages/login/index',
    'pages/selftour/index',
    'pages/grouptour/index',
    'pages/more/index',
    'pages/enroll/index',
    'pages/goods/index',
  ],
  window: {
    backgroundTextStyle: 'light',
    navigationBarBackgroundColor: '#fff',
    navigationBarTitleText: 'littleFish',
    navigationBarTextStyle: 'black'
  },
  tabBar: {
    list: [{
      iconPath: '/images/index.png',
      selectedIconPath: '/images/index_focus.png',
      pagePath: 'pages/index/index',
      text: '首页'
    }, {
      iconPath: '/images/grouptour.png',
      selectedIconPath: '/images/grouptour_focus.png',
      pagePath: 'pages/grouptour/index',
      text: '跟团游'
    }, {
      iconPath: '/images/selftour.png',
      selectedIconPath: '/images/selftour_focus.png',
      pagePath: 'pages/selftour/index',
      text: '自由行'
    },
    {
      iconPath: '/images/me.png',
      selectedIconPath: '/images/me_focus.png',
      pagePath: 'pages/more/index',
      text: '我的'
    }],
    color: '#000',
    selectedColor: '#56abe4',
    backgroundColor: '#fff',
    borderStyle: 'white'
  },
}
