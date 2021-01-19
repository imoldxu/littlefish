import { useState } from 'react';
import APIFunction from '@/services'
import AppContainer from '../container';

const {getOrderDetail, confirmOrder} = APIFunction

export default function useOrder(){
  const [state, setState] = useState({})
  
  const { user } = AppContainer.useContainer()
  
  async function getDetail(payload){

    try{
      await user.checkLogin()

      const {success, data} = await getOrderDetail(payload)
      if(success){
        setState({ ...state, ...data })
      }
    }catch(e){

    }
  }

  async function confirm(payload){
    try{
        await user.checkLogin()
  
        const {success, data} = await confirmOrder(payload)
        if(success){
          setState({ ...state, ...data })
        }
    }catch(e){

    }
  }

  return {state, getDetail, confirmOrder}
}