import React from 'react'
import { View, Image, Button, navigateBack, Text,showToast } from 'remax/wechat'
import AppContainer from '@/container'

import styles from './index.less'

export default ()=>{

    let {user} = AppContainer.useContainer()

    async function handleLogin (res){
        console.log(res)
        try{
            await user.authGetUserInfo()
            navigateBack()
        }catch(e){
            showToast(e)
        }   
    }

    return(
        <View>
            <View className={styles.header}>
                <Image src="/images/logo.png"></Image>
                <View>小鱼快游</View>
            </View>
            <View class={styles.content}>
                <View>申请获取以下权限</View>
                <Text>获得你的公开信息(昵称，头像等)</Text>
            </View>
            <Button className={styles.loginButton} type="primary" openType="getUserInfo" onGetUserInfo={handleLogin}>
                <Image src="/images/wechat.png" style={{height:"64",width:"64"}}></Image>
                <Text>微信登录</Text>
            </Button>
        </View>
    )
    
}