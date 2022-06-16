import { useState } from 'react';
import APIFunction from '@/services'

const { queryGroupTour } = APIFunction

export default function useGroupTourList(){
  const [state, setState] = useState({data:[],total:0})
  
  async function refresh(payload){
    const {success, data} = await queryGroupTour({current:1, pageSize:20})
    if(success){
      setState({ ...data})
    }
  }

  async function query(payload){
    const {success, data} = await queryGroupTour(payload)
    if(success){
        const { data, total } = data
        setState({ ...state, data: [...state.data, ...data], total })
    }  
  }

  return {state, refresh, query}
}