import { axios } from 'taro-axios'
import { cloneDeep, isEmpty } from 'lodash'
//import router from '@tarojs/router'
import Taro from '@tarojs/taro'
import config from './config'
const { parse, compile } = require("path-to-regexp")
//import { CANCEL_REQUEST_MESSAGE } from 'utils/constant'

const CANCEL_REQUEST_MESSAGE = "cancel"
const { CancelToken } = axios
window.cancelRequest = new Map()

export default function request(options) {
  let { data, url, method = 'get' } = options
  const cloneData = cloneDeep(data)

  axios.defaults.headers.post['Content-Type'] = 'application/json';
  
  try {
    let domain = config.host
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
    //message.error(e.message)
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

  //小程序附带后端的session
  const session = Taro.getStorageSync('sessionId')
  if (session){
    options.headers = {'Cookie': 'SESSION='+session};
  }

  return axios(options)
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
      const { response, message } = error

      if (String(message) === CANCEL_REQUEST_MESSAGE) {
        return {
          success: false,
        }
      }

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
