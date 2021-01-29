import React, { Component } from 'react';
import { View } from 'remax/wechat'
import styles from './index.less'

class TagRadioGroup extends Component {
    handleActiveChange(value) {
        console.log(`${value}被选中了`)
        this.props.onChange(value)
    }
    render() {
        return (
            <View className={styles['radio-group']}>
                {
                    React.Children.map(this.props.children, child => {
                        let isActive = this.props.active === child.props.value ? true : false
                        return React.cloneElement(child, {
                            label: child.props.children,
                            value: child.props.value,
                            key: child.props.value,
                            active: isActive,
                            onClick: this.handleActiveChange.bind(this)
                        })
                    })
                }
            </View>
        )
    }
}

export default TagRadioGroup;