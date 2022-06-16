import { Checkbox, Col, Input, InputNumber, Radio, Row, Select, Space, Tag } from "antd";

const TripleRoomPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected=1 } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                switch(payload.selected){
                    case 1:
                        payload.text = "本产品不提供3人间，也无法安排3成人入住一间房，敬请谅解"
                        break;
                    case 2:
                        payload.text = `产品可尝试申请3人间或加床服务，如您需3成人入住一间，在预定时备注，后续是否可以安排及相关费用以员工回复为准（温馨提示：当地酒店面积小，加床可能会引起您的不便，敬请谅解）
                        当地酒店政策原因，如2位成人携带2位或2位以上儿童出游，请选择2间房
                        如你选择单人入住，部分酒店可能会为您安排入住小间单人间（单人床），敬请谅解`
                        break;
                    default:
                        payload.text = "本产品不提供3人间，也无法安排3成人入住一间房，敬请谅解"
                        break;
                }
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
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
                        <Radio value={1}>本产品不提供3人间，也无法安排3成人入住一间房，敬请谅解</Radio>
                        <Radio value={2}>
                            产品可尝试申请3人间或加床服务，如您需3成人入住一间，在预定时备注，后续是否可以安排及相关费用以员工回复为准（温馨提示：当地酒店面积小，加床可能会引起您的不便，敬请谅解）
                            当地酒店政策原因，如2位成人携带2位或2位以上儿童出游，请选择2间房
                            如你选择单人入住，部分酒店可能会为您安排入住小间单人间（单人床），敬请谅解
                        </Radio>
                    </Space>
                </Radio.Group>
            </Col>
        </Row>
    )
}

export default TripleRoomPolicy;


