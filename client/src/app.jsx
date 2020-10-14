import React, { Component } from 'react'
import { Provider } from 'react-redux'
import dva from './utils/dva'
import models from './models'
import Taro, { getCurrentInstance } from '@tarojs/taro'

import './app.scss'
import 'taro-ui/dist/style/index.scss'
import config from './utils/config'

const dvaApp = dva.createApp({
  initialState: {},
  models: models,
  onError(e, dispatch) {
    //FIXME：全局处理effect的系统错误
    //dispatch(action("sys/error", e));
    Taro.hideLoading()
    //任意需要session的接口过期，则进入此流程，询问用户是否重新登陆
    if(e.errCode === config.errorCode.SESSION_ERROR){
      Taro.showModal({
        title: '登陆提示',
        content: '你的登陆状态已过期，请重新登陆',
        success: function (res) {
          if (res.confirm) {
            dispatch({type:'user/login', payload:{}})
          } else if (res.cancel) {
            //noting to do
          }
        }
      })
    }else{
      Taro.showToast({title:e.message, icon:'none', duration:3000})
    }
  },
})
const store = dvaApp.getStore()

export default class App extends Component {

  componentWillMount() {
    /**监听程序初始化，初始化完成时触发（全局只触发一次） */
    //启动时完成一次静默登陆
    const dispatch = dva.getDispatch()
    dispatch({type:'user/autoLogin', payload:{}})
  }

  componentDidMount() { }

  componentDidShow() { }

  componentDidHide() { }

  componentDidCatchError() { }

  // this.props.children 是将要会渲染的页面
  render() {
    //return this.props.children
    return (
      <Provider store={store}>
        {this.props.children}
      </Provider>
    )
  }
}