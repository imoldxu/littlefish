export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        path: '/user',
        routes: [
          {
            name: 'login',
            path: '/user/login',
            component: './User/login',
          },
        ],
      },
    ],
  },
  // {
  //   name: 'order',
  //   icon: 'Trademark',
  //   path: '/OrderList',
  //   access: 'admin',
  //   component: './OrderList',
  // },
  {
    path: '/dashboard',
    name: 'dashboard',
    access: 'admin',
    icon: 'dashboard',
    component: './Dashboard',
  },
  {
    name: 'trade',
    icon: 'Trademark',
    path: '/trade',
    access: 'admin',
    routes:[
      {
        name: 'order',
        //icon: 'Trademark',
        path: '/trade/order-list',
        access: 'admin',
        component: './OrderList',
      },
      {
        name: 'bill',
        //icon: 'MoneyCollect',
        access: 'admin',
        path: '/trade/bill-list',
        component: './BillList',
      },
    ]
  },
  {
    name: 'goods',
    icon: 'Snippets',
    path: '/goods-list',
    access: 'admin',
    component: './GoodsList',
  },
  {
    name: 'coupon',
    icon: 'Book',
    path: '/coupon-list',
    access: 'admin',
    component: './CouponList',
  },
  {
    name: 'sku',
    hideInMenu: true,
    path: '/goods-list/sku',
    access: 'admin',
    component: './GoodsList/Sku',
  },
  {
    name: 'changePassword',
    hideInMenu: true,
    access: 'admin',
    path: '/change-password',
    component: './ChangePassword',
  },
  // {
  //   name: 'stock',
  //   icon: 'Link',
  //   path: '/StockList',
  //   access: 'admin',
  //   component: './StockList',
  // },
  {
    name: 'user',
    icon: 'User',
    path: '/user-list',
    access: 'admin',
    component: './UserList',
  },
  {
    name: 'article',
    icon: 'FileText',
    access: 'admin',
    path: '/article-list',
    component: './ArticleList',
  },
  {
    name: 'activity',
    icon: 'Rocket',
    access: 'admin',
    path: '/test',
    component: './Test',
  },
  {
    name: 'banner',
    icon: 'PayCircle',
    path: '/banner',
    access: 'admin',
    component: './Banner',
  },
  // {
  //   name: 'saleStatistic',
  //   icon: 'BarChart',
  //   access: 'admin',
  //   path: '/saleStatistic',
  //   component: './SaleStatistic',
  // },
  {
    name: 'product',
    icon: 'Link',
    path: '/Product',
    access: 'admin',
    component: './ProductList',
  },
  {
    name: 'product',
    //path: '/Product',//好像可以不设置
    hideInMenu: true,
    routes: [
      {
        name: 'new',
        access: 'admin',
        path: '/product/new',
        component: './ProductList/edit',
      },
      {
        name: 'edit',
        access: 'admin',
        path: '/product/edit',
        component: './ProductList/edit',
      },
      {
        name: 'sku',
        access: 'admin',
        path: '/product/sku',
        component: './ProductList/skus',
      },
      {
        name: 'price-calendar',
        access: 'admin',
        path: '/product/sku/price-calendar',
        component: './ProductList/priceCalendar',
      },
    ],
  },
  {
    name: 'system',
    icon: 'BarChart',
    access: 'admin',
    path: '/system',
    routes: [
      // {
      //   name: 'storeList',
      //   icon: 'Bank',
      //   path: '/system/store',
      //   access: 'admin',
      //   component: './StoreList',
      // },
      {
        name: 'staffList',
        icon: 'UsergroupAdd',
        path: '/system/staffs',
        access: 'admin',
        component: './StaffList',
      },
      // {
      //   name: 'dealer',
      //   icon: 'PayCircle',
      //   path: '/system/dealer-list',
      //   access: 'admin',
      //   component: './DealerList',
      // },
    ]
  },
  {
    path: '/',
    redirect: '/dashboard',
  },
  {
    component: './404',
  },
];
