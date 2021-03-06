import React, { Component } from 'react'
import { ScrollView, View, Button, Image, Text, RichText, Swiper, SwiperItem } from '@tarojs/components'
import Taro from '@tarojs/taro'
import { AtTag, AtTabBar, AtDivider, AtList, AtListItem, AtCard, AtTimeline } from 'taro-ui'
import { connect } from 'react-redux'
import ExCalendar from '../../components/excalendar'

import styles from './goods.scss'

@connect(({ goodsDetail }) => ({ goodsDetail }))
class Goods extends Component {

    tabRef = React.createRef()

    componentWillMount() { 
        const { dispatch } = this.props
        console.log(Taro.getCurrentInstance().router.params)
        const { goodsId } = Taro.getCurrentInstance().router.params
        dispatch({ type: 'goodsDetail/get', payload: { id: goodsId } })
    }

    componentDidMount() {
        //const rect = this.tabRef.current.boundingClientRect()
        //console.log(rect.top)

        const query = Taro.createSelectorQuery().in(this)
        query.select('#detailTab').boundingClientRect()
        query.selectViewport().scrollOffset()
        query.exec(function(res){
            console.log(res[0].top)       // #the-id节点的上边界坐标
            console.log(res[1].scrollTop) // 显示区域的竖直滚动位置
        })
    }

    componentWillUnmount() { }

    componentDidShow() { }

    componentDidHide() { }

    handleTabBarClick = (current, e) => {
        const { dispatch } = this.props

        switch (current) {
            case 0:
                Taro.pageScrollTo({ selector: '#introduce', duration: '100' })
                break;
            case 1:
                Taro.pageScrollTo({ selector: '#schedule', duration: '100' })
                break;
            case 2:
                Taro.pageScrollTo({ selector: '#costinfo', duration: '100' })
                break;
            case 3:
                Taro.pageScrollTo({ selector: '#notice', duration: '100' })
                break;
            default:
                break;
        }

        dispatch({ type: 'goodsDetail/save', payload: { currentTab: current } })
    }

    handleClickBuy= ()=>{
        const { goodsDetail } = this.props
        //TODO，取goodsid
        const { id } = goodsDetail
        Taro.navigateTo({url: '/pages/enroll/enroll?goodsid='+id})
    }

