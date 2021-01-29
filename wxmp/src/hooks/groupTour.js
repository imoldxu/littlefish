import { useState } from 'react';
import APIFunction from '@/services'
import AppContainer from '../container';

const {getGroupTourDetail} = APIFunction

export default function useGroupTour(){
  const [state, setState] = useState({})
  const { user } = AppContainer.useContainer()
  
  async function getDetail(payload){
    if(await user.checkLogin()){
      const {success, data} = await getGroupTourDetail(payload)
      if(success){
        setState({ ...state, ...data })
      }
    }
  }

  return {state, getDetail}
}