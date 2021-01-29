import React, { useState } from 'react'
import { useQuery } from 'remax'
import { Cell, Icon } from 'annar'
import { View, Text, ScrollView, Button } from 'remax/wechat'
import useOrder from '../../hooks/order'

import styles from './index.less'

export default ()=>{

  const query = useQuery()
  const {state, confirmOrder} = useOrder()
  //const {totalPrice, setTotalPrice} = useState() //总价
  const [ adultList, setAdultList ] = useState([])
  const [ childList, setChildList ] = useState([])
  const [ roomNum, setRoomNum ] = useState(0)
  const [ showPriceDetail, setShowPriceDetail ] = useState(false)

  const { goodsId, goodName, skuId, skuName, dayPriceStr } = query

  const dayPrice = JSON.parse(dayPriceStr)

  const totalPrice = dayPrice.adultPrice*adultList.length + dayPrice.childPrice*childList.length //+ adultList/2 计算单房差


  return (
    <ScrollView className="x-page">
      <View className="x-card">
        <View className={styles.title}><Text>{goodName}</Text></View>
        <View><Text>{skuName}</Text></View>
        <View><Text>{`出发日期:${dayPrice.date}`}</Text></View>
      </View>

      <View className="x-block">
        <View><Text>出行信息</Text></View>
        <Cell label="成人" extra={
            <Button type="default" onClick={()=>handleAddTourist("adult")}>
              <Icon type="add"></Icon>
            </Button>
          }></Cell>
        <Cell label="儿童" extra={
          <Button type="default" onClick={()=>handleAddTourist("child")}>
            <Icon type="add"></Icon>
          </Button>
        }></Cell>
      </View>

      <View className="x-block">
        <View><Text>预订人信息</Text></View>
        <Cell.Input label="联系人"
          placeholder="请输入联系人姓名"
          icon="people"
          align="right"
          ></Cell.Input>
        <Cell.Input label="手机号"
          required
          placeholder="请输入手机号"
          icon="phone"
          align="right"
          ></Cell.Input>
        <Cell.Input label="邮箱"
          placeholder="请输入邮箱，便于接收订单通知"
          icon="mail"
          align="right"
          ></Cell.Input>
        <Cell.Input label="备注"
          placeholder="如有特殊需要请备注"
          icon="text"
          align="right"
          ></Cell.Input>
      </View>

      <View><Text>点击"提交订单"及标识已阅读并同意</Text><Text>《平台服务协议》</Text><Text>《旅游安全须知》</Text></View>

      <View className={styles.foot}>
          <View>
            <View><Text>总价</Text></View>
            <View><Text className={styles.price}>{`￥${totalPrice/100}`}</Text><Text onClick={()=>setShowPriceDetail(true)}>明细<Icon type="fold"></Icon></Text></View>
          </View>
          <Button className={styles.commitButton} type="primary">提交订单</Button>
      </View>
    </ScrollView>)
}
