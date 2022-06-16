import { Checkbox, Col, Input, InputNumber, Radio, Row, Select, Space, Tag } from "antd";

const ConfirmPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected=1, includeValue } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                switch(payload.selected){
                    case 1:
                        payload.text = "本产品支付成功后立即确认，无需等待"
                        break;
                    case 2:
                        payload.text = `支付完成后商家最晚会在${payload.includeValue}个工作小时内确认是否预定成功,超时未确认系统将自动退款，预计1-7个工作日退还到支付账户`
                        break;
                    default:
                        payload.text = "本产品支付成功后立即确认，无需等待"
                        break;
                }
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
    }

    const handleInclueChange = (includeValue) =>{
        let payload = { ...value }
        payload.includeValue = includeValue;
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
                        <Radio value={1}>本产品支付成功后立即确认，无需等待</Radio>
                        <Radio value={2}>
                            <Space>
                                <span>支付完成后商家最晚会在</span>
                                <InputNumber disabled={selected!=2} 
                                    style={{width:"120px"}}
                                    defaultValue={includeValue}
                                    min={0}
                                    max={8}
                                    onBlur={(e)=>handleInclueChange(e.target.value)}></InputNumber>
                                <span>个工作小时内确认是否预定成功,超时未确认系统将自动退款，预计1-7个工作日退还到支付账户</span>
                            </Space>
                        </Radio>
                    </Space>
                </Radio.Group>
            </Col>
        </Row>
    )
}

export default ConfirmPolicy;


