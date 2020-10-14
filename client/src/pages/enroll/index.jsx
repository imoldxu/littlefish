import React, { Component } from 'react'
import Taro from '@tarojs/taro'
import { View, Text, ScrollView } from '@tarojs/components'
import { AtButton, AtForm } from 'taro-ui'
import { connect } from 'react-redux'
import ExCalendar from '../../components/excalendar'
import TagRadio from '../../components/TagRadio'
import TagRadioGroup from '../../components/TagRadioGroup'

@connect(({enroll}) => ({enroll}))
export default class Enroll extends Component {

  onReady() {
  }

  componentWillMount () { }

  componentDidMount () { }

  componentWillUnmount () { }

  componentDidShow () { }

  componentDidHide () { }

  handleChange = (value)=>{
     const {dispatch} = this.props
     dispatch({type:'enroll/save', payload: value})
  }

  render () {

    const { enroll } = this.props

    const { line } = enroll

    return (
      <ScrollView>
          <View>
            <TagRadioGroup name="line" active={line} onChange={this.handleChange}>
              <TagRadio value="九黄熊4天3晚">九黄熊4天3晚</TagRadio>
              <TagRadio value="九黄草4天3晚">九黄草4天3晚</TagRadio>
            </TagRadioGroup>
            <TagRadioGroup name="traffic" active={line} onChange={this.handleChange}>
              <TagRadio value="普通车">普通车</TagRadio>
              <TagRadio value="保姆车">保姆车</TagRadio>
            </TagRadioGroup>
          </View>
          <ExCalendar view="month"></ExCalendar>
          <View>
          </View>
      </ScrollView>
    )
  }
}
