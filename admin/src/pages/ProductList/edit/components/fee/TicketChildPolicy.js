import { Checkbox, Col, Input, Radio, Row, Select, Space, Tag } from "antd";

const TicketChildPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected=1, includeValue } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                switch(payload.selected){
                    case 1:
                        payload.text = "行程中所列景点首道门票"
                        break;
                    case 2:
                        payload.text = `含${payload.includeValue}景点首道大门票，其余门票因景区儿童标准不一样，需您自行到景区购买或由服务人员代为购买`
                        break;
                    case 3:
                        payload.text = "因景区儿童标准不一样，需您自行到景区购买或由服务人员代为购买"
                        break;
                    default:
                        payload.text = "行程中所列景点首道门票"
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
                <Space>
                    <Tag color="green">儿童</Tag>
                
                <Radio.Group disabled={!checked} 
                    defaultValue={selected}
                    onChange={(e)=>handleChange(e.target.value)}>
                    <Space direction="vertical">
                        <Radio value={1}>行程中所列景点首道门票</Radio>
                        <Radio value={2}>
                            <Space>
                                <span>含</span>
                                <Input.TextArea disabled={selected!=2} 
                                    style={{width:"120px"}}
                                    defaultValue={includeValue}
                                    onBlur={(e)=>handleInclueChange(e.target.value)}></Input.TextArea>
                                <span>景点首道大门票，其余门票因景区儿童标准不一样，需您自行到景区购买或由服务人员代为购买</span>
                            </Space>
                        </Radio>
                        <Radio value={3}>因景区儿童标准不一样，需您自行到景区购买或由服务人员代为购买</Radio>
                    </Space>
                </Radio.Group>
                </Space>
            </Col>
        </Row>
    )
}

export default TicketChildPolicy;


