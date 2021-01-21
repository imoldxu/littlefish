import { Col, Loading, Row } from 'annar';
import React, { useState } from 'react'
import RecycleView from 'remax-recycle-view/lib/src/index'; // 重要，由于remax的跨平台同构特性，需要这样引入
import { usePageEvent } from 'remax/macro';
import { Image, navigateTo, showToast, Text, View } from "remax/wechat";
import GoodsItemRow from '../../components/goodsItemRow';
import useTourGroupList from '../../hooks/groupTourList';
import styles from './index.less'

// const mockData = [];
// for (let i = 0; i < 500; i++) {
//   mockData.push({
//     height: 220,//((i % 5) + 1) * 100,
//     text: `mock block ${i}`,
//   });
// }


export default () => {

    const [ loading, setLoading ] = useState(true)
    const tourGroupList = useTourGroupList()
    const [ triggered, setTriggered] = useState(false)
    const { list, pagination } = tourGroupList.state

    const data = list.map(i=>{
        i.height=240  //设置Item的高度
        return i
    })

    let isGetMore = false

    function refresh(){
        setLoading(true)
        tourGroupList.refresh().catch(e=>{
            console.log(e)
        }).finally(()=>
            setLoading(false)
        )
    }

    usePageEvent
    ('onLoad', () => {
        refresh()
    });

    function handleClickItem(goodsId, minPrice){
        navigateTo({url:`/pages/goods/index?goodsId=${goodsId}&minPrice=${minPrice}`})
    }

    return (
        <View className="x-page">
            {
                list.length>0? (
                    <RecycleView
                        className={styles.recycleView}
                        //placeholderImage="https://gw.alicdn.com/tfs/TB18fUJCxD1gK0jSZFyXXciOVXa-750-656.png"
                        data={list}
                        overscanCount={20}
                        // headerHeight={200}
                        // bottomHeight={300}
                        // renderHeader={() => {
                        //     return <View style={{ height: 200, backgroundColor: 'yellow' }}>this is header</View>;
                        // }}
                        // renderBottom={() => {
                        //     return <View style={{ height: 300, backgroundColor: 'yellow' }}>this is bottom</View>;
                        // }}
                        renderItem={(item, index) => {
                            return (
                                <GoodsItemRow data={item} onClick={()=>handleClickItem(item.id, item.minPrice)}></GoodsItemRow>
                            );
                        }}
                        refresher-enabled={true}
                        bindrefresherrefresh={(e)=>{
                            console.log(e)
                            setTriggered(true)
                            tourGroupList.refresh().finally(
                                ()=> setTriggered(false)
                            )
                        }}
                        refresher-triggered={triggered}
                        enhanced={true}
                        paging-enabled={true}
                        lower-threshold={480}
                        onScrollToLower={e=>{
                            
                            if(!isGetMore && pagination.current<=pagination.pages){
                                isGetMore = true
                                const lastId = list[list.length-1].id
                                const pageIndex = pagination.current+1
                                tourGroupList.query({lastId, pageIndex ,pageSize:20}).finally(()=>{
                                    isGetMore = false
                                })
                            }
                        }}
                    />
                ): 
                loading? (

                    <Row className={styles.loading} justify="center" align="middle">
                        <Col><Loading type="wave"></Loading></Col>
                    </Row>
                ) : (
                    <View className={styles.empty} onClick={refresh}>
                        <Image className={styles.image} mode="aspectFit" src="/images/empty-box.png"></Image>
                        <Text className={styles.tip}>暂无数据，点击重试</Text>
                    </View>
                )
            }
            
        </View>
    )

}
