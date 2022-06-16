import { Card, message, Button } from 'antd';
import { Pie } from '@ant-design/plots';
import { useEffect, useMemo, useState } from 'react';
import { getUserRatio } from '@/services/user';

const UserChannelRatio = (props)=>{

    const [data, setData] = useState([])

    useEffect(async()=>{
        await refresh()
    },[])

    const refresh = async()=>{
        try{
            const result = await getUserRatio()
            setData(result)
        }catch(e){
            message.error(e.message)
        }
    }

    const config = {
        appendPadding: 10,
        data,
        style: {height: 200},
        angleField: 'userCount',
        colorField: 'dealerName',
        radius: 1,
        innerRadius: 0.6,
        label: {
          type: 'inner',
          offset: '-50%',
          content: '{value}',
          style: {
            textAlign: 'center',
            fontSize: 14,
          },
        },
        interactions: [
          {
            type: 'element-selected',
          },
          {
            type: 'element-active',
          },
        ],
        statistic: {
          title: false,
          content: {
            style: {
              whiteSpace: 'pre-wrap',
              overflow: 'hidden',
              textOverflow: 'ellipsis',
              fontSize: "14px",
            },
            content: '用户渠道',
          },
        },
      };
    return (
        <Card title="用户渠道" extra={<Button onClick={refresh}>刷新</Button>}>
            <Pie {...config} />
        </Card>
        
    )
}

export default UserChannelRatio;