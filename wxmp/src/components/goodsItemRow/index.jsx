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

  return (
    <View onClick={props.onClick} className={styles.item}>
      <Row>
        <Col className={styles.content}>
          <Image mode="aspectFill" className={styles.image} src={imageUrl}></Image>
        </Col>
        <Col className={styles.content}>
          <View className={styles.title}>{title}</View>
          <View className={styles.price}>{`￥${minPrice/100}起`}</View>
        </Col>
      </Row>
    </View>
  );
}