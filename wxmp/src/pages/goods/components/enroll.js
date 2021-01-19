import { Popup } from 'annar'
import React from 'react'
import { Button, ScrollView, View } from 'remax/wechat'
import TagRadioGroup from '@/components/tagRadioGroup'
import TagRadio from '@/components/tagRadio'
import ExCalendar from '@/components/exCalendar'
import moment from 'moment'

export default (props) => {

    const {selectedSku, skus, datePrice, open, onClose, handleBuy} = props

    function handleChange(value) {
        
    }

    const today = moment().format('YYYY-MM-DD')

    return (
        <Popup transparent={true}
            position="bottom"
            open={open}
            onClose={onClose}
            style={{height: "60%vh", backgroundColor:"white"}}>
            <ScrollView onScroll={e=>e.stopPropagation()}>
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
                <ExCalendar view="month" extraInfo={datePrice} showDivider={true} minDate={today}></ExCalendar>
            </ScrollView>
            <Button type="primary" onClick={handleBuy}>我要参团</Button>
        </Popup>
    )
}