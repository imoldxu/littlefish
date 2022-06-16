import { Checkbox, Col, Radio, Row, Select, Space, Tag } from "antd";

const HotelChildPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected=1 } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                switch(payload.selected){
                    case 1:
                        payload.text = "行程所列酒店住宿费用"
                        break;
                    case 2:
                        payload.text = "行程所列酒店住宿段，儿童不占床(与成人共用床铺)，但是若您为1大1小出行，为避免打扰您和同房客人的休息，则儿童必须占床，请您预订页面中选择1间房或补差选项"
                        break;
                    default:
                        payload.text = "行程所列酒店住宿费用"
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
                <Tag color="green">儿童</Tag>
                <Radio.Group disabled={!checked} 
                    defaultValue={selected}
                    onChange={(e)=>handleChange(e.target.value)}>
                    <Space direction="vertical">
                        <Radio value={1}>行程所列酒店住宿费用</Radio>
                        <Radio value={2}>行程所列酒店住宿段，儿童不占床(与成人共用床铺)，但是若您为1大1小出行，为避免打扰您和同房客人的休息，则儿童必须占床，请您预订页面中选择1间房或补差选项</Radio>
                    </Space>
                </Radio.Group>
                </Space>
            </Col>
        </Row>
    )
}

export default HotelChildPolicy;


