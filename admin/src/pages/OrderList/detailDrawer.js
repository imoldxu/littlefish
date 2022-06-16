import { Descriptions, Drawer, message, Spin } from "antd"
import { useEffect, useState } from "react"
import { getOrderDetail } from '@/services/order';

const DetailDrawer = (props)=>{

    const [isLoading, setLoading] = useState(true)
    const [detail, setDetail] = useState({})
    const { visible, onClose } = props
    const { uid, orderNo, amount, createTime, invalidTime, payTime, completeTime, state, payMode,
        tradeInfo, invoiceInfo, expressInfo, pickUpInfo, deliverType, totalAmount, postage, discount,
        donation, userCouponId
        } = detail

    useEffect(async()=>{
        try{
            const {id} = props;
            setLoading(true)
            const detail = await getOrderDetail({id})
            setDetail(detail)
            setLoading(false)
        }catch(e){
            message.error(e.message)
        }
    },[props.id])

    return (
        <Drawer
        width={820}
        placement="right"
        closable={false}
        onClose={onClose}
        visible={visible}
        >
            {isLoading ? <Spin></Spin> :
            <>
            <Descriptions title="订单信息">
                <Descriptions.Item label="订单号">{orderNo}</Descriptions.Item>
                <Descriptions.Item label="订单金额">{totalAmount}</Descriptions.Item>
                <Descriptions.Item label="订单状态">{state}</Descriptions.Item> 
                <Descriptions.Item label="创建时间">{createTime}</Descriptions.Item>
                <Descriptions.Item label="失效时间">{invalidTime}</Descriptions.Item>
                <Descriptions.Item label="完成时间">{completeTime}</Descriptions.Item>
            </Descriptions>
            <Descriptions title="交易信息" column={4}>
                {tradeInfo.map(trade=>{
                    <>
                        <Descriptions.Item label="订单号"><Image src={trade.cover}></Image></Descriptions.Item>
                        <Descriptions.Item label="订单金额">{trade.name}</Descriptions.Item>
                        <Descriptions.Item label="订单状态">{trade.price}</Descriptions.Item> 
                        <Descriptions.Item label="创建时间">{trade.number}</Descriptions.Item>
                    </>
                })}
            </Descriptions>
            <Descriptions title="支付信息">
                <Descriptions.Item label="商品金额">{amount}</Descriptions.Item>
                <Descriptions.Item label="邮费">{postage}</Descriptions.Item>
                <Descriptions.Item label="折扣金额">{discount}</Descriptions.Item>
                <Descriptions.Item label="捐款金额">{donation}</Descriptions.Item>
                <Descriptions.Item label="支付方式">{payMode}</Descriptions.Item>
                <Descriptions.Item label="支付时间">{payTime}</Descriptions.Item>
            </Descriptions>
            <Descriptions title="用户信息">
                <Descriptions.Item label="用户id">{uid}</Descriptions.Item>
                <Descriptions.Item label="使用优惠券id">{userCouponId}</Descriptions.Item>
            </Descriptions>
            <Descriptions title="提货信息">
                <Descriptions.Item label="提货方式">{deliverType}</Descriptions.Item>
                {
                    deliverType == 1? <>
                        <Descriptions.Item label="快递公司">{deliverInfo?.company}</Descriptions.Item>
                        <Descriptions.Item label="快递单号">{deliverInfo?.mailNo}</Descriptions.Item>
                        <Descriptions.Item label="省份">{deliverInfo?.province}</Descriptions.Item>
                        <Descriptions.Item label="城市">{deliverInfo?.city}</Descriptions.Item>
                        <Descriptions.Item label="区县">{deliverInfo?.district}</Descriptions.Item>
                        <Descriptions.Item label="快递地址">{deliverInfo?.address}</Descriptions.Item>
                        <Descriptions.Item label="收件人">{deliverInfo?.recipient}</Descriptions.Item>
                        <Descriptions.Item label="收件人电话">{deliverInfo?.recipientPhone}</Descriptions.Item>
                        </>
                    : <>
                        <Descriptions.Item label="自提门店">{pickUpInfo?.storeName}</Descriptions.Item>
                        <Descriptions.Item label="自提地址">{pickUpInfo?.address}</Descriptions.Item>
                    </>
                }
            </Descriptions>
            <Descriptions title="发票信息">
                <Descriptions.Item label="发票类型">{invoiceInfo?.invoiceType}</Descriptions.Item>
                <Descriptions.Item label="发票抬头">{invoiceInfo?.titleName}</Descriptions.Item>
                <Descriptions.Item label="税号">{invoiceInfo?.taxNumber}</Descriptions.Item>
                <Descriptions.Item label="注册地址">{invoiceInfo?.regAddress}</Descriptions.Item>
                <Descriptions.Item label="注册电话">{invoiceInfo?.regPhone}</Descriptions.Item>
                <Descriptions.Item label="开户银行">{invoiceInfo?.bank}</Descriptions.Item>
                <Descriptions.Item label="银行账号">{invoiceInfo?.bankAccount}</Descriptions.Item>
            </Descriptions>
            </>
            }
        </Drawer>
    )

}

export default DetailDrawer