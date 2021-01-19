import { Col, Row } from 'annar';
import React from 'react'
import { View, Text, Image } from 'remax/wechat'
import styles from './index.less'

export default (props) => {

  const { imageUrls, title, maxPrice, minPrice} = props.data;

  let imageUrl = "/images/empty-image.png"
  if (imageUrls && imageUrls.length>0){
    imageUrl = imageUrls[0]
  }

  const minRMB = (minPrice/100)

  return (
    <View onClick={props.onClick}>
      <Row gutter={12} className={styles.item} >
        <Col>
          <Image mode="aspectFill" className={styles.image} src={imageUrl}></Image>
        </Col>
        <Col className={styles.content}>
          <View className={styles.title}><Text>{title}</Text></View>
          <View className={styles.price}><Text>{`￥${minRMB}起`}</Text></View>
        </Col>
      </Row>
    </View>
  );
}