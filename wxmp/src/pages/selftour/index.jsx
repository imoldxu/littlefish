import React , {useState, useEffect} from 'react'
import {getWindowHeight} from '@/utils/uicalc'
import GoodsItemRow from '@/components/goodsItemRow'
import { createSelectorQuery, View } from 'remax/wechat'
import { usePageEvent } from 'remax/macro'
import { Tabs, TabContent } from 'annar'

export default ()=> {

    usePageEvent('onReady', ()=>{
        createSelectorQuery().selectAll('.myid').boundingClientRect(rects=>{
            rects.forEach(rect=>
                console.log("rect.top"+rect.top)
                )}).exec()
    } )

    return (
        
        <View>
            <View className="xx myid" style={{height:100, width:"100vw", backgroundColor:"yellowgreen"}}>
                {/* <Tabs type="card" onTabClick={({ key }) => console.log(key)} activeKey="1"
                        animated={true}>
                        {
                            tabs.map((tab,index) => {
                                return (
                                    <TabContent key={tab.key} tab={tab.title} key={index}>
                                    </TabContent>
                                )
                            })
                        }
                </Tabs> */}
            </View>
            <View className="xx myid" style={{height:100, width:"100vw", backgroundColor:"red"}}>hello2</View>
            <View className="xx myid" style={{height:100, width:"100vw", backgroundColor:"blue"}}>hello3</View>
        </View>
        
    )

}

const tabs = [
    { key: 0, title: '图文介绍' },
    { key: 1, title: '行程路线' },
    { key: 2, title: '费用说明' },
    { key: 3, title: '行程须知' }
]

// export default class SelfTour extends React.Component{


//     onReady(){
//         createSelectorQuery().selectAll('.myid').boundingClientRect(rects=>{
//                     rects.forEach(rect=>
//                         console.log("rect.top"+rect.top)
//                         )}).exec()
//     }

//     render(){
//         return (
//                     <View>
//                         <View className="xx myid" style={{height:100, width:"100vw", backgroundColor:"yellowgreen"}}>
//                             {/* <Tabs type="card" onTabClick={({ key }) => console.log(key)} activeKey="1"
//                                     animated={true}>
//                                     {
//                                         tabs.map((tab,index) => {
//                                             return (
//                                                 <TabContent key={tab.key} tab={tab.title} key={index}>
//                                                 </TabContent>
//                                             )
//                                         })
//                                     }
//                             </Tabs> */}
//                         </View>
//                         <View className="xx myid" style={{height:100, width:"100vw", backgroundColor:"red"}}>hello2</View>
//                         <View className="xx myid" style={{height:100, width:"100vw", backgroundColor:"blue"}}>hello3</View>
//                     </View>
//                 )
//     }
// }