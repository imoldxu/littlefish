import { Checkbox, Col, Radio, Row, Select, Space, Tag } from "antd";

/**
 * 未成年人限制政策
 * @param {} props 
 * @returns 
 */
const JuvenilesLimitPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, ageValue } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = "出于安全考虑，"+payload.ageValue+"岁以下未成年人需要至少一名成年游客陪同";
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
    }

    const handleInputChange = (v)=>{
        let payload = { ...value }
        payload.ageValue = v;
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
                    <span>出于安全考虑，</span>
                    <Select disabled={!checked} 
                        style={{width:"120px"}}
                        defaultValue={ageValue}
                        options={[{label: 14, value:14},{label: 15, value:15},{label: 16, value:16},{label: 17, value:17},{label: 18, value:18}]}
                        onChange={(v)=>handleInputChange(v)}>
                    </Select>
                    <span>岁以下未成年人需要至少一名成年游客陪同</span>
                </Space>
                
            </Col>
        </Row>
    )
}

export default JuvenilesLimitPolicy;


