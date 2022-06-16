import { Checkbox, Col, Radio, Row, Select, Space, Tag } from "antd";

const TicketAdultPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected=1 } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = "行程中所列景点首道门票";
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
    }

    // const handleChange =(selected)=>{
    //     let payload = { ...value }
    //     payload.selected = selected;
    //     callOnChange(payload);
    // }

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
                <Tag color="blue">成人</Tag>行程中所列景点首道门票
            </Col>
        </Row>
    )
}

export default TicketAdultPolicy;