    render() {
        const { goodsDetail } = this.props

        const { imageList, title, points, price, detailImage, currentTab, schedule } = goodsDetail

        return (
            <View className={styles.content}>
                <ScrollView>
                    <Swiper indicatorColor='#999'
                        indicatorActiveColor='#333'
                        vertical={false}
                        circular
                        indicatorDots
                        duration="10"
                        autoplay>
                        {
                            imageList.map(image=>{
                                return (
                                <SwiperItem>
                                    <Image mode="scaleToFill"
                                    src={image}></Image>
                                </SwiperItem>
                                )
                            })
                        }
                    </Swiper>
                    <View className={styles.card}>
                    <View className="at-col--wrap"><Text>{title}</Text></View>
                        <View>
                            {
                                points.map(point=>{
                                    return (
                                        <AtTag name='tag-1' type='primary' circle>{point}</AtTag>
                                    )
                                })
                            }
                        </View>
                        <View><Text className={styles.price}>￥{price}</Text></View>
                    </View>
                    <View className={styles.card}>
                        <AtList>
                            <AtListItem title={'${departure}+参团'} arrow='right' />
                        </AtList>
                        <View><ExCalendar view="week" hideController={true} hideHeader={true}
                            startDay={new Date().getDay()}
                            extraInfo={[
                                { value: '2020-10-12', text: '￥1200', color: 'red' },
                                { value: '2020-10-13', text: '￥1200', color: 'red' },
                                { value: '2020-10-14', text: '￥1300', color: 'red' }
                            ]}
                            onDayClick={item => this.handleClickBuy}
                            ></ExCalendar></View>
                    </View>

                    <View className={`${styles.card} ${styles.last}`}>
                        <AtTabBar ref={this.tabRef} id="detailTab" tabList={[
                            { title: '图文介绍' },
                            { title: '行程路线' },
                            { title: '费用说明' },
                            { title: '行程须知' }
                        ]}
                            onClick={this.handleTabBarClick}
                            current={currentTab}
                        >
                        </AtTabBar>
                        <View id="introduce">
                            <AtDivider content="图文介绍"></AtDivider>
                            <Image src={detailImage}
                                    mode="widthFix"
                                    className={styles.twimage}
                                >
                            </Image>
                        </View>
                        <View id="schedule">
                            <AtDivider content="行程安排"></AtDivider>
                            {
                                schedule.map(daySchedule=>{
                                    const {num, todoList } = daySchedule

                                    return (
                                    <View className="at-row">
                                        <View className="at-col at-col-2"><Text>Day{num}</Text></View>
                                        <View className="at-col at-col-10">
                                            <AtTimeline 
                                            pending 
                                            items={[
                                                { title: '刷牙洗脸', content: ['大概8:00'], icon: 'check-circle' }, 
                                                { title: '吃早餐', content: ['牛奶+面包', '餐后记得吃药'], icon: 'clock' }, 
                                                { title: '上班', content: ['查看邮件', '写PPT', '发送PPT给领导'], icon: 'clock' }, 
                                                { title: '睡觉', content: ['不超过23:00'], icon: 'clock' }
                                            ]}
                                            >
                                            </AtTimeline>
                                        </View>
                                    </View>
                                    )
                                })
                            }
                        </View>
                        <View id="costinfo">
                           <AtDivider content="费用说明"></AtDivider>
                           <View><Text className={styles.subTitle}>费用包含</Text></View>
                           <View className="at-row">
                               <View className="at-col at-col-2"><Text>大交通</Text></View>
                               <View className="at-col at-col-10 at-col--wrap"><RichText nodes={"<div>1、飞机往返经济舱</div><div>2、燃油附加费</div>"}>
                                   
                                   </RichText></View>
                           </View>
                           <View className="at-row">
                               <View className="at-col at-col-2"><Text>当地交通</Text></View>
                               <View className="at-col at-col-10 at-col--wrap"><RichText nodes={"<div>1、飞机往返经济舱</div><div>2、燃油附加费</div>"}></RichText></View>
                           </View>
                           <View className="at-row">
                               <View className="at-col at-col-2"><Text>住宿</Text></View>
                               <View className="at-col at-col-10 at-col--wrap"><RichText nodes={"<div>1、飞机往返经济舱</div><div>2、燃油附加费</div>"}></RichText></View>
                           </View>
                           <View><Text className={styles.subTitle}>费用不含</Text></View>
                           <View className="at-row">
                               <View className="at-col at-col-2"><Text>说明</Text></View>
                               <View className="at-col at-col-10 at-col--wrap"><RichText nodes={"<div>1、飞机往返经济舱</div><div>2、燃油附加费</div>"}></RichText></View>
                           </View>
                           <View className="at-row">
                               <View className="at-col at-col-2"><Text>景点门票</Text></View>
                               <View className="at-col at-col-10 at-col--wrap"><Text>1、费用不含：丽江古城维护费、酒店担任房差、旅游意外伤害保险、景区电瓶车。
                                   </Text></View>
                           </View>
                           <View><Text className={styles.subTitle}>儿童政策</Text></View>
                           <View className="at-row">
                               <View className="at-col at-col-2"><Text>说明</Text></View>
                               <View className="at-col at-col-10 at-col--wrap"><Text>1、飞机往返经济舱
                                   2、燃油附加费
                                   </Text></View>
                           </View>
                           <View><Text className={styles.subTitle}>自费项目</Text></View>
                           <View className="at-row">
                               <View className="at-col at-col-2"><Text>说明</Text></View>
                               <View className="at-col at-col-10 at-col--wrap"><Text>1、飞机往返经济舱
                                   2、燃油附加费
                                   </Text></View>
                           </View>
                           <View className="at-row">
                               <View className="at-col at-col-2"><Text>景点门票</Text></View>
                               <View className="at-col at-col-10 at-col--wrap"><Text>1、费用不含：丽江古城维护费、酒店担任房差、旅游意外伤害保险、景区电瓶车。
                                   </Text></View>
                           </View>
                        </View>
                        <View id="notice">
                            <AtDivider content="购买须知"></AtDivider>
                            <View><Text className={styles.subTitle}>服务信息</Text></View>
                            <View><Text>本商品支付成功后立即确认，无需等待</Text></View>
                            <View><Text className={styles.subTitle}>取消政策</Text></View>
                            <View><Text>按规则退</Text></View>
                            <View><Text>旅游者违约退改规则</Text></View>
                            <View><Text>取消时间</Text></View>
                            <View className="at-row">
                               <View className="at-col at-col-8"><Text>取消时间</Text></View>
                               <View className="at-col at-col-4"><Text>损失费用</Text></View>
                            </View>
                            <View className="at-row">
                               <View className="at-col at-col-8"><Text>使用日期前7天17:00之前</Text></View>
                               <View className="at-col at-col-4"><Text>30%</Text></View>
                            </View>
                            <View className="at-row">
                               <View className="at-col at-col-8"><Text>使用日期前4天17:00之前</Text></View>
                               <View className="at-col at-col-4"><Text>50%</Text></View>
                            </View>
                            <View className="at-row">
                               <View className="at-col at-col-8"><Text>使用日期前2天17:00之前</Text></View>
                               <View className="at-col at-col-4"><Text>80%</Text></View>
                            </View>
                            <View className="at-row">
                               <View className="at-col at-col-8"><Text>使用日期前2天17:00之后</Text></View>
                               <View className="at-col at-col-4"><Text>100%</Text></View>
                            </View>
                        </View>
                    </View>
                </ScrollView>
                <View className={styles.foot}>
                    <View className="at-row">
                        <View className="at-col at-col-2"><Button >收藏</Button></View>
                        <View className="at-col at-col-4"><Button openType='contact' type='default'>联系客服</Button></View>
                        <View className="at-col at-col-6"><Button size='default' type='primary' onClick={this.handleClickBuy}>立即购买</Button></View>
                    </View>
                </View>
            </View>
        )
    }

}

export default Goods