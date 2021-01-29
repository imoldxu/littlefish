import * as React from 'react';
import { usePageEvent } from 'remax/macro';
import { View, Text, Image, ScrollView, Swiper, SwiperItem } from 'remax/wechat';
import useBanner from '../../hooks/banner';
import styles from './index.css';

export default () => {

  const banner = useBanner()

  usePageEvent('onLoad', () => {
    refresh()
  });

  function refresh(){
    banner.refresh()
  }

  return (
    <View className="x-page">
    <ScrollView className={styles.app}>
      <Swiper indicatorColor='#999'
        indicatorActiveColor='#333'
        vertical={false}
        circular
        indicatorDots
        duration="10"
        autoplay>
        {
          banner.list.map((imagesrc, index)=>{
              return (
                <SwiperItem key={index}>
                  <Image mode="scaleToFill" className={styles.bannerimage} src={imagesrc}></Image>
                </SwiperItem>
              )
            }
          )
        }
        
      </Swiper>
    </ScrollView>
    </View>
  );
};
