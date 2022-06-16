import MyPrice from "@/components/MyPrice";
import { queryPriceCalendar, setPriceCalendar } from "@/services/priceCalendar";
import { PageContainer } from "@ant-design/pro-layout";
import { Button, Calendar, Card, message, Space } from "antd";
import moment, { now } from "moment";
import { useEffect, useRef, useState } from "react";
import { history, Link } from "umi";
import DatePriceModal from "./DatePriceModal";

function PriceCalendar(props){

    const [priceModalVisible, setPriceModalVisible] = useState(false)
    const [ dataSource, setDataSource ] = useState([])
    const { query } = history.location;
    const { product_id, sku_id, title, name } = query;
    const currentMoment = useRef(moment())

    useEffect(async ()=>{
        try{
            await refreshPriceCalendar(currentMoment.current);
        }catch(e){
            message.error(e.message)
        }
    },[sku_id])

    async function refreshPriceCalendar(now) {
        const start = now.format('YYYY-MM') + '-01';
        const end = moment(start).add(1, 'month').add(-1, 'days').format('YYYY-MM-DD');
    
        const resp = await queryPriceCalendar({ skuId: sku_id, startDate: start, endDate: end });
        setDataSource(resp);
    }

    function getDailyPrice(date){
        const matched = dataSource.filter(v => v.date == date)
        if(matched.length>0){
            return matched[0]
        }else{
            return null
        }
    }

    function dateCellRender(value) {
        const date = value.format('YYYY-MM-DD')
        const datePrice = getDailyPrice(date);
        return (
        //   <Space size="small" direction="vertical" style={{fontSize:"8px"}}>
        datePrice &&
              <div style={{fontSize:"8px"}}>
                <div><span>成人价：</span><MyPrice amount={datePrice.adultPrice} /></div>
                <div><span>儿童价：</span><MyPrice amount={datePrice.childPrice} /></div>
                <div><span>单房差：</span><MyPrice amount={datePrice.singleRoomPrice} /></div>
                {/* <div>{`库存: 3`}</div> */}
              </div>
        //   </Space>
        );
    }

    function handlePanelChange(date, mode){
        currentMoment.current = date
        refreshPriceCalendar(date)
    }

    function handleDateSelect(date){

    }

    return (
        <PageContainer
            header={{
                breadcrumb: {
                    itemRender: (route, params, routes, paths)=>{
                        const last = routes.indexOf(route) === routes.length - 1;
                        console.log(params)
                        return last ? (
                          <span>{route.breadcrumbName}</span>
                        ) : 
                        route.path == 'sku' ?
                        (<Link to={'/'+paths.join('/')+"?product_id="+params.product_id}>{route.breadcrumbName}</Link>)
                        :
                        (<Link to={'/'+paths.join('/')}>{route.breadcrumbName}</Link>)
                    },
                    params: {
                        product_id: product_id,
                    },
                    routes: [
                    {
                        path: 'product',
                        breadcrumbName: '产品管理',
                    },
                    {
                        path: 'sku',
                        breadcrumbName: '套餐',
                    },
                    {
                        path: 'price-calendar',
                        breadcrumbName: '价格日历',
                    },
                    ]
                }
            }}
            >
            <Card
                title={(<Space><div style={{maxWidth: "300px" ,overflow: "hidden",
                    textOverflow:"ellipsis",
                    whiteSpace: "nowrap"}}>{title}</div><div>{name}</div></Space>)} 
                extra={
                <>
                    <Button type="primary" onClick={()=>{
                        setPriceModalVisible(true)
                    }}>设置价格</Button>
                </>
            }>
                <Calendar 
                    dateCellRender={dateCellRender}
                    onPanelChange={handlePanelChange}
                    onSelect={handleDateSelect}
                />
            </Card>
            <DatePriceModal 
            visible={priceModalVisible}
            commit={async (payload)=>{
                payload.skuId = sku_id
                await setPriceCalendar(payload)
                message.success("设置成功")
                refreshPriceCalendar(currentMoment.current)
            }}
            closeModal={()=>setPriceModalVisible(false)}></DatePriceModal>
        </PageContainer>
    )
}

export default PriceCalendar;


