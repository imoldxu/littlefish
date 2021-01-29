import React, { Component } from 'react';
import { View, Text } from 'remax/wechat'

import styles from "./index.less"

class TagRadio extends Component {
    render() {
        return (
            <View className={this.props.active === true ? styles.active : styles.label} onClick={this.props.onClick.bind(this,this.props.value)}>
                <Text>{this.props.label}</Text>
            </View>
        )
    }
}
export default TagRadio;