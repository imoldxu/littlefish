import { getUserDetail } from "@/services/user"
import { UserLevel } from "@/utils/bankendData"
import { Descriptions, Drawer, Image, message, Spin } from "antd"
import { useEffect, useState } from "react"

const DetailDrawer = (props)=>{

    const [isLoading, setLoading] = useState(true)
    const [detail, setDetail] = useState({})
    const { visible, onClose, value } = props

    useEffect(async()=>{
        try{
            const {id} = value;
            setLoading(true)
            const detail = await getUserDetail({id})
            setDetail(detail)
            setLoading(false)
        }catch(e){
            message.error(e.message)
        }
    },[value.id])

    return (
        <Drawer
        width={640}
        placement="right"
        closable={false}
        onClose={onClose}
        visible={visible}
        >
            {isLoading? <Spin></Spin> : <>
            <Descriptions title="用户基本信息" bordered>
                <Descriptions.Item label="用户编号">{detail.id}</Descriptions.Item>
                <Descriptions.Item label="头像"><Image src={detail.headImgUrl}></Image></Descriptions.Item>
                <Descriptions.Item label="用户昵称">{detail.nick}</Descriptions.Item>
                <Descriptions.Item label="用户姓名">{detail.name}</Descriptions.Item>
                <Descriptions.Item label="用户手机号">{detail.phone}</Descriptions.Item>
                <Descriptions.Item label="用户等级">{UserLevel[detail.level].text}</Descriptions.Item>
                <Descriptions.Item label="创建时间">{detail.createTime}</Descriptions.Item>
                <Descriptions.Item label="最后登录">{detail.lastLoginTime}</Descriptions.Item>
            </Descriptions>
            <Descriptions title="渠道信息" bordered>
                <Descriptions.Item label="经销商">{detail.dealerName}</Descriptions.Item>
                <Descriptions.Item label="销售">{detail.sellerName}</Descriptions.Item>
            </Descriptions>
            <Descriptions title="用户账户" bordered>
                <Descriptions.Item label="积分">{detail.account?.points}</Descriptions.Item>
                <Descriptions.Item label="奖金">{detail.account?.bonus}</Descriptions.Item>
            </Descriptions>
            </>
            }
        </Drawer>
    )

}

export default DetailDrawer