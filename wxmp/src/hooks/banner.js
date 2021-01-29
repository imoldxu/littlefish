import { useState } from 'react';
import APIFunction from '@/services'
import { getStorageSync, setStorageSync } from 'remax/wechat';

const { getBannerList } = APIFunction

export default function useBanner(){
  let bannerList = getStorageSync('bannerList')
  if(bannerList===""){
    bannerList=[]
  }
  const [list, setList] = useState(bannerList)
  
  async function refresh(){
    const {success, data} = await getBannerList()
    if(success){
      setStorageSync('bannerList', data)
      setList(data)
    }
    
  }

  return {list, refresh}
}