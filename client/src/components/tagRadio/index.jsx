import React, { Component } from 'react';
import { View } from '@tarojs/components'

import "./index.scss"

class TagRadio extends Component {
    render() {
        return (
            <View onClick={this.props.onClick.bind(this,this.props.value)}>
                <View className={this.props.active === true ? 'active' : ''}>{this.props.label}</View>
            </View>
        )
    }
}
export default TagRadio;