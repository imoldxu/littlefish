import React from 'react'
import { View, Text, Image } from '@tarojs/components'

import './index.scss'

const GoodsItemRow = React.memo(({ index, style, data }) => {

  const { imageUrl, name, price} = data;

  return (
    <View className="at-row">
      <Image className="at-col" src={imageUrl}></Image>
      <View className="at-col">
        <View className="at-row">
          <View><Text>{name}</Text></View>
          <View><Text>{price}</Text></View>
        </View>
      </View>
    </View>
  );
})

export default GoodsItemRow