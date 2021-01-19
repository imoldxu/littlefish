import config from '@/utils/config';
import APIFunction from '@/services';
import { navigateTo, getStorageSync, setStorageSync, getSetting, getUserInfo } from 'remax/wechat'
import { useState } from 'react';
import { wxLogin } from '@/utils/wxUser';

export default function useUser() {

  const info = getStorageSync('userInfo')
  const [state, setState] = useState(info)

  async function wxGetUserInfo() {//检查授权再处理
    const { authSetting } = await getSetting()
    if (authSetting['scope.userInfo']) {
      await authGetUserInfo()
      return Promise.resolve(true)
    } else {
      navigateTo({ url: '/pages/login/index' })
      return Promise.reject(false)
    }
  }

  async function checkLogin() {

    const userInfo = getStorageSync('userInfo')
    if (userInfo) {
      return Promise.resolve(true)
    } else {
      return wxGetUserInfo()
    }
  }

  async function authGetUserInfo() {//授权后的处理
    const wxUserInfo = await getUserInfo()
    try {
      const res = await APIFunction.updateUserInfo({
        rawData: wxUserInfo.rawData,
        signature: wxUserInfo.signature,
        encryptedData: wxUserInfo.encryptedData,
        iv: wxUserInfo.iv,
      })
      if (res.success) {
        setState(res.data)
        setStorageSync('userInfo', res.data)
      } else {
        console.log('更新用户失败')
      }
    } catch (e) {
      if (e.errCode === config.errorCode.SESSION_ERROR) {
        //强制登陆
        await wxLogin()
        const res = await APIFunction.updateUserInfo({
          rawData: wxUserInfo.rawData,
          signature: wxUserInfo.signature,
          encryptedData: wxUserInfo.encryptedData,
          iv: wxUserInfo.iv,
        })
        if (res.success) {
          setState(res.data)
          setStorageSync('userInfo', res.data)
        } else {
          console.log('更新用户失败')
        }
      } else {
        throw e
      }
    }
  }

  return { state, wxGetUserInfo, authGetUserInfo, checkLogin }
}