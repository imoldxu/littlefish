import { Checkbox, Col, Input, InputNumber, Radio, Row, Select, Space, Tag } from "antd";

const NoticePolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected=1, lastDayValue } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                switch(payload.selected){
                    case 1:
                        payload.text = "本商品不提供出团通知书/确认单"
                        break;
                    case 2:
                        payload.text = `本商品提供出团通知书/确认单，商家最晚在出行前${payload.lastDayValue}天发送，如未收到请及时与商家联系`
                        break;
                    default:
                        payload.text = "本商品不提供出团通知书/确认单"
                        break;
                }
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
    }

    const handleInputChange = (v) =>{
        let payload = { ...value }
        payload.lastDayValue = v;
        callOnChange(payload);
    }

    const handleChange =(selected)=>{
        let payload = { ...value }
        payload.selected = selected;
        callOnChange(payload);
    }


    const handleEnable = (checked)=>{
        let payload = { ...value }
        payload.checked = checked;
        callOnChange(payload);
    }

    return (
        <Row align="middle" gutter="12" wrap={false}>
            <Col>
            <Checkbox checked={checked} onChange={e=>{
                handleEnable(e.target.checked)
            }}></Checkbox>
            </Col>
            <Col>
                <Radio.Group disabled={!checked} 
                    defaultValue={selected}
                    onChange={(e)=>handleChange(e.target.value)}>
                    <Space direction="vertical">
                        <Radio value={1}>本商品不提供出团通知书/确认单</Radio>
                        <Radio value={2}>
                            <Space>
                                <span>本商品提供出团通知书/确认单，商家最晚在出行前</span>
                                <InputNumber disabled={selected!=2} 
                                    style={{width:"120px"}}
                                    defaultValue={lastDayValue}
                                    min={0}
                                    max={7}
                                    onBlur={(e)=>handleInputChange(e.target.value)}></InputNumber>
                                <span>天发送，如未收到请及时与商家联系</span>
                            </Space>
                        </Radio>
                    </Space>
                </Radio.Group>
            </Col>
        </Row>
    )
}

export default NoticePolicy;


