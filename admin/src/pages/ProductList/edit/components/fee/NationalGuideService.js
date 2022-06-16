import { Checkbox, Col, Input, InputNumber, Radio, Row, Select, Space, Tag } from "antd";

const NationalGuideService = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            payload.text = "提供出发集合地址起止的全程陪同服务"         
            onChange(payload);
        }else{                
            onChange(payload)
        }
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
                <span>提供出发集合地址起止的全程陪同服务</span>
            </Col>
        </Row>
    )
}

export default NationalGuideService;


