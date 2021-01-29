import { useState } from 'react';
import APIFunction from '@/services'
import AppContainer from '../container';

const {getOrder, confirmOrder} = APIFunction

export default function useOrder(){
  const [state, setState] = useState({})
  
  const { user } = AppContainer.useContainer()
  
  async function getDetail(payload){

    if(await user.checkLogin()){
      const {success, data} = await getOrder(payload)
      if(success){
        setState({ ...state, ...data })
      }
    }
  }

  async function confirm(payload){
    
    if(await user.checkLogin()){
      const {success, data} = await confirmOrder(payload)
      if(success){
        setState({ ...state, ...data })
      }
    }
  }

  return {state, getDetail, confirm}
}