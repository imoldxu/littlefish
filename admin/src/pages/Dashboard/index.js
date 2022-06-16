import { PageContainer } from "@ant-design/pro-layout"
import { Button, Card, Col, DatePicker, Grid, message, Row, Select, Space, Statistic } from "antd"
import Form from "antd/lib/form/Form"
import { downloadBill, statistics } from '@/services/bill';
import { DownloadOutlined, MinusOutlined, PlusOutlined } from "@ant-design/icons";
import { useRef, useState } from "react";
import FormItem from "antd/lib/form/FormItem";
import moment from 'moment';
import { regFenToYuan } from "@/utils/money";
import UserTrendLine from "./components/UserTrendLine";
import UserChannelRatio from "./components/UserChannelRatio";
import TradeTrendLine from "./components/TradeTrendLine";

const { RangePicker } = DatePicker;
const { Option } = Select;

const layout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 18 },
};

export default ()=>{

    const [ statisticsValue, setStatistic ] = useState({income:0, pay:0})
    const formRef = useRef()

    async function handleSubmit(values) {
        const hide =  message.loading("统计中")
        const { rangeTime, payMode } = values;
        let query = {}
        if(rangeTime && rangeTime.length>1){
            query.startTime = rangeTime[0].format('yyyy-MM-DD') + " 00:00:00"
            query.endTime = rangeTime[1].format('yyyy-MM-DD') + " 23:59:59"
        }
        if( payMode && payMode !=0 ){
            query.payMode = payMode
        }
        
        try{
            const result = await statistics(query)
            setStatistic(result)
        }catch(e){
            message.error(e.message, 3)
        }finally{
            hide()
        }
    }
  
    const handleReset = () => {
      const { getFieldsValue, setFieldsValue } = formRef.current
  
      const fields = getFieldsValue()
      for (let item in fields) {
        if ({}.hasOwnProperty.call(fields, item)) {
          if (fields[item] instanceof Array) {
            fields[item] = []
          } else {
            fields[item] = undefined
          }
        }
      }
      setFieldsValue(fields)
      handleSubmit(fields)
    }

    return (
        <PageContainer>
            <Space direction="vertical" style={{width:"100%"}}>
            <Row gutter={[16,16]}>
                <Col span={12}>
                    <UserTrendLine></UserTrendLine>
                </Col>
                <Col span={12}>
                    <UserChannelRatio></UserChannelRatio>
                </Col>
                <Col span={12}>
                    <TradeTrendLine></TradeTrendLine>
                </Col>
            </Row>
            
            </Space>
            
        </PageContainer>
    )
}