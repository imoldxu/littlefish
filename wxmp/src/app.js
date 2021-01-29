import AppContainer from './container';
import React from 'react';
import APIFunction from '@/services'
import { useAppEvent } from 'remax/macro';
import { canIUse, checkSession, getUpdateManager, showModal, showToast } from 'remax/wechat';

import 'annar/dist/annar.css';
import './app.css';
import { autoLogin } from './utils/wxUser';

const App = props => {

  useAppEvent('onLaunch', () => {

    autoLogin()

    if (canIUse('getUpdateManager')) {
      const updateManager = getUpdateManager();
      updateManager.onCheckForUpdate(function (res) {
        if (res.hasUpdate) {
          updateManager.onUpdateReady(function () {
            showModal({
              title: '更新提示',
              content: '新版本已经准备好，是否重启应用？',
              success: function (res) {
                if (res.confirm) {
                  updateManager.applyUpdate();
                }
              }
            });
          });
          updateManager.onUpdateFailed(function () {
            showModal({
              title: '已经有新版本了哟~',
              content: '新版本已经上线啦~，请您删除当前小程序，重新搜索打开哟~'
            });
          });
        }
      });
    } else {
      showModal({
        title: '提示',
        content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
      });
    }
  });

  useAppEvent('onUnhandledRejection', (error)=>{
    console.log(error)
    const { reason } = error
    const { message="未知错误" } = reason
    showToast({title: message, icon: "none", duration: 2000})
  })

  return (
    <AppContainer.Provider >
      {props.children}
    </AppContainer.Provider>
  )
}

export default App;
