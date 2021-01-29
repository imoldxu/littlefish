import { useState } from 'react';
import APIFunction from '@/services'
import { getStorageSync, setStorageSync } from 'remax/wechat';
import AppContainer from '../container';

const { addTourist, modifyTourist, delTourist, queryTourists } = APIFunction

export default function useTourist(){
  
  const [touristList, setList] = useState([])
  //const [tourist, setTourist] = useState()

  const { user } = AppContainer.useContainer()

  async function add(payload){
    if(user.checkLogin()){
        const {success, data} = await addTourist()
        if(success){
        setList(touristList.push(data))
        }
    }
  }

  async function modify(payload, index){
    const {success, data} = await modifyTourist(payload)
    if(success){
      touristList[index] = data
      setList(touristList)
    }
  }

  async function del(payload, index){
    const {success, data} = await delTourist(payload)
    if(success){
      touristList.splice(index,1)
      setList(TouristList)
    }
  }

  async function query(payload){
    const {success, data} = await queryTourists(payload)
    if(success){
        setList(data)
    }
  }

  return {list, add, modify, query}
}