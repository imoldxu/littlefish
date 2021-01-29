import { useState } from 'react';
import APIFunction from '@/services'

const { queryGroupTour } = APIFunction

export default function useGroupTourList(){
  const [state, setState] = useState({list:[],pagination:{}})
  
  async function refresh(payload){
    const {success, data} = await queryGroupTour({pageIndex:1, pageSize:20})
    if(success){
      setState({ ...data})
    }
  }

  async function query(payload){
    const {success, data} = await queryGroupTour(payload)
    if(success){
        const { list, pagination } = data
        setState({ ...state, list: [...state.list, ...list], pagination })
    }  
  }

  return {state, refresh, query}
}