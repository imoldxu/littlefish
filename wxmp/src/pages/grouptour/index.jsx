import { Col, Loading, Row } from 'annar';
import React, { useState } from 'react'
import RecycleView from 'remax-recycle-view/lib/src/index'; // 重要，由于remax的跨平台同构特性，需要这样引入
import { usePageEvent } from 'remax/macro';
import { Image, navigateTo, showToast, Text, View } from "remax/wechat";
import GoodsItemRow from '../../components/goodsItemRow';
import useTourGroupList from '../../hooks/groupTourList';
import styles from './index.less'

// const mockData = [];
// for (let i = 0; i < 50; i++) {
//   mockData.push({
//     id: i,
//     imageUrl: "http://p1-q.mafengwo.net/s17/M00/6D/42/CoUBXl-T1rGAdikJAAFvuTI95kY60.jpeg?imageMogr2%2Fthumbnail%2F%21400x300r%2Fgravity%2FCenter%2Fcrop%2F%21400x300%2Fquality%2F100",
//     name: `【5大优惠·5星希尔顿酒店】云南大理丽江6天游（活动可叠加+杨丽萍大剧院+私人游艇+大型民族歌舞表演+3大5A景点+3大特色餐+玉龙雪山）`,
//     price: '￥1800~2990'
//   });
// }

export default () => {

    const [ loading, setLoading ] = useState(false)
    const { state, query } = useTourGroupList()
    const { list } = state

    function refresh(){
        setLoading(true)
        query().catch(e=>{
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
        <View className={styles.page}>
            {
                list.length>0? (
                    <RecycleView
                        className={styles.recycleView}
                        placeholderImage="https://gw.alicdn.com/tfs/TB18fUJCxD1gK0jSZFyXXciOVXa-750-656.png"
                        data={list}
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
