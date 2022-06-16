import { Checkbox, Col, Radio, Row, Select, Space, Tag } from "antd";

const HotelAdultPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected=1 } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                switch (payload.selected) {
                    case 1:
                        payload.text = "行程所列酒店住宿费用";
                        break;
                    case 2:
                        payload.text = "自选酒店住宿费用";
                        break;
                    case 3:
                        payload.text = "自选酒店住宿+行程所列酒店住宿费用";
                        break;
                    default:
                        payload.text = "行程所列酒店住宿费用";
                        break;
                }
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
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
                <Tag color="blue">成人</Tag>
                <Radio.Group disabled={!checked} 
                    defaultValue={selected}
                    onChange={(e)=>handleChange(e.target.value)}>
                    <Space direction="vertical">
                        <Radio value={1}>行程所列酒店住宿费用</Radio>
                        <Radio value={2}>自选酒店住宿费用</Radio>
                        <Radio value={3}>自选酒店住宿+行程所列酒店住宿费用</Radio>
                    </Space>
                </Radio.Group>
                </Space>
            </Col>
        </Row>
    )
}

export default HotelAdultPolicy;


