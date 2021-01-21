import { Row, Tag, Col, Loading, Tabs, Grid, Cell } from 'annar'
import React, { useMemo, useState } from 'react'
import { useQuery } from 'remax'
import { usePageEvent } from 'remax/macro'
import { ScrollView, View, Button, Image, Text, RichText, Swiper, SwiperItem, pageScrollTo, navigateTo, showShareMenu } from 'remax/wechat'
import Timeline from '../../components/timeLine'
import useDatePrice from '../../hooks/datePrice'
import useGroupTour from '../../hooks/groupTour'
import EnrollPopup from './components/enroll'
import { getMonthFirstDay, getMonthLastDay } from '../../utils/datetime'
import MpSticky from '@miniprogram-component-plus/sticky/miniprogram_dist/index'
import '@miniprogram-component-plus/sticky/miniprogram_dist/index.wxss'

import styles from './index.less'

const { TabContent } = Tabs;

const tabs = [
    { key: 0, title: '图文介绍' },
    { key: 1, title: '行程路线' },
    { key: 2, title: '费用说明' },
    { key: 3, title: '行程须知' }
]

const cancelPolicy = [
    { text: "取消时间", type: "header" },
    { text: "损失费用", type: "header" },
    { text: "使用日期前7天17:00之前", type: "key" },
    { text: "30%", type: "value" },
    { text: "使用日期前4天17:00之前", type: "key" },
    { text: "50%", type: "value" },
    { text: "使用日期前2天17:00之前", type: "key" },
    { text: "80%", type: "value" },
    { text: "使用日期前2天17:00之后", type: "key" },
    { text: "100%", type: "value" }
]

