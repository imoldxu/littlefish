import React, { Component } from 'react';
import { View } from 'remax/wechat'

import styles from "./index.less"

class TagRadio extends Component {
    render() {
        return (
            <View className={styles['radio-wrap']} onClick={this.props.onClick.bind(this,this.props.value)}>
                <View className={this.props.active === true ? styles.active : styles.label}>{this.props.label}</View>
            </View>
        )
    }
}
export default TagRadio;