import { useState } from 'react';
import APIFunction from '@/services'

const {queryDatePrice} = APIFunction

export default function useDatePrice(){
  const [list, setList] = useState([])
  
  async function query(payload){

    try{
      const {success, data} = await queryDatePrice(payload)
      if(success){
        setList(data)
      }
    }catch(e){
      throw e
    }
  }

  return {list, query}
}