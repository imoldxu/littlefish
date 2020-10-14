import Taro from '@tarojs/taro'

export default {
    checkSession(){
        return new Promise((resolve, reject)=>{
            Taro.checkSession({
                success: function () {
                    //session_key 未过期，并且在本生命周期一直有效
                    resolve(true)
                },
                fail: function () {
                    resolve(false)
                }
            })
        })
    },
    login(){
        return new Promise((resolve, reject)=>{
            Taro.login({
                success: function (res) {
                  if (res.code) {
                    resolve(res.code)
                  } else {
                    reject(res)
                  }
                },
                fail: function(e){
                    reject(e)
                }, 
            });
        })
    },
    getUserInfo(){
        return new Promise((resolve, reject)=>{
            Taro.getUserInfo({
                success: function(res) {
                    resolve(res)
                  },
                  fail: function(e){
                    reject(e)
                  }
                }
            )
        })
    },
    getSetting(scope){
        return new Promise((resolve, reject)=>{
            Taro.getSetting({
                success: function (res) {
                    if(res.authSetting[scope]){
                        resolve(true)
                    }else{
                        resolve(false)
                    }
                },
                fail: function(e){
                    reject(e)
                }
              })

        })
    }



}