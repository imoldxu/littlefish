import Taro from '@tarojs/taro';
import config from '../utils/config';
import APIFunction from '../services';
import wxAPI from '../utils/wxpromise';

export default {
  namespace: 'user',
  state: {
  },
  subscriptions: {
    // fix: subscriptions 始终传入dispatch, app._history, taro 的 app._history 没有定义
  },
  reducers: {
    save(state, { payload }) {
      return { ...state, ...payload };
    },
    saveMore(state, { payload }) {
      const { list, pagination } = payload
      return { ...state, list: [...state.list, ...list], pagination };
    },
  },
  effects: {
    *autoLogin({ payload }, { call, put }) {
      console.log('autoLogin')
      const isvalid = yield call(wxAPI.checkSession, {})
      if (!isvalid) {
        yield put({type:'login', payload:{isShow:false}})
      } else {
        //noting to do
      }
    },
    *login({ payload }, { call, put }) { //强制登陆
      const {isShow=true} = payload
      if(isShow){
        Taro.showLoading({
          title: '登陆中',
        })
      }
      const wxCode = yield call(wxAPI.login, {})
      const res = yield call(APIFunction.login, { wxCode: wxCode })
      if(isShow){
        Taro.hideLoading()
      }
      if (res.success) {
        //保存sessionid
        Taro.setStorageSync('sessionId', res.data.sessionId);
        if(isShow){
          Taro.showToast({ title: '登陆成功', icon: 'success' })
        }
        //悄悄读取用户信息
      } else {
        Taro.showToast({ title: res.message, icon: 'fail' })
      }
    },
    *getUserInfo({ payload }, { call, put }) {//检查授权再处理
      const isAuth = yield call(wxAPI.getSetting, 'scope.userInfo')
      if (isAuth) {
        yield put({type:'authGetUserInfo', payload:{needGoback:false}})
      } else {
        //没有授权则跳转到授权页面
        Taro.navigateTo({ url: '/pages/login/login' })
      }
    },
    *authGetUserInfo({ payload }, { call, put, take }) {//授权后的处理
      const { needGoback=true } = payload

      Taro.showLoading({ title: '登陆中' })
      const wxUserInfo = yield call(wxAPI.getUserInfo, {})
      try {
        const res = yield call(APIFunction.updateUserInfo, {
          rawData: wxUserInfo.rawData,
          signature: wxUserInfo.signature,
          encryptedData: wxUserInfo.encryptedData,
          iv: wxUserInfo.iv,
        })
        Taro.hideLoading()
        if (res.success) {
          yield put({type:'save', payload: res.data})
          Taro.setStorageSync('userInfo', res.data)
          Taro.showToast({
            title: '登陆成功',
            icon: 'success',
            duration: 2000
          })
          if(needGoback){//针对于授权登录页，登陆成功之后要返回上一页
            Taro.navigateBack()
          }
        } else {
          Taro.showToast(res.message)
        }
      } catch (e) {
        if (e.errCode === config.errorCode.SESSION_ERROR) {
          //强制登陆
          yield put({type:'login', payload:{}})
          yield take('login/@@end')
          const res = yield call(APIFunction.updateUserInfo, {
            rawData: wxUserInfo.rawData,
            signature: wxUserInfo.signature,
            encryptedData: wxUserInfo.encryptedData,
            iv: wxUserInfo.iv,
          })
          Taro.hideLoading()
          if (res.success) {
            Taro.setStorageSync('userInfo', res.data)
            Taro.showToast({
              title: '登陆成功',
              icon: 'success',
              duration: 2000
            })
            Taro.navigateBack()
          } else {
            Taro.showToast(res.message)
          }
        } else {
          Taro.hideLoading()
          throw e
        }
      }
    },
    *getOrder({ payload }, { call, put }){
      const user = Taro.getStorageSync('userInfo')
      if(user){
        console.log("request my order")
      }else{
        const res = yield put({type:'user/getUserInfo', payload:{}})
        console.log("request after getUserInfo"+res)
      }
    }
  },
}