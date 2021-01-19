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
            <Button  type="primary" openType="getUserInfo" onGetUserInfo={handleLogin}>
                <Image src="/images/wechat.png"></Image>
                <Text>微信快速登录</Text>
            </Button>
        </View>
    )
    
}