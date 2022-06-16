import { Checkbox, Col, Input, Radio, Row, Select, Space, Tag } from "antd";

/**
 * 最小成团政策
 * @param {} props 
 * @returns 
 */
const MinimumMemberPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, leastValue } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = "本产品最少成团人数"+payload.leastValue+"人，若因旅行社原因未发团，旅行社将按双方合同约定的违约条款予以赔付";
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
    }

    const handleInputChange = (v)=>{
        let payload = { ...value }
        payload.leastValue = v;
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
                    <span>产品最少成团人数</span>
                    <Input disabled={!checked} 
                        style={{width:"120px"}}
                        defaultValue={leastValue}
                        onBlur={(e)=>handleInputChange(e.target.value)}>
                    </Input>
                    <span>人，若因旅行社原因未发团，旅行社将按双方合同约定的违约条款予以赔付</span>
                </Space>
                
            </Col>
        </Row>
    )
}

export default MinimumMemberPolicy;


