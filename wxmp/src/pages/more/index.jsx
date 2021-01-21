import { Cell, Col, Grid, Row } from 'annar'
import React, { Component } from 'react'
import { View, Text, Image } from 'remax/wechat'
import AppContainer from '../../container'

import styles from './index.less'

export default ()=>{

    const {user} = AppContainer.useContainer()

    return(<View className="x-page">
            <View className={styles.personalArea} >
                <Image src={user.state.avatarUrl}  className={styles.avatar}>
                </Image>
                <View class={styles.name}>
                    <Text>{user.state.nickName}</Text>
                    <Text>{user.state.userPhone}</Text>
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