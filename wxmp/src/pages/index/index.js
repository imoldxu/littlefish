import * as React from 'react';
import { usePageEvent } from 'remax/macro';
import { View, Text, Image, ScrollView, Swiper, SwiperItem } from 'remax/wechat';
import { autoLogin } from '../../utils/wxUser';
import styles from './index.css';

export default () => {

  return (
    <ScrollView className={styles.app}>
      <Swiper indicatorColor='#999'
        indicatorActiveColor='#333'
        vertical={false}
        circular
        indicatorDots
        duration="10"
        autoplay>
        <SwiperItem>
          <Image mode="scaleToFill" className={styles.bannerimage} src="http://p1-q.mafengwo.net/s17/M00/6D/42/CoUBXl-T1rGAdikJAAFvuTI95kY60.jpeg?imageMogr2%2Fthumbnail%2F%21400x300r%2Fgravity%2FCenter%2Fcrop%2F%21400x300%2Fquality%2F100"></Image>
        </SwiperItem>
      </Swiper>
    </ScrollView>
  );
};
