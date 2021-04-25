import * as React from 'react';
import { usePageEvent } from 'remax/macro';
import { View, Text, Image, ScrollView, Swiper, SwiperItem, navigateTo } from 'remax/wechat';
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

  //跳转到banner对应的地址
  function handleClick(redirectUrl){
    if(redirectUrl){
      navigateTo({url: redirectUrl})
    }
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
          banner.list.map((item, index)=>{
              return (
                <SwiperItem key={index} onClick={()=>handleClick(item.redirectUrl)}>
                  <Image mode="scaleToFill" className={styles.bannerimage} src={item.resUrl}></Image>
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
