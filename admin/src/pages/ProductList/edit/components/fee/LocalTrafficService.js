import { Checkbox, Col, Radio, Row, Select, Space, Tag } from "antd";

const LocalTrafficService = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected=1 } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = "安排当地专属用车(特殊路段因当地规定及安全考量会派遣小型车提供服务)";
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
                安排当地专属用车(特殊路段因当地规定及安全考量会派遣小型车提供服务)
            </Col>
        </Row>
    )
}

export default LocalTrafficService;