export default () => {

    const query = useQuery();
    const { state, getDetail } = useGroupTour();
    const datePrice = useDatePrice()
    const [loading, setLoading] = useState(true)
    const [currentTab, setCurrentTab] = useState("0");
    const [skuIndex, setSkuIndex] = useState();
    const [skuOpen, setSkuOpen] = useState(false);

    const goodsId = query["goodsId"]

    //初始化详情信息
    usePageEvent('onLoad', () => {
        refresh()
        showShareMenu({
            withShareTicket: true,
            menus: ['shareAppMessage', 'shareTimeline']
        })
    });

    //分享内容
    usePageEvent("onShareAppMessage", object=>{
        const {from, target, webViewUrl} = object
        return {title:"有没有兴趣一起", path: `/pages/goods/index?goodsId=${goodsId}` }
    })

    //根据传入的id获取商品详情
    function refresh() {
        setLoading(true)  
        getDetail({ id: goodsId }).finally(() => {
            setLoading(false)
        })
    }

    //处理sku切换带来的变化
    function handleSkuChange(current, e) {
        const index = parseInt(current)
        setSkuIndex(index)
        const skuId = state.skus[index].id
        const now = new Date()
        datePrice.query({ skuId: skuId, startDate: getMonthFirstDay(now), endDate: getMonthLastDay(now) })
    }

    //滚动到锚点
    function handleTabBarClick(current, e) {
        switch (current) {
            case "0":
                pageScrollTo({ selector: '#introduce', duration: '100' })
                break;
            case "1":
                pageScrollTo({ selector: '#schedule', duration: '100' })
                break;
            case "2":
                pageScrollTo({ selector: '#costinfo', duration: '100' })
                break;
            case "3":
                pageScrollTo({ selector: '#notice', duration: '100' })
                break;
            default:
                break;
        }
        setCurrentTab(current)
    }

    //绘制取消政策
    function renderCancelPolicyItem(data, index) {
        const { text, type } = data
        return (
            <View className={styles[`cancelPolicy_${type}`]} key={index}>
                <Text>{text}</Text>
            </View>
        )
    }

    //绘制信息展示，若值不为空则绘制
    function renderInfoRow(data) {
        const { key, name, value } = data
        if (value) {
            return (
                <Row key={key} align="middle" className={styles.info_row}>
                    <Col span={6}><Text>{name}</Text></Col>
                    <Col className={styles.info_value} span={18}><RichText nodes={value}></RichText></Col>
                </Row>
            )
        } else {
            return (<></>)
        }
    }

    function handleBuy() {

    }

    //处理日期切换
    function monthChange(now) {
        const skuId = state.skus[skuIndex].id
        datePrice.query({ skuId: skuId, startDate: getMonthFirstDay(now), endDate: getMonthLastDay(now) })
    }

    //处理购买
    function handleClickBuy() {
        setSkuOpen(true)
    }

    //获取当前选中的sku，默认选中第一个sku
    let selectedSku = {}
    let skuKeys = []
    if (state.skus && state.skus.length > 0) {
        if (skuIndex != undefined) {
            selectedSku = state.skus[skuIndex]
        } else {
            //初始化
            selectedSku = state.skus[0]
            setSkuIndex(0)
            const skuId = state.skus[0].id
            const now = new Date()
            datePrice.query({ skuId: skuId, startDate: getMonthFirstDay(now), endDate: getMonthLastDay(now) })
        }

        skuKeys = state.skus.map((sku, index) => {
            return {
                key: index,
                title: sku.name,
            }
        })
    }

    return (<View className="x-page">
        {
            state.id ? (
                <View>
                    <ScrollView>
                        <Swiper indicatorColor='#999'
                            indicatorActiveColor='#333'
                            vertical={false}
                            circular
                            indicatorDots
                            duration="10"
                            autoplay>
                            {
                                state.imageUrls.map((image, index) => {
                                    return (
                                        <SwiperItem>
                                            <Image mode="scaleToFill" className={styles.banner}
                                                src={image} key={index}></Image>
                                        </SwiperItem>
                                    )
                                })
                            }
                        </Swiper>
                        <View className="x-card">
                            <View className={styles.title}><Text>{state.title}</Text></View>
                            <View>
                                {
                                    state.tags.map((tag, index) => {
                                        return (
                                            <Tag key={index}>{tag}</Tag>
                                        )
                                    })
                                }
                            </View>
                            <View><Text className={styles.price}>￥{query['minPrice'] / 100}起</Text></View>
                        </View>


                        <View className={styles.card}>
                            <Cell label="出发地">
                                {state.departPlace}
                            </Cell>
                            <View>
                                {/* <ExCalendar view="week" hideController={true} hideHeader={true}
                                startDay={new Date().getDay()}
                                extraInfo={[
                                    { value: '2020-10-12', text: '￥1200', color: 'red' },
                                    { value: '2020-10-13', text: '￥1200', color: 'red' },
                                    { value: '2020-10-14', text: '￥1300', color: 'red' }
                                ]}
                                onDayClick={item => handleClickBuy()}
                                ></ExCalendar> */}
                            </View>
                        </View>

                        <View className={styles.card}>
                            <MpSticky offset-top={0}>
                                <View style={{ backgroundColor: "white", width: "100vw" }}>
                                    <Tabs className={styles.skuTab} onTabClick={({key}) => handleSkuChange(key)}
                                        activeKey={`${skuIndex}`} type="plain"
                                        animated>
                                        {
                                            skuKeys.map(tab => {
                                                return (
                                                    <TabContent key={tab.key} tab={tab.title}>
                                                    </TabContent>
                                                )
                                            })
                                        }
                                    </Tabs>

                                    <Tabs className={styles.tabs_header} type="card" onTabClick={({ key }) => handleTabBarClick(key)} activeKey={currentTab}
                                        headerStyle={{ border: "red" }} animated
                                    >
                                        {
                                            tabs.map(tab => {
                                                return (
                                                    <TabContent key={tab.key} tab={tab.title}>
                                                    </TabContent>
                                                )
                                            })
                                        }
                                    </Tabs>
                                </View>
                            </MpSticky>
                            <View>
                                <View id="introduce" className={styles.dark_anchor} />
                                <Text className={styles.panel_title}>图文介绍</Text>

                                {
                                    selectedSku.introduceImageUrls.map((detailImage, index) => {
                                        return (
                                            <Image key={index} src={detailImage}
                                                mode="widthFix"
                                                className={styles.twimage}
                                                style={{ width: "100%" }}>
                                            </Image>)
                                    })
                                }

                            </View>
                            <View>
                                <View id="schedule" className={styles.dark_anchor} />
                                <Text className={styles.panel_title}>行程安排</Text>
                                {
                                    selectedSku.scheduleList.map((daySchedule, index) => {
                                        const { num, places, todoList } = daySchedule
                                        
                                            return (
                                                <Row key={index}>
                                                    <Col span={4}><Text>Day{num}</Text></Col>
                                                    <Col span={20}>
                                                        <Timeline lastday={index == (selectedSku.scheduleList.length - 1)} places={places} items={todoList}></Timeline>
                                                    </Col>
                                                </Row>
                                            )
                                         
                                    })
                                }
                            </View>
                            <View>
                                <View id="costinfo" className={styles.dark_anchor} />
                                <View className={styles.panel_title}><Text>费用说明</Text></View>
                                <View><Text className={styles.subTitle}>费用包含</Text></View>
                                {
                                    renderInfoRow({ key: 'bigTrafficFeeDes', name: '大交通', value: selectedSku.bigTrafficFeeDes })
                                }
                                {
                                    renderInfoRow({ key: 'localTrafficFeeDes', name: '当地交通', value: selectedSku.localTrafficFeeDes })
                                }
                                {
                                    renderInfoRow({ key: 'hotelFeeDes', name: '住宿', value: selectedSku.hotelFeeDes })
                                }
                                {
                                    renderInfoRow({ key: 'foodFeeDes', name: '餐食', value: selectedSku.foodFeeDes })
                                }
                                {
                                    renderInfoRow({ key: 'ticketFeeDes', name: '景点门票', value: selectedSku.ticketFeeDes })
                                }
                                {
                                    renderInfoRow({ key: 'guideFeeDes', name: '导游服务', value: selectedSku.guideFeeDes })
                                }
                                {
                                    renderInfoRow({ key: 'otherFeeDes', name: '其他', value: selectedSku.otherFeeDes })
                                }
                                <View><Text className={styles.subTitle}>费用不含</Text></View>
                                {
                                    renderInfoRow({ key: 'withoutFeeDes', name: '说明', value: selectedSku.withoutFeeDes })
                                }
                                {
                                    renderInfoRow({ key: 'otherWithoutFeeDes', name: '其他', value: selectedSku.otherWithoutFeeDes })
                                }
                                <View><Text className={styles.subTitle}>儿童政策</Text></View>
                                {
                                    renderInfoRow({ key: 'childrenPolicy', name: '说明', value: selectedSku.childrenPolicy })
                                }
                                <View><Text className={styles.subTitle}>自费项目</Text></View>
                                {
                                    renderInfoRow({ key: 'ownfee', name: "说明", value: selectedSku.ownfee })
                                }
                            </View>
                            <View>
                                <View id="notice" className={styles.dark_anchor} />
                                <Text className={styles.panel_title}>购买须知</Text>
                                <View><Text className={styles.subTitle}>服务信息</Text></View>
                                <View><Text>{selectedSku.serviceInfo}</Text></View>
                                <View><Text className={styles.subTitle}>取消政策</Text></View>
                                <View><Text>旅游者违约退改规则</Text></View>
                                <Grid data={cancelPolicy} columns={2} span={[16, 8, 16, 8, 16, 8, 16, 8, 16, 8]}>
                                    {renderCancelPolicyItem}
                                </Grid>
                                <View><Text>{selectedSku.cancelPolicy}</Text></View>
                            </View>
                        </View>
                        {/* 避免被底部按钮遮挡 */}
                        <View style={{ height: "100" }}></View>
                    </ScrollView>
                    <View className={styles.foot}>
                        <Row>
                            {/* <Col span={6}><Button className={styles.button}>收藏</Button></Col> */}
                            <Col span={12}><Button className={styles.button} openType='contact' type='default'>联系客服</Button></Col>
                            <Col span={12}><Button className={styles.button} type='primary' onClick={handleClickBuy}>立即购买</Button></Col>
                        </Row>
                    </View>
                    <EnrollPopup
                        open={skuOpen}
                        skus={state.skus}
                        datePrice={datePrice.list}
                        onClose={() => setSkuOpen(false)}
                        selectedSku={selectedSku}
                        handleClickBuy={handleBuy}
                        onMonthChange={monthChange}
                    ></EnrollPopup>
                </View>
            ) : loading ? (
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