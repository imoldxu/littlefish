import { getTradeTrend } from '@/services/order';
import { timeDimensionOption } from '@/utils/bankendData';
import { regFenToYuan } from '@/utils/money';
import { Line } from '@ant-design/plots';
import { Card, message, Radio } from 'antd';
import moment from 'moment';
import { useEffect, useMemo, useState } from 'react';



const TradeTrendLine = (props)=>{

    const [data, setData] = useState([])

    useEffect(async()=>{
        await refresh(2)
    },[])

    const refresh = async(type)=>{
        let number = 12;
        switch (type) {
            case 2:
                number = 12
                break;
            case 3:
                number = 7
                break;
            case 4:
                number = 12
                break;
            default:
                number = 3;
                break;
        }
        try{
            const result = await getTradeTrend({timeDimension: type,
            lastTime: moment().format('yyyy-MM-DD HH:mm:ss'),
            intervalNum: number})
            setData(result)
        }catch(e){
            message.error(e.message)
        }
    }

    const config = {
        data,
        padding: "auto",
        style: {height: 200},
        xField: "time",
        yField: "amountSum",
        // seriesField: "dealerName",
        xAxis: {
           //type: "time",
        },
        yAxis: {
            //nice: true,
            // tickCount: 8,
            // 文本标签
            label: {
            //     // autoRotate: false,
            //     rotate: Math.PI / 6,
            //     offset: 10,
            //     style: {
            //         fill: '#aaa',
            //         fontSize: 12,
            //     },
                formatter: (v) => regFenToYuan(v),
            },
            // title: {
            //     text: '年份',
            //     style: {
            //     fontSize: 16,
            //     },
            // },
            // 坐标轴线的配置项 null 表示不展示
            // line: {
            //     style: {
            //     stroke: '#aaa',
            //     },
            // },
            // tickLine: {
            //     style: {
            //     lineWidth: 2,
            //     stroke: '#aaa',
            //     },
            //     length: 5,
            // },
            // grid: {
            //     line: {
            //     style: {
            //         stroke: '#ddd',
            //         lineDash: [4, 2],
            //     },
            //     },
            //     alternateColor: 'rgba(0,0,0,0.05)',
            // },
        },
        label: {
            formatter: (item) => regFenToYuan(item.amountSum),
        },
        smooth: true,
        // 动画方式
        // animation: {
        //     appear: {
        //         animation: 'path-in',
        //         duration: 5000,
        //     },
        // },
        
    }
 
    return (
        <Card title="交易趋势" extra={<Radio.Group options={timeDimensionOption} 
            onChange={(e)=>refresh(e.target.value)}
            defaultValue={2}
            optionType="button"
            />}>
            <Line {...config}>
            </Line>
        </Card>
    )
}

export default TradeTrendLine;