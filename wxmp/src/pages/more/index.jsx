import React, { Component } from 'react'
import { View, Text } from 'remax/wechat'
import AppContainer from '../../container'

import styles from './index.less'

export default ()=>{

    const {user} = AppContainer.useContainer()

    return(<View className="page">
        <Text>hello</Text>
    </View>)

}