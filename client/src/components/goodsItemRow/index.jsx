import React from 'react'
import { View, Text, Image } from '@tarojs/components'

import './index.scss'

const GoodsItemRow = React.memo(({ index, style, data }) => {

  const { imageUrl, name, price} = data;

  return (
    <View className="at-row">
      <Image src={imageUrl}></Image>
      <View className="at-row">
        <View><Text>{name}</Text></View>
        <View><Text>{price}</Text></View>
      </View>
    </View>
  );
})

export default GoodsItemRow