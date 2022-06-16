import { Checkbox, Col, Input, InputNumber, Radio, Row, Select, Space, Tag } from "antd";

/**
 * 普通话部分可以改为Select来选择
 * @param {} props 
 * @returns 
 */
const GuideService = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected, standar } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                switch (payload.selected) {
                    case 1:
                        payload.text = "当地普通话导游服务(接驳部分不含导游服务),团队出行人数不满"+payload.number+"人安排司机兼导游"
                        break;
                    case 2:
                        payload.text = "当地普通话导游服务"+payload.service;
                        break;
                    case 3:
                        payload.text = "全程普通话导游服务"
                        break;              
                    case 4:
                        payload.text = "当地普通话司机兼导游服务"
                        break;
                    case 5:
                        payload.text = "不提供导游服务，仅安排中文司机负责行程活动中接待服务（不提供景区讲解服务）"
                        break;
                    default:
                        break;
                }
                onChange(payload);
            }else{                
                onChange(payload)
            }
            
        }
    }

    const handleNumberChange =(number)=>{
        let payload = { ...value }
        payload.number = number;
        callOnChange(payload);
    }

    const handleServiceChange =(service)=>{
        let payload = { ...value }
        payload.service = service;
        callOnChange(payload);
    }

    const handleRadioChange =(selected)=>{
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
                <Space>
                    <Radio.Group disabled={!checked} 
                        defaultValue={selected}
                        onChange={(e)=>handleRadioChange(e.target.value)}>
                        <Space  direction="vertical">
                            
                            <Radio value={1}><Space><span>当地普通话导游服务(接驳部分不含导游服务),团队出行人数不满</span><InputNumber disabled={selected!=1} style={{ width: 120 }} addonAfter="人" onChange={(v)=>{handleNumberChange(v)}}></InputNumber><span>安排司机兼导游</span></Space></Radio>
                            <Radio value={2}>
                                <Space>
                                    <span>当地普通话导游服务</span>
                                    <Select disabled={selected!=2} onChange={(v)=>{handleServiceChange(v)}} style={{ width: 240 }}>
                                        <Select.Option value="(接驳部分不含导游服务)">(接驳部分不含导游服务)</Select.Option>
                                        <Select.Option value="(接驳部分含导游服务)">(接驳部分含导游服务)</Select.Option>
                                    </Select>
                                </Space>
                            </Radio>
                            <Radio value={3}>全程普通话导游服务</Radio>
                            <Radio value={4}>当地普通话司机兼导游服务</Radio>
                            <Radio value={5}>不提供导游服务，仅安排中文司机负责行程活动中接待服务（不提供景区讲解服务）</Radio>
                        
                        </Space>
                    </Radio.Group>
                </Space>
            </Col>
        </Row>
    )
}

export default GuideService;


