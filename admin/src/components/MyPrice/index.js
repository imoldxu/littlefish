import {regFenToYuan, regFenToYuanOnly } from '@/utils/money'

const MyPrice = (props) => {
 
    const { amount=0, negative=false, positive=false, decimal=true, style={}, fontStyle={} } = props

    return (
        <div style={{display: "inline-block", color: "red", fontSize:"14px", ...style}}>
            <span>{negative?'-￥': positive? '+￥': '￥'}</span><span style={{...fontStyle}}>{decimal? regFenToYuan(amount): regFenToYuanOnly(amount)}</span>
        </div>
  )
}

export default MyPrice;