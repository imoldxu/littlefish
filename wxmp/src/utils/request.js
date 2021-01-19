import axios from 'axios'
import mpadapter from 'axios-miniprogram-adapter'
import { cloneDeep, isEmpty } from 'lodash'
import config from './config'
import { getStorageSync, navigateTo, showModal, showToast } from 'remax/wechat'
const { parse, compile } = require("path-to-regexp")


const mpAxios = axios.create({
  adapter: mpadapter,
  baseURL: "http://"+config.host,
  //withCredentials: true,  //小程序和后台通信不依赖于Credential
})

//const mpAxios = axios
//mpAxios.defaults.baseURL = "http://"+config.host,

//配置post请求默认的方式
mpAxios.defaults.headers.post['Content-Type'] = 'application/json';
mpAxios.defaults.headers.put['Content-Type'] = 'application/json';
mpAxios.defaults.headers.patch['Content-Type'] = 'application/json';

mpAxios.interceptors.request.use(
  config => {
    try {
      const session = getStorageSync('sessionId')
      if (session){
        //config.headers = {'Cookie': 'SESSION='+session};
        config.headers.Cookie = `SESSION=${session}`;
      }
    } catch (e) {
      // Do something when catch error
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
)

//响应值过滤器
mpAxios.interceptors.response.use(
  response => {
    return response;
  }, err=> {
    console.log(err)
    if(err.response){
      //后台返回了错误信息
      if (err.response.status == 504||err.response.status == 404) {
        showToast({title: '找不到对应的资源', icon: "none",  duration: 2000});
        // return Promise.resolve(err);
      } else if (err.response.status == 403) {
        showToast({title: '权限不足,请联系管理员!', icon: "none",  duration: 2000});
        // return Promise.resolve(err);
      } else {
        //session过期
        if(err.response.data.code == config.errorCode.SESSION_ERROR){
          showModal({
            title: '登陆提示',
            content: '你的登陆状态已过期，请重新登陆',
            success: function (res) {
              if (res.confirm) {
                return navigateTo({url:'pages/login/index'})
              } else if (res.cancel) {
                //noting to do
              }
            }
          })
          // return Promise.resolve(err);
        } else {
          showToast({title: err.response.data.message, icon: "none", duration: 2000})
        } 
      }
    }else{
      showToast({title: err.message, icon: "none", duration: 2000})
    }
    return Promise.reject(err);
  }
)

export default function request(options) {
  let { data, url, method = 'GET' } = options
  const cloneData = cloneDeep(data)

  try {
    let domain = '' //config.host
    const urlMatch = url.match(/[a-zA-z]+:\/\/[^/]*/)
    if (urlMatch) {
      ;[domain] = urlMatch
      url = url.slice(domain.length)
    }

    const match = parse(url)
    url = compile(url)(data)

    for (const item of match) {
      if (item instanceof Object && item.name in cloneData) {
        delete cloneData[item.name]
      }
    }
    url = domain + url
  } catch (e) {
    console.log(e)
  }

  options.url = url
  if('GET' == options.method){
    options.params = cloneData  //非get方法，不采用url后面接参数
  }
  //取消请求？场景在哪儿
  // options.cancelToken = new CancelToken(cancel => {
  //   window.cancelRequest.set(Symbol(Date.now()), {
  //     pathname: Taro.getCurrentInstance().router.path,
  //     cancel,
  //   })
  // })

  return mpAxios(options)
    .then(response => {
      const { statusText, status, data } = response

      // let result = {}
      // if (typeof data === 'object') {
      //   result = data
      //   if (Array.isArray(data)) {
      //     result.list = data
      //   }
      // } else {
      //   result.data = data
      // }

      return Promise.resolve({
        success: true,
        message: statusText,
        statusCode: status,
        //...result,
        data,
      })
    })
    .catch(error => {
      console.log(error)
      const { response, message } = error

      // if (String(message) === CANCEL_REQUEST_MESSAGE) {
      //   return {
      //     success: false,
      //   }
      // }

      let msg
      let errCode
      let statusCode

      if (response && response instanceof Object) {
        const { data, statusText } = response
        statusCode = response.status
        errCode = data.code
        msg = data.message || statusText
      } else {
        statusCode = 600
        errCode = 0
        msg = error.message || '服务网络异常，请稍后重试'
      }

      /* eslint-disable */
      return Promise.reject({//使用reject导致请求获取不到错误的响应,使用reject只有在try catch才能获取到错误
        success: false,
        statusCode,
        errCode: errCode,
        message: msg,
      })
    })
}
