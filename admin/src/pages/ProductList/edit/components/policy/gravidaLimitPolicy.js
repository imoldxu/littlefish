import { Checkbox, Col, Radio, Row, Select, Space, Tag } from "antd";

/**
 * 孕妇限制策略
 * @param {} props 
 * @returns 
 */
const GravidaLimitPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected=1 } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = "出于安全考虑，本产品不接受孕妇预定，敬请谅解";
                onChange(payload);
            }else{                
                onChange(payload)
            }
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
                出于安全考虑，本产品不接受孕妇预定，敬请谅解
            </Col>
        </Row>
    )
}

export default GravidaLimitPolicy;


