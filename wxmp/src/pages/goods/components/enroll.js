import { Popup } from 'annar'
import React from 'react'
import { Button, ScrollView, View } from 'remax/wechat'
import TagRadioGroup from '@/components/tagRadioGroup'
import TagRadio from '@/components/tagRadio'
import PriceCalendar from '@/components/priceCalendar'
import moment from 'moment'

import styles from './enroll.less'

export default (props) => {

    const {selectedSku, skus, datePrice, open, onClose, handleBuy, onMonthChange} = props

    function handleChange(value) {
        
    }

    const today = moment().format('YYYY-MM-DD')

    return (
        <Popup transparent={true}
            position="bottom"
            open={open}
            onClose={onClose}
            style={{height: "70vh", backgroundColor:"white"}}>
            <ScrollView onTouchMove={
                e=>{
                    console.log(e)
                    e.stopPropagation()
                }}>
                <View>
                    <TagRadioGroup name="line" active={selectedSku} onChange={handleChange}>
                        {
                            skus.map((sku, index)=>{
                                return (
                                    <TagRadio key={index} value={sku.id}>{sku.name}</TagRadio>
                                )
                            })
                        }
                    </TagRadioGroup>
                </View>
                <PriceCalendar view="month" extraInfo={datePrice} showDivider={true} minDate={today}
                    onCurrentViewChange={(date)=>onMonthChange(date)}
                ></PriceCalendar>
            </ScrollView>
            <View className={styles.foot}>
                <Button className={styles.button} type="primary" onClick={handleBuy}>我要参团</Button>
            </View>
        </Popup>
    )
}