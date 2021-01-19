import { useState } from 'react';
import APIFunction from '@/services'

const { queryGroupTour } = APIFunction

export default function useGroupTourList(){
  const [state, setState] = useState({list:[],pagination:{}})
  
  async function query(payload){
    try{
        const {success, data} = await queryGroupTour(payload)
        if(success){
            const { list, pagination } = data
            setState({ ...state, list: [...state.list, ...list], pagination })
        }
    }catch(e){
        return Promise.reject(e)
    }
    
  }

  return {state, query}
}