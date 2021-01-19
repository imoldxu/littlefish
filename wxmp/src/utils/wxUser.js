import { checkSession, login, setStorageSync } from "remax/wechat";
import APIFunction from "@/services";

export async function autoLogin() {
    checkSession().then(() => console.log("checkSession is ok")).catch(e => wxLogin())
}

export async function wxLogin() { //强制登陆
    try {
        const { code } = await login()
        const { success, data, message } = await APIFunction.login({ code: code })
        if (success) {
            //保存sessionid
            setStorageSync('sessionId', data.sessionId);
            //悄悄读取用户信息
        } else {
            console.log(message)
        }
    } catch (e) {
        console.log(e)
    }
}