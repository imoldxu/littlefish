import { Checkbox, Col, Input, Radio, Row, Select, Space, Tag } from "antd";

const WithGroupTrafficService = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, feeValue } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = "随团浏览期间用车费用"+payload.feeValue;
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
    }

    const handleInputChange = (v)=>{
        let payload = { ...value }
        payload.feeValue = v;
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
                    <span>随团浏览期间用车费用</span>
                    <Input disabled={!checked} 
                        style={{width:"120px"}}
                        placeholder="输入数字"
                        defaultValue={feeValue}
                        onBlur={(e)=>handleInputChange(e.target.value)}></Input>
                </Space>
                
            </Col>
        </Row>
    )
}

export default WithGroupTrafficService;


