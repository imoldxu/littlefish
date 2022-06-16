import { Checkbox, Col, Input, Radio, Row, Select, Space, Tag } from "antd";

/**
 * 孕妇限制策略
 * @param {} props 
 * @returns 
 */
const OtherLimitPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, contentValue } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = payload.contentValue
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
    }

    const handleInclueChange = (v) =>{
        let payload = { ...value }
        payload.contentValue = v;
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
                    <Input.TextArea disabled={!checked} 
                        style={{minWidth: "400px"}}
                        size="large"
                        defaultValue={contentValue}
                        onBlur={(e)=>handleInclueChange(e.target.value)}></Input.TextArea>
                </Space>
            </Col>
        </Row>
    )
}

export default OtherLimitPolicy;


