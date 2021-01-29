import { Cell, Col, Grid, Row } from 'annar'
import React, { Component } from 'react'
import { View, Text, Image, navigateTo } from 'remax/wechat'
import AppContainer from '../../container'

import styles from './index.less'

export default ()=>{

    const {user} = AppContainer.useContainer()

    function handleClickUser(){
        if(user.state==""){
            navigateTo({url:'/pages/login/index'})
        }else{
            navigateTo({url:'pages/personal/index'})
        }
    }

    return(<View className="x-page">
            <View className={styles.personalArea} onClick={handleClickUser}>
                <Image src={user.state.avatarUrl? user.state.avatarUrl : '/images/nologin.png'}  className={styles.avatar}>
                </Image>
                <View class={styles.name}>
                    {
                        user.state.nickName? (<Text>{user.state.nickName}</Text>):(
                            <Text>立即登录</Text>)                        
                    }
                </View>
            </View>

            <View className="x-block">
                <Row>
                    <Col span={8}>
                        <Text>待成团</Text>    
                    </Col>
                    <Col span={8}>
                        <Text>已成团</Text>
                    </Col>
                    <Col span={8}>
                        <Text>待成团</Text>
                    </Col>
                </Row>
            </View>

            <View className="x-block">
                <Cell label="我的联系人" arrow>
                </Cell>
                <Cell label="我的消息" arrow>
                </Cell>
            </View>
    </View>)

}